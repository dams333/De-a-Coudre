package ch.dams333.dac.game;

import ch.dams333.dac.Dac;
import ch.dams333.dac.deACoudre.DeACoudre;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.*;

public class Game {

    private Dac main;
    private UUID uuid;
    private List<Player> message = new ArrayList<>();
    private List<Player> players;
    private Map<Player, Integer> life;
    private List<Player> toJump = new ArrayList<>();
    private DeACoudre dac;
    private Player currentPlayer;
    private List<Location> replaced;

    Player sauting;

    private Map<Player, Integer> maxLife = new HashMap<>();

    public Game(Dac main, List<Player> players, DeACoudre dac) {
        this.main = main;
        this.players = players;
        toJump.addAll(this.players);
        message.addAll(this.players);
        this.dac = dac;
        this.life = new HashMap<>();
        this.uuid = UUID.randomUUID();
        this.replaced = new ArrayList<>();
        for(Player p : players){
            life.put(p, dac.getLife());
            maxLife.put(p, dac.getLife());
            BossBar bb = main.bossBarManager.createBossBar(ChatColor.RED + "Vies: " + life.get(p), BarColor.RED, p);
            main.bossBars.put(p, bb);
        }
    }

    public DeACoudre getDac() {
        return dac;
    }

    public List<Player> getPlayers() {

        return message;
    }

    public Player getCurrentPlayer() {

        return currentPlayer;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void upPlayer() {

        if(players.size() <= 1){

            for(Player p : message){
                p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + players.get(0).getName() + " remporte la partie !!!");
            }

            main.gameManager.resetGame(this);
            return;
        }

        Player p = toJump.get(0);
        currentPlayer = p;

        toJump.remove(p);
        p.teleport(dac.getSpawn());

        for(Game game : main.gameTasks.keySet()){
            if(game.getUuid().equals(this.getUuid())){
                main.gameTasks.get(game).setNewPlayer(p);
            }
        }

        for(Player pl : message){
            pl.sendMessage(ChatColor.AQUA + p.getName() + " se prépare à sauter");
        }

    }

    public List<Location> getReplaced() {
        return replaced;
    }

    public void hasJump(Player p, Location loc) {


        for(Player pl : message){
            pl.sendMessage(ChatColor.GREEN + "Saut réussi pour " + p.getName());
        }

        if(dac.getDac()){

            int goodBlock = 0;

            loc.add(1, 0, 0);
            if(loc.getBlock().getType() != Material.WATER){
                goodBlock = goodBlock + 1;
            }
            loc.add(-1, 0, 1);
            if(loc.getBlock().getType() != Material.WATER){
                goodBlock = goodBlock + 1;
            }
            loc.add(-1, 0, -1);
            if(loc.getBlock().getType() != Material.WATER){
                goodBlock = goodBlock + 1;
            }
            loc.add(1, 0, -1);
            if(loc.getBlock().getType() != Material.WATER){
                goodBlock = goodBlock + 1;
            }
            loc.add(0, 0, 1);

            if(goodBlock >= 4){
                for(Player player : message){
                    player.sendMessage(ChatColor.DARK_GREEN + p.getName() + " réussi un dé à coudre !!!");
                }

                life.put(p, (life.get(p) + 1));
                maxLife.put(p, (maxLife.get(p) + 1));

                BossBar bb = main.bossBars.get(p);
                main.bossBars.put(p, main.bossBarManager.updateBossBar(bb, Double.parseDouble(String.valueOf(life.get(p))), Double.parseDouble(String.valueOf(maxLife.get(p)))));

            }

        }

        if (toJump.size() <= 0){
            toJump.addAll(this.players);
        }

        upPlayer();
        p.teleport(dac.getSpec());

        replaced.add(loc);
        loc.getBlock().setType(dac.getBlock());

        p.setLevel(0);

    }

    public void damage(Player p) {

        life.put(p, (life.get(p) - 1));

        BossBar bb = main.bossBars.get(p);
        main.bossBars.put(p, main.bossBarManager.updateBossBar(bb, Double.parseDouble(String.valueOf(life.get(p))), Double.parseDouble(String.valueOf(maxLife.get(p)))));

        for(Player pl : message){
            pl.sendMessage(ChatColor.RED + p.getName() + " perd une vie");
        }

        if(life.get(p) <= 0){

            for(Player pl : message){
                pl.sendMessage(ChatColor.RED + p.getName() + " est mort définitevement !");
            }

            players.remove(p);

        }

        if (toJump.size() <= 0){
            toJump.addAll(this.players);
        }

        upPlayer();
        if(p != currentPlayer) {
            p.teleport(dac.getSpec());
        }

        p.setLevel(0);

    }

    public void quited(Player p) {
        life.put(p, 0);
        for(Player pl : message){
            pl.sendMessage(ChatColor.RED + p.getName() + " est mort définitivement car il.elle a quitté le serveur !");
        }

        players.remove(p);


        if (toJump.size() <= 0){
            toJump.addAll(this.players);
        }

        upPlayer();
    }

    public boolean isSauting(Player p) {
        return currentPlayer.getUniqueId().equals(p.getUniqueId());
    }
}
