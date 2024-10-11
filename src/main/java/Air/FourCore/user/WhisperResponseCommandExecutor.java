package Air.FourCore.user;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhisperResponseCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            UserData data = UserData.getUserData(player);
            if(args.length > 0){
                Player target = data.setting.latestWhisper;
                if(target != null){
                    if(target.isOnline()){
                        String chat = "";
                        for (int i = 0; i < args.length; i ++){
                            chat += args[i] + " ";
                        }
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                "&a[&f 귓속말 &a] [&f 보냄 &a] [&f "+target.getName()+" &a] [&f "+chat+"&a]"));
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                "&6[&f 귓속말 &6] [&f 받음 &6] [&f "+player.getName()+" &6] [&f "+chat+"&6]"));
                        UserData.getUserData(player).setting.latestWhisper = target;
                        UserData.getUserData(target).setting.latestWhisper = player;
                        player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                        target.playSound(target.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                    }else{
                        player.sendMessage(ChatColor.RESET+"해당 플레이어는 오프라인 입니다.");
                    }
                }else{
                    player.sendMessage(ChatColor.RESET+"해당 플레이어가 없습니다.");
                }
            }else{
                player.sendMessage(ChatColor.RESET+"/[답장/답/r] [할 말]");
            }
        }
        return true;
    }
}
