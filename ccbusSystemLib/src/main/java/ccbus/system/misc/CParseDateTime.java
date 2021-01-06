package ccbus.system.misc;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public class CParseDateTime
{
    public static DateTimeFormatter formatterDate = DateTimeFormat.forPattern("dd/MM/yyyy").withZone(DateTimeZone.UTC);

    public static DateTimeFormatter formatterDateTime = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").withZone(DateTimeZone.UTC);

    public static DateTimeFormatter formatterDateISO = DateTimeFormat.forPattern("yyyy-MM-dd").withZone(DateTimeZone.UTC);

    public static DateTimeFormatter formatterDateTimeISO = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(DateTimeZone.UTC);


    public static Date parseToDate(String date)
    {
        return formatterDate.parseDateTime(date).toDate();
    }

    public static DateTime parseJodaDate(String date)
    {
        return formatterDate.parseDateTime(date);
    }

    public static Date parseToDateTime(String dateTime)
    {
        return formatterDateTime.parseDateTime(dateTime).toDate();
    }

    public static DateTime parseJodaDateTime(String dateTime)
    {
        return formatterDateTimeISO.parseDateTime(dateTime);
    }

    public static Date parseToDateISO(String date)
    {
        return formatterDateISO.parseDateTime(date).toDate();
    }

    public static DateTime parseJodaDateISO(String date)
    {
        return formatterDateISO.parseDateTime(date);
    }

    public static Date parseToDateTimeISO(String dateTime)
    {
        return formatterDateTimeISO.parseDateTime(dateTime).toDate();
    }

    public static DateTime parseJodaDateTimeISO(String dateTime)
    {
        return formatterDateTimeISO.parseDateTime(dateTime);
    }


    public static DateTime toDateTime(Date date)
    {
        return (new DateTime(date)).withZone(DateTimeZone.UTC);
    }
    public static DateTime now()
    {
        return DateTime.now().withZone(DateTimeZone.UTC);
    }

    public static DateTime currentMonday()
    {
        return now().weekOfWeekyear().roundFloorCopy();
    }

    public static double timeToFloat(DateTime date)
    {
        return date.getHourOfDay()+date.getMinuteOfHour()/60.0;
    }

    public static double timeToFloat(Date date)
    {
        return timeToFloat(toDateTime(date));
    }

    public static DateTime floatToDateTime(double time)
    {
        DateTime date=now();
        date=date
            .withHourOfDay((int)(Math.floor(time)))
            .withMinuteOfHour((int)(60.0*(time-Math.floor(time))))
            .withSecondOfMinute(0)
            .withMillisOfSecond(0);
        return date;
    }

    public static Date floatToDate(double time)
    {
        return floatToDateTime(time).toDate();
    }
}
