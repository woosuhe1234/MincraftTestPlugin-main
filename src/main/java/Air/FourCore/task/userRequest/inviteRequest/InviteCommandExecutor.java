package Air.FourCore.task.userRequest.inviteRequest;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import Air.FourCore.user.UserData;

public class InviteCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            UserData data = UserData.getUserData(p);
            InviteRequest request = data.<InviteRequest>getTimerable(InviteRequest.class);
            if (request == null) {
                p.sendMessage(ChatColor.RED + "초대받지 않았습니다.");
                return true;
            }
            if (command.getName().equalsIgnoreCase("수락")) {
                request.answer(true);
            }
            if (command.getName().equalsIgnoreCase("거절")) {
                request.answer(false);
            }
        }
        return true;
    }
}
