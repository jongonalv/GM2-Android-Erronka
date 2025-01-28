package com.ikaslea.komertzialakapp.utils;

import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import com.j256.ormlite.field.FieldType;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class LocalDateTimeLongType extends BaseDataType {

    private static final LocalDateTimeLongType singleTon = new LocalDateTimeLongType();

    public static LocalDateTimeLongType getSingleton() {
        return singleTon;
    }

    private LocalDateTimeLongType() {
        super(SqlType.LONG, new Class<?>[] { LocalDateTime.class });
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        return LocalDateTime.ofEpochSecond(Long.parseLong(defaultStr), 0, ZoneOffset.UTC);
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        long timestamp = results.getLong(columnPos);
        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC);
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        LocalDateTime localDateTime = (LocalDateTime) javaObject;
        return localDateTime.toEpochSecond(ZoneOffset.UTC);
    }
}
