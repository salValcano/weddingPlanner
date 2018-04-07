package com.example.deepanshub.weddingplanner.budgetPlanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.example.deepanshub.weddingplanner.R;

public class BudgetCalc extends AppCompatActivity {

    EditText budget, catering, venue, dj, photographer, officiate, miscellaneous;
    TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_calc);
        budget = findViewById(R.id.budget);
        catering = findViewById(R.id.catering);
        venue = findViewById(R.id.venue);
        dj = findViewById(R.id.dj);
        photographer = findViewById(R.id.photographer);
        officiate = findViewById(R.id.officiate);
        miscellaneous = findViewById(R.id.miscellaneous);
        total = findViewById(R.id.total);


    }
}
