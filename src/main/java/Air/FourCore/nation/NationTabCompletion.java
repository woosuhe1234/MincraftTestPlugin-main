package Air.FourCore.nation;

import Air.FourCore.user.UserData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NationTabCompletion implements TabCompleter {


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        List<String> results = new ArrayList<>();

        if(args.length == 1){
            if(command.getName().equalsIgnoreCase("국가")){
                results.addAll(new ArrayList<>(
                        Arrays.asList(
                                "생성", "임명", "양도", "초대", "정보","채팅", "금고","PVP",
                                "전쟁선포", "전쟁항복", "동맹신청", "동맹채팅", "동맹참전", "동맹파기"
                        )
                ));
            }else if(command.getName().equalsIgnoreCase("국가관리")){
                results.addAll(new ArrayList<>(
                        Arrays.asList(
                                "전쟁중단", "전쟁차단", "삭제", "추방", "임명"
                        )
                ));
            }

        }
        else if(args.length == 2){

            if(args[0].equalsIgnoreCase("정보") || args[0].equalsIgnoreCase("전쟁선포") ||
                    args[0].equalsIgnoreCase("동맹신청") || args[0].equalsIgnoreCase("삭제")){
                results.addAll(NationData.map.keySet());
            }
            else if(args[0].equalsIgnoreCase("임명")|| args[0].equalsIgnoreCase("양도") ||
                    args[0].equalsIgnoreCase("초대")|| args[0].equalsIgnoreCase("추방")){
                Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
                Bukkit.getServer().getOnlinePlayers().toArray(players);
                for (Player player : players) {
                    results.add(player.getName());
                }
            }
        }
        else if(args.length == 3){

            if(args[0].equalsIgnoreCase("임명")){
                results.add(UserData.Job.부왕.name());
                results.add(UserData.Job.장군.name());
                results.add(UserData.Job.시민.name());
            }
        }

        return results;
    }
}
