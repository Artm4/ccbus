package ccbus.system.misc;

public class CString {
    public static String rtrim(String str,String ch)
    {
        if(str.length()>0 && str.endsWith(ch))
        {
            return str.substring(0,str.lastIndexOf(ch));
        }
        return str;
    }

    public static String ltrim(String str,String ch)
    {
        if(str.length()>0 && str.startsWith(ch))
        {
            return str.substring(str.indexOf(ch));
        }
        return str;
    }

    public static String trim(String str,String ch)
    {
        return ltrim(rtrim(str,ch),ch);
    }

    public static String trim(String str,String... ch)
    {
        String result=str;
        for(int i=0;i<ch.length;i++)
        {
            result=trim(result,ch[i]);
        }
        return result;
    }
}
