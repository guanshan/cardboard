package com.wormhole.vrtoolkit.cardboard;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.util.Base64;
import android.util.Log;

import com.google.protobuf.nano.MessageNano;
import com.wormhole.vrtoolkit.cardboard.proto.CardboardDevice;

public class CardboardDeviceParams
{
  private static final String TAG = "CardboardDeviceParams";
  private static final String HTTP_SCHEME = "http";
  private static final String URI_HOST_GOOGLE_SHORT = "g.co";
  private static final String URI_HOST_GOOGLE = "google.com";
  private static final String URI_PATH_CARDBOARD_HOME = "cardboard";
  private static final String URI_PATH_CARDBOARD_CONFIG = "cardboard/cfg";
  private static final String URI_SCHEME_LEGACY_CARDBOARD = "cardboard";
  private static final String URI_HOST_LEGACY_CARDBOARD = "v1.0.0";
  private static final Uri URI_ORIGINAL_CARDBOARD_NFC = new Uri.Builder()
    .scheme("cardboard")
    .authority("v1.0.0")
    .build();

  private static final Uri URI_ORIGINAL_CARDBOARD_QR_CODE = new Uri.Builder()
    .scheme("http")
    .authority("g.co")
    .appendEncodedPath("cardboard")
    .build();
  private static final String URI_KEY_PARAMS = "p";
  private static final int STREAM_SENTINEL = 894990891;
  private static final String DEFAULT_VENDOR = "Google, Inc.";
  private static final String DEFAULT_MODEL = "Cardboard v1";
  private static final float DEFAULT_INTER_LENS_DISTANCE = 0.06F;
  private static final VerticalAlignmentType DEFAULT_VERTICAL_ALIGNMENT = VerticalAlignmentType.BOTTOM;
  private static final float DEFAULT_VERTICAL_DISTANCE_TO_LENS_CENTER = 0.035F;
  private static final float DEFAULT_SCREEN_TO_LENS_DISTANCE = 0.042F;
  private static final CardboardDeviceParams DEFAULT_PARAMS = new CardboardDeviceParams();
  private String vendor;
  private String model;
  private float interLensDistance;
  private VerticalAlignmentType verticalAlignment;
  private float verticalDistanceToLensCenter;
  private float screenToLensDistance;
  private FieldOfView leftEyeMaxFov;
  private boolean hasMagnet;
  private Distortion distortion;

  public CardboardDeviceParams()
  {
    setDefaultValues();
  }

  public CardboardDeviceParams(CardboardDeviceParams params)
  {
    copyFrom(params);
  }

  public CardboardDeviceParams(CardboardDevice.DeviceParams params)
  {
    setDefaultValues();

    if (params == null) {
      return;
    }

    this.vendor = params.getVendor();
    this.model = params.getModel();

    this.interLensDistance = params.getInterLensDistance();
    this.verticalAlignment = VerticalAlignmentType.fromProtoValue(params.getVerticalAlignment());
    this.verticalDistanceToLensCenter = params.getTrayToLensDistance();
    this.screenToLensDistance = params.getScreenToLensDistance();

    this.leftEyeMaxFov = FieldOfView.parseFromProtobuf(params.leftEyeFieldOfViewAngles);
    if (this.leftEyeMaxFov == null) {
      this.leftEyeMaxFov = new FieldOfView();
    }

    this.distortion = Distortion.parseFromProtobuf(params.distortionCoefficients);
    if (this.distortion == null) {
      this.distortion = new Distortion();
    }

    this.hasMagnet = params.getHasMagnet();
  }

  public static boolean isOriginalCardboardDeviceUri(Uri uri)
  {
    return (URI_ORIGINAL_CARDBOARD_QR_CODE.equals(uri)) || (
      (URI_ORIGINAL_CARDBOARD_NFC
      .getScheme().equals(uri.getScheme())) && 
      (URI_ORIGINAL_CARDBOARD_NFC
      .getAuthority().equals(uri.getAuthority())));
  }

  private static boolean isCardboardDeviceUri(Uri uri)
  {
    return ("http".equals(uri.getScheme())) && 
      ("google.com"
      .equals(uri
      .getAuthority())) && 
      ("/cardboard/cfg"
      .equals(uri
      .getPath()));
  }

