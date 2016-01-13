package com.wormhole.vrtoolkit.cardboard;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;
import android.util.Log;

public class DistortionRenderer {
	private static final String TAG = "DistortionRenderer";
	private static final int TEXTURE_FORMAT = 6407;
	private static final int TEXTURE_TYPE = 5121;
	private int textureId;
	private int renderbufferId;
	private int framebufferId;
	private IntBuffer originalFramebufferId;
	private float resolutionScale;
	private boolean restoreGLStateEnabled;
	private boolean chromaticAberrationCorrectionEnabled;
	private boolean vignetteEnabled;
	private DistortionMesh leftEyeDistortionMesh;
	private DistortionMesh rightEyeDistortionMesh;
	private GLStateBackup gLStateBackup;
	private GLStateBackup gLStateBackupAberration;
	private HeadMountedDisplay hmd;
	private EyeViewport leftEyeViewport;
	private EyeViewport rightEyeViewport;
	private boolean fovsChanged;
	private boolean viewportsChanged;
	private boolean drawingFrame;
	private float xPxPerTanAngle;
	private float yPxPerTanAngle;
	private float metersPerTanAngle;
	private ProgramHolder programHolder;
	private ProgramHolderAberration programHolderAberration;
	static final String VERTEX_SHADER = "attribute vec2 aPosition;\nattribute float aVignette;\nattribute vec2 aBlueTextureCoord;\nvarying vec2 vTextureCoord;\nvarying float vVignette;\nuniform float uTextureCoordScale;\nvoid main() {\n  gl_Position = vec4(aPosition, 0.0, 1.0);\n  vTextureCoord = aBlueTextureCoord.xy * uTextureCoordScale;\n  vVignette = aVignette;\n}\n";
	static final String FRAGMENT_SHADER = "#ifdef GL_ES\n#ifdef GL_FRAGMENT_PRECISION_HIGH\nprecision highp float;\n#else\nprecision mediump float;\n#endif\n#endif\nvarying vec2 vTextureCoord;\nvarying float vVignette;\nuniform sampler2D uTextureSampler;\nvoid main() {\n  gl_FragColor = vVignette * texture2D(uTextureSampler, vTextureCoord);\n}\n";
	static final String VERTEX_SHADER_ABERRATION = "attribute vec2 aPosition;\nattribute float aVignette;\nattribute vec2 aRedTextureCoord;\nattribute vec2 aGreenTextureCoord;\nattribute vec2 aBlueTextureCoord;\nvarying vec2 vRedTextureCoord;\nvarying vec2 vBlueTextureCoord;\nvarying vec2 vGreenTextureCoord;\nvarying float vVignette;\nuniform float uTextureCoordScale;\nvoid main() {\n  gl_Position = vec4(aPosition, 0.0, 1.0);\n  vRedTextureCoord = aRedTextureCoord.xy * uTextureCoordScale;\n  vGreenTextureCoord = aGreenTextureCoord.xy * uTextureCoordScale;\n  vBlueTextureCoord = aBlueTextureCoord.xy * uTextureCoordScale;\n  vVignette = aVignette;\n}\n";
	static final String FRAGMENT_SHADER_ABERRATION = "#ifdef GL_ES\n#ifdef GL_FRAGMENT_PRECISION_HIGH\nprecision highp float;\n#else\nprecision mediump float;\n#endif\n#endif\nvarying vec2 vRedTextureCoord;\nvarying vec2 vBlueTextureCoord;\nvarying vec2 vGreenTextureCoord;\nvarying float vVignette;\nuniform sampler2D uTextureSampler;\nvoid main() {\n  gl_FragColor = vVignette * vec4(texture2D(uTextureSampler, vRedTextureCoord).r,\n          texture2D(uTextureSampler, vGreenTextureCoord).g,\n          texture2D(uTextureSampler, vBlueTextureCoord).b, 1.0);\n}\n";

	public DistortionRenderer() {
		this.textureId = -1;
		this.renderbufferId = -1;
		this.framebufferId = -1;
		this.originalFramebufferId = IntBuffer.allocate(1);

		this.resolutionScale = 1.0F;

		this.gLStateBackup = new GLStateBackup();
		this.gLStateBackupAberration = new GLStateBackup();
	}

