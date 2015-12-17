package com.wormhole.vr.cardboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class UiLayer
{
  private static final String TAG = UiLayer.class.getSimpleName();
  private static final int ALIGNMENT_MARKER_LINE_WIDTH = 4;
  private static final int ICON_WIDTH_DP = 28;
  private static final float TOUCH_SLOP_FACTOR = 1.5F;
  private static final int ALIGNMENT_MARKER_LINE_COLOR = -13487566;
  private final Context context;
  private final DisplayMetrics metrics;
  private final Drawable settingsIconDrawable;
  private final Drawable backIconDrawable;
  private ImageView settingsButton;
  private ImageView backButton;
  private View alignmentMarker;
  private final RelativeLayout rootLayout;
  private volatile boolean isSettingsButtonEnabled = true;
  private volatile boolean isAlignmentMarkerEnabled = false;

  private volatile Runnable backButtonRunnable = null;

  public UiLayer(Context context)
  {
    if (!(context instanceof Activity)) {
      throw new RuntimeException("Context is not an instance of activity: Aborting.");
    }
    this.context = context;
    this.settingsIconDrawable = decodeBitmapFromString("iVBORw0KGgoAAAANSUhEUgAAAEgAAABICAQAAAD/5HvMAAADEklEQVRoBe3BXWjVZQDH8d/0HKW00OZ0kh6XiKUiIl0okhARdBcEjUQSmViYkF14K+TCnTNDEd9ShMGgFGZC9HaZqElo0aZDkhAkt2b5np7j8e3P+XazwWE8/+floA9enM9Hqqure0oxn0HSDPCyYqMDm82KjcPYHFRs9GHzq2KjiM1NxUUzLo2KieW4LFVMrMFllWIij0u7Hi/GcIRr7GexRmE8H/E3LgOsY7xG4VUOcJVDNCgUaxnxG2uZoGGsYABfl3hPw5jIh/zOiNUKw/NcodptvmARzfxIqO+ZymL2c4dql5moEGzDpEwtypgU5I+5PORJu89s+eIHYvhGfniLWN6QGxn+IJZ+xsqFT4hpveyYwi1ius5k2bCPUBV66SJPni76qBBql9LxIgkhinQwU1XIUaBEiEc0KQ1TKePvKDNkQI7j+CsxSelYxhB+DpFRCrL04GeQJbJjGsdwO0pGFmQ5gdtPNMmNDNuwKzJDDuQoYdfJWPniXYqk65AHOkl3m3cUhnmcx6zCTHlgFhXMzjFX4ViJWa88cRazVtWCjZh1yRPdmG1QLfgMs7w8UcBsk2pBO2Z5eaKA2SbVgo2YdckT3ZhtUC1YiVmfPNGPWavCsYA/MauQkwdaqGB2nnkKwwpKpCvIA1tJV6RVvsiyE7sSOTnQQhm77WTkxnR+xu04WVkwjpO4HWOa7FjOP/jpIasUjONr/AyxTOlo4h7+TpCTAS2cxN9dJisN00kIUaKTWapCC1spE+IhU5SOPYSqcJZuChTopp8KoXbIhhe4QUzXmCQ7PiamdXIhwzliOcMYufEmsbwuP3xLDEfkizk84Em7x0vyx+eYlKhFEZMOheA5/qXaLXaygCa+I9RhGlnIbv6j2hATFIY1jPiF1TyjYbRyEV8XeFvDeJY2TjFilULRwEEG2c1CjUKWDxjA5S/ayGgUFrGXy3xJgx4v8ri0KybacHlfMfEaLksUE824NCou7mBzU7HRi81pxUYPNl8pNrZg86li4xUukpCQkJCQkJCQkJDwiAvMUV1d3VPqfz17MXquI1uXAAAAAElFTkSuQmCC");
    this.backIconDrawable = decodeBitmapFromString("iVBORw0KGgoAAAANSUhEUgAAAGAAAABgCAQAAABIkb+zAAAArklEQVR42u3VyRHCMBBFwQkAcoQ8WJQbEJewby4XRxkzQ3cE/1VpiQAAANhEb73lnj9ruednTVjMz5iwmp8t4cP82Tn3/Ec/mG+++eabb7755ptvvvnmm2+++eYPmv8FyecPCdhz/oCAfecLKHCEClziAs9ogY9MggQJ/5DwlCBBQp2EowQJEmokvCRIkCDhZxJOEZkTLpHLKuEa+SwSMs5fJNwirynhHgAAAJt4A/ZvpX5veSF2AAAAAElFTkSuQmCC");

    WindowManager windowManager = (WindowManager)context.getSystemService("window");
    Display display = windowManager.getDefaultDisplay();
    this.metrics = new DisplayMetrics();

    if (Build.VERSION.SDK_INT >= 17)
      display.getRealMetrics(this.metrics);
    else {
      display.getMetrics(this.metrics);
    }

    this.rootLayout = new RelativeLayout(context);
    initializeViews();
  }

  private Drawable decodeBitmapFromString(String bitmapString) {
    byte[] decodedBytes = Base64.decode(bitmapString, 0);

    Bitmap buttonBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

    return new BitmapDrawable(this.context.getResources(), buttonBitmap);
  }

  private void initializeViews() {
    int iconWidthPx = (int)(ICON_WIDTH_DP * this.metrics.density);
    int touchWidthPx = (int)(iconWidthPx * TOUCH_SLOP_FACTOR);

    this.settingsButton = createButton(this.settingsIconDrawable, this.isSettingsButtonEnabled, new int[] { 12, 13 });

    this.settingsButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v) {
        UiUtils.launchOrInstallCardboard(v.getContext());
      }
    });
    this.rootLayout.addView(this.settingsButton);

    this.backButton = createButton(this.backIconDrawable, getBackButtonEnabled(), new int[] { 10, 9 });

    this.backButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        Runnable runnable = UiLayer.this.backButtonRunnable;
        if (runnable != null)
          runnable.run();
      }
    });
    this.rootLayout.addView(this.backButton);

    this.alignmentMarker = new View(this.context);
    this.alignmentMarker.setBackground(new ColorDrawable(ALIGNMENT_MARKER_LINE_COLOR));
    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)(ALIGNMENT_MARKER_LINE_WIDTH * this.metrics.density), -1);

    layoutParams.addRule(13);
    layoutParams.setMargins(0, touchWidthPx, 0, touchWidthPx);
    this.alignmentMarker.setLayoutParams(layoutParams);
    this.alignmentMarker.setVisibility(this.isAlignmentMarkerEnabled ? 0 : 8);
    this.rootLayout.addView(this.alignmentMarker);
  }

  private ImageView createButton(Drawable iconDrawable, boolean isEnabled, int[] layoutParams)
  {
    int iconWidthPx = (int)(ICON_WIDTH_DP * this.metrics.density);
    int touchWidthPx = (int)(iconWidthPx * TOUCH_SLOP_FACTOR);
    int padding = (touchWidthPx - iconWidthPx) / 2;

    ImageView buttonLayout = new ImageView(this.context);

    buttonLayout.setPadding(padding, padding, padding, padding);
    buttonLayout.setImageDrawable(iconDrawable);
    buttonLayout.setScaleType(ImageView.ScaleType.FIT_CENTER);
    RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(touchWidthPx, touchWidthPx);

    for (int layoutParam : layoutParams) {
      buttonParams.addRule(layoutParam);
    }
    buttonLayout.setLayoutParams(buttonParams);

    buttonLayout.setVisibility(isEnabled ? 0 : 4);
    return buttonLayout;
  }

  public void attachUiLayer(final ViewGroup parentView)
  {
    ((Activity)this.context)
      .runOnUiThread(new Runnable()
    {
      public void run()
      {
        if (parentView == null) {
          ((Activity)UiLayer.this.context)
            .addContentView(UiLayer.this.rootLayout, 
            new RelativeLayout.LayoutParams(-1, -1));
        }
        else
        {
          parentView.addView(UiLayer.this.rootLayout);
        }
      }
    });
  }

  public void setEnabled(final boolean enabled)
  {
    ((Activity)this.context)
      .runOnUiThread(new Runnable()
    {
      public void run()
      {
        UiLayer.this.rootLayout.setVisibility(enabled ? 0 : 4);
      }
    });
  }

  public void setSettingsButtonEnabled(final boolean enabled)
  {
    this.isSettingsButtonEnabled = enabled;

    ((Activity)this.context)
      .runOnUiThread(new Runnable()
    {
      public void run()
      {
        UiLayer.this.settingsButton.setVisibility(enabled ? 0 : 4);
      }
    });
  }

  public void setBackButtonListener(final Runnable runnable)
  {
    this.backButtonRunnable = runnable;
    ((Activity)this.context)
      .runOnUiThread(new Runnable()
    {
      public void run()
      {
        UiLayer.this.backButton.setVisibility(runnable == null ? 4 : 0);
      }
    });
  }

  public void setAlignmentMarkerEnabled(final boolean enabled)
  {
    this.isAlignmentMarkerEnabled = enabled;
    ((Activity)this.context)
      .runOnUiThread(new Runnable()
    {
      public void run()
      {
        UiLayer.this.alignmentMarker.setVisibility(enabled ? 0 : 4);
      }
    });
  }

  public boolean getSettingsButtonEnabled()
  {
    return this.isSettingsButtonEnabled;
  }

  public boolean getBackButtonEnabled()
  {
    return this.backButtonRunnable != null;
  }

  public boolean getAlignmentMarkerEnabled()
  {
    return this.isAlignmentMarkerEnabled;
  }
}