  public static boolean isCardboardUri(Uri uri)
  {
    return (isOriginalCardboardDeviceUri(uri)) || (isCardboardDeviceUri(uri)); } 
  // ERROR //
  public static CardboardDeviceParams createFromUri(Uri uri) { // Byte code:
    //   0: aload_0
    //   1: ifnonnull +5 -> 6
    //   4: aconst_null
    //   5: areturn
    //   6: aload_0
    //   7: invokestatic 40	com/google/vrtoolkit/cardboard/CardboardDeviceParams:isOriginalCardboardDeviceUri	(Landroid/net/Uri;)Z
    //   10: ifeq +25 -> 35
    //   13: ldc 42
    //   15: ldc 43
    //   17: invokestatic 44	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   20: pop
    //   21: new 35	com/google/vrtoolkit/cardboard/CardboardDeviceParams
    //   24: dup
    //   25: invokespecial 45	com/google/vrtoolkit/cardboard/CardboardDeviceParams:<init>	()V
    //   28: astore_1
    //   29: aload_1
    //   30: invokespecial 2	com/google/vrtoolkit/cardboard/CardboardDeviceParams:setDefaultValues	()V
    //   33: aload_1
    //   34: areturn
    //   35: aload_0
    //   36: invokestatic 41	com/google/vrtoolkit/cardboard/CardboardDeviceParams:isCardboardDeviceUri	(Landroid/net/Uri;)Z
    //   39: ifne +24 -> 63
    //   42: ldc 42
    //   44: ldc 46
    //   46: iconst_1
    //   47: anewarray 47	java/lang/Object
    //   50: dup
    //   51: iconst_0
    //   52: aload_0
    //   53: aastore
    //   54: invokestatic 48	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   57: invokestatic 49	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   60: pop
    //   61: aconst_null
    //   62: areturn
    //   63: aconst_null
    //   64: astore_1
    //   65: aload_0
    //   66: ldc 50
    //   68: invokevirtual 51	android/net/Uri:getQueryParameter	(Ljava/lang/String;)Ljava/lang/String;
    //   71: astore_2
    //   72: aload_2
    //   73: ifnull +77 -> 150
    //   76: aload_2
    //   77: bipush 11
    //   79: invokestatic 53	android/util/Base64:decode	(Ljava/lang/String;I)[B
    //   82: astore_3
    //   83: new 54	com/google/vrtoolkit/cardboard/proto/CardboardDevice$DeviceParams
    //   86: dup
    //   87: invokespecial 55	com/google/vrtoolkit/cardboard/proto/CardboardDevice$DeviceParams:<init>	()V
    //   90: aload_3
    //   91: invokestatic 56	com/google/protobuf/nano/MessageNano:mergeFrom	(Lcom/google/protobuf/nano/MessageNano;[B)Lcom/google/protobuf/nano/MessageNano;
    //   94: checkcast 54	com/google/vrtoolkit/cardboard/proto/CardboardDevice$DeviceParams
    //   97: astore_1
    //   98: ldc 42
    //   100: ldc 57
    //   102: invokestatic 44	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   105: pop
    //   106: goto +52 -> 158
    //   109: astore_3
    //   110: ldc 42
    //   112: ldc 59
    //   114: aload_3
    //   115: invokevirtual 60	java/lang/Exception:toString	()Ljava/lang/String;
    //   118: invokestatic 61	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   121: dup
    //   122: invokevirtual 62	java/lang/String:length	()I
    //   125: ifeq +9 -> 134
    //   128: invokevirtual 63	java/lang/String:concat	(Ljava/lang/String;)Ljava/lang/String;
    //   131: goto +12 -> 143
    //   134: pop
    //   135: new 64	java/lang/String
    //   138: dup_x1
    //   139: swap
    //   140: invokespecial 65	java/lang/String:<init>	(Ljava/lang/String;)V
    //   143: invokestatic 49	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   146: pop
    //   147: goto +11 -> 158
    //   150: ldc 42
    //   152: ldc 66
    //   154: invokestatic 49	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   157: pop
    //   158: new 35	com/google/vrtoolkit/cardboard/CardboardDeviceParams
    //   161: dup
    //   162: aload_1
    //   163: invokespecial 67	com/google/vrtoolkit/cardboard/CardboardDeviceParams:<init>	(Lcom/google/vrtoolkit/cardboard/proto/CardboardDevice$DeviceParams;)V
    //   166: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   76	106	109	java/lang/Exception 
	  return null;
	  } 
  // ERROR //
  public static CardboardDeviceParams createFromInputStream(java.io.InputStream inputStream) { // Byte code:
    //   0: aload_0
    //   1: ifnonnull +5 -> 6
    //   4: aconst_null
    //   5: areturn
    //   6: bipush 8
    //   8: invokestatic 70	java/nio/ByteBuffer:allocate	(I)Ljava/nio/ByteBuffer;
    //   11: astore_1
    //   12: aload_0
    //   13: aload_1
    //   14: invokevirtual 71	java/nio/ByteBuffer:array	()[B
    //   17: iconst_0
    //   18: aload_1
    //   19: invokevirtual 71	java/nio/ByteBuffer:array	()[B
    //   22: arraylength
    //   23: invokevirtual 72	java/io/InputStream:read	([BII)I
    //   26: iconst_m1
    //   27: if_icmpne +13 -> 40
    //   30: ldc 42
    //   32: ldc 73
    //   34: invokestatic 74	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   37: pop
    //   38: aconst_null
    //   39: areturn
    //   40: aload_1
    //   41: invokevirtual 75	java/nio/ByteBuffer:getInt	()I
    //   44: istore_2
    //   45: aload_1
    //   46: invokevirtual 75	java/nio/ByteBuffer:getInt	()I
    //   49: istore_3
    //   50: iload_2
    //   51: ldc 76
    //   53: if_icmpeq +13 -> 66
    //   56: ldc 42
    //   58: ldc 77
    //   60: invokestatic 74	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   63: pop
    //   64: aconst_null
    //   65: areturn
    //   66: iload_3
    //   67: newarray byte
    //   69: astore 4
    //   71: aload_0
    //   72: aload 4
    //   74: iconst_0
    //   75: aload 4
    //   77: arraylength
    //   78: invokevirtual 72	java/io/InputStream:read	([BII)I
    //   81: iconst_m1
    //   82: if_icmpne +13 -> 95
    //   85: ldc 42
    //   87: ldc 73
    //   89: invokestatic 74	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   92: pop
    //   93: aconst_null
    //   94: areturn
    //   95: new 35	com/google/vrtoolkit/cardboard/CardboardDeviceParams
    //   98: dup
    //   99: new 54	com/google/vrtoolkit/cardboard/proto/CardboardDevice$DeviceParams
    //   102: dup
    //   103: invokespecial 55	com/google/vrtoolkit/cardboard/proto/CardboardDevice$DeviceParams:<init>	()V
    //   106: aload 4
    //   108: invokestatic 56	com/google/protobuf/nano/MessageNano:mergeFrom	(Lcom/google/protobuf/nano/MessageNano;[B)Lcom/google/protobuf/nano/MessageNano;
    //   111: checkcast 54	com/google/vrtoolkit/cardboard/proto/CardboardDevice$DeviceParams
    //   114: invokespecial 67	com/google/vrtoolkit/cardboard/CardboardDeviceParams:<init>	(Lcom/google/vrtoolkit/cardboard/proto/CardboardDevice$DeviceParams;)V
    //   117: areturn
    //   118: astore_1
    //   119: ldc 42
    //   121: ldc 79
    //   123: aload_1
    //   124: invokevirtual 80	com/google/protobuf/nano/InvalidProtocolBufferNanoException:toString	()Ljava/lang/String;
    //   127: invokestatic 61	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   130: dup
    //   131: invokevirtual 62	java/lang/String:length	()I
    //   134: ifeq +9 -> 143
    //   137: invokevirtual 63	java/lang/String:concat	(Ljava/lang/String;)Ljava/lang/String;
    //   140: goto +12 -> 152
    //   143: pop
    //   144: new 64	java/lang/String
    //   147: dup_x1
    //   148: swap
    //   149: invokespecial 65	java/lang/String:<init>	(Ljava/lang/String;)V
    //   152: invokestatic 49	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   155: pop
    //   156: goto +41 -> 197
    //   159: astore_1
    //   160: ldc 42
    //   162: ldc 82
    //   164: aload_1
    //   165: invokevirtual 83	java/io/IOException:toString	()Ljava/lang/String;
    //   168: invokestatic 61	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   171: dup
    //   172: invokevirtual 62	java/lang/String:length	()I
    //   175: ifeq +9 -> 184
    //   178: invokevirtual 63	java/lang/String:concat	(Ljava/lang/String;)Ljava/lang/String;
    //   181: goto +12 -> 193
    //   184: pop
    //   185: new 64	java/lang/String
    //   188: dup_x1
    //   189: swap
    //   190: invokespecial 65	java/lang/String:<init>	(Ljava/lang/String;)V
    //   193: invokestatic 49	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   196: pop
    //   197: aconst_null
    //   198: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   6	39	118	com/google/protobuf/nano/InvalidProtocolBufferNanoException
    //   40	65	118	com/google/protobuf/nano/InvalidProtocolBufferNanoException
    //   66	94	118	com/google/protobuf/nano/InvalidProtocolBufferNanoException
    //   95	117	118	com/google/protobuf/nano/InvalidProtocolBufferNanoException
    //   6	39	159	java/io/IOException
    //   40	65	159	java/io/IOException
    //   66	94	159	java/io/IOException
    //   95	117	159	java/io/IOException 
	  return null;
	  } 
  public boolean writeToOutputStream(OutputStream outputStream) { try { byte[] paramBytes = toByteArray();
      ByteBuffer header = ByteBuffer.allocate(8);
      header.putInt(894990891);
      header.putInt(paramBytes.length);
      outputStream.write(header.array());
      outputStream.write(paramBytes);
      return true;
    } catch (IOException e) {
    	e.printStackTrace();
    	return false;
//      "CardboardDeviceParams";
    }
  }