	public void beforeDrawFrame() {
		this.drawingFrame = true;

		if (this.fovsChanged) {
			updateDistortionMesh(false);
			setupRenderTextureAndRenderbuffer();
			this.fovsChanged = false;
		}

		GLES20.glGetIntegerv(36006, this.originalFramebufferId);
		GLES20.glBindFramebuffer(36160, this.framebufferId);
	}

	public void afterDrawFrame() {
		GLES20.glBindFramebuffer(36160, this.originalFramebufferId.array()[0]);
		undistortTexture(this.textureId);
		this.drawingFrame = false;
	}

	public void undistortTexture(int textureId) {
		if (this.restoreGLStateEnabled) {
			if (this.chromaticAberrationCorrectionEnabled)
				this.gLStateBackupAberration.readFromGL();
			else {
				this.gLStateBackup.readFromGL();
			}
		}

		if (this.fovsChanged) {
			updateDistortionMesh(false);
			this.fovsChanged = false;
		}

		GLES20.glViewport(0, 0, this.hmd.getScreenParams().getWidth(), this.hmd
				.getScreenParams().getHeight());
		GLES20.glDisable(GLES20.GL_SCISSOR_TEST);
		GLES20.glDisable(GLES20.GL_CULL_FACE);

		GLES20.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
		GLES20.glClear(16640);

		if (this.chromaticAberrationCorrectionEnabled)
			GLES20.glUseProgram(this.programHolderAberration.program);
		else {
			GLES20.glUseProgram(this.programHolder.program);
		}

		GLES20.glEnable(GLES20.GL_SCISSOR_TEST);
		GLES20.glScissor(0, 0, this.hmd.getScreenParams().getWidth() / 2,
				this.hmd.getScreenParams().getHeight());
		renderDistortionMesh(this.leftEyeDistortionMesh, textureId);

		GLES20.glScissor(this.hmd.getScreenParams().getWidth() / 2, 0, this.hmd
				.getScreenParams().getWidth() / 2, this.hmd.getScreenParams()
				.getHeight());
		renderDistortionMesh(this.rightEyeDistortionMesh, textureId);

		if (this.restoreGLStateEnabled) {
			if (this.chromaticAberrationCorrectionEnabled)
				this.gLStateBackupAberration.writeToGL();
			else {
				this.gLStateBackup.writeToGL();
			}

		} else {
			GLES20.glActiveTexture(33984);

			GLES20.glBindBuffer(34962, 0);
			GLES20.glBindBuffer(34963, 0);

			GLES20.glDisable(3089);
		}
	}

	public void setResolutionScale(float scale) {
		this.resolutionScale = scale;
		this.viewportsChanged = true;
	}

	public void setRestoreGLStateEnabled(boolean enabled) {
		this.restoreGLStateEnabled = enabled;
	}

	public void setChromaticAberrationCorrectionEnabled(boolean enabled) {
		this.chromaticAberrationCorrectionEnabled = enabled;
	}

	public void setVignetteEnabled(boolean enabled) {
		this.vignetteEnabled = enabled;
		this.fovsChanged = true;
	}

	public void onFovChanged(HeadMountedDisplay hmd, FieldOfView leftFov,
			FieldOfView rightFov, float virtualEyeToScreenDistance) {
		if (this.drawingFrame) {
			throw new IllegalStateException(
					"Cannot change FOV while rendering a frame.");
		}

		this.hmd = new HeadMountedDisplay(hmd);

		this.leftEyeViewport = initViewportForEye(leftFov, 0.0F);
		this.rightEyeViewport = initViewportForEye(rightFov,
				this.leftEyeViewport.width);

		this.metersPerTanAngle = virtualEyeToScreenDistance;
		ScreenParams screen = hmd.getScreenParams();
		this.xPxPerTanAngle = (screen.getWidth() / (screen.getWidthMeters() / this.metersPerTanAngle));
		this.yPxPerTanAngle = (screen.getHeight() / (screen.getHeightMeters() / this.metersPerTanAngle));

		this.fovsChanged = true;
		this.viewportsChanged = true;
	}

	public boolean haveViewportsChanged() {
		return this.viewportsChanged;
	}

