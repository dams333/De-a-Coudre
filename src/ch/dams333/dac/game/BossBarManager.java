package ch.dams333.dac.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BossBarManager {

    public BossBar createBossBar(String name, BarColor color, Player p){

        BossBar bb = Bukkit.createBossBar(name, color, BarStyle.SOLID);
        bb.setVisible(true);
        bb.addPlayer(p);
        return bb;

    }

    public BossBar updateBossBar(BossBar bb, Double life, Double maxLife) {

        bb.setTitle(ChatColor.RED + "Vies: " + Math.round(life));
        bb.setProgress(life/maxLife);

        return bb;
    }

}
