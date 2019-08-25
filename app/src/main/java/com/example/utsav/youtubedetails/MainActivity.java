package com.example.utsav.youtubedetails;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText YoutubeURL = (EditText) findViewById(R.id.YoutubeURL);
        Button submit = (Button) findViewById(R.id.submit);
        final TextView title = (TextView) findViewById(R.id.title);
        final TextView channel = (TextView) findViewById(R.id.channel);
        final TextView views = (TextView) findViewById(R.id.views);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String[]output=new String[4];

                try
                {
                    output = new SimpleYouTubeHelper().execute(YoutubeURL.getText().toString()).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                title.setText("Video Title: "+output[0]);
                channel.setText("Video Channel: "+output[1]);
                views.setText("No. of Views:"+output[3]);
                Picasso.with(getApplicationContext()).load(output[2]).into(imageView);
            }
        });
    }
}
