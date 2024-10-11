package Air.FourCore.user;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public class BagData {
    private static HashMap<UUID, Inventory> inventoryMap = new HashMap<>();
    private static HashMap<UUID, Inventory> lockerMap = new HashMap<>();
    private static HashMap<UUID, Inventory> runeMap = new HashMap<>();

    public final static int bagSize = 54;

    public static Inventory getInventory(UUID uuid) {
        Inventory inventory;
        if (inventoryMap.containsKey(uuid)) {
            return inventoryMap.get(uuid);
        } else {
            inventory = Bukkit.createInventory(null, bagSize, ChatColor.BOLD + "가방");
            inventoryMap.put(uuid, inventory);
            return inventory;
        }
    }

    public static Inventory getLocker(UUID uuid) {
        Inventory inventory;
        if (lockerMap.containsKey(uuid)) {
            return lockerMap.get(uuid);
        } else {
            inventory = Bukkit.createInventory(null, 9, ChatColor.BOLD + "보관함");
            lockerMap.put(uuid, inventory);
            return inventory;
        }
    }

    public static Inventory getRune(UUID uuid) {
        Inventory inventory;
        if (runeMap.containsKey(uuid)) {
            return runeMap.get(uuid);
        } else {
            inventory = Bukkit.createInventory(null, 9, ChatColor.BOLD + "착용한룬");
            runeMap.put(uuid, inventory);
            return inventory;
        }
    }
}