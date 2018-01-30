package scrollview.custom.com.alarmdialog;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    Button mAlertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAlertButton = (Button) findViewById(R.id.ID_Alert_dialog);
        mAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                //Alarm manager
                //alarmManager.cancel(pendingIntent);
                //alarmManager.cancel(pendingIntent);
                Calendar reminderCalendar = Calendar.getInstance();
                // 这里时区需要设置一下，不然可能个别手机会有8个小时的时间差
                reminderCalendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                long timestamp = 10000;
                Intent intent = new Intent(getApplicationContext(), AlertDialogReceiver.class);
                PendingIntent mAlarmSender = PendingIntent.getBroadcast(getApplicationContext(),
                        167, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager amgr = (AlarmManager) getApplicationContext()
                        .getSystemService(getApplicationContext().ALARM_SERVICE);

                amgr.set(AlarmManager.RTC_WAKEUP, reminderCalendar.getTimeInMillis() + timestamp,
                        mAlarmSender);
            }
        });
    }
}
