package com.example.myapplication.myapplication;

import android.app.ActionBar;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    // called in both onCreate and onResume
    void loadGraph() {
        GraphView graph = (GraphView) findViewById(R.id.graph);

        DatabaseHandler db = new DatabaseHandler(this);
        List<Rating> ratings = db.getLastRatings();
        List<DataPoint> dataPointList = new ArrayList<DataPoint>();
        for (Rating rat : ratings) {
            dataPointList.add(new DataPoint(new Date(rat.date * 1000), rat.rating));
        }
        DataPoint[] data = dataPointList.toArray(new DataPoint[dataPointList.size()]);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(data);

        series.setColor(android.graphics.Color.parseColor("#3D5AFE"));
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
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
        staticLabelsFormatter.setVerticalLabels(new String[] {"", "", "", ""});
        staticLabelsFormatter.setDynamicLabelFormatter(new DateAsXAxisLabelFormatter(this));
        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getGridLabelRenderer().setNumHorizontalLabels(4); // only 4 because of the space
        graph.getGridLabelRenderer().setNumVerticalLabels(3);
        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(dataPointList.get(0).getX());
        graph.getViewport().setMaxX(dataPointList.get(dataPointList.size() - 1).getX());
        graph.getViewport().setXAxisBoundsManual(true);

        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        DatabaseHandler db = new DatabaseHandler(this);
//        String ans = db.getAnswers(0, 0);
//        Toast.makeText(getApplicationContext(),
//                ans, Toast.LENGTH_LONG).show();
        String result;

        result = db.addRating(new Answer(1490875200, 0, 4));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addRating(new Answer(1490961600, 0, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addRating(new Answer(1491048000, 0, 3));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addRating(new Answer(1491134400, 0, 2));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addRating(new Answer(1491220800, 0, 0));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addRating(new Answer(1491307200, 0, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addRating(new Answer(1491393600, 0, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addRating(new Answer(1491480000, 0, 2));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }

        result = db.addAnswer(new Answer(1490875200, 1, 2));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1490875200, 2, 2));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1490875200, 3, 2));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1490875200, 4, 2));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1490875200, 5, 2));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1490875200, 6, 2));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }

        result = db.addAnswer(new Answer(1490961600, 1, 0));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1490961600, 2, 1));if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1490961600, 3, 0));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1490961600, 4, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1490961600, 5, 0));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1490961600, 6, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }

        result = db.addAnswer(new Answer(1491048000, 1, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491048000, 2, 2));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491048000, 3, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491048000, 4, 2));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491048000, 5, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491048000, 6, 2));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }

        result = db.addAnswer(new Answer(1491134400, 1, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491134400, 2, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491134400, 3, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491134400, 4, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491134400, 5, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491134400, 6, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }

        result = db.addAnswer(new Answer(1491220800, 1, 0));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491220800, 2, 0));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491220800, 3, 0));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491220800, 4, 0));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491220800, 5, 0));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491220800, 6, 0));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }

        result = db.addAnswer(new Answer(1491307200, 1, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491307200, 2, 0));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491307200, 3, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491307200, 4, 0));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491307200, 5, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491307200, 6, 0));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }

        result = db.addAnswer(new Answer(1491393600, 1, 0));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491393600, 2, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491393600, 3, 0));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491393600, 4, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491393600, 5, 0));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491393600, 6, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }

        result = db.addAnswer(new Answer(1491480000, 1, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491480000, 2, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491480000, 3, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491480000, 4, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491480000, 5, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }
        result = db.addAnswer(new Answer(1491480000, 6, 1));
        if (result != null && !result.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "error adding to answers database: " + result,
                    Toast.LENGTH_SHORT).show();
        }

        loadGraph();

        findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewingActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.imageButton9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddQuestionActivity.class);
                startActivity(intent);
            }
        });

        if (db.didRateToday()) {
            findViewById(R.id.imageButton2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "You've already journalled today!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            findViewById(R.id.imageButton2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), QuestionairreActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadGraph();
    }
}