package ch.dams333.dac.game;

import ch.dams333.dac.Dac;
import ch.dams333.dac.deACoudre.DeACoudre;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class GameManager {
    Dac main;
    public GameManager(Dac dac) {
        this.main = dac;
    }

    public void playerJoin(DeACoudre dac){
        for(DeACoudre deACoudre : main.inDac.keySet()){
            if(deACoudre.getName().equals(dac.getName())){
                if(main.inDac.get(deACoudre).size() >= dac.getMinP()){
                    for(StartingGameTask startingGameTasks : main.startingGameTasks){
                        if(startingGameTasks.getDac().getName().equals(dac.getName())){
                            return;
                        }
                    }
                    StartingGameTask startingGameTask = new StartingGameTask(main, dac, dac.getTimeStart());
                    startingGameTask.runTaskTimer(main, 20, 20);
                    main.startingGameTasks.add(startingGameTask);
                }
            }
        }

    }

    public boolean playerIsInGame(Player p){
        for(Game game : main.games){
            if(game.getPlayers().contains(p)){
                return true;
            }
        }
        return false;
    }

    public Game getGame(Player p){
        if(playerIsInGame(p)){
            for(Game game : main.games){
                if(game.getPlayers().contains(p)){
                    return game;
                }
            }
        }
        return null;
    }

    public void playerHasJump(Player p, Location loc){
        if(getGame(p).getCurrentPlayer() == p) {
            getGame(p).hasJump(p, loc);
        }
    }

    public void playerDamage(Player p) {
        if(getGame(p).getCurrentPlayer() == p) {
            getGame(p).damage(p);
        }
    }

    public void quitPlayer(Player p){
        if(getGame(p).getCurrentPlayer() == p) {
            getGame(p).quited(p);
            if(main.inventorys.keySet().contains(p)){
                main.inventorys.get(p).apply(p);
                main.inventorys.remove(p);
            }
        }
    }

    public void resetGame(Game game) {

        for(Player p : main.inventorys.keySet()){
            if(game.getPlayers().contains(p)){
                main.inventorys.get(p).apply(p);
                main.inventorys.remove(p);
            }
        }

        main.gameSignManager.resetGameSign(main.gameSignManager.getSignFromDac(game.getDac()));

        for(Player p : main.bossBars.keySet()){
            if(game.getPlayers().contains(p)){
                BossBar bb = main.bossBars.get(p);
                bb.removeAll();
            }
        }

        for(Location block : game.getReplaced()){
            block.getBlock().setType(Material.WATER);
        }

        for(Game mainGame : main.gameTasks.keySet()){
            if(mainGame.getDac().getName().equalsIgnoreCase(game.getDac().getName())){
                main.gameTasks.remove(mainGame);
                break;
            }
        }

        for(Game mainGame : main.games){
            if(mainGame.getDac().getName().equalsIgnoreCase(game.getDac().getName())){
                main.games.remove(mainGame);
                break;
            }
        }

    }
}
