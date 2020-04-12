package zbv5.cn.XiaoBind.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{
    public static String getDate(String s)
    {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(s);
        return dateFormat.format(date);
    }
}
