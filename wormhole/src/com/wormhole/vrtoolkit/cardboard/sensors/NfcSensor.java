package com.wormhole.vrtoolkit.cardboard.sensors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.TagLostException;
import android.nfc.tech.Ndef;
import android.os.Handler;
import android.util.Log;

import com.wormhole.vrtoolkit.cardboard.CardboardDeviceParams;
import com.wormhole.vrtoolkit.cardboard.PermissionUtils;

public class NfcSensor
{
  private static final String TAG = "NfcSensor";
  private static final int MAX_CONNECTION_FAILURES = 1;
  private static final long NFC_POLLING_INTERVAL_MS = 250L;
  private static NfcSensor sInstance;
  private final Context context;
  private final NfcAdapter nfcAdapter;
  private final Object tagLock;
  private final List<ListenerHelper> listeners;
  private BroadcastReceiver nfcBroadcastReceiver;
  private IntentFilter[] nfcIntentFilters;
  private Ndef currentNdef;
  private Tag currentTag;
  private boolean currentTagIsCardboard;
  private Timer nfcDisconnectTimer;
  private int tagConnectionFailures;

  public static NfcSensor getInstance(Context context)
  {
    if (sInstance == null) {
      sInstance = new NfcSensor(context);
    }

    return sInstance;
  }

  private NfcSensor(Context context) {
    this.context = context.getApplicationContext();
    this.listeners = new ArrayList();
    this.tagLock = new Object();

    if (PermissionUtils.hasPermission(context, "android.permission.NFC"))
      this.nfcAdapter = NfcAdapter.getDefaultAdapter(context);
    else {
      this.nfcAdapter = null;
    }

    if (this.nfcAdapter == null) {
      return;
    }

    this.nfcBroadcastReceiver = new BroadcastReceiver()
    {
      public void onReceive(Context context, Intent intent) {
        NfcSensor.this.onNfcIntent(intent);
      }
    };
  }

  public void addOnCardboardNfcListener(OnCardboardNfcListener listener)
  {
    if (listener == null) {
      return;
    }

    synchronized (this.listeners)
    {
      IntentFilter ndefIntentFilter;
      if (this.listeners.isEmpty())
      {
        ndefIntentFilter = new IntentFilter("android.nfc.action.NDEF_DISCOVERED");
        ndefIntentFilter.addAction("android.nfc.action.TECH_DISCOVERED");
        ndefIntentFilter.addAction("android.nfc.action.TAG_DISCOVERED");
        this.nfcIntentFilters = new IntentFilter[] { ndefIntentFilter };

        this.context.registerReceiver(this.nfcBroadcastReceiver, ndefIntentFilter);
      }

      for (ListenerHelper helper : this.listeners) {
        if (helper.getListener() == listener) {
          return;
        }
      }

      this.listeners.add(new ListenerHelper(listener, new Handler()));
    }
  }

  public void removeOnCardboardNfcListener(OnCardboardNfcListener listener)
  {
    if (listener == null) {
      return;
    }

    synchronized (this.listeners) {
      for (ListenerHelper helper : this.listeners) {
        if (helper.getListener() == listener) {
          this.listeners.remove(helper);
          break;
        }

      }

      if ((this.nfcBroadcastReceiver != null) && (this.listeners.isEmpty()))
        this.context.unregisterReceiver(this.nfcBroadcastReceiver);
    }
  }

  public boolean isNfcSupported()
  {
    return this.nfcAdapter != null;
  }

  public boolean isNfcEnabled()
  {
    return (isNfcSupported()) && (this.nfcAdapter.isEnabled());
  }

  public boolean isDeviceInCardboard()
  {
    synchronized (this.tagLock) {
      return this.currentTagIsCardboard;
    }
  }

  public NdefMessage getTagContents()
  {
    synchronized (this.tagLock) {
      return this.currentNdef != null ? this.currentNdef.getCachedNdefMessage() : null;
    }
  }

