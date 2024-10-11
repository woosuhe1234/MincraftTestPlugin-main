package Air.FourCore.user;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DominationCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length > 1){

            }else{
                player.sendMessage(ChatColor.RESET+"/[귓속말|귓|w] [닉네임] [할 말]");
            }
        }
        return true;
    }
}
