package ch.dams333.dac.events;

import ch.dams333.dac.Dac;
import ch.dams333.dac.deACoudre.DeACoudre;
import ch.dams333.dac.deACoudre.SavedInventory;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.List;

public class InteractEvent implements Listener {
    Dac main;
    public InteractEvent(Dac dac) {
        this.main = dac;
    }

    @EventHandler
    public void interact(PlayerInteractEvent e){

        Player p = e.getPlayer();
        Block block = e.getClickedBlock();
        Action action = e.getAction();

        if(e.getHand() == EquipmentSlot.HAND && block != null && action == Action.RIGHT_CLICK_BLOCK){
            if(block.getType() == Material.OAK_SIGN || block.getType() == Material.OAK_WALL_SIGN){
                Sign sign = (Sign) block.getState();
                if(sign.getLine(0).equals(ChatColor.AQUA + "Dé à coudre:")){
                    DeACoudre dac = main.dacsManager.getDac(sign.getLine(1).replaceAll("§b",""));
                    if(!sign.getLine(2).equals(ChatColor.RED + "COMPLET") && !sign.getLine(2).equals(ChatColor.RED + "En jeu")){
                        boolean valid = false;
                        if(main.inDac.keySet().contains(dac)){
                            if(!main.dacsManager.getInPlayers(dac).contains(p)) {
                                List<Player> pl = main.inDac.get(dac);
                                pl.add(p);
                                main.inDac.put(dac, pl);
                                valid = true;
                            }
                        }else{
                            List<Player> pl = new ArrayList<>();
                            pl.add(p);
                            main.inDac.put(dac, pl);
                            valid = true;
                        }
                        if(valid) {
                            main.inventorys.put(p, new SavedInventory(p.getInventory(), p.getHealth(), p.getFoodLevel(), p.getGameMode(), p.getLocation()));
                            p.getInventory().clear();
                            p.setLevel(0);
                            p.setExp(0);
                            p.setHealth(20);
                            p.setFoodLevel(20);
                            p.teleport(dac.getSpec());
                            p.setGameMode(GameMode.ADVENTURE);
                            main.gameManager.playerJoin(dac);
                            main.gameSignManager.updateGameSigns(dac);
                        }
                    }
                }
            }
        }

    }

}
