package ch.dams333.dac.events;

import ch.dams333.dac.Dac;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvent implements Listener {
    Dac main;
    public MoveEvent(Dac dac) {
        this.main = dac;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){

        Player p = e.getPlayer();
        Location loc = p.getLocation();

        loc.add(0, 0.2, 0);

        if(loc.getBlock().getType() == Material.WATER){
            if(main.gameManager.playerIsInGame(p) && main.gameManager.isSauting(p)){
                main.gameManager.playerHasJump(p, loc);
            }
        }

    }

}
