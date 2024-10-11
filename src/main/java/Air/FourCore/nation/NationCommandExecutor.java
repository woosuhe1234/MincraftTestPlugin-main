package Air.FourCore.nation;

import Air.FourCore.item.CustomItemUtility;
import Air.FourCore.mongoDB.MongoDB;
import Air.FourCore.task.nationRequest.*;
import Air.FourCore.task.userRequest.inviteRequest.AllianceBreakRequest;
import Air.FourCore.task.userRequest.inviteRequest.AllianceInviteRequest;
import Air.FourCore.task.userRequest.inviteRequest.InviteRequest;
import Air.FourCore.task.userRequest.inviteRequest.NationInviteRequest;
import Air.FourCore.user.UserData;
import Air.FourCore.user.UserSetting;
import Air.FourCore.user.UserUtility;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import task.nationRequest.*;
import Air.FourCore.task.userRequest.RejoinNationCoolTime;
import Air.FourCore.FourCore;
import Air.FourCore.TimeUtility;
import Air.FourCore.WorldSetting;

import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;
import static Air.FourCore.nation.NationData.createNation;
import static Air.FourCore.nation.NationUtility.*;
import static Air.FourCore.user.UserData.getUserData;

public class NationCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;
        UserData data = UserData.getUserData(p);
        if (p != null) {
            /*
            if(data.<BattleModeTime>getTimerable(BattleModeTime.class) != null){
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[ &c전투모드 &f&l] &f전투 모드 중에는 할 수 없습니다."));
                return true;
            }*/
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("생성")) {
                    생성(args, p, data);
                } else if (args[0].equalsIgnoreCase("직급")) {
                    직급(args, p, data);
                } else if (args[0].equalsIgnoreCase("삭제")) {
                    삭제(args, p, data);
                } else if (args[0].equalsIgnoreCase("양도")) {
                    양도(args, p, data);
                } else if (args[0].equalsIgnoreCase("추방")) {
                    추방(args, p, data);
                } else if (args[0].equalsIgnoreCase("초대")) {
                    초대(args, p, data);
                } else if (args[0].equalsIgnoreCase("정보")) {
                    정보(args, p, data);
                } else if (args[0].equalsIgnoreCase("채팅")) {
                    채팅(p, data);
                } else if (args[0].equalsIgnoreCase("금고")) {
                    금고(args, p, data);
                } else if (args[0].equalsIgnoreCase("PVP")) {
                    PVP(p, data);
                } else if (args[0].equalsIgnoreCase("강화")) {
                    강화(p, data);
                } else if (args[0].equalsIgnoreCase("탈퇴")) {
                    탈퇴(p, data);
                } else if (args[0].equalsIgnoreCase("보호")) {
                    보호(args, p, data);
                } else if (args[0].equalsIgnoreCase("목록")) {
                    for (NationData n : NationData.map.values()) {
                        String k = "";
                        if (Bukkit.getOfflinePlayer(n.king) != null)
                            k = "&f, 국왕: " + UserUtility.playerToString(Bukkit.getOfflinePlayer(n.king));
                        p.sendMessage(nation("국가 명: &e" + n.nationName + k));
                    }
                } else if (args[0].equalsIgnoreCase("전쟁선포")) {
                    전쟁선포(args, p, data);
                } else if (args[0].equalsIgnoreCase("전쟁항복")) {
                    전쟁항복(args, p, data);
                } else if (args[0].equalsIgnoreCase("동맹신청")) {
                    동맹신청(args, p, data);
                } else if (args[0].equalsIgnoreCase("동맹채팅")) {
                    동맹채팅(p, data);
                } else if (args[0].equalsIgnoreCase("동맹참전")) {
                    동맹참전(args, p, data); // 제작중
                } else if (args[0].equalsIgnoreCase("동맹PVP")) {
                    동맹PVP(p, data);
                } else if (args[0].equalsIgnoreCase("동맹파기")) {
                    동맹파기(args, p, data);
                } else if (args[0].equalsIgnoreCase("입금")) {
                    p.sendMessage(nation("/국가 &6금고 &f[입금|출금] [금액]"));
                } else if (args[0].equalsIgnoreCase("출금")) {
                    p.sendMessage(nation("/국가 &6금고 &f[입금|출금] [금액]"));
                } else if (args[0].equalsIgnoreCase("쿨타임")) {
                    RejoinNationCoolTime t = data.<RejoinNationCoolTime>getTimerable(RejoinNationCoolTime.class);
                    if(t != null){
                        p.sendMessage(nation("&f국가 가입 쿨타임: "+TimeUtility.secToTime(t.timer)));
                    }else{
                        p.sendMessage(nation("쿨타임 없음"));
                    }
                }
            } else {
                p.sendMessage(nation("/국가 생성 [국가명] &7- 300만원과 국가강화석 2개, 신호기로 국가를 생성합니다."));
                p.sendMessage(nation("/국가 삭제 &7- 국가를 삭제합니다. &c(개인의 실수는 복구해드리지 않습니다)"));
                p.sendMessage(nation("/국가 직급 [닉네임] [직위] &7- 직위(시민, 장군, 부왕)를 임명합니다."));
                p.sendMessage(nation("/국가 양도 [닉네임] &7- 부왕에게 왕을 양도합니다."));
                p.sendMessage(nation("/국가 추방 [닉네임] &7- 시민을 추방합니다."));
                p.sendMessage(nation("/국가 초대 [닉네임] &7- 일반유저를 국가에 초대합니다."));
                p.sendMessage(nation("/국가 정보 [국가명] &7- 국가의 정보를 봅니다."));
                p.sendMessage(nation("/국가 채팅 &7- 국가채팅으로 전환합니다."));
                p.sendMessage(nation("/국가 금고 [입금/출금] [금액] &7- 국가의 돈을 관리합니다."));
                p.sendMessage(nation("/국가 탈퇴 &7- 국가를 탈퇴합니다."));
                p.sendMessage(nation("/국가 PVP &7- 국가원간 PVP모드를 전환합니다."));
                p.sendMessage(nation("/국가 전쟁선포 [국가명] &7- 전쟁권이 필요하며 각 국가의 인원이 4명 이상이어야 합니다."));
                p.sendMessage(nation("/국가 전쟁항복 &7- 600만원을 바치고 전쟁에서 항복합니다."));
                p.sendMessage(nation("/국가 동맹신청 [국가명] &7- 각자 동맹권을 하나씩 가지고 있어야 합니다."));
                p.sendMessage(nation("/국가 동맹채팅 &7- 동맹채팅으로 전환합니다."));
                p.sendMessage(nation("/국가 동맹참전 &7- 동맹 국가의 전쟁에 참전합니다."));
                p.sendMessage(nation("/국가 동맹PVP &7- 동맹과 PVP모드를 전환합니다."));
                p.sendMessage(nation("/국가 동맹파기 [국가명] &7- 동맹에게 동맹파기 요청을 보냅니다."));
            }
        } else {
            System.out.println("You are not Player");
        }
        return true;
    }

    private void 보호(String[] args, Player p, UserData data) {
        NationData nation = NationData.getNationData(data.nation);
        if (nation == null) {
            p.sendMessage(nation("당신은 국가에 소속되어 있지 않습니다."));
            return;
        }
        WarNewbieCoolTime t = nation.<WarNewbieCoolTime>getTimerable(WarNewbieCoolTime.class);
        if (args.length == 1) {
            if (t == null) {
                p.sendMessage(nation("국가 보호중이 아닙니다."));
            } else {
                p.sendMessage(nation("뉴비국가 보호중입니다. " + TimeUtility.secToTime(t.timer) + " 남음)"));
                p.sendMessage(nation("&7/국가 보호 해제 &f명령어로 해제할 수 있습니다."));
            }
            return;
        }
        if (data.job == null || (data.job != UserData.Job.왕 && data.job != UserData.Job.부왕)) {
            p.sendMessage(nation("권한이 없습니다."));
            return;
        }
        if (!args[1].equalsIgnoreCase("해제")) {
            p.sendMessage(nation("/국가 보호 해제"));
        }
        if (t == null) {
            p.sendMessage(nation("국가 보호중이 아닙니다."));
        } else {
            t.stop();
            p.sendMessage(nation("국가 보호를 해제하셨습니다!"));
        }
    }

    private void 탈퇴(Player p, UserData data) {

        NationData nation = NationData.getNationData(data.nation);
        if (nation == null) {
            p.sendMessage(nation("당신은 국가에 소속되어 있지 않습니다."));
            return;
        }
        if (data.job == UserData.Job.왕) {
            p.sendMessage(nation("왕은 탈퇴할 수 없습니다. 먼저 부왕에게 왕을 양도하세요."));
            return;
        }
        if (nation.isWar()) {
            p.sendMessage(nation("전쟁 중에는 할 수 없습니다."));
            return;
        }
        data.setUserJob(UserData.Job.일반유저);
        p.sendMessage(nation("&c국가를 탈퇴했습니다."));
        new RejoinNationCoolTime(86400, data);
    }

    private void 강화(Player p, UserData data) {
        NationData nation = NationData.getNationData(data.nation);
        if (nation == null) {
            p.sendMessage(nation("당신은 국가에 소속되어 있지 않습니다."));
            return;
        }
        if (data.job == null || (data.job != UserData.Job.왕 && data.job != UserData.Job.부왕)) {
            p.sendMessage(nation("권한이 없습니다."));
            return;
        }
        if (nation.castles.size() < 1) {
            p.sendMessage(nation("온전한 나라가 아닙니다. 먼저 성을 건설해주세요."));
            return;
        }
        if (nation.level >= 5) {
            p.sendMessage(nation("이미 국가가 최대 레벨입니다."));
            return;
        }

        int netherTarget = 0, moneyTarget = 0, stoneTarget = 0;
        switch (nation.level) {
            case 0:
                netherTarget = 5;
                moneyTarget = 500000;
                stoneTarget = 2;
                break;
            case 1:
                netherTarget = 7;
                moneyTarget = 1000000;
                stoneTarget = 4;
                break;
            case 2:
                netherTarget = 12;
                moneyTarget = 1500000;
                stoneTarget = 6;
                break;
            case 3:
                netherTarget = 16;
                moneyTarget = 2000000;
                stoneTarget = 8;
                break;
            case 4:
                netherTarget = 20;
                moneyTarget = 3000000;
                stoneTarget = 10;
                break;
            default:
                return;
        }
        Inventory inventory = p.getInventory();
        int nether = CustomItemUtility.amount(inventory, CustomItemUtility.NetherStartItem());
        int stone = CustomItemUtility.amount(inventory, CustomItemUtility.NationStoneItem());
        if (nether < netherTarget || nation.money < moneyTarget || stone < stoneTarget) {
            p.sendMessage(nation("국가 강화에 필요한 자원이 부족합니다. (돈은 국가 금고로 지불)"));
            p.sendMessage(nation("(&6" + (int) (nation.money * 0.0001) + " &f/ &f" + (int) (moneyTarget * 0.0001) + " &f만원, &6" + nether + " &f/ &f" + netherTarget + " &f네더의 별, &6" + stone + " &f/ &f" + stoneTarget + " &f국가 강화석) 필요"));
            return;
        }
        nation.money -= moneyTarget;
        CustomItemUtility.remove(inventory, CustomItemUtility.NetherStartItem(), netherTarget);
        CustomItemUtility.remove(inventory, CustomItemUtility.NationStoneItem(), stoneTarget);
        broadcastNation(nation, nation("성공적으로 국가를 강화했습니다! &6국가 " + nation.level + "->" + (nation.level + 1) + "레벨"));
        broadcastTitleNation(nation, ChatColor.GOLD + "[ 국가 레벨 " + (nation.level + 1) + " ]", ChatColor.RESET + "국가 레벨이 올랐습니다!");
        for (UserData d : nation.getAllPlayers()) {
            Player pp = Bukkit.getPlayer(d.uuid);
            try {
                pp.playSound(pp.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            } catch (Exception ignored) {
            }
        }
        nation.level++;
        switch (nation.level) {
            case 2:
                broadcastNation(nation, nation("&6국가 최대 인원: 6 -> 7"));
                break;
            case 3:
                broadcastNation(nation, nation("&6국가 최대 인원: 7 -> 8, 성/전초기지 최대 개수 1 -> 2"));
                break;
            case 4:
                broadcastNation(nation, nation("&6국가 최대 인원: 8 -> 9"));
                break;
            case 5:
                broadcastNation(nation, nation("&6국가 최대 인원: 9 -> 10, 성/전초기지 최대 개수 2 -> 3"));
                break;
            default:
        }
    }

    private void 동맹참전(String[] args, Player p, UserData data) {
        NationData nation = NationData.getNationData(data.nation);
        if (nation == null) {
            p.sendMessage(nation("당신은 국가에 소속되어 있지 않습니다."));
            return;
        }
        if (data.job == null || (data.job != UserData.Job.왕 && data.job != UserData.Job.부왕)) {
            p.sendMessage(nation("권한이 없습니다."));
            return;
        }
        if (nation.castles.size() < 1) {
            p.sendMessage(nation("온전한 나라가 아닙니다. 먼저 성을 건설해주세요."));
            return;
        }
        if (nation.allies.size() < 1) {
            p.sendMessage(nation("동맹국이 없습니다."));
            return;
        }
        NationData allyNation = null;
        for (String allies : nation.allies) {
            NationData n = NationData.getNationData(allies);
            if (n.<WarBreakTime>getTimerable(WarBreakTime.class) != null) {
                allyNation = n;
                break;
            }
        }
        if (allyNation == null) {
            p.sendMessage(nation("동맹국이 전쟁 준비 중이 아닙니다."));
            return;
        }
        // 동맹참전 기능
        Bukkit.broadcastMessage(nation("&6" + nation.nationName + "&f국가가 전쟁에 참전하였습니다!"));
        broadcastTitleAlliance(nation, "&6[ 국가 전쟁 ]", "&6" + nation.nationName + "&f국가 전쟁 참전");
        broadcastTitleAlliance(allyNation.getEnemyNation(), "&6[ 국가 전쟁 ]", "&6" + nation.nationName + "&f국가 전쟁 참전");
        if (WorldSetting.warSender == allyNation) {
            WorldSetting.warSenderAllie = nation;
        } else if (WorldSetting.warReceiver == allyNation) {
            WorldSetting.warReceiverAllie = nation;
        }
        //p.sendMessage(nation(" 동맹 참전 완료."));
    }

    private void 삭제(String[] args, Player p, UserData data) {
        NationData nation = NationData.getNationData(data.nation);
        if (nation == null) {
            p.sendMessage(nation("당신은 국가에 소속되어 있지 않습니다."));
            return;
        }
        if (args.length != 2) {
            p.sendMessage(nation("/국가 삭제 [본인국가명] &c※ 개인의 실수로 인한 피해는 복구해드리지 않습니다!"));
            return;
        }
        if (data.job != UserData.Job.왕) {
            p.sendMessage(nation("권한이 없습니다."));
            return;
        }
        if (nation.isWar()) {
            p.sendMessage(nation("전쟁 중에는 할 수 없습니다."));
            return;
        }
        if (!nation.nationName.equalsIgnoreCase(args[1])) {
            p.sendMessage(nation("본인의 국가 명과 일치하지 않습니다."));
            return;
        }
        if (NationData.map.containsKey(nation.nationName)) {
            p.sendMessage(nation("&c국가 삭제 완료."));
            nation.remove();
        }
    }

    private void 동맹파기(String[] args, Player p, UserData data) {
        NationData nation = NationData.getNationData(data.nation);
        if (nation == null) {
            p.sendMessage(nation("당신은 국가에 소속되어 있지 않습니다."));
            return;
        }
        if (args.length != 2) {
            p.sendMessage(nation("/국가 동맹파기 [국가명]"));
            return;
        }
        if (data.job == null || (data.job != UserData.Job.왕 && data.job != UserData.Job.부왕)) {
            p.sendMessage(nation("권한이 없습니다."));
            return;
        }
        NationData targetNation = NationData.getNationData(args[1]);
        if (targetNation == null) {
            p.sendMessage(nation("해당 국가가 없습니다."));
            return;
        }
        if (!nation.allies.contains(targetNation.nationName)) {
            p.sendMessage(nation("이미 적대국입니다."));
            return;
        }
        if (nation.isWar()) {
            p.sendMessage(nation("전쟁 중에는 할 수 없습니다."));
            return;
        }
        Player target = Bukkit.getPlayer(targetNation.king);
        if (target == null) {
            target = Bukkit.getPlayer(targetNation.viceroy);
        }
        if (target == null || !target.isOnline()) {
            p.sendMessage(nation("상대국의 대표가 모두 오프라인입니다."));
            return;
        }
        UserData targetData = UserData.getUserData(target);
        if (targetData.<InviteRequest>getTimerable(InviteRequest.class) == null) {
            new AllianceBreakRequest(30, targetData, data, nation, targetNation);
            p.sendMessage(nation("&6" + targetNation.nationName + " &f국가의 &a" + target.getName() + " &f에게 동맹 파기 요청을 보냈습니다."));
        } else {
            p.sendMessage(nation("상대국의 대표가 다른 요청을 처리중입니다. 잠시 후에 다시 시도해 주세요."));
        }
    }

    private void 동맹신청(String[] args, Player p, UserData data) {
        NationData nation = NationData.getNationData(data.nation);
        if (nation == null) {
            p.sendMessage(nation("당신은 국가에 소속되어 있지 않습니다."));
            return;
        }
        if (args.length != 2) {
            p.sendMessage(nation("/국가 동맹신청 [국가명]"));
            return;
        }
        if (data.job == null || (data.job != UserData.Job.왕 && data.job != UserData.Job.부왕)) {
            p.sendMessage(nation("권한이 없습니다."));
            return;
        }
        if (nation.allies.size() > 0) {
            p.sendMessage(nation("최대 동맹국은 1개 입니다. 다른 국가와 동맹을 맺으려면 먼저 기존 동맹을 파기하세요."));
            return;
        }
        if (!CustomItemUtility.contains(p.getInventory(), CustomItemUtility.AllianceTicketItem())) {
            p.sendMessage(nation("동맹을 신청하기 위해선 동맹권 아이템이 필요합니다."));
            return;
        }
        NationData targetNation = NationData.getNationData(args[1]);
        if (targetNation == null) {
            p.sendMessage(nation("해당 국가가 없습니다."));
            return;
        }
        if (nation.castles.size() < 1) {
            p.sendMessage(nation("온전한 나라가 아닙니다. 먼저 성을 건설해주세요."));
            return;
        }
        if (targetNation.castles.size() < 1) {
            p.sendMessage(nation("아직 건국 중인 국가입니다."));
            return;
        }
        if (nation.allies.contains(targetNation.nationName)) {
            p.sendMessage(nation("이미 동맹국입니다."));
            return;
        }
        if (nation.isWar()) {
            p.sendMessage(nation("전쟁 중에는 할 수 없습니다."));
            return;
        }
        Player target = Bukkit.getPlayer(targetNation.king);
        if (target == null) {
            target = Bukkit.getPlayer(targetNation.viceroy);
        }
        if (target == null || !target.isOnline()) {
            p.sendMessage(nation("상대국의 대표가 모두 오프라인입니다."));
            return;
        }
        if (!CustomItemUtility.contains(target.getInventory(), CustomItemUtility.AllianceTicketItem())) {
            p.sendMessage(nation("상대국의 대표가 동맹권이 없습니다."));
            return;
        }
        UserData targetData = UserData.getUserData(target);
        if (targetData.<InviteRequest>getTimerable(InviteRequest.class) == null) {
            new AllianceInviteRequest(30, targetData, data, nation, targetNation);
            p.sendMessage(nation("&6" + targetNation.nationName + " &f국가의 &a" + target.getName() + " &f에게 동맹 요청을 보냈습니다."));
        } else {
            p.sendMessage(nation("상대국의 대표가 다른 요청을 처리중입니다. 잠시 후에 다시 시도해 주세요."));
        }
    }

    private void 전쟁선포(String[] args, Player p, UserData data) {
        int cost = 3000000;
        NationData nation = NationData.getNationData(data.nation);
        if (nation == null) {
            p.sendMessage(nation("당신은 국가에 소속되어 있지 않습니다."));
            return;
        }
        if (args.length != 2) {
            p.sendMessage(nation("/국가 전쟁선포 [국가명]"));
            return;
        }
        if (WorldSetting.blockWar) {
            p.sendMessage(nation("현재 관리자가 임시로 전쟁을 차단한 상태입니다."));
            return;
        }
        if (data.job == null || (data.job != UserData.Job.왕 && data.job != UserData.Job.부왕)) {
            p.sendMessage(nation("권한이 없습니다."));
            return;
        }
        NationData targetNation = NationData.getNationData(args[1]);
        if (targetNation == null) {
            p.sendMessage(nation("해당 국가가 없습니다."));
            return;
        }
        if (nation.castles.size() < 1) {
            p.sendMessage(nation("온전한 나라가 아닙니다. 먼저 성을 건설해주세요."));
            return;
        }
        if (targetNation.castles.size() < 1) {
            p.sendMessage(nation("아직 건국 중인 국가입니다."));
            return;
        }
        if (nation.money < cost) {
            p.sendMessage(nation("전쟁을 선포하기 위해선 국고에 300만원이 필요합니다."));
            return;
        }
        if (nation.nationName.equalsIgnoreCase(targetNation.nationName)) {
            p.sendMessage(nation("자기 자신에게 전쟁을 걸수 없습니다."));
            return;
        }
        for (String name : nation.allies){
            if (targetNation.nationName.equalsIgnoreCase(name)) {
                p.sendMessage(nation("동맹국에게 전쟁을 걸수 없습니다."));
                return;
            }
        }
        if (nation.getOnlinePlayerCount() < 3) {
            p.sendMessage(nation("전쟁을 선포하기 위해선 우리 국가의 온라인 인원이 3명 이상이어야 합니다. 현재 " + nation.getOnlinePlayerCount() + "명"));
            return;
        }
        if (targetNation.getOnlinePlayerCount() < 3) {
            p.sendMessage(nation("전쟁을 선포하기 위해선 상대 국가의 온라인 인원이 3명 이상이어야 합니다. 현재 " + targetNation.getOnlinePlayerCount() + "명"));
            return;
        }
        if (p.getWorld().getName().equalsIgnoreCase("잠수대")) {
            p.sendMessage(nation("잠수대에선 할수 없습니다."));
            return;
        }
        Player targetKing = Bukkit.getPlayer(targetNation.king);
        Player targetViceroy = Bukkit.getPlayer(targetNation.viceroy);
        if ((targetKing == null || !targetKing.isOnline() || targetKing.getWorld().getName().equalsIgnoreCase("잠수대")) &&
                (targetViceroy == null || !targetViceroy.isOnline() || targetViceroy.getWorld().getName().equalsIgnoreCase("잠수대"))) {
            p.sendMessage(nation("상대국의 왕과 부왕 모두 오프라인입니다."));
            return;
        }
        if (nation.<WarCoolTime>getTimerable(WarCoolTime.class) != null) {
            p.sendMessage(nation("전쟁 쿨타임 또는 뉴비 보호기간 중입니다. " + TimeUtility.secToTime(nation.<WarCoolTime>getTimerable(WarCoolTime.class).timer) + " 남음"));
            return;
        }
        if (targetNation.<WarCoolTime>getTimerable(WarCoolTime.class) != null) {
            p.sendMessage(nation("상대 국가가 전쟁 쿨타임 또는 뉴비 보호기간 중입니다. " + TimeUtility.secToTime(targetNation.<WarCoolTime>getTimerable(WarCoolTime.class).timer) + " 남음"));
            return;
        }
        if (WorldSetting.isWar()) {
            p.sendMessage(nation("이미 다른 국가에서 전쟁중입니다. 전쟁이 끝날때까지 대기해주세요."));
            return;
        }
        nation.score += 1;
        nation.money -= cost;
        WorldSetting.warSender = nation;
        WorldSetting.warReceiver = targetNation;
        new WarBreakTime(300, nation, targetNation);
        broadcastTitleNation(nation, ChatColor.GOLD + "[ 국가 전쟁 ]", "전쟁 선포");
        broadcastTitleNation(targetNation, ChatColor.GOLD + "[ 국가 전쟁 ]", "전쟁 선포");

        p.sendMessage(nation("전쟁 선포에 300만원을 소모하였습니다."));
        Bukkit.broadcastMessage(nation("&6[ " + nation.nationName + " ] &f국이 &a[ " + targetNation.nationName + " ] &f국에게 전쟁을 선포했습니다!"));
        broadcastAlliance(nation, "&c5분&f간의 전쟁 준비시간이 주어지며, &6" + targetNation.nationName + " 국가는 &7항복&f할 수 있습니다.");
        broadcastAlliance(targetNation, "&c5분&f간의 전쟁 준비시간이 주어지며, &6" + targetNation.nationName + " 국가는 &7항복&f할 수 있습니다.");
        if (nation.allies.size() > 0)
            broadcastAlliance(nation, "&6" + nation.allies.get(0) + " 국가는 동맹국으로 전쟁에 &b참전&f할 수 있습니다.");
        if (targetNation.allies.size() > 0)
            broadcastAlliance(targetNation, "&6" + targetNation.allies.get(0) + " 국가는 동맹국으로 전쟁에 &b참전&f할 수 있습니다.");
        nation.playSound(Sound.BLOCK_END_PORTAL_SPAWN);
        nation.playSound(Sound.BLOCK_END_PORTAL_SPAWN);
    }

    private void 전쟁항복(String[] args, Player p, UserData data) {
        NationData nation = NationData.getNationData(data.nation);
        if (nation == null) {
            p.sendMessage(nation("당신은 국가에 소속되어 있지 않습니다."));
            return;
        }
        if (data.job == null || (data.job != UserData.Job.왕 && data.job != UserData.Job.부왕)) {
            p.sendMessage(nation("권한이 없습니다."));
            return;
        }
        if (nation != WorldSetting.warSender && nation != WorldSetting.warReceiver) {
            p.sendMessage(nation("전쟁중이 아닙니다."));
            return;
        }
        if (nation.<WarBreakTime>getTimerable(WarBreakTime.class) == null) {
            p.sendMessage(nation("준비 기간에만 항복할 수 있습니다."));
            return;
        }
        if (nation.money < 6000000) {
            p.sendMessage(nation("항복 비용 600만원이 부족합니다. 먼저 금고에 600만원 이상 넣어주세요"));
            return;
        }
        WarBreakTime timer = nation.<WarBreakTime>getTimerable(WarBreakTime.class);
        timer.stop();
        NationData targetNation = null;
        if (timer.nation == nation) {
            targetNation = timer.targetNation;
        } else {
            targetNation = timer.nation;
        }
        nation.money -= 6000000;
        targetNation.money += 6000000;
        // 항복 행동
        broadcastTitleAlliance(nation, ChatColor.GOLD + "[ 국가 전쟁 ]", ChatColor.GOLD + nation.nationName + ChatColor.GOLD + "국가가 항복했습니다.");
        broadcastTitleAlliance(targetNation, ChatColor.GOLD + "[ 국가 전쟁 ]", ChatColor.GOLD + nation.nationName + ChatColor.GOLD + "국가가 항복했습니다.");
        broadcastNation(nation, nation("600만원을 바치고 전쟁에서 항복했습니다."));
        broadcastNation(targetNation, nation("상대국이 600만원을 바치고 전쟁에서 항복했습니다."));
        Bukkit.broadcastMessage(nation("&6" + nation.nationName + "&f국가가 전쟁에서 항복했습니다."));
        nation.score--;
        targetNation.score++;
    }

    private void 추방(String[] args, Player p, UserData data) {
        NationData nation = NationData.getNationData(data.nation);
        if (nation == null) {
            p.sendMessage(nation("당신은 국가에 소속되어 있지 않습니다."));
            return;
        }
        if (args.length != 2) {
            p.sendMessage(nation("/국가 추방 [유저명]"));
            return;
        }
        if (data.job == null || (data.job != UserData.Job.왕 && data.job != UserData.Job.부왕)) {
            p.sendMessage(nation("권한이 없습니다."));
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        UserData targetData = null;
        if (target == null) {
            for (UserData d : UserData.map.values()) {
                if (d.userName.equalsIgnoreCase(args[1])) {
                    targetData = d;
                    break;
                }
            }
        } else {
            targetData = UserData.getUserData(target);
        }
        if (targetData == null) {
            p.sendMessage(nation("해당 플레이어가 없습니다."));
            return;
        }
        if (!data.nation.equalsIgnoreCase(targetData.nation)) {
            p.sendMessage(nation("&e" + nation.nationName + "&f의 국민이 아닙니다."));
            return;
        }
        if (targetData.job != UserData.Job.시민) {
            p.sendMessage(nation("시민 직급만 추방할 수 있습니다. 먼저 시민으로 강등하세요."));
            return;
        }
        targetData.setUserJob(UserData.Job.일반유저);
        p.sendMessage(nation(args[1] + "플레이어를 국가에서 추방했습니다."));
        new RejoinNationCoolTime(86400, targetData);
        if (target != null && target.isOnline()) {
            target.getPlayer().sendMessage(nation("국가에서 추방당하셨습니다."));
        } else {
            Document doc = new Document()
                    .append("Air/FourCore/nation", targetData.nation)
                    .append("job", targetData.job.name());
            UpdateOptions options = new UpdateOptions().upsert(true);
            MongoDB.user.updateOne(eq("uuid", targetData.uuid.toString()), new Document("$set", doc), options);
        }
    }

    private void 동맹채팅(Player p, UserData data) {
        if (data.setting.chatMod == UserSetting.ChatMod.동맹) {
            data.setting.chatMod = UserSetting.ChatMod.일반;
            p.sendMessage(nation("채팅 모드가 &6[ 일반채팅 ] &f으로 변경되었습니다"));
        } else {
            data.setting.chatMod = UserSetting.ChatMod.동맹;
            p.sendMessage(nation("채팅 모드가 &b[ 동맹채팅 ] &f으로 변경되었습니다"));
        }
    }

    private void 채팅(Player p, UserData data) {
        if (data.setting.chatMod == UserSetting.ChatMod.국가) {
            data.setting.chatMod = UserSetting.ChatMod.일반;
            p.sendMessage(nation("채팅 모드가 &6[ 일반채팅 ] &f으로 변경되었습니다"));
        } else {
            data.setting.chatMod = UserSetting.ChatMod.국가;
            p.sendMessage(nation("채팅 모드가 &9[ 국가채팅 ] &f으로 변경되었습니다"));
        }
    }

    private void 동맹PVP(Player p, UserData data) {
        NationData nation = data.getNation();
        if (nation == null) {
            p.sendMessage(nation("당신은 국가에 소속되어 있지 않습니다."));
            return;
        }
        if (data.job != UserData.Job.왕 && data.job != UserData.Job.부왕) {
            p.sendMessage(nation("왕과 부왕만 설정할 수 있습니다."));
            return;
        }
        if (!nation.setting.alliancePVP) {
            nation.setting.alliancePVP = true;
            broadcastNation(nation, nation("동맹원간 PVP가 &a[ 활성화 ] &f상태로 설정되었습니다."));
        } else {
            nation.setting.alliancePVP = false;
            broadcastNation(nation, nation("동맹원간 PVP가 &c[ 비활성화 ] &f상태로 설정되었습니다."));
        }
    }

    private void PVP(Player p, UserData data) {
        NationData nation = data.getNation();
        if (nation == null) {
            p.sendMessage(nation("당신은 국가에 소속되어 있지 않습니다."));
            return;
        }
        if (data.job != UserData.Job.왕 && data.job != UserData.Job.부왕) {
            p.sendMessage(nation("왕과 부왕만 설정할 수 있습니다."));
            return;
        }
        if (nation.castles.size() < 1) {
            p.sendMessage(nation("아직 건국 중인 국가입니다."));
            return;
        }
        if (!nation.setting.nationPVP) {
            nation.setting.nationPVP = true;
            broadcastNation(nation, nation("국가원간 PVP가 &a[ 활성화 ] &f상태로 설정되었습니다."));
        } else {
            nation.setting.nationPVP = false;
            broadcastNation(nation, nation("국가원간 PVP가 &c[ 비활성화 ] &f상태로 설정되었습니다."));
        }
    }

    private void 금고(String[] args, Player p, UserData data) {
        NationData nation = NationData.getNationData(data.nation);
        if (nation == null) {
            p.sendMessage(nation("당신은 국가에 소속되어 있지 않습니다."));
            return;
        }
        if (nation.castles.size() < 1) {
            p.sendMessage(nation("온전한 나라가 아닙니다. 먼저 성을 건설해주세요."));
            return;
        }
        if (nation.isWar()) {
            p.sendMessage(nation("전쟁 중에는 할 수 없습니다."));
            return;
        }
        if (args.length == 1) {
            p.sendMessage(nation("&e국가의 금고: &f" + String.format("%,d", (long) NationData.getNationData(data.nation).money) + "&eG"));
            for (String a : nation.allies) {
                NationData allieNation = NationData.getNationData(a);
                p.sendMessage(nation("&e동맹국 &6" + a + "&e의 금고: &f" + String.format("%,d", (long) allieNation.money) + "&eG"));
            }
        } else if (args.length == 3) {
            int value = Integer.parseInt(args[2]);
            if (0 >= value) {
                p.sendMessage(nation("금액은 0보다 커야합니다."));
                return;
            }
            if (args[1].trim().equalsIgnoreCase("입금")) {
                if (FourCore.economy.getBalance(p) < value) {
                    p.sendMessage(nation("그만한 금액이 없습니다."));
                    return;
                }
                FourCore.economy.withdrawPlayer(p, value);
                nation.money += value;
                broadcastNation(nation, nation("&e" + p.getName() + " &f님이 국가 금고에 &a" + String.format("%,d", (long) value) + " &f원을 입금하셨습니다."));
            } else if (args[1].trim().equalsIgnoreCase("출금")) {
                if (data.job != UserData.Job.왕 && data.job != UserData.Job.부왕) {
                    p.sendMessage(nation("왕과 부왕만 출금할 수 있습니다."));
                    return;
                }
                if (nation.money < value) {
                    p.sendMessage(nation("그만한 금액이 없습니다."));
                    return;
                }
                nation.money -= value;
                FourCore.economy.depositPlayer(p, value);
                broadcastNation(nation, nation("&e" + p.getName() + " &f님이 국가 금고에 &a" + String.format("%,d", (long) value) + " &f원을 출금하셨습니다."));
            } else {
                p.sendMessage(nation("/국가 금고 [입금|출금] [금액]"));
            }
        } else {
            p.sendMessage(nation("/국가 금고 [입금|출금] [금액]"));
        }

    }

    private void 초대(String[] args, Player p, UserData data) {
        NationData nation = NationData.getNationData(data.nation);
        if (nation == null) {
            p.sendMessage(nation("당신은 국가에 소속되어 있지 않습니다."));
            return;
        }
        if (args.length != 2) {
            p.sendMessage(nation("/국가 초대 [유저]"));
            return;
        }
        if (data.job != UserData.Job.왕 && data.job != UserData.Job.부왕) {
            p.sendMessage(nation("권한이 없습니다."));
            return;
        }
        if (nation.castles.size() < 1) {
            p.sendMessage(nation("온전한 나라가 아닙니다. 먼저 성을 건설해주세요."));
            return;
        }
        if (nation.getAllPlayerCount() >= nation.level + 5) {
            p.sendMessage(nation("국가의 인원이 가득 차서 더 이상 초대할 수 없습니다."));
            return;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            p.sendMessage(nation("해당 플레이어가 없습니다."));
            return;
        }
        if (!target.isOnline()) {
            p.sendMessage(nation("해당 플레이어는 오프라인 입니다."));
            return;
        }
        UserData targetData = UserData.getUserData(target);
        if (targetData.job != UserData.Job.일반유저) {
            p.sendMessage(nation("해당 플레이어는 이미 국가에 속해있습니다."));
            return;
        }
        if (nation.isWar()) {
            p.sendMessage(nation("전쟁 중에는 할 수 없습니다."));
            return;
        }
        RejoinNationCoolTime t = targetData.<RejoinNationCoolTime>getTimerable(RejoinNationCoolTime.class);
        if (t != null) {
            p.sendMessage(nation("해당 플레이어는 현재 국가 가입 쿨타임 중입니다. 남은시간: " + TimeUtility.secToTime(t.timer) + "초"));
            target.sendMessage(nation("당신은 현재 국가 가입 쿨타임 중입니다. 남은시간: " + TimeUtility.secToTime(t.timer) + "초"));
            return;
        }
        if (targetData.<InviteRequest>getTimerable(InviteRequest.class) == null) {
            new NationInviteRequest(30, targetData, data, nation);
            p.sendMessage(nation("&a" + target.getName() + " &f에게 초대 요청을 보냈습니다."));
        } else {
            p.sendMessage(nation("해당 플레이어가 다른 요청을 처리중입니다. 잠시 후에 다시 시도해 주세요."));
        }
    }

    private void 정보(String[] args, Player p, UserData data) {
        NationData nation = null;
        if (args.length == 1) {
            if (data.nation != null) {
                nation = NationData.getNationData(data.nation);
            }
        } else if (args.length >= 2) {
            nation = NationData.getNationData(args[1]);
        }
        if (nation == null) {
            p.sendMessage(nation("해당 국가가 없습니다."));
            return;
        }
        /*
        NationInfoMenu menu = new NationInfoMenu(PlayerMenuUtility.getPlayerMenuUtility(p));
        menu.target = nation;
        menu.open();
        */

        p.sendMessage(nation("&e" + nation.nationName + " &f국가 정보"));
        if (nation.allies.size() > 0) {
            p.sendMessage(nation("&f동맹국: &e" + nation.allies.get(0)));
        }
        p.sendMessage(nation("&f레벨: &6[&f" + nation.level + "&6]"));

        if (nation.king == null) {
            p.sendMessage(nation("&f국왕: &7-"));
        } else {
            p.sendMessage(nation("&f국왕: " + UserUtility.playerToString(Bukkit.getOfflinePlayer(nation.king))));
        }

        if (nation.viceroy == null) {
            p.sendMessage(nation("&f부왕: &7-"));
        } else {
            p.sendMessage(nation("&f부왕: " + UserUtility.playerToString(Bukkit.getOfflinePlayer(nation.viceroy))));
        }

        String generals = "";
        System.out.println("" + nation.generals.size());
        for (int i = 0; i < nation.generals.size(); i++) {
            UUID id = nation.generals.get(i);
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(id);
            generals += UserUtility.playerToString(offlinePlayer);
            if (i != nation.generals.size() - 1) {
                generals += "&f, ";
            }
        }

        String citizens = "";
        System.out.println("" + nation.citizens.size());
        for (int i = 0; i < nation.citizens.size(); i++) {
            UUID id = nation.citizens.get(i);
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(id);
            citizens += UserUtility.playerToString(offlinePlayer);
            if (i != nation.citizens.size() - 1) {
                citizens += "&f, ";
            }
        }

        p.sendMessage(nation("&f장군: " + generals));
        p.sendMessage(nation("&f시민: " + citizens));
        p.sendMessage(nation("&f성개수: " + nation.castles.size() + "개"));
        if (nation.nationName.equalsIgnoreCase(data.nation))
            p.sendMessage(nation("&f금고: " + String.format("%,d", (long) nation.money) + "원"));
        WarNewbieCoolTime t = nation.<WarNewbieCoolTime>getTimerable(WarNewbieCoolTime.class);
        if (t != null)
            p.sendMessage(nation("&f뉴비 보호 남은시간: " + TimeUtility.secToTime(t.timer)));

    }

    private void 양도(String[] args, Player p, UserData data) {
        NationData nation = NationData.getNationData(data.nation);
        if (nation == null) {
            p.sendMessage(nation("당신은 국가에 소속되어 있지 않습니다."));
            return;
        }
        if (args.length != 2) {
            p.sendMessage(nation("/국가 양도 [유저]"));
        }
        if (data.job != UserData.Job.왕) {
            p.sendMessage(nation("왕의 권한이 없습니다."));
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            p.sendMessage(nation("해당 플레이어가 없습니다."));
            return;
        }
        if (!target.isOnline()) {
            p.sendMessage(nation("해당 플레이어는 오프라인 입니다."));
            return;
        }
        UserData targetData = UserData.getUserData(target);
        if (!data.nation.equalsIgnoreCase(targetData.nation)) {
            p.sendMessage(nation("&e" + nation.nationName + "&f의 국민이 아닙니다."));
            return;
        }
        if (targetData.job != UserData.Job.부왕) {
            p.sendMessage(nation("왕은 부왕에게만 양도할 수 있습니다. 먼저 부왕으로 승격시켜주세요."));
            return;
        }
        if (nation.<ChangeKingCoolTime>getTimerable(ChangeKingCoolTime.class) != null) {
            p.sendMessage(nation("현재 국가는 왕 양도 쿨타임 중입니다. 남은시간: " + TimeUtility.secToTime(nation.<ChangeKingCoolTime>getTimerable(ChangeKingCoolTime.class).timer)));
            return;
        }

        nation.king = target.getUniqueId();
        nation.viceroy = p.getUniqueId();
        targetData.job = UserData.Job.왕;
        data.job = UserData.Job.부왕;

        new ChangeKingCoolTime(86400, nation);
        Bukkit.broadcastMessage(nation("&e" + p.getName() + " &f님께서 &6" + nation.nationName + " &f국가를 &e" + args[1] + " &f님에게 양도하였습니다."));
    }

    private void 직급(String[] args, Player p, UserData data) {
        NationData nation = NationData.getNationData(data.nation);
        if (nation == null) {
            p.sendMessage(nation("당신은 국가에 소속되어 있지 않습니다."));
            return;
        }
        if (args.length != 3) {
            p.sendMessage(nation("/국가 직급 [유저명] [직급]"));
            return;
        }
        if (data.job != UserData.Job.왕) {
            p.sendMessage(nation("왕의 권한이 없습니다."));
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        UserData targetData = null;
        if (target == null) {
            for (UserData d : UserData.map.values()) {
                if (d.userName.equalsIgnoreCase(args[1])) {
                    targetData = d;
                    break;
                }
            }
        } else {
            targetData = UserData.getUserData(target);
        }
        if (targetData == null) {
            p.sendMessage(nation("해당 플레이어가 없습니다."));
            return;
        }
        /*
        if (!target.isOnline() && args[2] == Job.부왕.name()) {
            p.sendMessage(nation("해당 플레이어는 오프라인 입니다."));
            return;
        }*/
        if (!data.nation.equalsIgnoreCase(targetData.nation)) {
            p.sendMessage(nation("&e" + nation.nationName + "&f의 국민이 아닙니다."));
            return;
        }
        if (args[2].trim().equalsIgnoreCase(UserData.Job.부왕.name())) {
            if (nation.<ChangeViceroyCoolTime>getTimerable(ChangeViceroyCoolTime.class) != null) {
                p.sendMessage(nation("현재 국가는 부왕 양도 쿨타임 중입니다. 남은시간: " + TimeUtility.secToTime(nation.<ChangeViceroyCoolTime>getTimerable(ChangeViceroyCoolTime.class).timer)));
                return;
            }
            if (nation.viceroy == null) {
                targetData.setUserJob(UserData.Job.부왕);
                p.sendMessage(nation(args[1] + "에게 " + args[2] + "직급을 임명했습니다"));
                new ChangeViceroyCoolTime(86400, nation);
                if (target != null && target.isOnline()) {
                    target.getPlayer().sendMessage(nation("부왕을 임명받았습니다!"));
                } else {
                    Document doc = new Document()
                            .append("job", targetData.job.name());
                    UpdateOptions options = new UpdateOptions().upsert(true);
                    MongoDB.user.updateOne(eq("uuid", targetData.uuid.toString()), new Document("$set", doc), options);
                }
            } else {
                p.sendMessage(nation("부왕이 이미 존재합니다."));
            }
        } else if (args[2].trim().equalsIgnoreCase(UserData.Job.장군.name())) {
            if (nation.generals.size() >= 2) {
                p.sendMessage(nation("&f장군은 최대 2명입니다."));
                return;
            }
            targetData.setUserJob(UserData.Job.장군);
            p.sendMessage(nation(args[1] + "에게 " + args[2] + "직급을 임명했습니다"));
            if (target != null && target.isOnline()) {
                target.getPlayer().sendMessage(nation("장군을 임명받았습니다."));
            } else {
                Document doc = new Document()
                        .append("job", targetData.job.name());
                UpdateOptions options = new UpdateOptions().upsert(true);
                MongoDB.user.updateOne(eq("uuid", targetData.uuid.toString()), new Document("$set", doc), options);
            }
        } else if (args[2].trim().equalsIgnoreCase(UserData.Job.시민.name())) {
            targetData.setUserJob(UserData.Job.시민);
            p.sendMessage(nation(args[1] + "에게 " + args[2] + "직급을 임명했습니다"));
            if (target != null && target.isOnline()) {
                target.getPlayer().sendMessage(nation("시민을 임명받았습니다."));
            } else {
                Document doc = new Document()
                        .append("job", targetData.job.name());
                UpdateOptions options = new UpdateOptions().upsert(true);
                MongoDB.user.updateOne(eq("uuid", targetData.uuid.toString()), new Document("$set", doc), options);
            }
        } else {
            p.sendMessage(nation("올바른 직급이 아닙니다. [부왕/장군/시민] 직급을 임명하세요. "));
        }
    }

    private void 생성(String[] args, Player p, UserData data) {
        Material mat = Material.COBBLESTONE;
        double cost = 3000000.0;
        int mat_cost = 2;

        if (args.length != 2) {
            p.sendMessage(nation("&7/국가 생성 [국가명(5글자이하)]"));
            return;
        }
        if (data.job != UserData.Job.일반유저) {
            p.sendMessage(nation("당신은 이미 국가에 소속되어 있습니다."));
            return;
        }
        int stone = CustomItemUtility.amount(p.getInventory(), CustomItemUtility.NationStoneItem());
        if (FourCore.economy.getBalance(p) < cost || stone < mat_cost || !p.getInventory().contains(Material.BEACON)) {
            p.sendMessage(nation("국가 생성에 필요한 자원이 부족합니다."));
            p.sendMessage(nation("(&6" + (long) (FourCore.economy.getBalance(p) * 0.0001) + "&f/300 &f만원, &6" + stone + "&f/" + mat_cost + " &f국가 강화석, &6" + CustomItemUtility.amount(p.getInventory(), new ItemStack(Material.BEACON)) + "&f/&f1 신호기) 필요"));
            return;
        }
        if (args[1].length() <= 0 || args[1].length() > 5) {
            p.sendMessage(nation("국가 이름은 5글자 이하여야 합니다."));
            return;
        }
        NationData nation = NationData.createNation(args[1], p);
        if (nation == null) {
            p.sendMessage(nation("같은 이름의 다른 국가가 이미 있습니다. 다른 이름의 국가를 생성해주세요."));
            return;
        }
        Bukkit.broadcastMessage(nation("&a[ " + p.getName() + " ] &f님이 &e[ " + args[1] + " ] &f국가를 생성하셨습니다!"));
        p.sendMessage(nation("완전한 국가가 되기 위해 &c1시간 &f안에 국가의 성을 생성해 주세요."));
        p.sendMessage(nation("뉴비국가 보호기간이 72시간 동안 적용됩니다. '/국가 보호 해제'로 풀 수 있습니다."));
        new CastleBuildTime(3600, nation);
        //new WarNewbieCoolTime(259200, nation); // 72h
        FourCore.economy.withdrawPlayer(p, cost);
        data.job = UserData.Job.왕;
        data.nation = args[1];
        CustomItemUtility.remove(p.getInventory(), CustomItemUtility.NationStoneItem(), 2);
        if (data.<RejoinNationCoolTime>getTimerable(RejoinNationCoolTime.class) != null) {
            data.<RejoinNationCoolTime>getTimerable(RejoinNationCoolTime.class).stop();
        }
        //p.getInventory().removeItem(new ItemStack(mat, mat_cost));
    }
}
