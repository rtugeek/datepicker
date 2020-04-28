package com.rtugeek.datepicker

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import cn.aigestudio.datepicker.bizs.themes.ITimeTheme
import cn.aigestudio.datepicker.cons.DPMode
import cn.aigestudio.datepicker.cons.DateType
import cn.aigestudio.datepicker.views.DatePicker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        tv_pick.setOnClickListener {

            val momentTheme = ITimeTheme(Color.BLACK)
            val datePicker = DatePicker(this, momentTheme, DateType.SOLAR)
            datePicker.setDate(2020, 4,5)
            datePicker.setMode(DPMode.SINGLE)
            val chineseDatePickerDialog = AlertDialog.Builder(this).create()
            chineseDatePickerDialog.show()
            val params = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            chineseDatePickerDialog.window!!.setContentView(datePicker, params)
            chineseDatePickerDialog.window!!.setGravity(Gravity.CENTER)
        }
    }
}
