package Air.FourCore.nation;

import Air.FourCore.user.UserData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import Air.FourCore.task.nationRequest.WarNewbieCoolTime;
import Air.FourCore.task.nationRequest.WarTimerable;
import Air.FourCore.WorldSetting;

import static Air.FourCore.nation.NationUtility.nation;
import static Air.FourCore.user.UserData.getUserData;

public class NationManagementCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;
        UserData data = UserData.getUserData(p);
        if (p != null) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("전쟁중단")) {
                    전쟁중단(args, p);
                } else if (args[0].equalsIgnoreCase("전쟁차단")) {
                    if (WorldSetting.blockWar) {
                        WorldSetting.blockWar = false;
                        Bukkit.broadcastMessage(nation("관리자 &e" + p.getName() + " &f님에 의해 전쟁이 &a활성화&f되었습니다."));
                        Bukkit.broadcastMessage(nation("이제부터 국왕들은 다른 국가에게 전쟁을 선포할 수 있습니다."));
                    } else {
                        WorldSetting.blockWar = true;
                        Bukkit.broadcastMessage(nation("관리자 &e" + p.getName() + " &f님에 의해 전쟁이 &c비활성화&f되었습니다."));
                        Bukkit.broadcastMessage(nation("이제부터 국왕들은 다른 국가에게 전쟁을 선포할 수 없습니다."));
                    }
                } else if (args[0].equalsIgnoreCase("삭제")) {
                    삭제(args, p);
                } else if (args[0].equalsIgnoreCase("성삭제")) {
                    NationData nation = NationData.getNationData(args[1]);
                    int index = Integer.parseInt(args[2]) - 1;
                    if (NationData.map.containsKey(nation.nationName)) {
                        nation.castles.remove(index);
                        p.sendMessage(nation(nation.nationName + " 성 삭제 완료."));
                    } else {
                        p.sendMessage(nation(nation.nationName + " 해당 국가가 없음"));
                    }
                } else if (args[0].equalsIgnoreCase("전초기지삭제")) {
                    NationData nation = NationData.getNationData(args[1]);
                    int index = Integer.parseInt(args[2]) - 1;
                    if (NationData.map.containsKey(nation.nationName)) {
                        nation.outposts.remove(index);
                        p.sendMessage(nation(nation.nationName + " 전초기지 삭제 완료."));
                    } else {
                        p.sendMessage(nation(nation.nationName + " 해당 국가가 없음"));
                    }
                } else if (args[0].equalsIgnoreCase("추방")) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                    if (target == null) {
                        p.sendMessage(nation("해당 플레이어가 없습니다."));
                        return true;
                    }
                    UserData targetData = UserData.getUserData(target);
                    targetData.setUserJob(UserData.Job.일반유저);
                    p.sendMessage(nation(args[1] + "를 국가에서 추방했습니다."));
                    try {
                        target.getPlayer().sendMessage(nation("관리자에 의해 국가에서 추방되었습니다."));
                    } catch (Exception ignored) {
                    }
                } else if (args[0].equalsIgnoreCase("직급")) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                    if (target == null) {
                        p.sendMessage(nation("해당 플레이어가 없습니다."));
                        return true;
                    }
                    UserData targetData = UserData.getUserData(target);
                    NationData targetNation;
                    try {
                        targetNation = targetData.getNation();
                    }catch (Exception e){
                        p.sendMessage(nation("해당플레이어의 국가가 없습니다. 먼저 가입을 시키고 직급을 부여하세요."));
                        return true;
                    }
                    if (args[2].trim().equalsIgnoreCase(UserData.Job.왕.name())) {
                        UserData otherData = UserData.getUserData(targetNation.king);
                        otherData.setUserJob(UserData.Job.시민);
                        targetData.setUserJob(UserData.Job.왕);
                        p.sendMessage(nation(args[1] + "에게 " + args[2] + "직급을 임명했습니다"));
                        try {
                            target.getPlayer().sendMessage(nation("관리자에 의해 왕을 임명받았습니다."));
                        } catch (Exception ignored) {
                        }
                    } else if (args[2].trim().equalsIgnoreCase(UserData.Job.부왕.name())) {
                        UserData otherData = UserData.getUserData(targetNation.viceroy);
                        otherData.setUserJob(UserData.Job.시민);
                        targetData.setUserJob(UserData.Job.부왕);
                        p.sendMessage(nation(args[1] + "에게 " + args[2] + "직급을 임명했습니다"));
                        try {
                            target.getPlayer().sendMessage(nation("관리자에 의해 부왕을 임명받았습니다."));
                        } catch (Exception ignored) {
                        }
                    } else if (args[2].trim().equalsIgnoreCase(UserData.Job.장군.name())) {
                        targetData.setUserJob(UserData.Job.장군);
                        p.sendMessage(nation(args[1] + "에게 " + args[2] + "직급을 임명했습니다"));
                        try {
                            target.getPlayer().sendMessage(nation("관리자에 의해 장군을 임명받았습니다."));
                        } catch (Exception ignored) {
                        }
                    } else if (args[2].trim().equalsIgnoreCase(UserData.Job.시민.name())) {
                        targetData.setUserJob(UserData.Job.시민);
                        p.sendMessage(nation(args[1] + "에게 " + args[2] + "직급을 임명했습니다"));
                        try {
                            target.getPlayer().sendMessage(nation("관리자에 의해 시민을 임명받았습니다."));
                        } catch (Exception ignored) {
                        }
                    }
                } else if (args[0].equalsIgnoreCase("보호")) {
                    if (args.length != 2) {
                        p.sendMessage(nation("/국가관리 보호 [국가명]"));
                        return true;
                    }
                    NationData nation = NationData.getNationData(args[1]);
                    if (NationData.map.containsKey(nation.nationName)) {
                        new WarNewbieCoolTime(259200, nation);
                        p.sendMessage(nation(nation.nationName + " 국가에 72시간 보호 적용 완료"));
                    } else {
                        p.sendMessage(nation(nation.nationName + " 해당 국가가 없음"));
                    }
                } else if (args[0].equalsIgnoreCase("점수")) {
                    if (args.length != 3) {
                        p.sendMessage(nation("/국가관리 점수 [국가명] [숫자(음수가능)]"));
                        return true;
                    }
                    NationData nation = NationData.getNationData(args[1]);
                    if (NationData.map.containsKey(nation.nationName)) {
                        int score = Integer.parseInt(args[2]);
                        nation.score += score;
                        p.sendMessage(nation(nation.nationName + " 국가에 " + args[2] + "점 지급 완료. 현재 " + nation.score + "점"));
                    } else {
                        p.sendMessage(nation(nation.nationName + " 해당 국가가 없음"));
                    }
                }
            } else {
                p.sendMessage(nation("/국가관리 전쟁중단"));
                p.sendMessage(nation("/국가관리 전쟁차단"));
                p.sendMessage(nation("/국가관리 삭제 [국가명]"));
                p.sendMessage(nation("/국가관리 성삭제 [국가명] [번호] (데이터만 사라지고 실제 블록은 그대로)"));
                p.sendMessage(nation("/국가관리 전초기지삭제 [국가명] [번호] (데이터만 사라지고 실제 블록은 그대로)"));
                p.sendMessage(nation("/국가관리 추방 [플레이어]"));
                p.sendMessage(nation("/국가관리 직급 [플레이어] [직급]"));
                p.sendMessage(nation("/국가관리 점수 [국가명] [숫자(음수가능)]"));
            }

        } else {
            System.out.println("You are not Player");
        }
        return true;
    }

    private void 전쟁중단(String[] args, Player p) {
        if (WorldSetting.isWar()) {
            WorldSetting.warSender.<WarTimerable>getTimerable(WarTimerable.class).stop();
            Bukkit.broadcastMessage(nation("관리자 &e" + p.getName() + " &f님에 의해 현재 전쟁이 &c중단&f되었습니다."));
        } else {
            p.sendMessage(nation("전쟁중이 아닙니다."));
        }
    }

    private void 삭제(String[] args, Player p) {
        NationData nation = NationData.getNationData(args[1]);
        if (NationData.map.containsKey(nation.nationName)) {
            nation.remove();
            p.sendMessage(nation(nation.nationName + " 국가 삭제 완료."));
        } else {
            p.sendMessage(nation(nation.nationName + " 해당 국가가 없음"));
        }
    }
}
