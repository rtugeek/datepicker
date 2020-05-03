package com.rtugeek.datepicker;

import android.graphics.Color;

import com.rtugeek.datepicker.bizs.themes.DPCNTheme;

/**
 * @author Jack Fu <rtugeek@gmail.com>
 * @date 2015/8/29
 * @description
 */
public class ITimeTheme extends DPCNTheme {
    private int colorTitleBg = 0;

    public ITimeTheme(int colorTitleBg) {
        this.colorTitleBg = colorTitleBg;
    }

    @Override
    public int colorBG() {
        return super.colorBG();
    }

    @Override
    public int colorDeferred() {
        return super.colorBG();
    }

    @Override
    public int colorL() {
        return super.colorL();
    }

    @Override
    public int colorTitleBG() {
        if(colorTitleBg == 0) return super.colorTitleBG();
        return colorTitleBg;
    }

    @Override
    public int colorBGCircle() {
        return super.colorBGCircle();
    }

    @Override
    public int colorTitle() {
        return super.colorTitle();
    }

    @Override
    public int colorToday() {
        if(colorTitleBg == 0)  return super.colorToday();
        return getAlphaColor(colorTitleBg);
    }

    @Override
    public int colorG() {
        return super.colorG();
    }

    @Override
    public int colorF() {
        return super.colorF();
    }

    @Override
    public int colorWeekend() {
        return super.colorWeekend();
    }

    @Override
    public int colorHoliday() {
        return super.colorBG();
    }


    private int getAlphaColor(int color){
        return Color.argb(130, Color.red(color), Color.green(color),Color.blue(color));
    }
}
