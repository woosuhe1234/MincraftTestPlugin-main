package Air.FourCore.management;

import com.mongodb.client.model.UpdateOptions;
import Air.FourCore.mongoDB.MongoDB;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import Air.FourCore.user.BagData;
import Air.FourCore.user.UserData;
import Air.FourCore.user.UserSetting;

import static com.mongodb.client.model.Filters.eq;
import static Air.FourCore.management.ManagementUtility.manage;
import static Air.FourCore.user.UserData.getUserData;

public class ManagementCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length >= 1) {
                if(args[0].equalsIgnoreCase("가방")){
                    if (args.length != 2) {
                        return true;
                    }
                    if (!p.isOp()) {
                        p.sendMessage(manage("op가 아닙니다."));
                        return true;
                    }
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                    if(target != null) {
                        p.openInventory(BagData.getInventory(target.getUniqueId()));
                    }
                }else if (args.length == 3) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    UserData targetData = getUserData(target);
                    int warning = 1;
                    try {
                        warning = Integer.parseInt(args[1]);
                    } catch (Exception e) {
                        p.sendMessage(manage("경고 강도는 숫자로 적어주세요. /관리 [닉네임] [경고강도] [처벌사유]"));
                        return true;
                    }
                    if (targetData.role != UserSetting.Role.일반유저) {
                        p.sendMessage(manage("관리자끼리는 처벌할 수 없습니다. 먼저 '/역할 부여' 를 통해 낮춰주세요."));
                        return true;
                    }
                    if (target != null) {
                        targetData.warning += warning;
                        if (targetData.warning >= 10) {
                            try {
                                target.getPlayer().banPlayer(args[2]);
                            } catch (Exception e) {
                            }
                        }
                        Bukkit.broadcastMessage(ChatColor.RESET + "───────────────────");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage(ChatColor.RESET + "닉네임: " + ChatColor.YELLOW + args[0]);
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage(ChatColor.RESET + "처리자: " + ChatColor.AQUA + p.getName());
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage(ChatColor.RESET + "처벌사유: " + ChatColor.RED + args[2]);
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage(ChatColor.RESET + "경고추가횟수: " + ChatColor.GRAY + args[1]);
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage(ChatColor.RESET + "───────────────────");

                        UpdateOptions options = new UpdateOptions().upsert(true);
                        MongoDB.user.updateOne(eq("uuid", targetData.uuid.toString()), new Document("$set", new Document("warning", targetData.warning)), options);
                    } else {
                        p.sendMessage(manage("해당 플레이어가 없습니다."));
                    }
                } else {
                    p.sendMessage(manage("/관리 가방 [닉네임]"));
                    p.sendMessage(manage("/관리 [닉네임] [경고강도] [처벌사유]"));
                    p.sendMessage(manage(" ex) /관리 User1 10 버그악용"));
                }
            } else {
                p.sendMessage(manage("/관리 가방 [닉네임]"));
                p.sendMessage(manage("/관리 [닉네임] [경고강도] [처벌사유]"));
                p.sendMessage(manage(" ex) /관리 User1 10 버그악용"));
            }
        } else {
            if (args.length == 3) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                UserData targetData = getUserData(target);
                int warning = 1;
                try {
                    warning = Integer.parseInt(args[1]);
                } catch (Exception e) {
                    System.out.println(manage("경고 강도는 숫자로 적어주세요. /관리 [닉네임] [경고강도] [처벌사유]"));
                    return true;
                }
                if (target != null) {
                    targetData.warning += warning;
                    if (targetData.warning >= 10) {
                        try {
                            target.getPlayer().banPlayerFull(args[2]);
                        } catch (Exception e) {
                        }
                    }
                    Bukkit.broadcastMessage(ChatColor.RESET + "───────────────────");
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage(ChatColor.RESET + "닉네임: " + ChatColor.YELLOW + args[0]);
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage(ChatColor.RESET + "처리자: " + ChatColor.AQUA + "[ 서버 ]");
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage(ChatColor.RESET + "처벌사유: " + ChatColor.RED + args[2]);
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage(ChatColor.RESET + "경고추가횟수: " + ChatColor.GRAY + args[1]);
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage(ChatColor.RESET + "───────────────────");

                    UpdateOptions options = new UpdateOptions().upsert(true);
                    MongoDB.user.updateOne(eq("uuid", targetData.uuid.toString()), new Document("$set", new Document("warning", targetData.warning)), options);
                } else {
                    System.out.println(manage("해당 플레이어가 없습니다."));
                }
            } else {
                System.out.println(manage("/관리 [닉네임] [경고강도] [처벌사유]"));
                System.out.println(manage(" ex) /관리 User1 10 버그악용"));
            }
        }
        return true;
    }
}
