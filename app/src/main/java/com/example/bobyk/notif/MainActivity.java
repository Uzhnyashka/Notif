package com.example.bobyk.notif;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bobyk on 26/07/15.
 */
public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

    public static final String TAG = "tag19";

    public static final String EXTRA_MESSAGE = "message";

    public static final String MESSAGE_INTENT = "com.example.bobyk.notif.receive_message";

    private MessageReceiver mMessageReceiver;

    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    String regid;

    DbUtils dbUtils;

    ArrayList<NotificationItem> notifactionList;

    private static final int NOTIFICATION_ID = 1234;

    public ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        register();
    }

    private void findViews(){
        notifactionList = new ArrayList<>();

        dbUtils = new DbUtils(this);

        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(new NAdapter(this, notifactionList));
        lv.setOnItemClickListener(this);
    }

    private void register(){
        mMessageReceiver = new MessageReceiver();

        if (!GcmPushHelper.checkPlayServices(this)) return;

        gcm = GoogleCloudMessaging.getInstance(this);
        regid = GcmPushHelper.getRegistrationId(this);
        if (regid.isEmpty()) {
            GcmPushHelper.registerGcmAsync(this, gcm);
        }

        try {
            notifactionList = dbUtils.getJsonFromDb();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        updateAdapter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_Notif:
                onClickAddNotification();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(MESSAGE_INTENT));
        register();
        GcmPushHelper.checkPlayServices(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    private void onClickAddNotification() {
        NotificationItem ni = new NotificationItem();
        ni.setMessage("message");
        ni.setTitle("title");
        ni.setSubtitle("subtitle");
        ni.setTickerText("tickerText");
        ni.setVibrate(1);
        ni.setSound(1);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("message", "message");
            jsonObject.put("title", "title");
            jsonObject.put("subtitle", "subtitle");
            jsonObject.put("tickerText", "tickerText");
            jsonObject.put("vibrate", 1);
            jsonObject.put("sound", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dbUtils.addJsonToDB(jsonObject);

        notifactionList.add(ni);

        showNotification(this, ni);

        updateAdapter();
    }


    public static void showNotification(Context _context, NotificationItem _item) {
        String message = _item.getMessage();
        String title = _item.getTitle();
        String subtitle = _item.getSubtitle();
        String tickerText = _item.getTickerText();
        int vibrate = _item.getVibrate();
        int sound = _item.getVibrate();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(_context)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(title)
                .setSubText(subtitle)
                .setContentText(message)
                .setTicker(tickerText)
                .setAutoCancel(true);

        if (vibrate == 1) builder.setVibrate(new long[]{0, 100});
        if (sound == 1) builder.setSound(Uri.parse("android.resource://" + _context.getPackageName() + "/" + R.raw.notification_music));

        NotificationManager notificationManager = (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int _pos, long l) {
        showNotification(this,notifactionList.get(_pos));
    }

    public NotificationItem intentToNI(Intent _intent){
        NotificationItem ni = new NotificationItem();
        ni.setMessage(_intent.getStringExtra("message"));
        ni.setTitle(_intent.getStringExtra("title"));
        ni.setSubtitle(_intent.getStringExtra("subtitle"));
        ni.setTickerText(_intent.getStringExtra("tickerText"));
        ni.setVibrate(Integer.valueOf(_intent.getStringExtra("vibrate")));
        ni.setSound(Integer.valueOf(_intent.getStringExtra("sound")));

        notifactionList.add(ni);

        updateAdapter();
        return ni;
    }

    public void updateAdapter(){
        lv.setAdapter(new NAdapter(this, notifactionList));
    }

    private class MessageReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context _context, Intent _intent) {
            showNotification(_context, intentToNI(_intent));
            JSONObject json = dbUtils.bundleToJson(_intent.getExtras());
            dbUtils.addJsonToDB(json);
        }
    }

}
