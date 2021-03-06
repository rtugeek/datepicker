package com.rtugeek.datepicker.entities;

/**
 * 日历数据实体
 * 封装日历绘制时需要的数据
 * <p>
 * Entity of calendar
 *
 * @author AigeStudio 2015-03-26
 */
public class DPInfo {
    public String strGregorian, strFestival, strLunar;
    public boolean isHoliday;
    public boolean isToday, isWeekend;
    public boolean isSolarTerms, isFestival, isDeferred;
    public boolean isDecorBG;
    public boolean isDecorTL, isDecorT, isDecorTR, isDecorL, isDecorR;
}