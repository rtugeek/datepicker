package cn.aigestudio.datepicker.bizs.calendars;


import java.util.Date;

/**
 * Created by leon on 16-3-22.
 */
public class Lunar {
    /**
     * 是否是闰月
     */
    public boolean isLeap;
    public int dayOfMonth;
    public int month;
    public int year;
    public final static String CHINESE_MONTH[] = {"一", "二", "三", "四", "五", "六", "七", "八", "九",
            "十", "冬", "腊"};
    public final static String CHINESE_NUMBER[] = {"〇", "一", "二", "三", "四", "五", "六", "七",
            "八", "九"};
    public final static String NUMBER = "0123456789";

    public Lunar() {
    }

    public Lunar(int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    public Lunar(int year, int month, int dayOfMonth, boolean isLeap) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.isLeap = isLeap;
    }

    public boolean isleap() {
        return isLeap;
    }

    public void setLeap(boolean leap) {
        this.isLeap = leap;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(year + "年");
        if (isLeap) {
            stringBuilder.append("闰");
        }
        stringBuilder.append(CHINESE_MONTH[month - 1]).append("月").append(getChinaDayString(dayOfMonth));
        return stringBuilder.toString();
    }

    public String toStringWithoutYear() {
        StringBuilder stringBuilder = new StringBuilder();
        if (isLeap) {
            stringBuilder.append("闰");
        }
        stringBuilder.append(CHINESE_MONTH[month - 1]).append("月").append(getChinaDayString(dayOfMonth));
        return stringBuilder.toString();
    }

    @Deprecated
    public Date toDate() {
        return LunarSolarConverter.toDate(year, month, dayOfMonth);
    }


    public static String numberToChinaFormat(String number) {
        StringBuilder result = new StringBuilder();
        for (char c : number.toCharArray()) {
            result.append(CHINESE_NUMBER[NUMBER.indexOf(c)]);
        }
        return result.toString();
    }


    private static String getChinaDayString(int day) {
        if (day > 30) {
            return "";
        }
        if (day == 10) {
            return "初十";
        } else if (day == 20) {
            return "廿十";
        } else if (day == 30) {
            return "三十";
        } else {
            String chineseTen[] = {"初", "十", "廿", "三"};
            int n = day % 10 == 0 ? 9 : day % 10;
            return chineseTen[day / 10] + CHINESE_NUMBER[n];
        }
    }
}
