package ch.dams333.dac.events;

import ch.dams333.dac.Dac;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class StatsEvent implements Listener {
    Dac main;
    public StatsEvent(Dac dac) {
        this.main = dac;
    }

    @EventHandler
    public void damabeEntity(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            if(main.gameManager.playerIsInGame(p) || main.dacsManager.isInDac(p)){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void damage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            if(main.gameManager.playerIsInGame(p) || main.dacsManager.isInDac(p)){
                e.setCancelled(true);
            }
            if(main.gameManager.playerIsInGame(p)){
                main.gameManager.playerDamage(p);
            }
        }
    }

    @EventHandler
    public void food(FoodLevelChangeEvent e){
        Player p = (Player) e.getEntity();
        if(main.gameManager.playerIsInGame(p) || main.dacsManager.isInDac(p)){
            e.setCancelled(true);
        }
    }

}
