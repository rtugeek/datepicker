package cn.aigestudio.datepicker.bizs.calendars;

import org.junit.Test;

import static org.junit.Assert.*;

public class LunarSolarConverterTest {

    @Test
    public void solarToLunar() {
        Solar solar = new Solar(2044,8,8);
        Lunar lunar = LunarSolarConverter.SolarToLunar(solar);
        System.out.println(lunar);
    }
}