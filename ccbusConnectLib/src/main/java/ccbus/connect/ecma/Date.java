package ccbus.connect.ecma;

public class Date
{
    /**
     * Constructor
     * @param dateString A string value representing a date, specified in a format recognized by the Date.parse() method (these formats are IETF-compliant RFC 2822 timestamps and also strings in a version of ISO8601).
     */
    public Date(String dateString){}

    /**
     * Constructor
     * @param value An integer value representing the number of milliseconds since January 1, 1970, 00:00:00 UTC (the Unix epoch), with leap seconds ignored. Keep in mind that most Unix Timestamp functions are only accurate to the nearest second.
     */
    public Date(int value){}
    /**
     * Constructor
     */
    public Date(int year,int monthIndex){}
    /**
     * Constructor
     */
    public Date(int year,int monthIndex,int day){}
    /**
     * Constructor
     */
    public Date(int year,int monthIndex,int day ,int hours){}
    /**
     * Constructor
     */
    public Date(int year,int monthIndex,int day ,int hours,int minutes){}
    /**
     * Constructor
     */
    public Date(int year,int monthIndex,int day ,int hours,int minutes,int seconds){}
    /**
     * Constructor
     */
    public Date(int year,int monthIndex,int day ,int hours,int minutes,int seconds,int milliseconds){}

    /**
     * Returns the day of the month (1-31) for the specified date according to local time.
     */
    public int getDate(){return 0;}
            
    /**
     * Returns the day of the week (0-6) for the specified date according to local time.
     */
    public int getDay(){return 0;}
            
    /**
     * Returns the year (4 digits for 4-digit years) of the specified date according to local time.
     */
    public int getFullYear(){return 0;}

    /**
     * Returns the hour (0-23) in the specified date according to local time. 
     */
    public int getHours(){return 0;}

    /**
     * Returns the milliseconds (0-999) in the specified date according to local time.
     */
    public int getMilliseconds(){return 0;}
            
    /**
     * Returns the minutes (0-59) in the specified date according to local time.
     */
    public int getMinutes(){return 0;}

    /**
     * Returns the month (0-11) in the specified date according to local time.
     */
    public int getMonth(){return 0;}

    /**
     * Returns the seconds (0-59) in the specified date according to local time.
     */
    public int getSeconds(){return 0;}

    /**
     * Returns the numeric value of the specified date as the number of milliseconds since January 1, 1970, 00:00:00 UTC (negative for prior times).
     * @return
     */
    public int getTime(){return 0;}

    /**
     * Returns the time-zone offset in minutes for the current locale.
     * @return
     */
    public int getTimezoneOffset(){return 0;}

    /**
     * Returns the day (date) of the month (1-31) in the specified date according to universal time.
     * @return
     */
    public int getUTCDate(){return 0;}

    /**
     * Returns the day of the week (0-6) in the specified date according to universal time.
     * @return
     */
    public int getUTCDay(){return 0;}

    /**
     * Returns the year (4 digits for 4-digit years) in the specified date according to universal time.
     * @return
     */
    public int getUTCFullYear(){return 0;}

    /**
     * Returns the hours (0-23) in the specified date according to universal time.
     * @return
     */
    public int getUTCHours(){return 0;}

    /**
     * Returns the milliseconds (0-999) in the specified date according to universal time.
     * @return
     */
    public int getUTCMilliseconds(){return 0;}

    /**
     * Returns the minutes (0-59) in the specified date according to universal time.
     * @return
     */
    public int getUTCMinutes(){return 0;}

    /**
     * Returns the month (0-11) in the specified date according to universal time.
     * @return
     */
    public int getUTCMonth(){return 0;}

    /**
     * Returns the seconds (0-59) in the specified date according to universal time.
     * @return
     */
    public int getUTCSeconds(){return 0;}

    /**
     * Sets the day of the month for a specified date according to local time.
     * @return
     */
    public void setDate(int day){}

    /**
     * Sets the full year (e.g. 4 digits for 4-digit years) for a specified date according to local time.
     * @return
     */
    public void setFullYear(int year){}

    /**
     * Sets the hours for a specified date according to local time.
     * @return
     */
    public void setHours(int hours){}

    /**
     * Sets the milliseconds for a specified date according to local time.
     * @return
     */
    public void setMilliseconds(int seconds){}


    /**
     * Sets the minutes for a specified date according to local time.
     * @return
     */
    public void setMinutes(int minutes){}

    /**
     * Sets the month for a specified date according to local time.
     * @return
     */
    public void setMonth(int months){}

    /**
     * Sets the seconds for a specified date according to local time.
     * @return
     */
    public void setSeconds(int seconds){}

    /**
     * Sets the Date object to the time represented by a number of milliseconds since January 1, 1970, 00:00:00 UTC, allowing for negative numbers for times prior.
     * @return
     */
    public void setTime(int milliseconds){}

    /**
     * Sets the day of the month for a specified date according to universal time.
     * @return
     */
    public void setUTCDate(int dayOfMonth){}

    /**
     * Sets the full year (e.g. 4 digits for 4-digit years) for a specified date according to universal time.
     * @return
     */
    public void setUTCFullYear(int fullYear){}

    /**
     * Sets the hour for a specified date according to universal time.
     * @return
     */
    public void setUTCHours(int minutes){}

    /**
     * Sets the milliseconds for a specified date according to universal time.
     * @return
     */
    public void setUTCMilliseconds(int milliseconds){}

    /**
     * Sets the minutes for a specified date according to universal time.
     * @return
     */
    public void setUTCMinutes(int minutes){}

    /**
     * Sets the month for a specified date according to universal time.
     * @return
     */
    public void setUTCMonth(int month){}


    /**
     * Sets the seconds for a specified date according to universal time.
     * @return
     */
    public void setUTCSeconds(int seconds){}

    /**
     *Converts a date to a string following the ISO 8601 Extended Format.
     * @return
     */
    String toISOString(){return "";}
}
