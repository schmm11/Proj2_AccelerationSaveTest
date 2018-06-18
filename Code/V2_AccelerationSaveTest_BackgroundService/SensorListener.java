package ch.bfh.students.schmm11.proj2_nearbyv4;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.nearby.connection.ConnectionsClient;

public class SensorListener extends Service implements SensorEventListener {
    private static final String TAG = "NearbyV4_SensorService";

    private ConnectionsClient mConnectionsClient;
    private boolean bNearbyConnected = false;

    private final IBinder mBinder = new MyBinder();
    private Saver saver;
    private SensorManager mSensorManager;
    private float[] mAccelGravityData = new float[3];
    private long amount;
    private String strToSace = "";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onHandleIntent is called");
        saver = new Saver();
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener((SensorEventListener) this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);

        return mBinder;
    }

    public class MyBinder extends Binder {
        SensorListener getService() {
            return SensorListener.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate(); // if you override onCreate(), make sure to call super().
        // If a Context object is needed, call getApplicationContext() here.
    }


    @Override
    public void onSensorChanged(SensorEvent event) {


        mAccelGravityData[0]=(mAccelGravityData[0]*2+event.values[0])*0.33334f;
        mAccelGravityData[1]=(mAccelGravityData[1]*2+event.values[1])*0.33334f;
        mAccelGravityData[2]=(mAccelGravityData[2]*2+event.values[2])*0.33334f;
        long timestamp = System.currentTimeMillis();
        strToSace += "TS:"+ timestamp + "; x:" + mAccelGravityData[0] + ";y:"  + mAccelGravityData[1] + ";z:" + mAccelGravityData[2] + System.getProperty ("line.separator");
        amount++;
        /*
        Only call save() every n time => sonst verhacken sich die Save Befehle
         */
        if(amount % 6 == 0) {
            saver.save(strToSace);
            strToSace ="";
            Log.d(TAG, "onSensorChangedisCalled amount: " + amount);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
