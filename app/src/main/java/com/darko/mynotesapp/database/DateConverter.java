package com.darko.mynotesapp.database;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    //METHOD THAT TAKES A LONG NUMBER AND CONVERTS IT TO A DATE OBJECT
    @TypeConverter
    public static Date toDate(Long timeStamp){
        return timeStamp == null ? null : new Date(timeStamp);
    }

    //METHOD THAT TAKES A DATE OBJECT AND CONVERTS IT TO A LONG NUMBER
    @TypeConverter
    public static Long toTimeStamp(Date date){
        return date == null ? null : date.getTime();
    }
}