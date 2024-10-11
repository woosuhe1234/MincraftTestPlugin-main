package Air.FourCore.management;

import org.bukkit.ChatColor;

public class ManagementUtility {
    public static String manage(String str) {
        return ChatColor.translateAlternateColorCodes('&', "&f&l[ &b관리 &f&l] &f" + str);
    }
}
