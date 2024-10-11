package Air.FourCore;

import org.bukkit.ChatColor;

public class TimeUtility {
    public static String secToTime(long sec){
        String result = ChatColor.GOLD+"";
        int day = (int) (sec * 0.0000115740740740740740740740740740);
        int hour = (int) (sec * 0.00027777777777777777777777777777 - (day * 24));                // 1/3600
        int min = (int) (sec * 0.0166666666666666666666666666666 - (hour * 60) - (day * 1440));    // 1/60
        sec = sec - (min * 60L) - (hour * 3600L) - (day * 86400L);

        if(day > 0)
            result += day+"일 ";
        if(hour > 0)
            result += hour+"시간 ";
        if(min > 0)
            result += min+"분 ";
        result += sec+"초"+ChatColor.RESET;

        return result;
    }
}
