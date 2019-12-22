package cn.aigestudio.datepicker;


import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.runner.AndroidJUnit4;
import cn.aigestudio.datepicker.bizs.calendars.DPCNCalendar;
@RunWith(AndroidJUnit4.class)
public class DPCNCalendarTest {

    @Test
    public void buildMonthLunar() {
        DPCNCalendar calendar = new DPCNCalendar();
        String[][] strings = calendar.buildMonthLunar(2018, 11);
        System.out.println(strings);
    }
}