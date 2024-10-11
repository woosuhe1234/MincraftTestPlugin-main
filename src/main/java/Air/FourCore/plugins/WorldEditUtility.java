package Air.FourCore.plugins;


import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.io.File;
import java.io.IOException;

public class WorldEditUtility {

    public static void loadSchematic(Location loc, String url){
        WorldEditPlugin worldEditPlugin = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("worldedit");
        File schematic = new File(url);
        EditSession session = worldEditPlugin.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(loc.getWorld()), -1);
        //ClipboardFormat format = ClipboardFormat.findByFile(schematic);
        try {
            //ClipboardReader reader = format.getReader(new FileInputStream(schematic));
            CuboidClipboard clipboard = MCEditSchematicFormat.getFormat(schematic).load(schematic);
            clipboard.rotate2D(90);
            clipboard.paste(session, new Vector(loc.getX(), loc.getY(), loc.getZ()), false);
        } catch (DataException | WorldEditException | IOException e){
            e.printStackTrace();
        }
    }
    public static void setEmpty(Location loc, int x, int z){
        Vector min = new Vector(loc.getX()-x,loc.getY()-16, loc.getZ()-z);
        Vector max = new Vector(loc.getX()+x,256,loc.getZ()+z);
        for(int blockX = min.getBlockX();blockX <= max.getBlockX(); blockX++){
            for(int blockY = min.getBlockY();blockY <= max.getBlockY(); blockY++){
                for(int blockZ = min.getBlockZ();blockZ <= max.getBlockZ(); blockZ++){
                    Location tmpBlock = new Location(loc.getWorld(), blockX, blockY, blockZ);
                    if(blockY == loc.getY()-16){
                        tmpBlock.getBlock().setType(Material.GRASS);
                    }else{
                        tmpBlock.getBlock().setType(Material.AIR);
                    }
                }
            }
        }
    }
}
