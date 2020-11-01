package com.group11.cmpt276_project.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.group11.cmpt276_project.R;

public class RestuarantDetailActivity extends AppCompatActivity {

    public static Intent startActivity(Context context, int index) {
        Intent intent = new Intent(context, RestuarantDetailActivity.class);
        intent.putExtra("index", index);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restuarant_detail);
    }
}