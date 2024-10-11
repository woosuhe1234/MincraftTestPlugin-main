package Air.FourCore.task.userRequest;

import Air.FourCore.user.UserData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EventTime extends UserTimerable {

    ItemStack item;

    public EventTime(long timer, UserData data, ItemStack item) {
        super(timer, data);
        this.item = item;
        tick();
    }

    @Override
    public void tick() {
        Player p = Bukkit.getPlayer(data.uuid);
        if(p != null)
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e※ "+timer+"초 후 이벤트 아이템이 지급됩니다. &7인벤토리를 한 칸 비워두세요."));
    }

    @Override
    public void end() {
        Player p = Bukkit.getPlayer(data.uuid);
        if(p != null) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e※ 이벤트 아이템이 지급되었습니다!"));
            p.getInventory().addItem(item);
        }
    }

    @Override
    public void stop(){
        super.stop();
    }
}
