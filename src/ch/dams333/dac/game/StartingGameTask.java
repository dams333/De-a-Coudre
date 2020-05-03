package ch.dams333.dac.game;

import ch.dams333.dac.Dac;
import ch.dams333.dac.deACoudre.DeACoudre;
import ch.dams333.dac.sign.GameSign;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class StartingGameTask extends BukkitRunnable {
    private Dac main;
    private DeACoudre dac;

    public DeACoudre getDac() {
        return dac;
    }

    public StartingGameTask(Dac dac, DeACoudre deACoudre, int timeStart) {
        this.main = dac;
        this.dac = deACoudre;
        this.timer = timeStart;
    }
    private int timer;

    @Override
    public void run() {
        timer = timer - 1;
        for(Player p : main.inDac.get(dac)){
            p.setLevel(timer);
        }
        if(timer == 0) {
            for(StartingGameTask mainTask : main.startingGameTasks){
                if(mainTask.getDac().getName().equals(dac.getName())){
                    main.startingGameTasks.remove(mainTask);
                    break;
                }
            }
            for (GameSign gameSign : main.gameSignManager.getSignFromDac(dac)) {
                Sign sign = (Sign) gameSign.getLoc().getBlock().getState();
                sign.setLine(2, ChatColor.RED + "En jeu");
                sign.update(true);
                for (DeACoudre deACoudre : main.inDac.keySet()) {
                    if (deACoudre.getName().equals(dac.getName())) {
                        Game game = new Game(main, main.inDac.get(deACoudre), dac);
                        main.inDac.remove(deACoudre);
                        main.games.add(game);
                        if (dac.getTime() > 0) {
                            GameTask gameTask = new GameTask(main, dac.getTime());
                            gameTask.runTaskTimer(main, 20, 20);
                            main.gameTasks.put(game, gameTask);
                        }
                        game.upPlayer();
                        break;
                    }
                }
                cancel();
            }
        }
    }
}
