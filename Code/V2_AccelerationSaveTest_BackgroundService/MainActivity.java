package ch.bfh.students.schmm11.proj2_nearbyv4;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.SensorEventListener;
import android.media.MediaCas;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ServiceConnection {
    private static final String TAG = "NearbyV4Main";
    private Button btnService;
    private TextView txtTimeStamp;

    // Binding => use mSensorListener to get access to BackgroundService
    private SensorListener mSensorListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtTimeStamp = (TextView) findViewById(R.id.txtTimeStamp);


        btnService = (Button) findViewById(R.id.btnService);
        btnService.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                launchTestService();
            }
        });
    }
    public void launchTestService() {
        Intent intent= new Intent(this, SensorListener.class);
        bindService(intent, this, this.BIND_AUTO_CREATE);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        SensorListener.MyBinder b = (SensorListener.MyBinder) service;
        mSensorListener = b.getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
