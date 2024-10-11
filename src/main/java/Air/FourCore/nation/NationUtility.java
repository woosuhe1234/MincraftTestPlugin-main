package Air.FourCore.nation;

import Air.FourCore.user.UserData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NationUtility {

    public static NationBlock getAllCastles(NationData myNation){ // null 이면 모든 성
        List<NationData> result1 = new ArrayList<>();
        List<BlockData> result2 = new ArrayList<>();
        for(NationData nation : NationData.map.values()){
            if(myNation != null && nation.nationName.equalsIgnoreCase(myNation.nationName)) continue;
            for(BlockData b : nation.castles){
                if(b.getBlock() == null) continue;
                result1.add(nation);
                result2.add(b);
            }
        }
        return new NationBlock(result1, result2);
    }

    public static NationBlock getAllOutposts(NationData myNation){ // null 이면 모든 성
        List<NationData> result1 = new ArrayList<>();
        List<BlockData> result2 = new ArrayList<>();
        for(NationData nation : NationData.map.values()){
            if(myNation != null && nation.nationName.equalsIgnoreCase(myNation.nationName)) continue;
            for(BlockData b : nation.outposts){
                if(b.getBlock() == null) continue;
                result1.add(nation);
                result2.add(b);
            }
        }
        return new NationBlock(result1, result2);
    }

    public static NationBlock getAllCastlesWithoutAllie(NationData myNation) {
        List<NationData> result1 = new ArrayList<>();
        List<BlockData> result2 = new ArrayList<>();
        for(NationData nation : NationData.map.values()){
            boolean skip = false;
            if(myNation != null) {
                if( nation.nationName.equalsIgnoreCase(myNation.nationName)) continue;
                for (String allie : myNation.allies) {
                    if (allie.equalsIgnoreCase(nation.nationName))
                        skip = true;
                }
            }
            if(skip) continue;
            for(BlockData b : nation.castles){
                if(b.getBlock() == null) continue;
                result1.add(nation);
                result2.add(b);
            }
        }
        return new NationBlock(result1, result2);
    }

    public static int isCastleArea(NationBlock n, Location loc){
        int protect = 36;
        int x1 = (int)loc.getX(), z1 = (int)loc.getZ();
        for (int i = 0; i < n.block.size(); i++) {
            BlockData b = n.block.get(i);
            int x2 = b.x, z2 = b.z;
            double distance = Math.max(Math.abs(x1 - x2), Math.abs(z1 - z2));
            if (b.world.equalsIgnoreCase(loc.getWorld().getName()) && distance <= protect) {
                return i;
            }
        }
        return -1;
    }

    public static void broadcastNation(String nation, String message){
        broadcastNation(NationData.getNationData(nation), message);
    }
    public static void broadcastNation(NationData nation, String message){
        //message = ChatColor.translateAlternateColorCodes('&',"&9[&f 국가채팅 &9] "+message);
        for(UserData data : nation.getAllPlayers()){
            Player player = Bukkit.getPlayer(data.uuid);
            if(player != null && player.isOnline()){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',message));
            }
        }
    }
    public static void broadcastAlliance(String nation, String message){
        broadcastAlliance(NationData.getNationData(nation), message);
    }
    public static void broadcastAlliance(NationData nation, String message){
        //message = ChatColor.translateAlternateColorCodes('&',"&b[&f 동맹채팅 &b] "+message);
        for(UserData data : nation.getAllPlayers()){
            Player player = Bukkit.getPlayer(data.uuid);
            if(player != null && player.isOnline()){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',message));
            }
        }
        for(String alliance : nation.allies){
            for(UserData data : NationData.getNationData(alliance).getAllPlayers()){
                Player player = Bukkit.getPlayer(data.uuid);
                if(player != null && player.isOnline()){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',message));
                }
            }
        }
    }

    public static void broadcastTitleNation(NationData nation, String title, String desc){
        for(UserData data : nation.getAllPlayers()){
            Player player = Bukkit.getPlayer(data.uuid);
            if(player != null && player.isOnline()){
                player.sendTitle(ChatColor.translateAlternateColorCodes('&',title), ChatColor.translateAlternateColorCodes('&',desc), 5, 80, 20);
            }
        }
    }

    public static void broadcastTitleAlliance(NationData nation, String title, String desc){
        broadcastTitleNation(nation, title, desc);

        for(String alliance : nation.allies){
            for(UserData data : NationData.getNationData(alliance).getAllPlayers()){
                Player player = Bukkit.getPlayer(data.uuid);
                if(player != null && player.isOnline()){
                    player.sendTitle(ChatColor.translateAlternateColorCodes('&',title), ChatColor.translateAlternateColorCodes('&',desc), 5, 80, 20);
                }
            }
        }
    }

    public static String nation(String str) {
        return ChatColor.translateAlternateColorCodes('&', "&f&l[ &6국가 &f&l] &f" + str);
    }

    public static String ex(String str) {
        return ChatColor.translateAlternateColorCodes('&', "&6[!] &f" + str);
    }
}
