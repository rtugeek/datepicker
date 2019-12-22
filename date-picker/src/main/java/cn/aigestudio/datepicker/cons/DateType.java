package cn.aigestudio.datepicker.cons;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

import static cn.aigestudio.datepicker.cons.DateType.LUNAR;
import static cn.aigestudio.datepicker.cons.DateType.SOLAR;

@Retention(RetentionPolicy.SOURCE)
@IntDef(value = {SOLAR, LUNAR})
public @interface DateType {
    int LUNAR = 1;
    int SOLAR = 0;
}
