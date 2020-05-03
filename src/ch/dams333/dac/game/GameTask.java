package ch.dams333.dac.game;

import ch.dams333.dac.Dac;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class GameTask extends BukkitRunnable{

    Dac main;
    int timer;
    Player p;
    int current;
    UUID uuid;

    public GameTask(Dac main, int timer) {
        this.main = main;
        this.timer = timer;
        this.p = null;
        this.current = timer;
        this.uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public void run() {
        if(p != null){
            p.setLevel(current);
            if(current <= 0){
                for(Game game : main.gameTasks.keySet()){
                    if(main.gameTasks.get(game).getUuid().equals(this.uuid)){
                        for(Player pl : game.getPlayers()){
                            pl.sendMessage(ChatColor.RED + p.getName() + " n'a pas sauté dans les temps");
                        }
                        game.damage(p);
                        return;
                    }
                }
            }
            current = current - 1;
        }
    }

    public void setNewPlayer(Player p) {
        this.p = p;
        this.current = timer;
    }
}
