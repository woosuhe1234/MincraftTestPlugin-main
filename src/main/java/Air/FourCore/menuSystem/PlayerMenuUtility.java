package Air.FourCore.menuSystem;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerMenuUtility {

    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();

    private Player owner;

    public  PlayerMenuUtility(Player owner){
        this.owner = owner;
    }

    public Player getOwner(){
        return owner;
    }

    public void setOwner(Player owner){
        this.owner = owner;
    }

    public static PlayerMenuUtility getPlayerMenuUtility(Player p){
        PlayerMenuUtility playerMenuUtility;
        if(playerMenuUtilityMap.containsKey(p)){
            return playerMenuUtilityMap.get(p);
        }else{
            playerMenuUtility = new PlayerMenuUtility(p);
            playerMenuUtilityMap.put(p, playerMenuUtility);

            return playerMenuUtility;
        }
    }

}