	public void updateViewports(Viewport leftViewport, Viewport rightViewport) {
		leftViewport.setViewport(
				Math.round(this.leftEyeViewport.x * this.xPxPerTanAngle
						* this.resolutionScale),
				Math.round(this.leftEyeViewport.y * this.yPxPerTanAngle
						* this.resolutionScale),
				Math.round(this.leftEyeViewport.width * this.xPxPerTanAngle
						* this.resolutionScale),
				Math.round(this.leftEyeViewport.height * this.yPxPerTanAngle
						* this.resolutionScale));

		rightViewport.setViewport(
				Math.round(this.rightEyeViewport.x * this.xPxPerTanAngle
						* this.resolutionScale),
				Math.round(this.rightEyeViewport.y * this.yPxPerTanAngle
						* this.resolutionScale),
				Math.round(this.rightEyeViewport.width * this.xPxPerTanAngle
						* this.resolutionScale),
				Math.round(this.rightEyeViewport.height * this.yPxPerTanAngle
						* this.resolutionScale));

		this.viewportsChanged = false;
	}

	private void updateDistortionMesh(boolean flip180) {
		ScreenParams screen = this.hmd.getScreenParams();
		CardboardDeviceParams cdp = this.hmd.getCardboardDeviceParams();

		if (this.programHolder == null) {
			this.programHolder = createProgramHolder();
		}

		if (this.programHolderAberration == null) {
			this.programHolderAberration = ((ProgramHolderAberration) createProgramHolder(true));
		}

		float textureWidthTanAngle = this.leftEyeViewport.width
				+ this.rightEyeViewport.width;
		float textureHeightTanAngle = Math.max(this.leftEyeViewport.height,
				this.rightEyeViewport.height);

		float xEyeOffsetTanAngleScreen = (screen.getWidthMeters() / 2.0F - cdp
				.getInterLensDistance() / 2.0F) / this.metersPerTanAngle;

		float yEyeOffsetTanAngleScreen = cdp.getYEyeOffsetMeters(screen)
				/ this.metersPerTanAngle;

		this.leftEyeDistortionMesh = createDistortionMesh(this.leftEyeViewport,
				textureWidthTanAngle, textureHeightTanAngle,
				xEyeOffsetTanAngleScreen, yEyeOffsetTanAngleScreen, flip180);

		xEyeOffsetTanAngleScreen = screen.getWidthMeters()
				/ this.metersPerTanAngle - xEyeOffsetTanAngleScreen;
		this.rightEyeDistortionMesh = createDistortionMesh(
				this.rightEyeViewport, textureWidthTanAngle,
				textureHeightTanAngle, xEyeOffsetTanAngleScreen,
				yEyeOffsetTanAngleScreen, flip180);
	}

	private EyeViewport initViewportForEye(FieldOfView fov, float xOffset) {
		float left = (float) Math.tan(Math.toRadians(fov.getLeft()));
		float right = (float) Math.tan(Math.toRadians(fov.getRight()));
		float bottom = (float) Math.tan(Math.toRadians(fov.getBottom()));
		float top = (float) Math.tan(Math.toRadians(fov.getTop()));

		EyeViewport vp = new EyeViewport();
		vp.x = xOffset;
		vp.y = 0.0F;
		vp.width = (left + right);
		vp.height = (bottom + top);
		vp.eyeX = (left + xOffset);
		vp.eyeY = bottom;

		return vp;
	}

	private DistortionMesh createDistortionMesh(EyeViewport eyeViewport,
			float textureWidthTanAngle, float textureHeightTanAngle,
			float xEyeOffsetTanAngleScreen, float yEyeOffsetTanAngleScreen,
			boolean flip180) {
		return new DistortionMesh(this.hmd.getCardboardDeviceParams()
				.getDistortion(), this.hmd.getCardboardDeviceParams()
				.getDistortion(), this.hmd.getCardboardDeviceParams()
				.getDistortion(), this.hmd.getScreenParams().getWidthMeters()
				/ this.metersPerTanAngle, this.hmd.getScreenParams()
				.getHeightMeters() / this.metersPerTanAngle,
				xEyeOffsetTanAngleScreen, yEyeOffsetTanAngleScreen,
				textureWidthTanAngle, textureHeightTanAngle, eyeViewport.eyeX,
				eyeViewport.eyeY, eyeViewport.x, eyeViewport.y,
				eyeViewport.width, eyeViewport.height, flip180);
	}

