package Air.FourCore.task;

import Air.FourCore.nation.NationData;
import Air.FourCore.task.nationRequest.WarBreakTime;
import Air.FourCore.task.nationRequest.WarTime;
import Air.FourCore.user.UserData;
import Air.FourCore.user.UserUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import Air.FourCore.BossRoom;
import Air.FourCore.FourCore;
import Air.FourCore.WorldSetting;

import java.time.LocalDateTime;

public class UpdateTask extends BukkitRunnable {
    @Override
    public void run() {
        // 액션바
        for (Player p : Bukkit.getOnlinePlayers()) {
            UserData data = UserData.getUserData(p);
            String message = ChatColor.translateAlternateColorCodes('&',
                    "&f&l레벨 &6&l[&f&l" + data.level + "&6&l]&f&l | " +
                            "&f&l경험치 &6&l[&f&l " + (long) data.exp + " &6&l/&f&l " + (long) data.getEXPGoal() + " &e&l(" + String.format("%.2f", data.exp / data.getEXPGoal() * 100) + "%) &6&l]&f&l | " +
                            "&f&l국가 &6&l[&f&l " + (data.nation == null ? "없음" : data.nation) + " &6&l]&f&l | " +
                            "&f&l소지금 &6&l[&f&l " + String.format("%,d", (long) FourCore.economy.getBalance(p)) + "&e&lG &6&l]&f &l| " +
                            "" + data.setting.chatMod.name() + "채팅 모드");
            if(WorldSetting.ExpEvent != 0){
                message += ChatColor.translateAlternateColorCodes('&', "&l | [+"+WorldSetting.ExpEvent+"%]");
            }
            NationData nation = data.getNation();
            if(nation != null){
                if(nation.<WarBreakTime>getTimerable(WarBreakTime.class) != null){
                    message += ChatColor.translateAlternateColorCodes('&', "&l | [전쟁 준비 "+nation.<WarBreakTime>getTimerable(WarBreakTime.class).timer+"초 남음]");
                }else if(nation.<WarTime>getTimerable(WarTime.class) != null){
                    message += ChatColor.translateAlternateColorCodes('&', "&l | [전쟁 중 "+nation.<WarTime>getTimerable(WarTime.class).timer+"초 남음]");
                }
            }
            p.sendActionBar(message);
        }

        // 보스룸 시간
        for (BossRoom room : WorldSetting.bossRooms){
            room.Tick();
        }

        // 낮 고정
        for (World world : Bukkit.getServer().getWorlds()) {
            world.setTime(0L);
        }

        // 플레이 타임 증가
        for (Player p : Bukkit.getOnlinePlayers()) {
            UserData data = UserData.getUserData(p);
            data.setting.dailyPlayTime++;
        }


        LocalDateTime now = LocalDateTime.now();
        // 일일보상 초기화
        if (WorldSetting.lastResetReward == null || WorldSetting.lastResetReward.getYear() < now.getYear() || WorldSetting.lastResetReward.getDayOfYear() < now.getDayOfYear()) {
            WorldSetting.lastResetReward = now;
            UserUtility.resetReward();
        }
        /*
        if (now.getHour() == 0 && now.getMinute() == 0 && now.getSecond() == 0) {
            UserUtility.resetReward();
        }*/

        // 코인 업데이트
        /*
        if (now.getSecond() % 10 == 0) {
            CoinManager.updateCoin();
        }
         */

        // 드롭 아이템 청소
        if (now.getMinute() % 5 == 0) {
            if (now.getSecond() == 10) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "kill @e[type=item]");
                Bukkit.broadcastMessage(ChatColor.RED + "※ 드랍된 아이템들이 청소되었습니다!");
            } else if (now.getSecond() == 5) {
                Bukkit.broadcastMessage(ChatColor.RED + "※ 5초 후 드랍된 아이템들이 청소됩니다!");
            } else if (now.getSecond() == 0) {
                Bukkit.broadcastMessage(ChatColor.RED + "※ 10초 후 드랍된 아이템들이 청소됩니다!");
            }
        }

        // 잠수대 점수 지급
        if (now.getSecond() == 0) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getWorld().getName().equalsIgnoreCase("잠수대")) {
                    //if(RandomSystem.random.nextInt(1) == 0)
                    //{
                    UserData data = UserData.getUserData(p);
                    data.point += 1;
                    p.sendMessage("잠수 포인트+1 (현재 " + data.point + "포인트)");
                    //}else{
                    //    UserData data = UserData.getUserData(p);
                    //    data.point += 2;
                    //    p.sendMessage("잠수 포인트+2 당첨! (현재 " + data.point + "포인트)");
                    //}
                }
            }
        }

        // 백업
        if (now.getMinute() % 10 == 0 && now.getSecond() == 0) {
            FourCore.instance.onDisable();
        }
    }

}
