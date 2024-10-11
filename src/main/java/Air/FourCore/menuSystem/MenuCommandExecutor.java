package Air.FourCore.menuSystem;

import Air.FourCore.menuSystem.menu.mainmenu.MainMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MenuCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            new MainMenu(PlayerMenuUtility.getPlayerMenuUtility(p)).open();
        }else{
            //System.out.println("Not Player");
        }
        return true;
    }
}
