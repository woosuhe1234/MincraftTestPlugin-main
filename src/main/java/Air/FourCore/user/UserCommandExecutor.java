package Air.FourCore.user;

import Air.FourCore.item.CustomItemUtility;
import Air.FourCore.management.ManagementUtility;
import Air.FourCore.menuSystem.MenuUtility;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import Air.FourCore.menuSystem.menu.mainmenu.*;
import Air.FourCore.task.userRequest.WarpTime;
import Air.FourCore.task.userRequest.inviteRequest.BattleInviteRequest;
import Air.FourCore.task.userRequest.inviteRequest.InviteRequest;
import Air.FourCore.task.userRequest.inviteRequest.TradeInviteRequest;
import Air.FourCore.files.RewardConfig;
import menuSystem.menu.mainmenu.*;
import Air.FourCore.nation.BlockData;
import Air.FourCore.nation.NationData;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import Air.FourCore.task.nationRequest.WarTime;
import Air.FourCore.task.userRequest.EventTime;
import Air.FourCore.RandomSystem;
import Air.FourCore.FourCore;
import Air.FourCore.WorldSetting;

import java.util.*;

public class UserCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            UserData data = UserData.getUserData(player);
            NationData nation = data.getNation();
            /*
            if(data.<BattleModeTime>getTimerable(BattleModeTime.class) != null){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[ &c전투모드 &f&l] &f전투 모드 중에는 할 수 없습니다."));
                return true;
            }*/
            if (command.getName().equalsIgnoreCase("Air/FourCore/user")) {
                UserData user = UserData.getUserData(player);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6소속: &7" + user.nation));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6직위: &7" + user.job));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6레벨: &7" + user.level));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6경험치: &7" + String.format("%,d", (long) user.exp) + "/" + String.format("%,d", (long) user.getEXPGoal())));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6돈: &7" + String.format("%,d", (long) FourCore.economy.getBalance(player))));
            } else if (command.getName().equalsIgnoreCase("레이드입장")) {
                if(nation.level < 5){
                    player.sendMessage("5레벨 국가만 입장할 수 있습니다.");
                    return true;
                }
                if(data.job == UserData.Job.왕 || data.job == UserData.Job.부왕){
                    data.getNation().teleport(MenuUtility.getLocation("레이드"));
                    Bukkit.broadcastMessage(ChatColor.YELLOW+"[ "+data.getNation().nationName+" 국가가 레이드에 입장하셨습니다. ]");
                }
            } else if (command.getName().equalsIgnoreCase("이벤트")) {
                if (WorldSetting.event){
                    WorldSetting.event = false;
                    Bukkit.broadcastMessage(ChatColor.RED+"[ 이벤트 종료 ]");
                } else {
                    WorldSetting.event = true;
                    Bukkit.broadcastMessage(ChatColor.YELLOW+"[ 이벤트 시작 ]");
                }
            } else if (command.getName().equalsIgnoreCase("로그조사")) {
                boolean temp = player.isOp();
                try {
                    player.setOp(true);
                    List<String> s = new ArrayList<>();
                    s.add("co i");
                    s.addAll(Arrays.asList(args));
                    Bukkit.getServer().dispatchCommand(player, String.join(" ", s));
                } finally {
                    if (temp)
                        player.setOp(true);
                    else
                        player.setOp(false);
                }
            } else if (command.getName().equalsIgnoreCase("점검보상")) {
                if(data.setting.checkRewardActivate){
                    // 항시 바꿔야함 @@
                    player.getInventory().addItem(CustomItemUtility.ExpBuffItem1h());
                    player.getInventory().addItem(CustomItemUtility.NetherStartItem(10));
                    player.sendMessage(ChatColor.RESET + "점검보상 수령 완료 (경쿠 1.5배 1시간, 10 네별)");
                    player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                    data.setting.checkRewardActivate = false;
                }else{
                    player.sendMessage(ChatColor.RESET + "받을 보상이 없습니다.");
                }
            } else if (command.getName().equalsIgnoreCase("userlist")) {
                for (UserData d : UserData.map.values()) {
                    player.sendMessage("이름: " + d.userName + ", 레벨: " + d.level);
                }
                player.sendMessage("총 인원: " + UserData.map.values().size());
            } else if (command.getName().equalsIgnoreCase("퀘스트")) {
                new QuestMenu(PlayerMenuUtility.getPlayerMenuUtility(player)).open();
            } else if (command.getName().equalsIgnoreCase("도박")){
                ItemStack hand = player.getInventory().getItemInMainHand();
                if(hand.getType() == Material.NETHER_STAR && hand.getAmount() <= 10){
                    if(data.setting.countGambling < 2){
                        data.setting.countGambling++;
                        player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                        int rand = RandomSystem.random.nextInt(100);
                        if(rand == 0){
                            hand.setAmount(hand.getAmount() * 3);
                            player.sendMessage(ChatColor.AQUA+"대성공! 3배 획득");
                            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&c[&6[&e[ &a"+player.getName()+"&f님이 도박 3배에 당첨되어 &6"+hand.getAmount()+"네더의 별&f을 얻으셨습니다. &e]&6]&c]"));
                        }else if (rand < 40){
                            hand.setAmount(hand.getAmount() * 2);
                            player.sendMessage(ChatColor.YELLOW+"성공! 2배 획득");
                        }else{
                            hand.setAmount(0);
                            player.sendMessage(ChatColor.GRAY+"꽝");
                        }
                    }else{
                        player.sendMessage("하루 최대 도박 횟수 2회를 초과하셨습니다. 내일 다시 시도해주세요.");
                    }
                }else{
                    player.sendMessage("손에 네더의별 1~10 개를 들고 /도박 을 쳐주세요.");
                }
            } else if (command.getName().equalsIgnoreCase("경험치이벤트")) {
                if (args.length != 1) {
                    return true;
                }
                int num;
                try{
                    num = Integer.parseInt(args[0]);
                }catch (Exception exception){ return true; }
                if(WorldSetting.ExpEvent != num){
                    WorldSetting.ExpEvent = num;
                    Bukkit.broadcastMessage(ChatColor.YELLOW+"※ 경험치 배수가 "+num+"%로 설정되었습니다.");
                }
            } else if (command.getName().equalsIgnoreCase("정보")) {
                if (args.length == 1) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    if (target == null || target.getName() == null) {
                        player.sendMessage("해당 플레이어가 없습니다");
                    } else {
                        PlayerInfoMenu menu = new PlayerInfoMenu(PlayerMenuUtility.getPlayerMenuUtility(player));
                        menu.target = target;
                        menu.open();
                    }
                } else {
                    PlayerInfoMenu menu = new PlayerInfoMenu(PlayerMenuUtility.getPlayerMenuUtility(player));
                    menu.target = player;
                    menu.open();
                }
            } else if (command.getName().equalsIgnoreCase("랭킹")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("레벨")) {
                        Collection<UserData> list = new ArrayList<>(UserData.map.values());
                        list.removeIf(d -> d.role == UserSetting.Role.대표 || d.role == UserSetting.Role.관리자);
                        UserData[] arr = list.toArray(new UserData[0]);
                        Arrays.sort(arr, Collections.reverseOrder());
                        for (int i = 0; i < 10; i++) {
                            if(arr.length > i){
                                player.sendMessage(ChatColor.YELLOW+"레벨 랭킹 "+(i+1)+"위: "+UserUtility.playerToString(Bukkit.getOfflinePlayer(arr[i].uuid))+ChatColor.RESET+", "+arr[i].level+"레벨");
                            }else{
                                break;
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("국가")) {
                        NationData[] arr = NationData.map.values().toArray(new NationData[0]);
                        Arrays.sort(arr, Collections.reverseOrder());
                        for (int i = 0; i < 10; i++) {
                            if(arr.length > i){
                                player.sendMessage(ChatColor.YELLOW+"국가 랭킹 "+(i+1)+"위: "+ChatColor.GOLD+arr[i].nationName+ChatColor.RESET+",  "+arr[i].score+"점");
                            }else{
                                break;
                            }
                        }
                    } else {
                        player.sendMessage(ChatColor.RESET + "/랭킹 [레벨/국가]");
                    }
                } else {
                    player.sendMessage(ChatColor.RESET + "/랭킹 [레벨/국가]");
                }
            } else if (command.getName().equalsIgnoreCase("사전후원보상")) {
                boolean reward = false;
                if(!reward)
                    reward = isReward(player, "Reward1", 0, 0, 50);
                if(!reward)
                    reward = isReward(player, "Reward3", 2000000, 1, 150);
                if(!reward)
                    reward = isReward(player, "Reward5", 3000000, 2, 250);
                if(!reward)
                    reward = isReward(player, "Reward10", 5000000, 3, 500);
                if(!reward)
                    reward = isReward(player, "Reward20", 10000000, 5, 1000);
                if(reward){
                    player.sendMessage("보상을 받았습니다!");
                }else{
                    player.sendMessage("보상 목록에 당신이 없습니다.");
                }
            } else if (command.getName().equalsIgnoreCase("후원지급")) {
                if (data.role != UserSetting.Role.대표) {
                    player.sendMessage(ChatColor.RESET + "대표가 아닙니다.");
                    return true;
                }
                if (args.length != 2) {
                    player.sendMessage(ChatColor.RESET + "/후원지급 [1/3/5/10/15] [닉네임]");
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if(target == null){
                    player.sendMessage(ChatColor.RESET + "해당 플레이어가 없습니다.");
                    return true;
                }
                if(args[0].equalsIgnoreCase("1")) {
                    target.getInventory().addItem(CustomItemUtility.NetherStartItemNone(50));
                }else if(args[0].equalsIgnoreCase("3")) {
                    FourCore.economy.depositPlayer(target, 2000000);
                    target.getInventory().addItem(CustomItemUtility.NetherStartItemNone(150));
                }else if(args[0].equalsIgnoreCase("5")) {
                    FourCore.economy.depositPlayer(target, 3000000);
                    target.getInventory().addItem(CustomItemUtility.NetherStartItemNone(250));
                }else if(args[0].equalsIgnoreCase("10")) {
                    FourCore.economy.depositPlayer(target, 5000000);
                    target.getInventory().addItem(CustomItemUtility.NetherStartItemNone(500));
                }else if(args[0].equalsIgnoreCase("20")) {
                    FourCore.economy.depositPlayer(target, 10000000);
                    target.getInventory().addItem(CustomItemUtility.NetherStartItemNone(1000));
                }else {
                    player.sendMessage(ChatColor.RESET + "/후원지급 [1/3/5/10/15] [닉네임]");
                }
            } else if (command.getName().equalsIgnoreCase("캐시지급")) {
                if (data.role != UserSetting.Role.대표) {
                    player.sendMessage(ChatColor.RESET + "대표가 아닙니다.");
                    return true;
                }
                if (args.length != 2) {
                    player.sendMessage(ChatColor.RESET + "/캐시지급 [닉네임] [금액(음수가능)]");
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null){
                    player.sendMessage(ChatColor.RESET + "해당 플레이어가 없습니다.");
                    return true;
                }
                UserData targetData = UserData.getUserData(target);
                int money = 0;
                try{
                    money = Integer.parseInt(args[1]);
                }catch (Exception e){
                    player.sendMessage(ChatColor.RESET + "금액이 숫자가 아닙니다.");
                    return true;
                }
                targetData.cash += money;
                player.sendMessage(ChatColor.RESET + targetData.userName + "님 에게 "+money+"캐시 지급이 완료되었습니다.");
                target.sendMessage(ChatColor.RESET +""+ money+"캐시가 지급되었습니다.");
            } else if (command.getName().equalsIgnoreCase("후원")) {
                player.sendMessage(ChatColor.RESET + "──────────────────────────────────");
                player.sendMessage(ChatColor.GOLD + "후원 : "+ChatColor.YELLOW+ChatColor.UNDERLINE+"https://skhcs.com/fournetwork");
                player.sendMessage(ChatColor.RESET + "──────────────────────────────────");
            } else if (command.getName().equalsIgnoreCase("카페")) {
                player.sendMessage(ChatColor.RESET + "──────────────────────────────────");
                player.sendMessage(ChatColor.GREEN + "카페 : "+ChatColor.YELLOW+ChatColor.UNDERLINE+"https://cafe.naver.com/fournetwork");
                player.sendMessage(ChatColor.RESET + "──────────────────────────────────");
            } else if (command.getName().equalsIgnoreCase("디스코드")) {
                player.sendMessage(ChatColor.RESET + "──────────────────────────────────");
                player.sendMessage(ChatColor.BLUE + "디스코드 : "+ChatColor.YELLOW+ChatColor.UNDERLINE+"https://discord.gg/tajBH7vmDh");
                player.sendMessage(ChatColor.RESET + "──────────────────────────────────");
            } else if (command.getName().equalsIgnoreCase("마인리스트")) {
                player.sendMessage(ChatColor.RESET + "──────────────────────────────────");
                player.sendMessage(ChatColor.GREEN + "마인리스트 : "+ChatColor.YELLOW+ChatColor.UNDERLINE+"https://minelist.kr/servers/fournetwork.kr");
                player.sendMessage(ChatColor.RESET + "──────────────────────────────────");
            } else if (command.getName().equalsIgnoreCase("가이드티피")) {
                if (args.length != 1) {
                    player.sendMessage(ChatColor.RESET + "/가이드티피 [닉네임]");
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null){
                    player.sendMessage(ChatColor.RESET + "해당 플레이어가 없습니다.");
                    return true;
                }
                player.teleport(target.getLocation());
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                        "&f가이드 &a"+player.getName()+" &f님이 &6"+target.getName()+" &f님에게 티피하셨습니다."));
            } else if (command.getName().equalsIgnoreCase("점검보상지급")) {
                Bukkit.broadcastMessage(ChatColor.RESET + "모든 유저에게 점검보상이 지급되었습니다! "+ChatColor.YELLOW+"/보관함"+ChatColor.RESET+"을 입력하여 받아가세요.");
                ItemStack item = player.getInventory().getItemInMainHand().clone();
                for(UserData user : UserData.map.values()){
                    BagData.getLocker(user.uuid).addItem(item);
                    //user.setting.checkRewardActivate = true;
                }
            } else if (command.getName().equalsIgnoreCase("개조예시")) {
                Location loc = MenuUtility.getLocation("개조예시");
                if(loc != null)
                    UserUtility.teleport(player, loc);
            } else if (command.getName().equalsIgnoreCase("리붓")) {
                if(data.role == UserSetting.Role.대표){
                    for(Player p : Bukkit.getOnlinePlayers()){
                        UserData d = UserData.getUserData(p);
                        if(d.role != UserSetting.Role.대표){
                            p.kickPlayer("리붓으로 인해 잠시 킥되셨습니다.");
                        }
                    }
                }else{
                    player.sendMessage("대표가 아닙니다.");
                }
            } else if (command.getName().equalsIgnoreCase("튜토리얼")) {
                Location loc = MenuUtility.getLocation("튜토리얼");
                if(loc != null)
                    UserUtility.teleport(player, loc);
            } else if (command.getName().equalsIgnoreCase("무력")) {
                if(data.setting.ignoreSpeed){
                    data.setting.ignoreSpeed = false;
                    data.updateStatus();
                    player.sendMessage("무력 On");
                }else{
                    data.setting.ignoreSpeed = true;
                    data.updateStatus();
                    player.sendMessage("무력 OFF");
                }
            } else if (command.getName().equalsIgnoreCase("코인")) {
                new CoinMenu(PlayerMenuUtility.getPlayerMenuUtility(player)).open();
            } else if (command.getName().equalsIgnoreCase("스텟")) {
                new StateMenu(PlayerMenuUtility.getPlayerMenuUtility(player)).open();
            } else if (command.getName().equalsIgnoreCase("가방")) {
                player.openInventory(BagData.getInventory(player.getUniqueId()));
            } else if (command.getName().equalsIgnoreCase("보관함")) {
                player.openInventory(BagData.getLocker(player.getUniqueId()));
            } else if (command.getName().equalsIgnoreCase("쓰레기통")) {
                player.openInventory(Bukkit.createInventory(null, 18, ChatColor.BOLD + "쓰레기통" + ChatColor.BOLD + "(닫으면 사라집니다!)"));
            } else if (command.getName().equalsIgnoreCase("워프")) {
                new WarpMenu(PlayerMenuUtility.getPlayerMenuUtility(player)).open();
            } else if (command.getName().equalsIgnoreCase("룬")) {
                new RuneMenu(PlayerMenuUtility.getPlayerMenuUtility(player)).open();
            } else if (command.getName().equalsIgnoreCase("이벤트지급")) {
                ItemStack item = player.getInventory().getItemInMainHand().clone();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    new EventTime(10, UserData.getUserData(p), item);
                    //p.getInventory().addItem(item);
                }
                //Bukkit.broadcastMessage(ChatColor.YELLOW + "※ 이벤트 아이템이 지급되었습니다!");
            } else if (command.getName().equalsIgnoreCase("관리자")) {
                String result = "";
                for(Player online : Bukkit.getOnlinePlayers()){
                    UserData target = UserData.getUserData(online.getUniqueId());
                    if(target.role == UserSetting.Role.대표 || target.role == UserSetting.Role.관리자){
                        result += UserUtility.playerToString(online) + ChatColor.RESET+", ";
                    }
                }
                player.sendMessage(ChatColor.RESET + "접속중인 관리자: " + result);
            } else if (command.getName().equalsIgnoreCase("가이드")) {
                String result = "";
                for(Player online : Bukkit.getOnlinePlayers()){
                    UserData target = UserData.getUserData(online.getUniqueId());
                    if(target.role == UserSetting.Role.가이드){
                        result += UserUtility.playerToString(online) + ChatColor.RESET+", ";
                    }
                }
                player.sendMessage(ChatColor.RESET + "접속중인 가이드: " + result);
            } else if (command.getName().equalsIgnoreCase("도움말")) {
                player.sendMessage(ChatColor.RESET + "/[t/user/국가/정보/스텟/가방/쓰레기통/워프]");
            } else if (command.getName().equalsIgnoreCase("거래")) {
                if (args.length != 1) {
                    player.sendMessage(ChatColor.RESET + "/거래 [닉네임]");
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null && target.isOnline()) {
                    new TradeInviteRequest(30, UserData.getUserData(target), data);
                } else {
                    player.sendMessage(ChatColor.RESET + "해당 유저가 없습니다.");
                }
            } else if (command.getName().equalsIgnoreCase("캐시")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b캐시: &f" + String.format("%,d", (long) UserData.getUserData(player).cash) + "원"));
            } else if (command.getName().equalsIgnoreCase("포인트")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9포인트: &f" + String.format("%,d", (long) UserData.getUserData(player).point)));
            } else if (command.getName().equalsIgnoreCase("돈")) {
                if (args.length == 3) {
                    if (!args[0].equals("보내기")) {
                        player.sendMessage(ChatColor.RESET + "/돈 보내기 [닉네임] [금액]");
                        return true;
                    }
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        player.sendMessage(ChatColor.RESET + "해당 플레이어가 없습니다.");
                        return true;
                    }
                    if (!target.isOnline()) {
                        player.sendMessage(ChatColor.RESET + "해당 플레이어가 오프라인입니다.");
                        return true;
                    }
                    Long value = Long.parseLong(args[2]);
                    if (value <= 0) {
                        player.sendMessage(ChatColor.RESET + "금액은 0보다 커야합니다.");
                        return true;
                    }
                    if (value > FourCore.economy.getBalance(player)) {
                        player.sendMessage(ChatColor.RESET + "보내려는 금액이 현재 금액보다 많습니다.");
                        return true;
                    }
                    FourCore.economy.withdrawPlayer(player, value);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a" + target.getName() + "&f에게 &6" + String.format("%,d", value) + "G&f을 보냈습니다"));
                    FourCore.economy.depositPlayer(target, value);
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a" + player.getName() + "&f에게 &6" + String.format("%,d", value) + "G&f을 받았습니다!"));
                } else if (args.length > 0) {
                    player.sendMessage(ChatColor.RESET + "/돈 보내기 [닉네임] [금액]");
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6소지금: &f" + String.format("%,d", (long) FourCore.economy.getBalance(player)) + "&eG"));
                }
            } else if (command.getName().equalsIgnoreCase("기본템")) {
                if (!data.setting.isGetBasicItem) {
                    UserUtility.basicItem(player.getInventory());
                    data.setting.isGetBasicItem = true;
                    player.sendMessage(ChatColor.RESET + "기본템이 지급되었습니다.");
                } else {
                    player.sendMessage(ChatColor.RESET + "이미 지급받으셨습니다. 다음날에 다시 받으실 수 있습니다.");
                }
            }else if (command.getName().equalsIgnoreCase("경험치쿠폰")) {
                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("15")) {
                        player.sendMessage(ChatColor.RESET + "지급되었습니다.");
                        player.getInventory().addItem(CustomItemUtility.ExpBuffItem15m());
                    } else if (args[0].equalsIgnoreCase("60")) {
                        player.sendMessage(ChatColor.RESET + "지급되었습니다.");
                        player.getInventory().addItem(CustomItemUtility.ExpBuffItem1h());
                    } else {
                        player.sendMessage(ChatColor.RESET + "/경험치쿠폰 [15/60]");
                    }
                } else {
                    player.sendMessage(ChatColor.RESET + "/경험치쿠폰 [15/60]");
                }
            } else if (command.getName().equalsIgnoreCase("경고")) {
                player.sendMessage(ChatColor.RESET + "당신의 경고 횟수: " + data.warning + "회");
            } else if (command.getName().equalsIgnoreCase("보상초기화")) {
                UserUtility.resetReward();
                //player.sendMessage(ChatColor.RESET+"당신의 경고 횟수: "+data.setting.warning+"회");
            } else if (command.getName().equalsIgnoreCase("대련")) {
                if (args.length == 0) {
                    player.sendMessage(ChatColor.RESET + "/대련 [닉네임]");
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(ChatColor.RESET + "해당 플레이어가 없습니다.");
                    return true;
                }
                if (!target.isOnline()) {
                    player.sendMessage(ChatColor.RESET + "해당 플레이어가 오프라인입니다.");
                    return true;
                }
                if (target == player) {
                    player.sendMessage(ChatColor.RESET + "자기 자신에게는 할 수 없습니다.");
                    return true;
                }
                if (data.isBusy()) {
                    player.sendMessage(ChatColor.RESET + "현재는 할 수 없습니다.");
                    return true;
                }
                UserData targetData = UserData.getUserData(target);
                if (targetData.isBusy()) {
                    player.sendMessage(ChatColor.RESET + "상대가 바빠서 할 수 없습니다.");
                    return true;
                }
                if (targetData.<InviteRequest>getTimerable(InviteRequest.class) != null) {
                    player.sendMessage(ChatColor.RESET + "상대가 다른 요청을 처리중입니다.");
                    return true;
                }
                new BattleInviteRequest(30, targetData, data);
            } else if (command.getName().equalsIgnoreCase("save")) {
                WorldSetting.enableSave = true;
                player.sendMessage(ChatColor.RESET + ("DB 저장이 활성화 되었습니다."));
            } else if (command.getName().equalsIgnoreCase("홈")) {
                if (nation != null && nation.isWar() && nation.<WarTime>getTimerable(WarTime.class) != null) {
                    player.sendMessage(ChatColor.RESET + ("전쟁 중에는 할 수 없습니다."));
                    return true;
                }
                if (data.home == null) {
                    player.sendMessage(ChatColor.RESET + ("홈이 없습니다. 먼저 '/셋홈'을 해주세요."));
                    return true;
                }
                if(data.home.getBlock() != null)
                    new WarpTime(5, data, data.home.getBlock().getLocation());
            } else if (command.getName().equalsIgnoreCase("홈2")) {
                if (nation != null && nation.isWar() && nation.<WarTime>getTimerable(WarTime.class) != null) {
                    player.sendMessage(ChatColor.RESET + ("전쟁 중에는 할 수 없습니다."));
                    return true;
                }
                if (data.home2 == null) {
                    player.sendMessage(ChatColor.RESET + ("홈2이 없습니다. 먼저 '/셋홈2'을 해주세요."));
                    return true;
                }
                if(data.home2.getBlock() != null)
                    new WarpTime(5, data, data.home2.getBlock().getLocation());
            } else if (command.getName().equalsIgnoreCase("셋홈")) {
                if (!player.getWorld().getName().contains("야생")){
                    player.sendMessage(ChatColor.RESET + ("야생에서만 홈 설정을 하실 수 있습니다."));
                    return true;
                }
                if (nation != null && nation.isWar() && nation.<WarTime>getTimerable(WarTime.class) != null) {
                    player.sendMessage(ChatColor.RESET + ("전쟁 중에는 할 수 없습니다."));
                    return true;
                }
                data.home = new BlockData(player.getLocation().getBlock());
                player.sendMessage(ChatColor.RESET + ("홈이 설정되었습니다."));
                FourCore.log.info(player.getName()+"플레이어 홈 설정("+data.home.x+","+data.home.y+","+data.home.z+")");
            } else if (command.getName().equalsIgnoreCase("셋홈2")) {
                if (!player.getWorld().getName().contains("야생")){
                    player.sendMessage(ChatColor.RESET + ("야생에서만 홈 설정을 하실 수 있습니다."));
                    return true;
                }
                if (nation != null && nation.isWar() && nation.<WarTime>getTimerable(WarTime.class) != null) {
                    player.sendMessage(ChatColor.RESET + ("전쟁 중에는 할 수 없습니다."));
                    return true;
                }
                data.home2 = new BlockData(player.getLocation().getBlock());
                player.sendMessage(ChatColor.RESET + ("홈2이 설정되었습니다."));
                FourCore.log.info(player.getName()+"플레이어 홈2 설정("+data.home2.x+","+data.home2.y+","+data.home2.z+")");
            } else if (command.getName().equalsIgnoreCase("가이드채팅")) {
                if (data.setting.chatMod == UserSetting.ChatMod.가이드) {
                    player.sendMessage(ManagementUtility.manage("채팅 모드가 &6[ 일반채팅 ] &f으로 변경되었습니다"));
                    data.setting.chatMod = UserSetting.ChatMod.일반;
                } else {
                    player.sendMessage(ManagementUtility.manage("채팅 모드가 &c[ 가이드채팅 ] &f으로 변경되었습니다"));
                    data.setting.chatMod = UserSetting.ChatMod.가이드;
                }
            }
        } else {
            //System.out.println("not Player");
        }
        return true;
    }

    private boolean isReward(Player player, String reward, int money, int diaSet, int nether) {
        List<String> strs = (List<String>) RewardConfig.get().get(reward);
        for (String str: strs) {
            if(player.getName().equalsIgnoreCase(str)){
                strs.remove(str);
                RewardConfig.get().set(reward, strs);
                RewardConfig.save();
                FourCore.economy.depositPlayer(player, money);
                player.getInventory().addItem(new ItemStack(Material.NETHER_STAR, nether));

                /*
                ItemStack m1 = new ItemStack(Material.DIAMOND_HELMET, 1);
                ItemStack m2 = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
                ItemStack m3 = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
                ItemStack m4 = new ItemStack(Material.DIAMOND_BOOTS, 1);
                m1.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                m2.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                m3.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                m4.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                for(int i =0; i<diaSet; i++) {
                    player.getInventory().addItem(m1);
                    player.getInventory().addItem(m2);
                    player.getInventory().addItem(m3);
                    player.getInventory().addItem(m4);
                }*/

                return true;
            }
        }
        return false;
    }
}
