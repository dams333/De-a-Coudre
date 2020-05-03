package ch.dams333.dac.events;

import ch.dams333.dac.Dac;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {
    Dac main;
    public QuitEvent(Dac dac) {
        this.main = dac;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if(main.gameManager.playerIsInGame(p)){

        }
    }
}
