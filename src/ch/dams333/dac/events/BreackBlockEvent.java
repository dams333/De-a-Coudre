package ch.dams333.dac.events;

import ch.dams333.dac.Dac;
import ch.dams333.dac.sign.GameSign;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreackBlockEvent implements Listener {
    Dac main;
    public BreackBlockEvent(Dac dac) {
        this.main = dac;
    }

    @EventHandler
    public void onBreack(BlockBreakEvent e){

        Block block = e.getBlock();
        if(main.gameSignManager.isGameSignForLoc(block.getLocation())){
            for(GameSign gameSign : main.gameSigns){
                for(GameSign gs : main.gameSignManager.getSignFromLoc(block.getLocation())){
                    if(gs.getDac().getName().equalsIgnoreCase(gameSign.getDac().getName())){
                        main.gameSigns.remove(gameSign);
                    }
                }
            }
        }

    }

}
