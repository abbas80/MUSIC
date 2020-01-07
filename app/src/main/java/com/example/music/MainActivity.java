package com.example.music;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public ListView listView;
    public String f[];
    static final int MY_PERMISSION_REQUEST=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }

        } else {

            final ArrayList<File> songs = op(Environment.getExternalStorageDirectory());
            f = new String[songs.size()];
            for (int i = 0; i < songs.size(); i++) {
                f[i] = songs.get(i).getName().replace(".mp3", "");
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.song_layout, R.id.t1, f);

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String song_name=listView.getItemAtPosition(position).toString();
                    startActivity(new Intent(getApplicationContext(),detail.class)
                    .putExtra("song",songs).putExtra("sname",song_name));
                }
            });

        }
    }

    public ArrayList<File> op(File root) {
        ArrayList<File> ad = new ArrayList<File>();
        File files[] = root.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                ad.addAll(op(file));
            } else {
                if (file.getName().endsWith(".mp3")) {
                    ad.add(file);
                }
            }
        }
        return ad;
    }
}
