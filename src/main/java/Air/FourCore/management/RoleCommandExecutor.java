package Air.FourCore.management;

import com.mongodb.client.model.UpdateOptions;
import Air.FourCore.mongoDB.MongoDB;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import Air.FourCore.FourCore;
import Air.FourCore.user.UserData;
import Air.FourCore.user.UserSetting;

import static com.mongodb.client.model.Filters.eq;
import static Air.FourCore.management.ManagementUtility.manage;
import static Air.FourCore.user.UserData.getUserData;

public class RoleCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            UserData data = getUserData(p);
            if (args.length == 3) {
                if (data.role != UserSetting.Role.대표) {
                    p.sendMessage(manage("대표만 부여할 수 있습니다."));
                    return true;
                }
                if (args[0].equalsIgnoreCase("부여")) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                    UserData targetData = getUserData(target);
                    if (target != null) {
                        if (args[2].equalsIgnoreCase("관리자")) {
                            target.setOp(true);
                            targetData.role = UserSetting.Role.관리자;
                            p.sendMessage(manage("해당 플레이어에게 &b관리자&f를 부여했습니다."));
                            if (target.isOnline()) {
                                target.getPlayer().addAttachment(FourCore.instance).setPermission("FourCore.op", true);
                                target.getPlayer().addAttachment(FourCore.instance).setPermission("FourCore.guide", true);
                                target.getPlayer().sendMessage(manage("당신은 &b관리자&f를 부여받았습니다."));
                            } else {
                                Document doc = new Document("role", targetData.role);
                                UpdateOptions options = new UpdateOptions().upsert(true);
                                MongoDB.user.updateOne(eq("uuid", targetData.uuid.toString()), new Document("$set", doc), options);
                            }
                        } else if (args[2].equalsIgnoreCase("가이드")) {
                            target.setOp(false);
                            targetData.role = UserSetting.Role.가이드;
                            p.sendMessage(manage("해당 플레이어에게 &9가이드&f를 부여했습니다."));
                            if (target.isOnline()) {
                                target.getPlayer().addAttachment(FourCore.instance).setPermission("FourCore.op", false);
                                target.getPlayer().addAttachment(FourCore.instance).setPermission("FourCore.guide", true);
                                target.getPlayer().sendMessage(manage("당신은 &9가이드&f를 부여받았습니다."));
                            } else {
                                Document doc = new Document("role", targetData.role);
                                UpdateOptions options = new UpdateOptions().upsert(true);
                                MongoDB.user.updateOne(eq("uuid", targetData.uuid.toString()), new Document("$set", doc), options);
                            }
                        } else if (args[2].equalsIgnoreCase("일반유저")) {
                            target.setOp(false);
                            targetData.role = UserSetting.Role.일반유저;
                            p.sendMessage(manage("해당 플레이어에게 &7일반유저&f를 부여했습니다."));
                            if (target.isOnline()) {
                                target.getPlayer().addAttachment(FourCore.instance).setPermission("FourCore.op", false);
                                target.getPlayer().addAttachment(FourCore.instance).setPermission("FourCore.guide", false);
                                target.getPlayer().sendMessage(manage("당신은 &7일반유저&f를 부여받았습니다."));
                            } else {
                                Document doc = new Document("role", targetData.role);
                                UpdateOptions options = new UpdateOptions().upsert(true);
                                MongoDB.user.updateOne(eq("uuid", targetData.uuid.toString()), new Document("$set", doc), options);
                            }
                        } else {
                            p.sendMessage(manage("올바른 역할이 아닙니다. (관리자/가이드/일반유저)"));
                        }

                    } else {
                        p.sendMessage(manage("해당 플레이어가 없습니다."));
                    }
                }
            } else {
                p.sendMessage(manage("/역할 부여 [닉네임] [관리자/가이드/일반유저]"));
            }
        } else {
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("부여")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    UserData targetData = getUserData(target);
                    if (target != null) {
                        if (args[2].equalsIgnoreCase("대표")) {
                            target.addAttachment(FourCore.instance).setPermission("FourCore.op", true);
                            target.addAttachment(FourCore.instance).setPermission("FourCore.guide", true);
                            target.setOp(true);
                            targetData.role = UserSetting.Role.대표;
                            target.sendMessage(manage("당신은 &c대표&f를 부여받았습니다."));
                            System.out.println(manage("해당 플레이어에게 &c대표&f를 부여했습니다."));
                        } else if (args[2].equalsIgnoreCase("관리자")) {
                            target.addAttachment(FourCore.instance).setPermission("FourCore.op", true);
                            target.addAttachment(FourCore.instance).setPermission("FourCore.guide", true);
                            target.setOp(true);
                            targetData.role = UserSetting.Role.관리자;
                            target.sendMessage(manage("당신은 &b관리자&f를 부여받았습니다."));
                            System.out.println(manage("해당 플레이어에게 &b관리자&f를 부여했습니다."));
                        } else if (args[2].equalsIgnoreCase("가이드")) {
                            target.addAttachment(FourCore.instance).setPermission("FourCore.op", false);
                            target.addAttachment(FourCore.instance).setPermission("FourCore.guide", true);
                            target.setOp(false);
                            targetData.role = UserSetting.Role.가이드;
                            target.sendMessage(manage("당신은 &9가이드&f를 부여받았습니다."));
                            System.out.println(manage("해당 플레이어에게 &9가이드&f를 부여했습니다."));
                        } else if (args[2].equalsIgnoreCase("일반유저")) {
                            target.addAttachment(FourCore.instance).setPermission("FourCore.op", false);
                            target.addAttachment(FourCore.instance).setPermission("FourCore.guide", false);
                            target.setOp(false);
                            targetData.role = UserSetting.Role.일반유저;
                            target.sendMessage(manage("당신은 &7일반유저&f를 부여받았습니다."));
                            System.out.println(manage("해당 플레이어에게 &7일반유저&f를 부여했습니다."));
                        } else {
                            System.out.println(manage("올바른 역할이 아닙니다. (관리자/가이드/일반유저)"));
                        }

                    } else {
                        System.out.println(manage("해당 플레이어가 없습니다."));
                    }
                }
            } else {
                System.out.println(manage("/역할 부여 [닉네임] [대표/관리자/가이드/일반유저]"));
            }
        }
        return true;
    }
}
