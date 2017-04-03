package com.example.myapplication.myapplication;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.app.ListActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.Utils;

import static com.github.mikephil.charting.components.Legend.LegendPosition.PIECHART_CENTER;

public class ViewingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public int day_quality = 0;
    public int question_id = 0;
    public Set day_qualities;
    public Set questions;

    PieChart pieChart;
    List<PieEntry> entries;
    PieDataSet set;
    PieData data;

    // load the pie chart with the data of the spinners
    public void load_pie_graph() {
        // get pie chart
        pieChart = (PieChart) findViewById(R.id.chart1);
        String question_type;
        // get strings for the pie chart
        String type[];
        if (question_id == 0) {
            type = getResources().getStringArray(R.array.stress_levels);
            question_type = "Stress levels";
        } else if (question_id == 1) {
            type = getResources().getStringArray(R.array.diet_levels);
            question_type = "Diet levels";
        } else if (question_id == 2) {
            type = getResources().getStringArray(R.array.sleep_quality);
            question_type = "Sleep quality";
        } else if (question_id == 3) {
            type = getResources().getStringArray(R.array.work_quality);
            question_type = "Work quality";
        } else if (question_id == 4) {
            type = getResources().getStringArray(R.array.exercise_levels);
            question_type = "Exercise levels";
        } else {    // question_id == 5
            type = getResources().getStringArray(R.array.friends_talked_to);
            question_type = "# of friends talked to";
        }

        // get database
        DatabaseHandler db = new DatabaseHandler(this);
        List<Answer> answers = db.getAnswers(question_id + 1, day_quality);

        int response_frequency[] = new int[3];     // default initial value to zero
        for (Answer answer : answers) {
            ++response_frequency[answer.response];     // store the # of each response to this particular question
        }
        ArrayList<Integer> colors = new ArrayList<>();
        entries = new ArrayList<>();
        if (((float)response_frequency[0] / (float)answers.size()) * 100 > 0) {
            entries.add(new PieEntry((((float)response_frequency[0] / (float)answers.size()) * 100), type[0]));
            colors.add(ContextCompat.getColor(this, R.color.red));
        }
        if (((float)response_frequency[1] / (float)answers.size()) * 100 > 0) {
            entries.add(new PieEntry((((float)response_frequency[1] / (float)answers.size()) * 100), type[1]));
            colors.add(ContextCompat.getColor(this, R.color.yellow));
        }
        if (((float)response_frequency[2] / (float)answers.size()) * 100 > 0) {
            entries.add(new PieEntry((((float)response_frequency[2] / (float)answers.size()) * 100), type[2]));
            colors.add(ContextCompat.getColor(this, R.color.green));
        }

        // establish data for the pie chart
        set = new PieDataSet(entries, question_type + " on a " + getResources().getStringArray(R.array.day_qualities)[day_quality] + " day");
        set.setColors(colors);
        set.setValueTextSize(18f);
        set.setValueFormatter(new PercentFormatter());
        data = new PieData(set);
        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);
        pieChart.setNoDataText("There is no data for this question yet!");
        pieChart.setHoleRadius(60f);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setEntryLabelColor(Color.BLACK);

        // put legend in center of pie chart
        Legend legend = pieChart.getLegend();
        legend.setPosition(Legend.LegendPosition.PIECHART_CENTER);
        // reload pie chart
        pieChart.invalidate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner1Items, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner = (Spinner) findViewById(R.id.spinner2);
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner2Items, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // populate the sets
        String[] quality_array = getResources().getStringArray(R.array.spinner1Items);
//        ArrayAdapter<String> string_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, quality_array);
//        ListView lv = (ListView)findViewById(R.id)
        day_qualities = new HashSet(Arrays.asList(quality_array));
        String[] question_array = getResources().getStringArray(R.array.spinner2Items);
//        string_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, question_array);
//        for (int i = 0; i < string_adapter.getCount(); ++i) {
//            questions.add(string_adapter.getItem(i));
//        }
        questions = new HashSet(Arrays.asList(question_array));

        load_pie_graph();
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
//        Toast.makeText(parent.getContext(),
//                "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString() + ", id: " + Long.toString(id),
//                Toast.LENGTH_SHORT).show();

        // get database
//        DatabaseHandler db = new DatabaseHandler(this);
        if (day_qualities.contains(parent.getItemAtPosition(pos).toString())) {
            // changing quality of day
            day_quality = pos;
        } else {    // questions.contains(parent.getItemAtPosition(pos).toString()) == true
            // changing the question type
            question_id = pos;
        }
//        List<Answer> answers = db.getAnswers(question_id + 1, day_quality);
//        int response_frequency[] = new int[3];     // default initial value to zero
//        for (Answer answer : answers) {
//            ++response_frequency[answer.response];     // store the # of each response to this particular question
//        }

//        Toast.makeText(parent.getContext(),
//                "answers.size(): " + Integer.toString(answers.size()), Toast.LENGTH_SHORT).show();
        load_pie_graph();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        return;
    }
}
