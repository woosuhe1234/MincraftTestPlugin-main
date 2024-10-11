package Air.FourCore.menuSystem;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import Air.FourCore.FourCore;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MenuUtility {

    private static final int COLOR = 7;

    public static String color(String str){
        return ChatColor.translateAlternateColorCodes('&',"&r"+str);
    }

    public static void fillBackground(Inventory inventory){
        fillBackground(inventory, COLOR);
    }

    public static void fillBackground(Inventory inventory, int color){
        ItemStack[] stacks = inventory.getContents();
        int bound = inventory.getContents().length;
        for (int i = 0; i < bound; i++) {
            if (stacks[i] == null || stacks[i].getType() == Material.AIR) {
                background(inventory, i, color);
            }
        }
    }

    public static void fillLineBackground(Inventory inventory, int index, boolean isVertical){
        fillLineBackground(inventory, index, COLOR, isVertical);
    }

    public static void fillLineBackground(Inventory inventory, int index, int color, boolean isVertical){
        ItemStack[] stacks = inventory.getContents();
        int bound = inventory.getContents().length;
        for (int i = 0; i < bound; i++) {
            if (stacks[i] == null || stacks[i].getType() == Material.AIR) {
                if(isVertical){
                    if(i % 9 == index){
                        background(inventory, i, color);
                    }
                }else{
                    if(i / 9 == index){
                        background(inventory, i, color);
                    }
                }
            }
        }
    }

    public static void background(Inventory inventory, int index) {
        background(inventory, index, COLOR);
    }

    public static void background(Inventory inventory, int index, int color) {
        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1);
        glass.setDurability((byte)color);
        ItemMeta glass_meta = glass.getItemMeta();
        glass_meta.setDisplayName(" ");
        glass.setItemMeta(glass_meta);
        inventory.setItem(index, glass);
    }

    public static void button(Inventory inventory, Material m, String s, int i) {
        button(inventory, m, s, i, null);
    }

    public static void button(Inventory inventory, Material m, String s, int i, List<String> l) {
        ItemStack button = new ItemStack(m, 1);
        ItemMeta button_meta = button.getItemMeta();
        if(m != Material.AIR)
            button_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',s));
        if(l != null)
            button_meta.setLore(l);
        button.setItemMeta(button_meta);
        inventory.setItem(i, button);
    }


    public static void button(Inventory inventory, ItemStack m, int i) {
        button(inventory, m, i, null);
    }

    public static void button(Inventory inventory, ItemStack m, int i, List<String> l) {
        ItemStack button = new ItemStack(m);
        ItemMeta button_meta = button.getItemMeta();
        if(l != null) {
            List<String> lore = button_meta.getLore(); //button_meta.setLore();
            if(lore != null) {
                lore.addAll(l);
                button_meta.setLore(lore);
            }else{
                button_meta.setLore(l);
            }
        }
        button.setItemMeta(button_meta);
        inventory.setItem(i, button);
    }

    public static void button(Inventory inventory, ItemStack m, String s, int i) {
        button(inventory, m, s, i, null);
    }

    public static void button(Inventory inventory, ItemStack m, String s, int i, List<String> l) {
        ItemStack button = new ItemStack(m);
        ItemMeta button_meta = button.getItemMeta();
        button_meta.setDisplayName(s);
        if(l != null) {
            List<String> lore = button_meta.getLore(); //button_meta.setLore();
            if(lore != null) {
                lore.addAll(l);
                button_meta.setLore(lore);
            }else{
                button_meta.setLore(l);
            }
        }
        button.setItemMeta(button_meta);
        inventory.setItem(i, button);
    }

    public static ItemStack getPlayerHead(){
        return new ItemStack(Material.SKULL_ITEM, 1 , (short) 3);
    }

    public static Location getLocation(String name){
        Location location = null;
        World world;

        FileConfiguration config = FourCore.instance.getConfig();
        Set<String> set = config.getConfigurationSection("Portals").getKeys(false);
        for (Iterator<String> keyIterator = set.iterator(); keyIterator.hasNext(); ) {
            String key = keyIterator.next();
            if(name.equalsIgnoreCase(key)){
                int x = config.getInt("Portals."+key+".x");
                int y = config.getInt("Portals."+key+".y");
                int z = config.getInt("Portals."+key+".z");
                String w = config.getString("Portals."+key+".world");
                world = Bukkit.getWorld(w);
                if(world == null) continue;
                location = new Location(world, x, y, z);
                break;
            }
        }

        return location;
    }
}
