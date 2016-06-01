package app.num.linachartmedia;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements OnChartValueSelectedListener, OnSeekBarChangeListener{
    //MediaPlayer mySound;
    int v=0;
    TimerTask time;
    PlaybackParams params = new PlaybackParams();
    private LineChart mChart;
    private SeekBar mSeekBar;
    private TextView tvX;
    SoundPool mySound;
    SoundPool.Builder soundPoolBuilder;
    AudioManager am;
    int raygunID;

    MediaPlayer mp;
    Button btn;
    void playmp(int a)
    {

        switch (a)
        {
            case 0:
            {
                mp = MediaPlayer.create(this,R.raw.p1);
                params.setPitch(0.4f);
                mp.setPlaybackParams(params);
                mp.start();
                v++;
                break;
            }

            case 1:
            {
                mp = MediaPlayer.create(this,R.raw.p1);
                params.setPitch(0.8f);
                mp.setPlaybackParams(params);
                mp.start();
                v++;
                break;
            }

            case 2:
            {
                mp = MediaPlayer.create(this,R.raw.p1);
                params.setPitch(.6f);
                mp.setPlaybackParams(params);
                mp.start();
                v++;
                break;
            }

            case 3:
            {
                mp = MediaPlayer.create(this,R.raw.p1);
                params.setPitch(.2f);
                mp.setPlaybackParams(params);
                mp.start();
                v++;
                break;
            }

            case 4:
            {
                mp = MediaPlayer.create(this,R.raw.p1);
                params.setPitch(1.5f);
                mp.setPlaybackParams(params);
                mp.start();
                v++;
                break;
            }

            case 5:
            {
                mp = MediaPlayer.create(this,R.raw.p1);
                params.setPitch(.9f);
                mp.setPlaybackParams(params);
                mp.start();
                v++;
                break;
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ini adalah load Soundpool
        mySound=new SoundPool(6, AudioManager.STREAM_NOTIFICATION, 0);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
        int curVolume = am.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
        raygunID=mySound.load(this,R.raw.p1,1);
        //setVolumeControlStream(AudioManager.STREAM_NOTIFICATION);
        //initControls();

        tvX = (TextView) findViewById(R.id.freqChart);

        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mSeekBar.setMax(5);
        mSeekBar.setProgress(1);
        mSeekBar.setOnSeekBarChangeListener(this);

        mChart = (LineChart) findViewById(R.id.chart);
        mChart.setOnChartValueSelectedListener(this);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(2f, 3));
        entries.add(new Entry(15f, 4));
        entries.add(new Entry(9f, 5));

        LineDataSet dataset = new LineDataSet(entries, "# of Calls");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        LineData data = new LineData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);

        mChart.setData(data);
        mChart.animateY(1000);

        btn= (Button) findViewById(R.id.button3);
        mp = MediaPlayer.create(this,R.raw.p1);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                //Creating Thread

                Thread thread = new Thread()
                {
                    @Override
                    public void run() {
                        try {

                            sleep(1);

                            // Body Of Timer
                            time = new TimerTask() {
                                @Override
                                public void run() {

                                    //Perform background work here
                                    if(!mp.isPlaying())
                                    {

                                        playmp(v);
                                        //mp.release();
                                    }


                                }
                            };
                            //Starting Timer
                            Timer timer = new Timer();
                            timer.schedule(time, 10, 500);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();



            }
        });
    }



    public void pause(View view) {
        mp.pause();

    }

    public void stop(View view) {
        mp.release();
        mp=null;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        tvX.setText("" + (mSeekBar.getProgress()));
        float volume= ((mChart.getYChartMax()/(mChart.getYChartMax()-mChart.getYChartMin()))*5);
        //float volume= ((mChart.getY/(mChart.getYChartMax()-mChart.getYChartMin()))*5);
        mySound.play(raygunID, 1, 1, 1, 0, (volume*mSeekBar.getProgress()));

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        float volume= ((e.getVal()/(mChart.getYChartMax()-mChart.getYChartMin()))*mSeekBar.getProgress());
        //float volume= ((mChart.getY/(mChart.getYChartMax()-mChart.getYChartMin()))*5);
        mySound.play(raygunID, 1, 1, 1, 0, volume);
        Log.i("Entry selected", e.toString());
        Log.i("Apa", String.valueOf(volume));
        Log.i("Apa si", mChart.getXValue(dataSetIndex));
        Log.i("LOWHIGH", "low: " + mChart.getLowestVisibleXIndex() + ", high: " + mChart.getHighestVisibleXIndex());
        Log.i("MIN MAX", "xmin: " + mChart.getXChartMin() + ", xmax: " + mChart.getXChartMax() + ", ymin: " + mChart.getYChartMin() + ", ymax: " + mChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        mySound.release();
        Log.i("Nothing selected", "Nothing selected.");
    }
}
