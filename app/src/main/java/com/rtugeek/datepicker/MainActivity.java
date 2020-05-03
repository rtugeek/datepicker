package com.rtugeek.datepicker;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.rtugeek.datepicker.bizs.calendars.Solar;
import com.rtugeek.datepicker.cons.DPMode;
import com.rtugeek.datepicker.cons.DateType;
import com.rtugeek.datepicker.views.DatePicker;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       final AlertDialog chineseDatePickerDialog =new AlertDialog.Builder(MainActivity.this).create();

        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_pick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ITimeTheme theme = new ITimeTheme(Color.BLACK);
                DatePicker datePicker = new DatePicker(MainActivity.this, theme, DateType.SOLAR);
                datePicker.setDate(2020, 4, 5);
                datePicker.setMode(DPMode.SINGLE);
                chineseDatePickerDialog.show();
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                chineseDatePickerDialog.getWindow().setContentView(datePicker, params);
                chineseDatePickerDialog.getWindow().setGravity(Gravity.CENTER);
                datePicker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
                    @Override
                    public void onDatePicked(Solar solar, int targetDateType) {
                        System.out.println(solar.toString()+"-"+targetDateType);
                        chineseDatePickerDialog.dismiss();
                    }
                });

            }
        });
    }
}
