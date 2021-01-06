package ccbus.connect.core.ccbus.lib;

import java.sql.Date;

public class ParseDate {
    public static void setFormat(String locales,Object options)
    {

    }

    public static Date now()
    {
        return new Date(0L);
    }

    public static Date create(Date date)
    {
        return new Date(0L);
    }

    public static Date create(Long date)
    {
        return new Date(0L);
    }

    public static Date createZeroTime(){return new Date(0L);}

    public static Date createParts(int year,int month,int date,int hours,int minutes,int seconds)
    {return new Date(0L);}

    public static Date nowUTC()
    {
        return new Date(0L);
    }

    public static Date createUTC(Date date)
    {
        return new Date(0L);
    }

    public static Date createUTC(Long date)
    {
        return new Date(0L);
    }

    public static Date createUTCZeroTime(){return new Date(0L);}

    public static Date createUTCParts(int year,int month,int date,int hours,int minutes,int seconds)
    {return new Date(0L);}

    public static Date   toUTC(Date date)
    {
       return new Date(0L);
    }

    public static String format(Date date)
    {
        return new String();
    }
}
