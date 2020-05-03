package ch.dams333.dac.events;

import ch.dams333.dac.Dac;
import ch.dams333.dac.sign.GameSign;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignWritEvent implements Listener {
    Dac main;
    public SignWritEvent(Dac dac) {
        this.main = dac;
    }

    @EventHandler
    public void signChangeEvent(SignChangeEvent e) {

        Player p = e.getPlayer();
        String[] lines = e.getLines();

        if(p.hasPermission("dac.createDacSign")) {
            if (lines[0].equalsIgnoreCase("[DAC]") && lines[2].equalsIgnoreCase("[join game]") && !lines[1].equalsIgnoreCase("")) {

                String dacName = lines[1];
                if (main.dacsManager.isDac(dacName)) {

                    e.setLine(0, ChatColor.AQUA + "Dé à coudre:");
                    e.setLine(1, ChatColor.AQUA + main.dacsManager.getDac(dacName).getName());
                    e.setLine(2, ChatColor.GREEN + "0/" + main.dacsManager.getDac(dacName).getMaxP());
                    e.setLine(3, ChatColor.BLUE + "Rejoindre");

                    GameSign gameSign = new GameSign(main.dacsManager.getDac(dacName), e.getBlock().getLocation());
                    main.gameSigns.add(gameSign);

                }

            }
        }

    }

}
