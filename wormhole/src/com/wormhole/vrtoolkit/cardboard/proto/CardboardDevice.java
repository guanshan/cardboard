package com.wormhole.vrtoolkit.cardboard.proto;

import java.io.IOException;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;

public abstract interface CardboardDevice {
	public static final class DeviceParams extends MessageNano implements
			Cloneable {
		private static volatile DeviceParams[] _emptyArray;
		private int bitField0_;
		private String vendor_;
		private String model_;
		private float screenToLensDistance_;
		private float interLensDistance_;
		public float[] leftEyeFieldOfViewAngles;
		private int verticalAlignment_;
		private float trayToLensDistance_;
		public float[] distortionCoefficients;
		private boolean hasMagnet_;
		private int primaryButton_;

		public static DeviceParams[] emptyArray() {
			if (_emptyArray == null) {
				synchronized (InternalNano.LAZY_INIT_LOCK) {
					if (_emptyArray == null) {
						_emptyArray = new DeviceParams[0];
					}
				}
			}
			return _emptyArray;
		}

		public String getVendor() {
			return this.vendor_;
		}

		public DeviceParams setVendor(String value) {
			if (value == null) {
				throw new NullPointerException();
			}
			this.vendor_ = value;
			this.bitField0_ |= 1;
			return this;
		}

		public boolean hasVendor() {
			return (this.bitField0_ & 0x1) != 0;
		}

		public DeviceParams clearVendor() {
			this.vendor_ = "";
			this.bitField0_ &= -2;
			return this;
		}

		public String getModel() {
			return this.model_;
		}

		public DeviceParams setModel(String value) {
			if (value == null) {
				throw new NullPointerException();
			}
			this.model_ = value;
			this.bitField0_ |= 2;
			return this;
		}

		public boolean hasModel() {
			return (this.bitField0_ & 0x2) != 0;
		}

		public DeviceParams clearModel() {
			this.model_ = "";
			this.bitField0_ &= -3;
			return this;
		}

		public float getScreenToLensDistance() {
			return this.screenToLensDistance_;
		}

		public DeviceParams setScreenToLensDistance(float value) {
			this.screenToLensDistance_ = value;
			this.bitField0_ |= 4;
			return this;
		}

		public boolean hasScreenToLensDistance() {
			return (this.bitField0_ & 0x4) != 0;
		}

		public DeviceParams clearScreenToLensDistance() {
			this.screenToLensDistance_ = 0.0F;
			this.bitField0_ &= -5;
			return this;
		}

		public float getInterLensDistance() {
			return this.interLensDistance_;
		}

		public DeviceParams setInterLensDistance(float value) {
			this.interLensDistance_ = value;
			this.bitField0_ |= 8;
			return this;
		}

		public boolean hasInterLensDistance() {
			return (this.bitField0_ & 0x8) != 0;
		}

		public DeviceParams clearInterLensDistance() {
			this.interLensDistance_ = 0.0F;
			this.bitField0_ &= -9;
			return this;
		}

		public int getVerticalAlignment() {
			return this.verticalAlignment_;
		}

		public DeviceParams setVerticalAlignment(int value) {
			this.verticalAlignment_ = value;
			this.bitField0_ |= 16;
			return this;
		}

		public boolean hasVerticalAlignment() {
			return (this.bitField0_ & 0x10) != 0;
		}

		public DeviceParams clearVerticalAlignment() {
			this.verticalAlignment_ = 0;
			this.bitField0_ &= -17;
			return this;
		}

		public float getTrayToLensDistance() {
			return this.trayToLensDistance_;
		}

		public DeviceParams setTrayToLensDistance(float value) {
			this.trayToLensDistance_ = value;
			this.bitField0_ |= 32;
			return this;
		}

		public boolean hasTrayToLensDistance() {
			return (this.bitField0_ & 0x20) != 0;
		}

		public DeviceParams clearTrayToLensDistance() {
			this.trayToLensDistance_ = 0.0F;
			this.bitField0_ &= -33;
			return this;
		}

		public boolean getHasMagnet() {
			return this.hasMagnet_;
		}

		public DeviceParams setHasMagnet(boolean value) {
			this.hasMagnet_ = value;
			this.bitField0_ |= 64;
			return this;
		}

		public boolean hasHasMagnet() {
			return (this.bitField0_ & 0x40) != 0;
		}

		public DeviceParams clearHasMagnet() {
			this.hasMagnet_ = false;
			this.bitField0_ &= -65;
			return this;
		}

		public int getPrimaryButton() {
			return this.primaryButton_;
		}

		public DeviceParams setPrimaryButton(int value) {
			this.primaryButton_ = value;
			this.bitField0_ |= 128;
			return this;
		}

		public boolean hasPrimaryButton() {
			return (this.bitField0_ & 0x80) != 0;
		}

		public DeviceParams clearPrimaryButton() {
			this.primaryButton_ = 1;
			this.bitField0_ &= -129;
			return this;
		}

		public DeviceParams() {
			clear();
		}

		public DeviceParams clear() {
			this.bitField0_ = 0;
			this.vendor_ = "";
			this.model_ = "";
			this.screenToLensDistance_ = 0.0F;
			this.interLensDistance_ = 0.0F;
			this.leftEyeFieldOfViewAngles = WireFormatNano.EMPTY_FLOAT_ARRAY;
			this.verticalAlignment_ = 0;
			this.trayToLensDistance_ = 0.0F;
			this.distortionCoefficients = WireFormatNano.EMPTY_FLOAT_ARRAY;
			this.hasMagnet_ = false;
			this.primaryButton_ = 1;
			this.cachedSize = -1;
			return this;
		}

		public DeviceParams clone() {
			DeviceParams cloned;
			try {
				cloned = (DeviceParams) super.clone();
			} catch (CloneNotSupportedException e) {
				throw new AssertionError(e);
			}
			if ((this.leftEyeFieldOfViewAngles != null)
					&& (this.leftEyeFieldOfViewAngles.length > 0)) {
				cloned.leftEyeFieldOfViewAngles = ((float[]) this.leftEyeFieldOfViewAngles
						.clone());
			}
			if ((this.distortionCoefficients != null)
					&& (this.distortionCoefficients.length > 0)) {
				cloned.distortionCoefficients = ((float[]) this.distortionCoefficients
						.clone());
			}
			return cloned;
		}

		public void writeTo(CodedOutputByteBufferNano output)
				throws IOException {
			if ((this.bitField0_ & 0x1) != 0) {
				output.writeString(1, this.vendor_);
			}
			if ((this.bitField0_ & 0x2) != 0) {
				output.writeString(2, this.model_);
			}
			if ((this.bitField0_ & 0x4) != 0) {
				output.writeFloat(3, this.screenToLensDistance_);
			}
			if ((this.bitField0_ & 0x8) != 0) {
				output.writeFloat(4, this.interLensDistance_);
			}
			if ((this.leftEyeFieldOfViewAngles != null)
					&& (this.leftEyeFieldOfViewAngles.length > 0)) {
				int dataSize = 4 * this.leftEyeFieldOfViewAngles.length;
				output.writeRawVarint32(42);
				output.writeRawVarint32(dataSize);
				for (int i = 0; i < this.leftEyeFieldOfViewAngles.length; i++) {
					output.writeFloatNoTag(this.leftEyeFieldOfViewAngles[i]);
				}
			}
			if ((this.bitField0_ & 0x20) != 0) {
				output.writeFloat(6, this.trayToLensDistance_);
			}
			if ((this.distortionCoefficients != null)
					&& (this.distortionCoefficients.length > 0)) {
				int dataSize = 4 * this.distortionCoefficients.length;
				output.writeRawVarint32(58);
				output.writeRawVarint32(dataSize);
				for (int i = 0; i < this.distortionCoefficients.length; i++) {
					output.writeFloatNoTag(this.distortionCoefficients[i]);
				}
			}
			if ((this.bitField0_ & 0x40) != 0) {
				output.writeBool(10, this.hasMagnet_);
			}
			if ((this.bitField0_ & 0x10) != 0) {
				output.writeInt32(11, this.verticalAlignment_);
			}
			if ((this.bitField0_ & 0x80) != 0) {
				output.writeInt32(12, this.primaryButton_);
			}
			super.writeTo(output);
		}

		protected int computeSerializedSize() {
			int size = super.computeSerializedSize();
			if ((this.bitField0_ & 0x1) != 0) {
				size = size
						+ CodedOutputByteBufferNano.computeStringSize(1,
								this.vendor_);
			}

			if ((this.bitField0_ & 0x2) != 0) {
				size = size
						+ CodedOutputByteBufferNano.computeStringSize(2,
								this.model_);
			}

			if ((this.bitField0_ & 0x4) != 0) {
				size = size
						+ CodedOutputByteBufferNano.computeFloatSize(3,
								this.screenToLensDistance_);
			}

			if ((this.bitField0_ & 0x8) != 0) {
				size = size
						+ CodedOutputByteBufferNano.computeFloatSize(4,
								this.interLensDistance_);
			}

			if ((this.leftEyeFieldOfViewAngles != null)
					&& (this.leftEyeFieldOfViewAngles.length > 0)) {
				int dataSize = 4 * this.leftEyeFieldOfViewAngles.length;
				size += dataSize;
				size++;

				size = size
						+ CodedOutputByteBufferNano
								.computeRawVarint32Size(dataSize);
			}

			if ((this.bitField0_ & 0x20) != 0) {
				size = size
						+ CodedOutputByteBufferNano.computeFloatSize(6,
								this.trayToLensDistance_);
			}

			if ((this.distortionCoefficients != null)
					&& (this.distortionCoefficients.length > 0)) {
				int dataSize = 4 * this.distortionCoefficients.length;
				size += dataSize;
				size++;

				size = size
						+ CodedOutputByteBufferNano
								.computeRawVarint32Size(dataSize);
			}

			if ((this.bitField0_ & 0x40) != 0) {
				size = size
						+ CodedOutputByteBufferNano.computeBoolSize(10,
								this.hasMagnet_);
			}

			if ((this.bitField0_ & 0x10) != 0) {
				size = size
						+ CodedOutputByteBufferNano.computeInt32Size(11,
								this.verticalAlignment_);
			}

			if ((this.bitField0_ & 0x80) != 0) {
				size = size
						+ CodedOutputByteBufferNano.computeInt32Size(12,
								this.primaryButton_);
			}

			return size;
		}

		public DeviceParams mergeFrom(CodedInputByteBufferNano input)
				throws IOException {
			int arrayLength;
			float[] newArray;
			int i = 0;
			int value = 0;
			int length;
			int limit;
			while (true) {
				int tag = input.readTag();
				switch (tag) {
				case 0:
					return this;
				default:
					if (!WireFormatNano.parseUnknownField(input, tag)) {
						return this;
					}

					break;
				case 10:
					this.vendor_ = input.readString();
					this.bitField0_ |= 1;
					break;
				case 18:
					this.model_ = input.readString();
					this.bitField0_ |= 2;
					break;
				case 29:
					this.screenToLensDistance_ = input.readFloat();
					this.bitField0_ |= 4;
					break;
				case 37:
					this.interLensDistance_ = input.readFloat();
					this.bitField0_ |= 8;
					break;
				case 45:
					arrayLength = WireFormatNano
							.getRepeatedFieldArrayLength(input, 45);

					i = this.leftEyeFieldOfViewAngles == null ? 0
							: this.leftEyeFieldOfViewAngles.length;
					newArray = new float[i + arrayLength];
					if (i != 0) {
						System.arraycopy(this.leftEyeFieldOfViewAngles, 0,
								newArray, 0, i);
					}
					for (; i < newArray.length - 1; i++) {
						newArray[i] = input.readFloat();
						input.readTag();
					}

					newArray[i] = input.readFloat();
					this.leftEyeFieldOfViewAngles = newArray;
					break;
				case 42:
					length = input.readRawVarint32();
					limit = input.pushLimit(length);
					arrayLength = length / 4;
					i = this.leftEyeFieldOfViewAngles == null ? 0
							: this.leftEyeFieldOfViewAngles.length;
					newArray = new float[i + arrayLength];
					if (i != 0) {
						System.arraycopy(this.leftEyeFieldOfViewAngles, 0,
								newArray, 0, i);
					}
					for (; i < newArray.length; i++) {
						newArray[i] = input.readFloat();
					}
					this.leftEyeFieldOfViewAngles = newArray;
					input.popLimit(limit);
					break;
				case 53:
					this.trayToLensDistance_ = input.readFloat();
					this.bitField0_ |= 32;
					break;
				case 61:
					arrayLength = WireFormatNano
							.getRepeatedFieldArrayLength(input, 61);

					i = this.distortionCoefficients == null ? 0
							: this.distortionCoefficients.length;
					newArray = new float[i + arrayLength];
					if (i != 0) {
						System.arraycopy(this.distortionCoefficients, 0,
								newArray, 0, i);
					}
					for (; i < newArray.length - 1; i++) {
						newArray[i] = input.readFloat();
						input.readTag();
					}

					newArray[i] = input.readFloat();
					this.distortionCoefficients = newArray;
					break;
				case 58:
					length = input.readRawVarint32();
					limit = input.pushLimit(length);
					arrayLength = length / 4;
					i = this.distortionCoefficients == null ? 0
							: this.distortionCoefficients.length;
					newArray = new float[i + arrayLength];
					if (i != 0) {
						System.arraycopy(this.distortionCoefficients, 0,
								newArray, 0, i);
					}
					for (; i < newArray.length; i++) {
						newArray[i] = input.readFloat();
					}
					this.distortionCoefficients = newArray;
					input.popLimit(limit);
					break;
				case 80:
					this.hasMagnet_ = input.readBool();
					this.bitField0_ |= 64;
					break;
				case 88:
					value = input.readInt32();
					switch (value) {
					case 0:
					case 1:
					case 2:
						this.verticalAlignment_ = value;
						this.bitField0_ |= 16;
					}

					break;
				case 96:
					value = input.readInt32();
					switch (value) {
					case 0:
					case 1:
					case 2:
					case 3:
						this.primaryButton_ = value;
						this.bitField0_ |= 128;
					}
				}
			}
		}

		public static DeviceParams parseFrom(byte[] data)
				throws InvalidProtocolBufferNanoException {
			return (DeviceParams) MessageNano.mergeFrom(new DeviceParams(),
					data);
		}

		public static DeviceParams parseFrom(CodedInputByteBufferNano input)
				throws IOException {
			return new DeviceParams().mergeFrom(input);
		}

		public static abstract interface ButtonType {
			public static final int NONE = 0;
			public static final int MAGNET = 1;
			public static final int TOUCH = 2;
			public static final int INDIRECT_TOUCH = 3;
		}

		public static abstract interface VerticalAlignmentType {
			public static final int BOTTOM = 0;
			public static final int CENTER = 1;
			public static final int TOP = 2;
		}
	}
}