  public static CardboardDeviceParams createFromNfcContents(NdefMessage tagContents)
  {
    if (tagContents == null) {
      Log.w("CardboardDeviceParams", "Could not get contents from NFC tag.");
      return null;
    }

    for (NdefRecord record : tagContents.getRecords()) {
      CardboardDeviceParams params = createFromUri(record.toUri());

      if (params != null) {
        return params;
      }
    }

    return null;
  }

  byte[] toByteArray()
  {
    CardboardDevice.DeviceParams params = new CardboardDevice.DeviceParams();

    params.setVendor(this.vendor);
    params.setModel(this.model);
    params.setInterLensDistance(this.interLensDistance);
    params.setVerticalAlignment(this.verticalAlignment.toProtoValue());
    if (this.verticalAlignment == VerticalAlignmentType.CENTER)
    {
      params.setTrayToLensDistance(0.035F);
    }
    else params.setTrayToLensDistance(this.verticalDistanceToLensCenter);

    params.setScreenToLensDistance(this.screenToLensDistance);
    params.leftEyeFieldOfViewAngles = this.leftEyeMaxFov.toProtobuf();
    params.distortionCoefficients = this.distortion.toProtobuf();

    if (this.hasMagnet) {
      params.setHasMagnet(this.hasMagnet);
    }

    return MessageNano.toByteArray(params);
  }

