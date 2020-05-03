package com.rtugeek.datepicker.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;

import com.rtugeek.datepicker.R;
import com.rtugeek.datepicker.bizs.calendars.Lunar;
import com.rtugeek.datepicker.bizs.calendars.LunarSolarConverter;
import com.rtugeek.datepicker.bizs.calendars.Solar;
import com.rtugeek.datepicker.bizs.decors.DPDecor;
import com.rtugeek.datepicker.bizs.languages.DPLManager;
import com.rtugeek.datepicker.bizs.themes.DPTManager;
import com.rtugeek.datepicker.bizs.themes.DPTheme;
import com.rtugeek.datepicker.cons.DPMode;
import com.rtugeek.datepicker.cons.DateType;
import com.rtugeek.datepicker.utils.MeasureUtil;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * DatePicker
 *
 * @author AigeStudio 2015-06-29
 */
public class DatePicker extends LinearLayout {
    private DPTManager mTManager;// 主题管理器
    private DPLManager mLManager;// 语言管理器
    private MonthView monthView;// 月视图
    private TextView tvConfirm, tvYear, tvDate1, tvDate2;// 年份 月份显示
    private short[] years = new short[199];
    private String[] yearsString = new String[199];
    private final short startYear = 1901;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日", Locale.CHINA);
    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy年", Locale.CHINA);
    private AbsoluteSizeSpan solarSizeSpan = new AbsoluteSizeSpan(18, true);
    private AbsoluteSizeSpan lunarSizeSpan = new AbsoluteSizeSpan(14, true);
    private OnDatePickedListener onDatePickedListener;// 日期多选后监听
    private OnDateSelectedListener onDateSelectedListener;// 日期多选后监听
    private int dateType = 0;
    private Solar selectedSolar;
    AlertDialog yearPicker;
    private boolean showGuide = false;

    /**
     * 日期选择回调
     */
    public interface OnDatePickedListener {
        /**
         * @param solar          返回的阳历日期
         * @param targetDateType 选择的是农历还是阳历,如果是农历,要将返回的{@link Solar}转为{@link Lunar}
         * @see LunarSolarConverter
         */
        void onDatePicked(Solar solar, @DateType int targetDateType);
    }

    /**
     * 日期多选监听器
     */
    public interface OnDateSelectedListener {
        void onDateSelected(List<String> date);
    }

    public DatePicker(Context context) {
        this(context, null, DateType.SOLAR);
    }

    public DatePicker(Context context, DPTheme dpTheme, @DateType final int dateType) {
        this(context, null, dpTheme, dateType);
    }

    public DatePicker(final Context context, AttributeSet attrs, DPTheme dpTheme, @DateType final int dateType) {
        super(context, attrs);
        mTManager = DPTManager.getInstance();
        mTManager.initCalendar(dpTheme);
        mLManager = DPLManager.getInstance();
        // 设置排列方向为竖向
        setOrientation(VERTICAL);

        LayoutParams llParams =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        // 标题栏根布局
        RelativeLayout rlTitle = new RelativeLayout(context);
        rlTitle.setBackgroundColor(mTManager.colorTitleBG());
        int rlTitlePadding = MeasureUtil.dp2px(context, 10);
        rlTitle.setPadding(rlTitlePadding, rlTitlePadding, rlTitlePadding, rlTitlePadding);

        // 周视图根布局
        LinearLayout llWeek = new LinearLayout(context);
        llWeek.setBackgroundColor(mTManager.colorTitleBG());
        llWeek.setOrientation(HORIZONTAL);
        int llWeekPadding = MeasureUtil.dp2px(context, 5);
        llWeek.setPadding(0, llWeekPadding, 0, llWeekPadding);
        LayoutParams lpWeek = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpWeek.weight = 1;

        // 标题栏子元素布局参数
        RelativeLayout.LayoutParams lpDateType =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, MeasureUtil.dp2px(context, 22));
        lpDateType.addRule(RelativeLayout.CENTER_VERTICAL);
        RelativeLayout.LayoutParams lpDate =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpDate.addRule(RelativeLayout.CENTER_IN_PARENT);
        RelativeLayout.LayoutParams lpEnsure =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpEnsure.addRule(RelativeLayout.CENTER_VERTICAL);
        lpEnsure.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        // --------------------------------------------------------------------------------标题栏

        short year = startYear;
        for (int i = 0; i < years.length; i++) {
            years[i] = year;
            yearsString[i] = String.valueOf(year);
            year++;
        }

