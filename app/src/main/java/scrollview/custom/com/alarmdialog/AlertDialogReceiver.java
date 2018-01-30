package scrollview.custom.com.alarmdialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

/**
 * Created by zw.zhang on 2017/9/11.
 */

//DownloadManager下载完成后会发出一个广播 android.intent.action.DOWNLOAD_COMPLETE 新建一个广播接收者即可：
public class AlertDialogReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context ct, Intent arg1) {
//        Vibrator vib = (Vibrator) ct.getSystemService(ct.VIBRATOR_SERVICE);    //for Vibration
//        vib.vibrate(2000);
        //Launch the alertDialog.
        Intent alarmIntent = new Intent("android.intent.action.MAIN");
        alarmIntent.setClass(ct, AlertDialogClass.class);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Start the popup activity
        ct.startActivity(alarmIntent);
    }
}
