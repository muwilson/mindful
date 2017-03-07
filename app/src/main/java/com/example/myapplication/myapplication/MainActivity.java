package com.example.myapplication.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.graphics.Color.GREEN;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // generate Dates
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -10);
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d4 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d5 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d6 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d7 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d8 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d9 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d10 = calendar.getTime();

        GraphView graph = (GraphView) findViewById(R.id.graph);

        // you can directly pass Date objects to DataPoint-Constructor
        // this will convert the Date to double via Date#getTime()
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(d1, 1),
                new DataPoint(d2, 10),
                new DataPoint(d3, 3),
                new DataPoint(d4, 7),
                new DataPoint(d5, 2),
                new DataPoint(d6, 12),
                new DataPoint(d7, 11),
                new DataPoint(d8, 4),
                new DataPoint(d9, 6),
                new DataPoint(d10, 1)
        });
        series.setColor(GREEN);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(20);
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, final DataPointInterface dataPoint) {
                runOnUiThread( new Runnable() {
                    public void run()
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        Toast.makeText(getApplicationContext(), sdf.format(dataPoint.getX()), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        graph.addSeries(series);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setVerticalLabels(new String[] {"Bad","Meh", "Good", "Good"});
        staticLabelsFormatter.setDynamicLabelFormatter(new DateAsXAxisLabelFormatter(this));
        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getGridLabelRenderer().setNumHorizontalLabels(4); // only 4 because of the space
        graph.getGridLabelRenderer().setNumVerticalLabels(3);
        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d10.getTime());
        graph.getViewport().setXAxisBoundsManual(true);

        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);

        findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewingActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.imageButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuestionairreActivity.class);
                startActivity(intent);
            }
        });

    }
}