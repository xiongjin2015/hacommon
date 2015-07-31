package com.haha.common.utils;

import java.util.Calendar;

public final class HaTime {
    
    public static int getCurrentYearInt(){
        return Calendar.getInstance().get(Calendar.YEAR);
    }
    
    public static String getCurrentYearString(){
        return String.valueOf(getCurrentYearInt());
    }

}
