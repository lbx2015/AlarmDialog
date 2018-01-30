package scrollview.custom.com.alarmdialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

/**
 * Created by zw.zhang on 2017/9/11.
 */

public class AlertDialogReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context ct, Intent arg1) {
        //Launch the alertDialog.
        Intent alarmIntent = new Intent("android.intent.action.MAIN");
        alarmIntent.setClass(ct, AlertDialogClass.class);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Start the popup activity
        ct.startActivity(alarmIntent);
    }
}