	private void renderDistortionMesh(DistortionMesh mesh, int textureId) {
		ProgramHolder holder;
		if (this.chromaticAberrationCorrectionEnabled)
			holder = this.programHolderAberration;
		else {
			holder = this.programHolder;
		}

		GLES20.glBindBuffer(34962, mesh.arrayBufferId);
		GLES20.glVertexAttribPointer(holder.aPosition, 2, 5126, false, 36, 0);

		GLES20.glEnableVertexAttribArray(holder.aPosition);

		GLES20.glVertexAttribPointer(holder.aVignette, 1, 5126, false, 36, 8);

		GLES20.glEnableVertexAttribArray(holder.aVignette);

		GLES20.glVertexAttribPointer(holder.aBlueTextureCoord, 2, 5126, false,
				36, 28);

		GLES20.glEnableVertexAttribArray(holder.aBlueTextureCoord);

		if (this.chromaticAberrationCorrectionEnabled) {
			GLES20.glVertexAttribPointer(
					((ProgramHolderAberration) holder).aRedTextureCoord, 2,
					5126, false, 36, 12);

			GLES20.glEnableVertexAttribArray(((ProgramHolderAberration) holder).aRedTextureCoord);

			GLES20.glVertexAttribPointer(
					((ProgramHolderAberration) holder).aGreenTextureCoord, 2,
					5126, false, 36, 20);

			GLES20.glEnableVertexAttribArray(((ProgramHolderAberration) holder).aGreenTextureCoord);
		}

		GLES20.glActiveTexture(33984);
		GLES20.glBindTexture(3553, textureId);
		GLES20.glUniform1i(this.programHolder.uTextureSampler, 0);
		GLES20.glUniform1f(this.programHolder.uTextureCoordScale,
				this.resolutionScale);

		GLES20.glBindBuffer(34963, mesh.elementBufferId);
		GLES20.glDrawElements(5, mesh.nIndices, 5123, 0);
	}

