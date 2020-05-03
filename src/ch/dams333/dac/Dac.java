package ch.dams333.dac;

import ch.dams333.dac.commands.DacCommand;
import ch.dams333.dac.deACoudre.DacsManager;
import ch.dams333.dac.deACoudre.DeACoudre;
import ch.dams333.dac.deACoudre.SavedInventory;
import ch.dams333.dac.events.*;
import ch.dams333.dac.game.*;
import ch.dams333.dac.sign.GameSign;
import ch.dams333.dac.sign.GameSignManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dac extends JavaPlugin {

    public List<DeACoudre> dacs = new ArrayList<>();
    public List<GameSign> gameSigns = new ArrayList<>();
    public List<StartingGameTask> startingGameTasks = new ArrayList<>();
    public List<Game> games = new ArrayList<>();

    public Map<DeACoudre, List<Player>> inDac = new HashMap<>();
    public Map<Player, SavedInventory> inventorys = new HashMap<>();
    public Map<Game, GameTask> gameTasks = new HashMap<>();
    public Map<Player, BossBar> bossBars = new HashMap<>();

    public DacsManager dacsManager = new DacsManager(this);
    public GameSignManager gameSignManager = new GameSignManager(this);
    public GameManager gameManager = new GameManager(this);
    public BossBarManager bossBarManager = new BossBarManager();

    @Override
    public void onEnable(){
        getCommand("dac").setExecutor(new DacCommand(this));

        getServer().getPluginManager().registerEvents(new SignWritEvent(this), this);
        getServer().getPluginManager().registerEvents(new BreackBlockEvent(this), this);
        getServer().getPluginManager().registerEvents(new InteractEvent(this), this);
        getServer().getPluginManager().registerEvents(new MoveEvent(this), this);
        getServer().getPluginManager().registerEvents(new StatsEvent(this), this);
        getServer().getPluginManager().registerEvents(new QuitEvent(this), this);


        dacsManager.deserialize();
        gameSignManager.deserialize();

        gameSignManager.resetGameSigns();

    }

    @Override
    public void onDisable(){
        for(Player p : inventorys.keySet()){
            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Votre partie a été annulée car le serveur s'arrête");
            inventorys.get(p).apply(p);
        }

        for(Game game : games){
            for(Location loc : game.getReplaced()){
                loc.getBlock().setType(Material.WATER);
            }
        }

        for(Player p : bossBars.keySet()){
            BossBar bb = bossBars.get(p);
            bb.removeAll();
        }

        for(String key : getConfig().getKeys(false)){
            getConfig().set(key, null);
        }

        gameSignManager.serialize();
        dacsManager.serialize();
        saveConfig();
    }

}
