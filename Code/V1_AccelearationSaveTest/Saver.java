package com.example.michu.proj2_accelerationsavetest;


import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Saver {

    private File file;
    public Saver(){
        if(isExternalStorageWritable()){
            //nice go On
            Log.e("SUCESS", " Writable");
        }
        else{
            //fucked up
            Log.e("Error", "Not Writable");
        }
         //file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "AccelerationTest.txt");
        File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Proj2Acceleration");

        if (!filepath.exists()) {
            filepath.mkdirs();
        }
        file = new File( filepath.getPath(), "AccelerationTest.txt");
        if (!file.exists()) {
            try {

                file.createNewFile();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

    }

    public void save(long timestamp, float[] mAccelGravityData){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);

            BufferedWriter fbw = new BufferedWriter(writer);

            fbw.append("Timestamp: "+ timestamp + "Data ==> x: " + mAccelGravityData[0] + "; y: "  + mAccelGravityData[0] + "; z: " + mAccelGravityData[2] );
            fbw.newLine();
            fbw.flush();
            fbw.close();
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