        yearPicker = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.rtugeek_datepicker_year))
                .setItems(yearsString, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setDate(years[i], monthView.getCurrentMonth(), monthView.getSelectedDayOfMonth());
                    }
                }).create();

        View titleView = LayoutInflater.from(context).inflate(R.layout.rtugeek_datepicker_title, null);
        titleView.findViewById(R.id.root).setBackgroundColor(mTManager.colorTitleBG());
        ((ImageView) titleView.findViewById(R.id.img_swap)).setColorFilter(mTManager.colorTitle());
        //年份
        tvYear = titleView.findViewById(R.id.tv_year);
        tvYear.setTextColor(mTManager.colorTitle());
        tvYear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                yearPicker.show();
                yearPicker.getListView().setSelection(monthView.getCurrentYear() - startYear);
            }
        });

        // 公历，阳历
        tvDate1 = titleView.findViewById(R.id.tv_date);
        tvDate1.setTextColor(mTManager.colorTitle());

        tvDate2 = titleView.findViewById(R.id.tv_date_2);
        tvDate2.setTextColor(mTManager.colorTitle());


        SwapDateTypeListener swapDateTypeListener = new SwapDateTypeListener();
        tvDate2.setOnClickListener(swapDateTypeListener);
        tvDate1.setOnClickListener(swapDateTypeListener);

        //确定按钮
        tvConfirm = titleView.findViewById(R.id.tv_confirm);
        tvConfirm.setTextColor(mTManager.colorTitle());
        tvConfirm.setText(context.getString(android.R.string.ok));
        tvConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDatePickedListener != null) {
                    onDatePickedListener.onDatePicked(selectedSolar, DatePicker.this.dateType);
                }
            }
        });

        addView(titleView, llParams);

        // --------------------------------------------------------------------------------周视图
        for (int i = 0; i < mLManager.titleWeek().length; i++) {
            TextView tvWeek = new TextView(context);
            tvWeek.setText(mLManager.titleWeek()[i]);
            tvWeek.setGravity(Gravity.CENTER);
            tvWeek.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            tvWeek.setTextColor(mTManager.colorTitle());
            llWeek.addView(tvWeek, lpWeek);
        }
        addView(llWeek, llParams);

        // ------------------------------------------------------------------------------------月视图
        monthView = new MonthView(context);
        monthView.setOnDateChangeListener(new MonthView.OnDateChangeListener() {
            @Override
            public void onMonthChange(int month) {
                updateDateText();
            }

            @Override
            public void onYearChange(int year) {
                updateDateText();
            }

            @Override
            public void onDateChange(int dayOfMonth) {
                updateDateText();
            }
        });
        addView(monthView, llParams);

        setDateType(dateType);
    }

    private class SwapDateTypeListener implements OnClickListener {

        @Override
        public void onClick(View view) {

            if (dateType == DateType.LUNAR) {
                DatePicker.this.dateType = DateType.SOLAR;
            } else {
                DatePicker.this.dateType = DateType.LUNAR;
            }
            updateDateText();
        }
    }

    public void updateDateText() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(monthView.getCurrentYear(), monthView.getCurrentMonth() - 1,
                monthView.getSelectedDayOfMonth());

        selectedSolar = new Solar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1
                , calendar.get(Calendar.DAY_OF_MONTH));
        Lunar lunar = LunarSolarConverter.SolarToLunar(selectedSolar);

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(simpleDateFormat.format(new Date(calendar.getTimeInMillis()))).append(" ");

        int solarLength = builder.length();

        builder.append(lunar.toStringWithoutYear());

        builder.setSpan(solarSizeSpan, 0, solarLength, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(lunarSizeSpan, solarLength, builder.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvYear.setText(monthView.getCurrentYear() + "年");
        String solarDate = dateFormat.format(new Date(calendar.getTimeInMillis()));
        String lunarDate = lunar.toStringWithoutYear();
        if (dateType == DateType.LUNAR) {
            tvDate1.setText(lunarDate);
            tvDate2.setText(solarDate);
        } else {
            tvDate1.setText(solarDate);
            tvDate2.setText(lunarDate);
        }
    }

    /**
     * 设置初始化年月日期
     *
     * @param year  ...
     * @param month ...
     */
    public void setDate(int year, int month, int monthOfDay) {
        if (month < 1) {
            month = 1;
        }
        if (month > 12) {
            month = 12;
        }
        monthView.setDate(year, month, monthOfDay);
        updateDateText();
    }

    public void setDPDecor(DPDecor decor) {
        monthView.setDPDecor(decor);
    }

    /**
     * 设置日期选择模式
     *
     * @param mode ...
     */
    public void setMode(DPMode mode) {
        monthView.setDPMode(mode);
    }

    public void setFestivalDisplay(boolean isFestivalDisplay) {
        monthView.setFestivalDisplay(isFestivalDisplay);
    }

    public void setTodayDisplay(boolean isTodayDisplay) {
        monthView.setTodayDisplay(isTodayDisplay);
    }

    public void setHolidayDisplay(boolean isHolidayDisplay) {
        monthView.setHolidayDisplay(isHolidayDisplay);
    }

    public void setDeferredDisplay(boolean isDeferredDisplay) {
        monthView.setDeferredDisplay(isDeferredDisplay);
    }

    /**
     * 设置多选监听器
     *
     * @param onDateSelectedListener ...
     */
    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
        if (monthView.getDPMode() != DPMode.MULTIPLE) {
            throw new RuntimeException(
                    "Current DPMode does not MULTIPLE! Please call setMode set DPMode to MULTIPLE!");
        }
        this.onDateSelectedListener = onDateSelectedListener;
    }

    public int getDateType() {
        return dateType;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }

    public void setOnDatePickedListener(DatePicker.OnDatePickedListener onDatePickedListener) {
        this.onDatePickedListener = onDatePickedListener;
    }

}
