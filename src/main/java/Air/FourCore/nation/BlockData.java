package Air.FourCore.nation;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockData {
    public BlockData(Block block){
        world = block.getWorld().getName();
        x = block.getX();
        y = block.getY();
        z = block.getZ();
    }
    public BlockData(String world, int x, int y, int z){
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public String world;
    public int x, y, z;
    public Block getBlock(){
        World w = Bukkit.getWorld(world);
        if(w == null)
            return null;
        return w.getBlockAt(x, y, z);
    }
}
