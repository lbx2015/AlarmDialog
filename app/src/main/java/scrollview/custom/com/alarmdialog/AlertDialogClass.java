package scrollview.custom.com.alarmdialog;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.Calendar;

/**
 * Created by zw.zhang on 2017/7/11.
 */

public class AlertDialogClass extends AppCompatActivity {
    private Ringtone ringtone;
    private Vibrator vibrator;

    /**
     * 开始播放铃声
     */
    private void startMedia() {
        try {
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            ; //铃声类型为默认闹钟铃声
            ringtone = RingtoneManager.getRingtone(this, uri);
            ringtone.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createDialog() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_launcher_background)
                .setTitle("闹钟提醒时间到了!!")
                .setMessage("你使用闹钟时间到了!!!")
                .setPositiveButton("推迟10分钟", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        tenMRemind();
                        ringtone.stop();
                        vibrator.cancel();
                        finish();
                    }
                })
                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ringtone.stop();
                        vibrator.cancel();
                        finish();
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                ringtone.stop();
                vibrator.cancel();
                finish();
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                ringtone.stop();
                vibrator.cancel();
                finish();
            }
        }).show();
    }

    /**
     * 推迟10分钟提醒
     */
    private void tenMRemind() {
        //设置时间
        Calendar calendar_now = Calendar.getInstance();


        calendar_now.setTimeInMillis(System.currentTimeMillis());
        calendar_now.set(Calendar.HOUR_OF_DAY, calendar_now.get(Calendar.HOUR_OF_DAY));
        calendar_now.set(Calendar.MINUTE, calendar_now.get(Calendar.MINUTE) + 10);
        calendar_now.set(Calendar.SECOND, 0);
        calendar_now.set(Calendar.MILLISECOND, 0);

        //时间选择好了
        Intent intent = new Intent(this, AlertDialogReceiver.class);
        //注册闹钟广播
        PendingIntent sender = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am;
        am = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, calendar_now.getTimeInMillis(), sender);
    }

    /**
     * 震动
     */
    private void startVibrator() {
        /**
         * 想设置震动大小可以通过改变pattern来设定，如果开启时间太短，震动效果可能感觉不到
         *
         */
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {500, 1000, 500, 1000}; // 停止 开启 停止 开启
        vibrator.vibrate(pattern, -1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("zzw", "onStop");
        requestWindowFeature(Window.FEATURE_NO_TITLE); // hide title
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        winParams.flags |= (WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        startMedia();
        startVibrator();
        createDialog();

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ringtone.stop();
                vibrator.cancel();
            }
        }, 10000);
    }

    @Override
    protected void onPause() {
        Log.d("zzw", "onPause");
        super.onPause();
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d("zzw", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("zzw", "onDestroy");
        Window win = getWindow();
        win.clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER);

//        // 屏幕解锁
//        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
//        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("unLock");
//        // 屏幕锁定
//        keyguardLock.reenableKeyguard();
//        keyguardLock.disableKeyguard(); // 解锁

    }
}