	private int createTexture(int width, int height, int textureFormat,
			int textureType) {
		int[] textureIds = new int[1];
		GLES20.glGenTextures(1, textureIds, 0);

		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIds[0]);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);

		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);

		GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, textureFormat, width, height, 0,
				textureFormat, textureType, null);

		return textureIds[0];
	}

	private int setupRenderTextureAndRenderbuffer() {
		float textureWidthTanAngle = this.leftEyeViewport.width
				+ this.rightEyeViewport.width;
		float textureHeightTanAngle = Math.max(this.leftEyeViewport.height,
				this.rightEyeViewport.height);

		int[] maxTextureSize = new int[1];
		GLES20.glGetIntegerv(3379, maxTextureSize, 0);

		int width = Math.min(
				Math.round(textureWidthTanAngle * this.xPxPerTanAngle),
				maxTextureSize[0]);
		int height = Math.min(
				Math.round(textureHeightTanAngle * this.yPxPerTanAngle),
				maxTextureSize[0]);

		return setupRenderTextureAndRenderbuffer(width, height);
	}

	private int setupRenderTextureAndRenderbuffer(int width, int height) {
    if (this.textureId != -1) {
      GLES20.glDeleteTextures(1, new int[] { this.textureId }, 0);
    }
    if (this.renderbufferId != -1) {
      GLES20.glDeleteRenderbuffers(1, new int[] { this.renderbufferId }, 0);
    }
    if (this.framebufferId != -1) {
      GLES20.glDeleteFramebuffers(1, new int[] { this.framebufferId }, 0);
    }
    clearGlError();

    this.textureId = createTexture(width, height, 6407, 5121);

    checkGlError("setupRenderTextureAndRenderbuffer: create texture");

    int[] renderbufferIds = new int[1];
    GLES20.glGenRenderbuffers(1, renderbufferIds, 0);
    GLES20.glBindRenderbuffer(36161, renderbufferIds[0]);
    GLES20.glRenderbufferStorage(36161, 33189, width, height);

    this.renderbufferId = renderbufferIds[0];
    checkGlError("setupRenderTextureAndRenderbuffer: create renderbuffer");

    int[] framebufferIds = new int[1];
    GLES20.glGenFramebuffers(1, framebufferIds, 0);
    GLES20.glBindFramebuffer(36160, framebufferIds[0]);
    this.framebufferId = framebufferIds[0];

    GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.textureId, 0);

    GLES20.glFramebufferRenderbuffer(36160, 36096, 36161, renderbufferIds[0]);

    int status = GLES20.glCheckFramebufferStatus(36160);

    if (status != 36053)
    {
      RuntimeException tmp208_205 = new java/lang/RuntimeException; tmp208_205;
      new String(tmp208_205);
    }
  }

	private int loadShader(int shaderType, String source) {
		int shader = GLES20.glCreateShader(shaderType);
		if (shader != 0) {
			GLES20.glShaderSource(shader, source);
			GLES20.glCompileShader(shader);
			int[] compiled = new int[1];
			GLES20.glGetShaderiv(shader, 35713, compiled, 0);
			if (compiled[0] == 0) {
				Log.e("DistortionRenderer", 37 + "Could not compile shader "
						+ shaderType + ":");
				Log.e("DistortionRenderer", GLES20.glGetShaderInfoLog(shader));
				GLES20.glDeleteShader(shader);
				shader = 0;
			}
		}
		return shader;
	}

	private int createProgram(String vertexSource, String fragmentSource) {
		int vertexShader = loadShader(35633, vertexSource);
		if (vertexShader == 0) {
			return 0;
		}
		int pixelShader = loadShader(35632, fragmentSource);
		if (pixelShader == 0) {
			return 0;
		}

		int program = GLES20.glCreateProgram();
		if (program != 0) {
			clearGlError();
			GLES20.glAttachShader(program, vertexShader);
			checkGlError("glAttachShader");
			GLES20.glAttachShader(program, pixelShader);
			checkGlError("glAttachShader");
			GLES20.glLinkProgram(program);
			int[] linkStatus = new int[1];
			GLES20.glGetProgramiv(program, 35714, linkStatus, 0);
			if (linkStatus[0] != 1) {
				Log.e("DistortionRenderer", "Could not link program: ");
				Log.e("DistortionRenderer", GLES20.glGetProgramInfoLog(program));
				GLES20.glDeleteProgram(program);
				program = 0;
			}
		}
		return program;
	}

	private ProgramHolder createProgramHolder() {
		return createProgramHolder(false);
	}

	private ProgramHolder createProgramHolder(boolean aberrationCorrected) {
		GLStateBackup state;
		ProgramHolder holder;
		if (aberrationCorrected) {
			holder = new ProgramHolderAberration();

			holder.program = createProgram(
					"attribute vec2 aPosition;\nattribute float aVignette;\nattribute vec2 aRedTextureCoord;\nattribute vec2 aGreenTextureCoord;\nattribute vec2 aBlueTextureCoord;\nvarying vec2 vRedTextureCoord;\nvarying vec2 vBlueTextureCoord;\nvarying vec2 vGreenTextureCoord;\nvarying float vVignette;\nuniform float uTextureCoordScale;\nvoid main() {\n  gl_Position = vec4(aPosition, 0.0, 1.0);\n  vRedTextureCoord = aRedTextureCoord.xy * uTextureCoordScale;\n  vGreenTextureCoord = aGreenTextureCoord.xy * uTextureCoordScale;\n  vBlueTextureCoord = aBlueTextureCoord.xy * uTextureCoordScale;\n  vVignette = aVignette;\n}\n",
					"#ifdef GL_ES\n#ifdef GL_FRAGMENT_PRECISION_HIGH\nprecision highp float;\n#else\nprecision mediump float;\n#endif\n#endif\nvarying vec2 vRedTextureCoord;\nvarying vec2 vBlueTextureCoord;\nvarying vec2 vGreenTextureCoord;\nvarying float vVignette;\nuniform sampler2D uTextureSampler;\nvoid main() {\n  gl_FragColor = vVignette * vec4(texture2D(uTextureSampler, vRedTextureCoord).r,\n          texture2D(uTextureSampler, vGreenTextureCoord).g,\n          texture2D(uTextureSampler, vBlueTextureCoord).b, 1.0);\n}\n");
			if (holder.program == 0) {
				throw new RuntimeException(
						"Could not create aberration-corrected program");
			}

			state = this.gLStateBackupAberration;
		} else {
			holder = new ProgramHolder();

			holder.program = createProgram(
					"attribute vec2 aPosition;\nattribute float aVignette;\nattribute vec2 aBlueTextureCoord;\nvarying vec2 vTextureCoord;\nvarying float vVignette;\nuniform float uTextureCoordScale;\nvoid main() {\n  gl_Position = vec4(aPosition, 0.0, 1.0);\n  vTextureCoord = aBlueTextureCoord.xy * uTextureCoordScale;\n  vVignette = aVignette;\n}\n",
					"#ifdef GL_ES\n#ifdef GL_FRAGMENT_PRECISION_HIGH\nprecision highp float;\n#else\nprecision mediump float;\n#endif\n#endif\nvarying vec2 vTextureCoord;\nvarying float vVignette;\nuniform sampler2D uTextureSampler;\nvoid main() {\n  gl_FragColor = vVignette * texture2D(uTextureSampler, vTextureCoord);\n}\n");
			if (holder.program == 0) {
				throw new RuntimeException("Could not create program");
			}

			state = this.gLStateBackup;
		}
		clearGlError();

		holder.aPosition = GLES20.glGetAttribLocation(holder.program,
				"aPosition");
		checkGlError("glGetAttribLocation aPosition");
		if (holder.aPosition == -1) {
			throw new RuntimeException(
					"Could not get attrib location for aPosition");
		}
		state.addTrackedVertexAttribute(holder.aPosition);
		holder.aVignette = GLES20.glGetAttribLocation(holder.program,
				"aVignette");
		checkGlError("glGetAttribLocation aVignette");
		if (holder.aVignette == -1) {
			throw new RuntimeException(
					"Could not get attrib location for aVignette");
		}
		state.addTrackedVertexAttribute(holder.aVignette);

		if (aberrationCorrected) {
			((ProgramHolderAberration) holder).aRedTextureCoord = GLES20
					.glGetAttribLocation(holder.program, "aRedTextureCoord");

			checkGlError("glGetAttribLocation aRedTextureCoord");
			if (((ProgramHolderAberration) holder).aRedTextureCoord == -1) {
				throw new RuntimeException(
						"Could not get attrib location for aRedTextureCoord");
			}
			((ProgramHolderAberration) holder).aGreenTextureCoord = GLES20
					.glGetAttribLocation(holder.program, "aGreenTextureCoord");

			checkGlError("glGetAttribLocation aGreenTextureCoord");
			if (((ProgramHolderAberration) holder).aGreenTextureCoord == -1) {
				throw new RuntimeException(
						"Could not get attrib location for aGreenTextureCoord");
			}
			state.addTrackedVertexAttribute(((ProgramHolderAberration) holder).aRedTextureCoord);
			state.addTrackedVertexAttribute(((ProgramHolderAberration) holder).aGreenTextureCoord);
		}

		holder.aBlueTextureCoord = GLES20.glGetAttribLocation(holder.program,
				"aBlueTextureCoord");

		checkGlError("glGetAttribLocation aBlueTextureCoord");
		if (holder.aBlueTextureCoord == -1) {
			throw new RuntimeException(
					"Could not get attrib location for aBlueTextureCoord");
		}
		state.addTrackedVertexAttribute(holder.aBlueTextureCoord);
		holder.uTextureCoordScale = GLES20.glGetUniformLocation(holder.program,
				"uTextureCoordScale");

		checkGlError("glGetUniformLocation uTextureCoordScale");
		if (holder.uTextureCoordScale == -1) {
			throw new RuntimeException(
					"Could not get attrib location for uTextureCoordScale");
		}
		holder.uTextureSampler = GLES20.glGetUniformLocation(holder.program,
				"uTextureSampler");

		checkGlError("glGetUniformLocation uTextureSampler");
		if (holder.uTextureSampler == -1) {
			throw new RuntimeException(
					"Could not get attrib location for uTextureSampler");
		}

		return holder;
	}

	private void clearGlError() {
		while (GLES20.glGetError() != 0)
			;
	}

	private void checkGlError(String op) {
		int error;
		if ((error = GLES20.glGetError()) != 0) {
			Log.e("DistortionRenderer", 21 + String.valueOf(op).length() + op
					+ ": glError " + error);
			throw new RuntimeException(21 + String.valueOf(op).length() + op
					+ ": glError " + error);
		}
	}

	private static float clamp(float val, float min, float max) {
		return Math.max(min, Math.min(max, val));
	}

	private class DistortionMesh {
		public static final int BYTES_PER_FLOAT = 4;
		public static final int BYTES_PER_SHORT = 2;
		public static final int COMPONENTS_PER_VERT = 9;
		public static final int DATA_STRIDE_BYTES = 36;
		public static final int DATA_POS_OFFSET = 0;
		public static final int DATA_POS_COMPONENTS = 2;
		public static final int DATA_VIGNETTE_OFFSET = 2;
		public static final int DATA_VIGNETTE_COMPONENTS = 1;
		public static final int DATA_RUV_OFFSET = 3;
		public static final int DATA_GUV_OFFSET = 5;
		public static final int DATA_BUV_OFFSET = 7;
		public static final int DATA_UV_COMPONENTS = 2;
		public static final int ROWS = 40;
		public static final int COLS = 40;
		public static final float VIGNETTE_SIZE_TAN_ANGLE = 0.05F;
		public int nIndices;
		public int arrayBufferId = -1;
		public int elementBufferId = -1;

		public DistortionMesh(Distortion distortionRed,
				Distortion distortionGreen, Distortion distortionBlue,
				float screenWidth, float screenHeight, float xEyeOffsetScreen,
				float yEyeOffsetScreen, float textureWidth,
				float textureHeight, float xEyeOffsetTexture,
				float yEyeOffsetTexture, float viewportXTexture,
				float viewportYTexture, float viewportWidthTexture,
				float viewportHeightTexture, boolean flip180) {
			float[] vertexData = new float[14400];
			short vertexOffset = 0;

			for (int row = 0; row < 40; row++) {
				for (int col = 0; col < 40; col++) {
					float uTextureBlue = col / 39.0F
							* (viewportWidthTexture / textureWidth)
							+ viewportXTexture / textureWidth;

					float vTextureBlue = row / 39.0F
							* (viewportHeightTexture / textureHeight)
							+ viewportYTexture / textureHeight;

					float xTexture = uTextureBlue * textureWidth
							- xEyeOffsetTexture;
					float yTexture = vTextureBlue * textureHeight
							- yEyeOffsetTexture;
					float rTexture = (float) Math.sqrt(xTexture * xTexture
							+ yTexture * yTexture);

					float textureToScreenBlue = rTexture > 0.0F ? distortionBlue
							.distortInverse(rTexture) / rTexture
							: 1.0F;

					float xScreen = xTexture * textureToScreenBlue;
					float yScreen = yTexture * textureToScreenBlue;

					float uScreen = (xScreen + xEyeOffsetScreen) / screenWidth;
					float vScreen = (yScreen + yEyeOffsetScreen) / screenHeight;

					float rScreen = rTexture * textureToScreenBlue;

					float screenToTextureGreen = rScreen > 0.0F ? distortionGreen
							.distortionFactor(rScreen) : 1.0F;

					float uTextureGreen = (xScreen * screenToTextureGreen + xEyeOffsetTexture)
							/ textureWidth;

					float vTextureGreen = (yScreen * screenToTextureGreen + yEyeOffsetTexture)
							/ textureHeight;

					float screenToTextureRed = rScreen > 0.0F ? distortionRed
							.distortionFactor(rScreen) : 1.0F;

					float uTextureRed = (xScreen * screenToTextureRed + xEyeOffsetTexture)
							/ textureWidth;

					float vTextureRed = (yScreen * screenToTextureRed + yEyeOffsetTexture)
							/ textureHeight;

					float vignetteSizeTexture = 0.05F / textureToScreenBlue;
					float dxTexture = xTexture
							+ xEyeOffsetTexture
							- DistortionRenderer.clamp(xTexture
									+ xEyeOffsetTexture, viewportXTexture
									+ vignetteSizeTexture, viewportXTexture
									+ viewportWidthTexture
									- vignetteSizeTexture);

					float dyTexture = yTexture
							+ yEyeOffsetTexture
							- DistortionRenderer.clamp(yTexture
									+ yEyeOffsetTexture, viewportYTexture
									+ vignetteSizeTexture, viewportYTexture
									+ viewportHeightTexture
									- vignetteSizeTexture);

					float drTexture = (float) Math.sqrt(dxTexture * dxTexture
							+ dyTexture * dyTexture);
					float vignette;
					if (DistortionRenderer.this.vignetteEnabled)
						vignette = 1.0F - DistortionRenderer.clamp(drTexture
								/ vignetteSizeTexture, 0.0F, 1.0F);
					else {
						vignette = 1.0F;
					}

					if (flip180) {
						uTextureBlue = 1.0F - uTextureBlue;
						uTextureRed = 1.0F - uTextureRed;
						uTextureGreen = 1.0F - uTextureGreen;
						vTextureBlue = 1.0F - vTextureBlue;
						vTextureRed = 1.0F - vTextureRed;
						vTextureGreen = 1.0F - vTextureGreen;
					}

					vertexData[(vertexOffset + 0)] = (2.0F * uScreen - 1.0F);
					vertexData[(vertexOffset + 1)] = (2.0F * vScreen - 1.0F);
					vertexData[(vertexOffset + 2)] = vignette;
					vertexData[(vertexOffset + 3)] = uTextureRed;
					vertexData[(vertexOffset + 4)] = vTextureRed;
					vertexData[(vertexOffset + 5)] = uTextureGreen;
					vertexData[(vertexOffset + 6)] = vTextureGreen;
					vertexData[(vertexOffset + 7)] = uTextureBlue;
					vertexData[(vertexOffset + 8)] = vTextureBlue;

					vertexOffset = (short) (vertexOffset + 9);
				}

			}

			this.nIndices = 3158;
			short[] indexData = new short[this.nIndices];
			short indexOffset = 0;
			vertexOffset = 0;
			for (int row = 0; row < 39; row++) {
				if (row > 0) {
					indexData[indexOffset] = indexData[(indexOffset - 1)];
					indexOffset = (short) (indexOffset + 1);
				}
				for (int col = 0; col < 40; col++) {
					if (col > 0) {
						if (row % 2 == 0) {
							vertexOffset = (short) (vertexOffset + 1);
						} else {
							vertexOffset = (short) (vertexOffset - 1);
						}
					}
					indexOffset = (short) (indexOffset + 1);
					indexData[indexOffset] = vertexOffset;
					indexOffset = (short) (indexOffset + 1);
					indexData[indexOffset] = ((short) (vertexOffset + 40));
				}
				vertexOffset = (short) (vertexOffset + 40);
			}

			FloatBuffer vertexBuffer = ByteBuffer
					.allocateDirect(vertexData.length * 4)
					.order(ByteOrder.nativeOrder()).asFloatBuffer();
			vertexBuffer.put(vertexData).position(0);

			ShortBuffer indexBuffer = ByteBuffer
					.allocateDirect(indexData.length * 2)
					.order(ByteOrder.nativeOrder()).asShortBuffer();
			indexBuffer.put(indexData).position(0);

			int[] bufferIds = new int[2];
			GLES20.glGenBuffers(2, bufferIds, 0);
			this.arrayBufferId = bufferIds[0];
			this.elementBufferId = bufferIds[1];

			GLES20.glBindBuffer(34962, this.arrayBufferId);
			GLES20.glBufferData(34962, vertexData.length * 4, vertexBuffer,
					35044);

			GLES20.glBindBuffer(34963, this.elementBufferId);
			GLES20.glBufferData(34963, indexData.length * 2, indexBuffer, 35044);

			GLES20.glBindBuffer(34962, 0);
			GLES20.glBindBuffer(34963, 0);
		}
	}

	private class EyeViewport {
		public float x;
		public float y;
		public float width;
		public float height;
		public float eyeX;
		public float eyeY;

		private EyeViewport() {
		}

		public String toString() {
			float f = this.x;
			f = this.y;
			f = this.width;
			f = this.height;
			f = this.eyeX;
			f = this.eyeY;

			return "{\n"
					+ new StringBuilder(22).append("  x: ").append(f)
							.append(",\n").toString()
					+ new StringBuilder(22).append("  y: ").append(f)
							.append(",\n").toString()
					+ new StringBuilder(26).append("  width: ").append(f)
							.append(",\n").toString()
					+ new StringBuilder(27).append("  height: ").append(f)
							.append(",\n").toString()
					+ new StringBuilder(25).append("  eyeX: ").append(f)
							.append(",\n").toString()
					+ new StringBuilder(25).append("  eyeY: ").append(f)
							.append(",\n").toString() + "}";
		}
	}

	private class ProgramHolderAberration extends
			DistortionRenderer.ProgramHolder {
		public int aRedTextureCoord;
		public int aGreenTextureCoord;

		private ProgramHolderAberration() {
			super();
		}
	}

	private class ProgramHolder {
		public int program;
		public int aPosition;
		public int aVignette;
		public int aBlueTextureCoord;
		public int uTextureCoordScale;
		public int uTextureSampler;

		private ProgramHolder() {
		}
	}
}