  public NdefMessage getCurrentTagContents()
    throws TagLostException, IOException, FormatException
  {
    synchronized (this.tagLock) {
      return this.currentNdef != null ? this.currentNdef.getNdefMessage() : null;
    }
  }

  public int getTagCapacity()
  {
    synchronized (this.tagLock) {
      if (this.currentNdef == null) {
        throw new IllegalStateException("No NFC tag");
      }

      return this.currentNdef.getMaxSize(); }  } 
  // ERROR //
  public void writeUri(Uri uri) throws TagLostException, IOException, java.lang.IllegalArgumentException { // Byte code:
    //   0: aload_0
    //   1: getfield 5	com/google/vrtoolkit/cardboard/sensors/NfcSensor:tagLock	Ljava/lang/Object;
    //   4: dup
    //   5: astore_2
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield 54	com/google/vrtoolkit/cardboard/sensors/NfcSensor:currentTag	Landroid/nfc/Tag;
    //   11: ifnonnull +13 -> 24
    //   14: new 50	java/lang/IllegalStateException
    //   17: dup
    //   18: ldc 55
    //   20: invokespecial 52	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   23: athrow
    //   24: aconst_null
    //   25: astore_3
    //   26: aconst_null
    //   27: astore 4
    //   29: aload_1
    //   30: invokestatic 56	android/nfc/NdefRecord:createUri	(Landroid/net/Uri;)Landroid/nfc/NdefRecord;
    //   33: astore 5
    //   35: aload_0
    //   36: invokevirtual 57	com/google/vrtoolkit/cardboard/sensors/NfcSensor:getCurrentTagContents	()Landroid/nfc/NdefMessage;
    //   39: astore_3
    //   40: goto +10 -> 50
    //   43: astore 6
    //   45: aload_0
    //   46: invokevirtual 59	com/google/vrtoolkit/cardboard/sensors/NfcSensor:getTagContents	()Landroid/nfc/NdefMessage;
    //   49: astore_3
    //   50: aload_3
    //   51: ifnull +110 -> 161
    //   54: new 12	java/util/ArrayList
    //   57: dup
    //   58: invokespecial 13	java/util/ArrayList:<init>	()V
    //   61: astore 6
    //   63: iconst_0
    //   64: istore 7
    //   66: aload_3
    //   67: invokevirtual 60	android/nfc/NdefMessage:getRecords	()[Landroid/nfc/NdefRecord;
    //   70: astore 8
    //   72: aload 8
    //   74: arraylength
    //   75: istore 9
    //   77: iconst_0
    //   78: istore 10
    //   80: iload 10
    //   82: iload 9
    //   84: if_icmpge +52 -> 136
    //   87: aload 8
    //   89: iload 10
    //   91: aaload
    //   92: astore 11
    //   94: aload_0
    //   95: aload 11
    //   97: invokespecial 61	com/google/vrtoolkit/cardboard/sensors/NfcSensor:isCardboardNdefRecord	(Landroid/nfc/NdefRecord;)Z
    //   100: ifeq +22 -> 122
    //   103: iload 7
    //   105: ifne +25 -> 130
    //   108: aload 6
    //   110: aload 5
    //   112: invokevirtual 62	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   115: pop
    //   116: iconst_1
    //   117: istore 7
    //   119: goto +11 -> 130
    //   122: aload 6
    //   124: aload 11
    //   126: invokevirtual 62	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   129: pop
    //   130: iinc 10 1
    //   133: goto -53 -> 80
    //   136: new 63	android/nfc/NdefMessage
    //   139: dup
    //   140: aload 6
    //   142: aload 6
    //   144: invokevirtual 64	java/util/ArrayList:size	()I
    //   147: anewarray 65	android/nfc/NdefRecord
    //   150: invokevirtual 66	java/util/ArrayList:toArray	([Ljava/lang/Object;)[Ljava/lang/Object;
    //   153: checkcast 67	[Landroid/nfc/NdefRecord;
    //   156: invokespecial 68	android/nfc/NdefMessage:<init>	([Landroid/nfc/NdefRecord;)V
    //   159: astore 4
    //   161: aload 4
    //   163: ifnonnull +21 -> 184
    //   166: new 63	android/nfc/NdefMessage
    //   169: dup
    //   170: iconst_1
    //   171: anewarray 65	android/nfc/NdefRecord
    //   174: dup
    //   175: iconst_0
    //   176: aload 5
    //   178: aastore
    //   179: invokespecial 68	android/nfc/NdefMessage:<init>	([Landroid/nfc/NdefRecord;)V
    //   182: astore 4
    //   184: aload_0
    //   185: getfield 4	com/google/vrtoolkit/cardboard/sensors/NfcSensor:currentNdef	Landroid/nfc/tech/Ndef;
    //   188: ifnull +150 -> 338
    //   191: aload_0
    //   192: getfield 4	com/google/vrtoolkit/cardboard/sensors/NfcSensor:currentNdef	Landroid/nfc/tech/Ndef;
    //   195: invokevirtual 69	android/nfc/tech/Ndef:isConnected	()Z
    //   198: ifne +10 -> 208
    //   201: aload_0
    //   202: getfield 4	com/google/vrtoolkit/cardboard/sensors/NfcSensor:currentNdef	Landroid/nfc/tech/Ndef;
    //   205: invokevirtual 70	android/nfc/tech/Ndef:connect	()V
    //   208: aload_0
    //   209: getfield 4	com/google/vrtoolkit/cardboard/sensors/NfcSensor:currentNdef	Landroid/nfc/tech/Ndef;
    //   212: invokevirtual 53	android/nfc/tech/Ndef:getMaxSize	()I
    //   215: aload 4
    //   217: invokevirtual 71	android/nfc/NdefMessage:getByteArrayLength	()I
    //   220: if_icmpge +64 -> 284
    //   223: new 72	java/lang/IllegalArgumentException
    //   226: dup
    //   227: aload_0
    //   228: getfield 4	com/google/vrtoolkit/cardboard/sensors/NfcSensor:currentNdef	Landroid/nfc/tech/Ndef;
    //   231: invokevirtual 53	android/nfc/tech/Ndef:getMaxSize	()I
    //   234: istore 6
    //   236: aload 4
    //   238: invokevirtual 71	android/nfc/NdefMessage:getByteArrayLength	()I
    //   241: istore 7
    //   243: new 73	java/lang/StringBuilder
    //   246: dup
    //   247: bipush 82
    //   249: invokespecial 74	java/lang/StringBuilder:<init>	(I)V
    //   252: ldc 75
    //   254: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   257: iload 6
    //   259: invokevirtual 77	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   262: ldc 78
    //   264: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   267: iload 7
    //   269: invokevirtual 77	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   272: ldc 79
    //   274: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   277: invokevirtual 80	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   280: invokespecial 81	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   283: athrow
    //   284: aload_0
    //   285: getfield 4	com/google/vrtoolkit/cardboard/sensors/NfcSensor:currentNdef	Landroid/nfc/tech/Ndef;
    //   288: aload 4
    //   290: invokevirtual 82	android/nfc/tech/Ndef:writeNdefMessage	(Landroid/nfc/NdefMessage;)V
    //   293: goto +139 -> 432
    //   296: astore 6
    //   298: new 84	java/lang/RuntimeException
    //   301: dup
    //   302: ldc 85
    //   304: aload 6
    //   306: invokevirtual 86	android/nfc/FormatException:toString	()Ljava/lang/String;
    //   309: invokestatic 87	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   312: dup
    //   313: invokevirtual 88	java/lang/String:length	()I
    //   316: ifeq +9 -> 325
    //   319: invokevirtual 89	java/lang/String:concat	(Ljava/lang/String;)Ljava/lang/String;
    //   322: goto +12 -> 334
    //   325: pop
    //   326: new 90	java/lang/String
    //   329: dup_x1
    //   330: swap
    //   331: invokespecial 91	java/lang/String:<init>	(Ljava/lang/String;)V
    //   334: invokespecial 92	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   337: athrow
    //   338: aload_0
    //   339: getfield 54	com/google/vrtoolkit/cardboard/sensors/NfcSensor:currentTag	Landroid/nfc/Tag;
    //   342: invokestatic 93	android/nfc/tech/NdefFormatable:get	(Landroid/nfc/Tag;)Landroid/nfc/tech/NdefFormatable;
    //   345: astore 6
    //   347: aload 6
    //   349: ifnonnull +13 -> 362
    //   352: new 94	java/io/IOException
    //   355: dup
    //   356: ldc 95
    //   358: invokespecial 96	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   361: athrow
    //   362: ldc 97
    //   364: ldc 98
    //   366: invokestatic 99	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   369: pop
    //   370: aload 6
    //   372: invokevirtual 100	android/nfc/tech/NdefFormatable:connect	()V
    //   375: aload 6
    //   377: aload 4
    //   379: invokevirtual 101	android/nfc/tech/NdefFormatable:format	(Landroid/nfc/NdefMessage;)V
    //   382: aload 6
    //   384: invokevirtual 102	android/nfc/tech/NdefFormatable:close	()V
    //   387: goto +45 -> 432
    //   390: astore 7
    //   392: new 84	java/lang/RuntimeException
    //   395: dup
    //   396: ldc 85
    //   398: aload 7
    //   400: invokevirtual 86	android/nfc/FormatException:toString	()Ljava/lang/String;
    //   403: invokestatic 87	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   406: dup
    //   407: invokevirtual 88	java/lang/String:length	()I
    //   410: ifeq +9 -> 419
    //   413: invokevirtual 89	java/lang/String:concat	(Ljava/lang/String;)Ljava/lang/String;
    //   416: goto +12 -> 428
    //   419: pop
    //   420: new 90	java/lang/String
    //   423: dup_x1
    //   424: swap
    //   425: invokespecial 91	java/lang/String:<init>	(Ljava/lang/String;)V
    //   428: invokespecial 92	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   431: athrow
    //   432: aload_0
    //   433: aload_0
    //   434: getfield 54	com/google/vrtoolkit/cardboard/sensors/NfcSensor:currentTag	Landroid/nfc/Tag;
    //   437: invokespecial 103	com/google/vrtoolkit/cardboard/sensors/NfcSensor:onNewNfcTag	(Landroid/nfc/Tag;)V
    //   440: aload_2
    //   441: monitorexit
    //   442: goto +10 -> 452
    //   445: astore 12
    //   447: aload_2
    //   448: monitorexit
    //   449: aload 12
    //   451: athrow
    //   452: return
    //
    // Exception table:
    //   from	to	target	type
    //   35	40	43	java/lang/Exception
    //   284	293	296	android/nfc/FormatException
    //   370	387	390	android/nfc/FormatException
    //   7	442	445	finally
    //   445	449	445	finally 
	  } 
  public void onResume(Activity activity) { if (!isNfcEnabled()) {
      return;
    }

    Intent intent = new Intent("android.nfc.action.NDEF_DISCOVERED");
    intent.setPackage(activity.getPackageName());

    PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, 0, intent, 0);
    this.nfcAdapter.enableForegroundDispatch(activity, pendingIntent, this.nfcIntentFilters, (String[][])null);
  }

  public void onPause(Activity activity)
  {
    if (!isNfcEnabled()) {
      return;
    }

    this.nfcAdapter.disableForegroundDispatch(activity);
  }

  public void onNfcIntent(Intent intent)
  {
    if ((!isNfcEnabled()) || (intent == null) || (!this.nfcIntentFilters[0].matchAction(intent.getAction()))) {
      return;
    }

    onNewNfcTag((Tag)intent.getParcelableExtra("android.nfc.extra.TAG")); } 
  // ERROR //
  private void onNewNfcTag(Tag nfcTag) { // Byte code:
    //   0: aload_1
    //   1: ifnonnull +4 -> 5
    //   4: return
    //   5: aload_0
    //   6: getfield 5	com/google/vrtoolkit/cardboard/sensors/NfcSensor:tagLock	Ljava/lang/Object;
    //   9: dup
    //   10: astore_2
    //   11: monitorenter
    //   12: aload_0
    //   13: getfield 54	com/google/vrtoolkit/cardboard/sensors/NfcSensor:currentTag	Landroid/nfc/Tag;
    //   16: astore_3
    //   17: aload_0
    //   18: getfield 4	com/google/vrtoolkit/cardboard/sensors/NfcSensor:currentNdef	Landroid/nfc/tech/Ndef;
    //   21: astore 4
    //   23: aload_0
    //   24: getfield 47	com/google/vrtoolkit/cardboard/sensors/NfcSensor:currentTagIsCardboard	Z
    //   27: istore 5
    //   29: aload_0
    //   30: invokespecial 2	com/google/vrtoolkit/cardboard/sensors/NfcSensor:closeCurrentNfcTag	()V
    //   33: aload_0
    //   34: aload_1
    //   35: putfield 54	com/google/vrtoolkit/cardboard/sensors/NfcSensor:currentTag	Landroid/nfc/Tag;
    //   38: aload_0
    //   39: aload_1
    //   40: invokestatic 118	android/nfc/tech/Ndef:get	(Landroid/nfc/Tag;)Landroid/nfc/tech/Ndef;
    //   43: putfield 4	com/google/vrtoolkit/cardboard/sensors/NfcSensor:currentNdef	Landroid/nfc/tech/Ndef;
    //   46: aload_0
    //   47: getfield 4	com/google/vrtoolkit/cardboard/sensors/NfcSensor:currentNdef	Landroid/nfc/tech/Ndef;
    //   50: ifnonnull +15 -> 65
    //   53: iload 5
    //   55: ifeq +7 -> 62
    //   58: aload_0
    //   59: invokespecial 1	com/google/vrtoolkit/cardboard/sensors/NfcSensor:sendDisconnectionEvent	()V
    //   62: aload_2
    //   63: monitorexit
    //   64: return
    //   65: iconst_0
    //   66: istore 6
    //   68: aload 4
    //   70: ifnull +59 -> 129
    //   73: aload_0
    //   74: getfield 54	com/google/vrtoolkit/cardboard/sensors/NfcSensor:currentTag	Landroid/nfc/Tag;
    //   77: invokevirtual 119	android/nfc/Tag:getId	()[B
    //   80: astore 7
    //   82: aload_3
    //   83: invokevirtual 119	android/nfc/Tag:getId	()[B
    //   86: astore 8
    //   88: aload 7
    //   90: ifnull +22 -> 112
    //   93: aload 8
    //   95: ifnull +17 -> 112
    //   98: aload 7
    //   100: aload 8
    //   102: invokestatic 120	java/util/Arrays:equals	([B[B)Z
    //   105: ifeq +7 -> 112
    //   108: iconst_1
    //   109: goto +4 -> 113
    //   112: iconst_0
    //   113: istore 6
    //   115: iload 6
    //   117: ifne +12 -> 129
    //   120: iload 5
    //   122: ifeq +7 -> 129
    //   125: aload_0
    //   126: invokespecial 1	com/google/vrtoolkit/cardboard/sensors/NfcSensor:sendDisconnectionEvent	()V
    //   129: aload_0
    //   130: getfield 4	com/google/vrtoolkit/cardboard/sensors/NfcSensor:currentNdef	Landroid/nfc/tech/Ndef;
    //   133: invokevirtual 70	android/nfc/tech/Ndef:connect	()V
    //   136: aload_0
    //   137: getfield 4	com/google/vrtoolkit/cardboard/sensors/NfcSensor:currentNdef	Landroid/nfc/tech/Ndef;
    //   140: invokevirtual 48	android/nfc/tech/Ndef:getCachedNdefMessage	()Landroid/nfc/NdefMessage;
    //   143: astore 7
    //   145: goto +60 -> 205
    //   148: astore 8
    //   150: ldc 97
    //   152: ldc 121
    //   154: aload 8
    //   156: invokevirtual 122	java/lang/Exception:toString	()Ljava/lang/String;
    //   159: invokestatic 87	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   162: dup
    //   163: invokevirtual 88	java/lang/String:length	()I
    //   166: ifeq +9 -> 175
    //   169: invokevirtual 89	java/lang/String:concat	(Ljava/lang/String;)Ljava/lang/String;
    //   172: goto +12 -> 184
    //   175: pop
    //   176: new 90	java/lang/String
    //   179: dup_x1
    //   180: swap
    //   181: invokespecial 91	java/lang/String:<init>	(Ljava/lang/String;)V
    //   184: invokestatic 123	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   187: pop
    //   188: iload 6
    //   190: ifeq +12 -> 202
    //   193: iload 5
    //   195: ifeq +7 -> 202
    //   198: aload_0
    //   199: invokespecial 1	com/google/vrtoolkit/cardboard/sensors/NfcSensor:sendDisconnectionEvent	()V
    //   202: aload_2
    //   203: monitorexit
    //   204: return
    //   205: aload_0
    //   206: aload_0
    //   207: aload 7
    //   209: invokespecial 124	com/google/vrtoolkit/cardboard/sensors/NfcSensor:isCardboardNdefMessage	(Landroid/nfc/NdefMessage;)Z
    //   212: putfield 47	com/google/vrtoolkit/cardboard/sensors/NfcSensor:currentTagIsCardboard	Z
    //   215: iload 6
    //   217: ifne +78 -> 295
    //   220: aload_0
    //   221: getfield 47	com/google/vrtoolkit/cardboard/sensors/NfcSensor:currentTagIsCardboard	Z
    //   224: ifeq +71 -> 295
    //   227: aload_0
    //   228: getfield 14	com/google/vrtoolkit/cardboard/sensors/NfcSensor:listeners	Ljava/util/List;
    //   231: dup
    //   232: astore 8
    //   234: monitorenter
    //   235: aload_0
    //   236: getfield 14	com/google/vrtoolkit/cardboard/sensors/NfcSensor:listeners	Ljava/util/List;
    //   239: invokeinterface 34 1 0
    //   244: astore 9
    //   246: aload 9
    //   248: invokeinterface 35 1 0
    //   253: ifeq +28 -> 281
    //   256: aload 9
    //   258: invokeinterface 36 1 0
    //   263: checkcast 37	com/google/vrtoolkit/cardboard/sensors/NfcSensor$ListenerHelper
    //   266: astore 10
    //   268: aload 10
    //   270: aload 7
    //   272: invokestatic 125	com/google/vrtoolkit/cardboard/CardboardDeviceParams:createFromNfcContents	(Landroid/nfc/NdefMessage;)Lcom/google/vrtoolkit/cardboard/CardboardDeviceParams;
    //   275: invokevirtual 126	com/google/vrtoolkit/cardboard/sensors/NfcSensor$ListenerHelper:onInsertedIntoCardboard	(Lcom/google/vrtoolkit/cardboard/CardboardDeviceParams;)V
    //   278: goto -32 -> 246
    //   281: aload 8
    //   283: monitorexit
    //   284: goto +11 -> 295
    //   287: astore 11
    //   289: aload 8
    //   291: monitorexit
    //   292: aload 11
    //   294: athrow
    //   295: aload_0
    //   296: getfield 47	com/google/vrtoolkit/cardboard/sensors/NfcSensor:currentTagIsCardboard	Z
    //   299: ifeq +42 -> 341
    //   302: aload_0
    //   303: iconst_0
    //   304: putfield 3	com/google/vrtoolkit/cardboard/sensors/NfcSensor:tagConnectionFailures	I
    //   307: aload_0
    //   308: new 127	java/util/Timer
    //   311: dup
    //   312: ldc 128
    //   314: invokespecial 129	java/util/Timer:<init>	(Ljava/lang/String;)V
    //   317: putfield 130	com/google/vrtoolkit/cardboard/sensors/NfcSensor:nfcDisconnectTimer	Ljava/util/Timer;
    //   320: aload_0
    //   321: getfield 130	com/google/vrtoolkit/cardboard/sensors/NfcSensor:nfcDisconnectTimer	Ljava/util/Timer;
    //   324: new 131	com/google/vrtoolkit/cardboard/sensors/NfcSensor$2
    //   327: dup
    //   328: aload_0
    //   329: invokespecial 132	com/google/vrtoolkit/cardboard/sensors/NfcSensor$2:<init>	(Lcom/google/vrtoolkit/cardboard/sensors/NfcSensor;)V
    //   332: ldc2_w 133
    //   335: ldc2_w 133
    //   338: invokevirtual 135	java/util/Timer:schedule	(Ljava/util/TimerTask;JJ)V
    //   341: aload_2
    //   342: monitorexit
    //   343: goto +10 -> 353
    //   346: astore 12
    //   348: aload_2
    //   349: monitorexit
    //   350: aload 12
    //   352: athrow
    //   353: return
    //
    // Exception table:
    //   from	to	target	type
    //   129	145	148	java/lang/Exception
    //   235	284	287	finally
    //   287	292	287	finally
    //   12	64	346	finally
    //   65	204	346	finally
    //   205	343	346	finally
    //   346	350	346	finally 
	  } 
  private void closeCurrentNfcTag() { if (this.nfcDisconnectTimer != null) {
      this.nfcDisconnectTimer.cancel();
    }

    if (this.currentNdef == null) {
      return;
    }

    try
    {
      this.currentNdef.close();
    } catch (IOException e) {
      Log.w("NfcSensor", e.toString());
    }

    this.currentTag = null;
    this.currentNdef = null;
    this.currentTagIsCardboard = false; }

  private void sendDisconnectionEvent()
  {
    synchronized (this.listeners) {
      for (ListenerHelper listener : this.listeners)
        listener.onRemovedFromCardboard();
    }
  }

  private boolean isCardboardNdefMessage(NdefMessage message)
  {
    if (message == null) {
      return false;
    }

    for (NdefRecord record : message.getRecords()) {
      if (isCardboardNdefRecord(record)) {
        return true;
      }
    }

    return false;
  }

  private boolean isCardboardNdefRecord(NdefRecord record) {
    if (record == null) {
      return false;
    }

    Uri uri = record.toUri();
    return (uri != null) && (CardboardDeviceParams.isCardboardUri(uri));
  }

  private static class ListenerHelper
    implements NfcSensor.OnCardboardNfcListener
  {
    private NfcSensor.OnCardboardNfcListener listener;
    private Handler handler;

    public ListenerHelper(NfcSensor.OnCardboardNfcListener listener, Handler handler)
    {
      this.listener = listener;
      this.handler = handler;
    }

    public NfcSensor.OnCardboardNfcListener getListener() {
      return this.listener;
    }

    public void onInsertedIntoCardboard(final CardboardDeviceParams deviceParams)
    {
      this.handler.post(new Runnable()
      {
        public void run() {
          NfcSensor.ListenerHelper.this.listener.onInsertedIntoCardboard(deviceParams);
        }
      });
    }

    public void onRemovedFromCardboard()
    {
      this.handler.post(new Runnable()
      {
        public void run() {
          NfcSensor.ListenerHelper.this.listener.onRemovedFromCardboard();
        }
      });
    }
  }

  public static abstract interface OnCardboardNfcListener
  {
    public abstract void onInsertedIntoCardboard(CardboardDeviceParams paramCardboardDeviceParams);

    public abstract void onRemovedFromCardboard();
  }
}