  public Uri toUri()
  {
    byte[] paramsData = toByteArray();
    int paramsSize = paramsData.length;

    return new Uri.Builder()
      .scheme("http")
      .authority("google.com")
      .appendEncodedPath("cardboard/cfg")
      .appendQueryParameter("p", 
      Base64.encodeToString(paramsData, 0, paramsSize, 11))
      .build();
  }

  public void setVendor(String vendor)
  {
    this.vendor = (vendor != null ? vendor : "");
  }

  public String getVendor()
  {
    return this.vendor;
  }

  public void setModel(String model)
  {
    this.model = (model != null ? model : "");
  }

  public String getModel()
  {
    return this.model;
  }

  public void setInterLensDistance(float interLensDistance)
  {
    this.interLensDistance = interLensDistance;
  }

  public float getInterLensDistance()
  {
    return this.interLensDistance;
  }

  public VerticalAlignmentType getVerticalAlignment()
  {
    return this.verticalAlignment;
  }

  public void setVerticalAlignment(VerticalAlignmentType verticalAlignment)
  {
    this.verticalAlignment = verticalAlignment;
  }

  public void setVerticalDistanceToLensCenter(float verticalDistanceToLensCenter)
  {
    this.verticalDistanceToLensCenter = verticalDistanceToLensCenter;
  }

  public float getVerticalDistanceToLensCenter()
  {
    return this.verticalDistanceToLensCenter;
  }

  float getYEyeOffsetMeters(ScreenParams screen)
  {
//    switch (1.$SwitchMap$com$google$vrtoolkit$cardboard$CardboardDeviceParams$VerticalAlignmentType[getVerticalAlignment().ordinal()]) {
    switch (this.verticalAlignment[getVerticalAlignment().ordinal()]) {
    case 1:
    default:
      return screen.getHeightMeters() / 2.0F;
    case 2:
      return getVerticalDistanceToLensCenter() - screen.getBorderSizeMeters();
    case 3:
    }
    return screen.getHeightMeters() - (
      getVerticalDistanceToLensCenter() - screen.getBorderSizeMeters());
  }

  public void setScreenToLensDistance(float screenToLensDistance)
  {
    this.screenToLensDistance = screenToLensDistance;
  }

  public float getScreenToLensDistance()
  {
    return this.screenToLensDistance;
  }

  public Distortion getDistortion()
  {
    return this.distortion;
  }

  public FieldOfView getLeftEyeMaxFov()
  {
    return this.leftEyeMaxFov;
  }

