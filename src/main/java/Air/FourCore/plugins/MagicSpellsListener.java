package Air.FourCore.plugins;

import com.nisovin.magicspells.events.SpellApplyDamageEvent;
import com.nisovin.magicspells.events.SpellCastEvent;
import com.nisovin.magicspells.events.SpellTargetEvent;
import Air.FourCore.nation.NationData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import Air.FourCore.FourCore;
import Air.FourCore.user.UserData;

import java.util.HashSet;

import static Air.FourCore.user.UserData.getUserData;

public class MagicSpellsListener implements Listener {
    public static HashSet<Player> set = new HashSet<>();

    @EventHandler
    public void onSpellTarget(SpellTargetEvent e) {
        // 국가원간 PVP 금지
        try {
            Player p = e.getCaster();
            Player target = Bukkit.getPlayer(e.getTarget().getUniqueId());
            if (target != null) {
                NationData nation = getUserData(p).getNation();
                String targetNation = getUserData(target).nation;
                if (nation != null && targetNation != null && !p.getWorld().getName().contains("사흉수") && !p.getWorld().getName().contains("PVP")) {
                    if (nation.nationName.equalsIgnoreCase(targetNation) && !nation.setting.nationPVP) {
                        e.setCancelled(true);
                    }
                    if (nation.allies.contains(targetNation) && !nation.setting.alliancePVP) {
                        e.setCancelled(true);
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @EventHandler
    public void onSpellApplyDamage(SpellApplyDamageEvent e) {
        set.add(e.getCaster());
        new BukkitRunnable() {
            @Override
            public void run() {
                set.remove(e.getCaster());
            }
        }.runTaskLater(FourCore.instance, 25);
    }


    @EventHandler
    public void onSpellCast(SpellCastEvent e) {
        if (e == null || e.getSpell() == null || e.getCaster() == null) return;
        String spellName = e.getSpell().getInternalName();
        if (!spellName.equals("물대쉬P1") && !spellName.equals("D") && !spellName.equals("Apollon_man") && !spellName.equals("Zeus_passive_cool") && !spellName.equals("Z") && !spellName.equals("주작SRpp")) {
            //System.out.println("스펠 사용: "+spellName);
            String itemName = null;
            try {
                itemName = e.getCaster().getInventory().getItemInMainHand().getItemMeta().getDisplayName();
            } catch (Exception ignored) {
            }
            if (itemName == null) return;
            if (itemName.contains("요르문간드") && UserData.getUserData(e.getCaster()).defense < 80) {
                e.getCaster().sendMessage("스텟이 미충족합니다.");
                e.setCancelled(true);
            } else if (itemName.contains("리바이어던") && UserData.getUserData(e.getCaster()).strength < 80) {
                e.getCaster().sendMessage("스텟이 미충족합니다.");
                e.setCancelled(true);
            } else if (itemName.contains("파프니르") && UserData.getUserData(e.getCaster()).mental < 80) {
                e.getCaster().sendMessage("스텟이 미충족합니다.");
                e.setCancelled(true);
            } else if (itemName.contains("펜리르") && UserData.getUserData(e.getCaster()).force < 80) {
                e.getCaster().sendMessage("스텟이 미충족합니다.");
                e.setCancelled(true);
            }
        }
    }
}