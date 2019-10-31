package com.cartmell.travis.tcartmelllab1;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Set;

public class TracerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            notify("onCreate NO state");
        }
        else {
            Set<String> keys = savedInstanceState.keySet() ;
            Iterator iter = keys.iterator();

            notify("onCreate WITH state");

            while (iter.hasNext())
                notify("key:" + (String)iter.next()) ;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        notify("onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        notify("onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        notify("onStart");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        notify("onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        notify("onStop");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        notify("onActivityResult");
    }

    private void notify(String msg) {
        String strClass = this.getClass().getName() ;
        String[] strings = strClass.split("\\.") ;
        Notification.Builder notibuild = new Notification.Builder(this) ;

        notibuild.setContentTitle(msg) ;
        notibuild.setAutoCancel(true).setSmallIcon(R.mipmap.ic_launcher) ;
        notibuild.setContentText(strings[strings.length-1]) ;
        Notification noti = notibuild.build() ;
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE) ;
        notificationManager.notify(msg.hashCode(), noti) ;
    }
}