  public boolean getHasMagnet()
  {
    return this.hasMagnet;
  }

  public void setHasMagnet(boolean magnet)
  {
    this.hasMagnet = magnet;
  }

  public boolean equals(Object other)
  {
    if (other == null) {
      return false;
    }

    if (other == this) {
      return true;
    }

    if (!(other instanceof CardboardDeviceParams)) {
      return false;
    }

    CardboardDeviceParams o = (CardboardDeviceParams)other;

    if ((this.vendor.equals(o.vendor)) && 
      (this.model
      .equals(o.model)) && 
      (this.interLensDistance == o.interLensDistance) && (this.verticalAlignment == o.verticalAlignment) && ((this.verticalAlignment == VerticalAlignmentType.CENTER) || (this.verticalDistanceToLensCenter == o.verticalDistanceToLensCenter)) && (this.screenToLensDistance == o.screenToLensDistance));
    return (this.leftEyeMaxFov
      .equals(o.leftEyeMaxFov)) && 
      (this.distortion
      .equals(o.distortion)) && 
      (this.hasMagnet == o.hasMagnet);
  }

  public String toString()
  {
    String str1 = this.vendor;
    str1 = this.model;
    float f1 = this.interLensDistance;
    String str2 = String.valueOf(this.verticalAlignment);
    float f2 = this.verticalDistanceToLensCenter;
    f2 = this.screenToLensDistance;

    String str3 = String.valueOf(this.leftEyeMaxFov
      .toString().replace("\n", "\n  "));

    str3 = String.valueOf(this.distortion
      .toString()
      .replace("\n", "\n  ")); boolean bool = this.hasMagnet;

    return "{\n" + 
      new StringBuilder(12 + String.valueOf(str1).length()).append("  vendor: ").append(str1).append(",\n").toString() + 
      new StringBuilder(11 + String.valueOf(str1).length()).append("  model: ").append(str1).append(",\n").toString() + 
      new StringBuilder(40).append("  inter_lens_distance: ").append(f1).append(",\n").toString() + 
      new StringBuilder(24 + String.valueOf(str2).length()).append("  vertical_alignment: ").append(str2).append(",\n").toString() + 
      new StringBuilder(53).append("  vertical_distance_to_lens_center: ").append(f2).append(",\n").toString() + 
      new StringBuilder(44).append("  screen_to_lens_distance: ").append(f2).append(",\n").toString() + 
      new StringBuilder(22 + String.valueOf(str3).length()).append("  left_eye_max_fov: ").append(str3).append(",\n").toString() + 
      new StringBuilder(16 + String.valueOf(str3).length()).append("  distortion: ").append(str3).append(",\n").toString() + new StringBuilder(17).append("  magnet: ").append(bool).append(",\n").toString() + 
      "}\n";
  }

  public boolean isDefault()
  {
    return DEFAULT_PARAMS.equals(this);
  }

  private void setDefaultValues() {
    this.vendor = "Google, Inc.";
    this.model = "Cardboard v1";

    this.interLensDistance = 0.06F;
    this.verticalAlignment = DEFAULT_VERTICAL_ALIGNMENT;
    this.verticalDistanceToLensCenter = 0.035F;
    this.screenToLensDistance = 0.042F;

    this.leftEyeMaxFov = new FieldOfView();

    this.hasMagnet = true;

    this.distortion = new Distortion();
  }

  private void copyFrom(CardboardDeviceParams params) {
    this.vendor = params.vendor;
    this.model = params.model;

    this.interLensDistance = params.interLensDistance;
    this.verticalAlignment = params.verticalAlignment;
    this.verticalDistanceToLensCenter = params.verticalDistanceToLensCenter;
    this.screenToLensDistance = params.screenToLensDistance;

    this.leftEyeMaxFov = new FieldOfView(params.leftEyeMaxFov);

    this.hasMagnet = params.hasMagnet;

    this.distortion = new Distortion(params.distortion);
  }

  public static enum VerticalAlignmentType
  {
    BOTTOM(0), 

    CENTER(1), 

    TOP(2);

    private final int protoValue;

    private VerticalAlignmentType(int protoValue) {
      this.protoValue = protoValue;
    }
    int toProtoValue() {
      return this.protoValue;
    }
    static VerticalAlignmentType fromProtoValue(int protoValue) {
      for (VerticalAlignmentType type : values()) {
        if (type.protoValue == protoValue) {
          return type;
        }
      }

      Log.e("CardboardDeviceParams", String.format("Unknown alignment type from proto: %d", new Object[] { Integer.valueOf(protoValue) }));
      return BOTTOM;
    }
  }
}