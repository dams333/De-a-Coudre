package ch.dams333.dac.sign;

import ch.dams333.dac.Dac;
import ch.dams333.dac.deACoudre.DeACoudre;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameSignManager {

    private Dac main;
    public GameSignManager(Dac dac) {
        this.main = dac;
    }

    public void serialize(){

        main.getConfig().createSection("GameSign");

        for(GameSign gameSign : main.gameSigns){

            ConfigurationSection sec = main.getConfig().getConfigurationSection("GameSign").createSection(UUID.randomUUID().toString());
            ConfigurationSection loc = sec.createSection("Location");

            loc.set("World", gameSign.getLoc().getWorld().getName());
            loc.set("X", gameSign.getLoc().getX());
            loc.set("Y", gameSign.getLoc().getY());
            loc.set("Z", gameSign.getLoc().getZ());

            sec.set("Dac", gameSign.getDac().getName());

        }

    }

    public void deserialize(){

        for(String uuid : main.getConfig().getConfigurationSection("GameSign").getKeys(false)){

            ConfigurationSection sec = main.getConfig().getConfigurationSection("GameSign").getConfigurationSection(uuid);
            ConfigurationSection loc = sec.getConfigurationSection("Location");

            World world = Bukkit.getWorld(loc.getString("World"));
            Double x = loc.getDouble("X");
            Double y = loc.getDouble("Y");
            Double z = loc.getDouble("Z");
            Location location = new Location(world, x, y, z);

            DeACoudre dac = main.dacsManager.getDac(sec.getString("Dac"));

            GameSign gameSign = new GameSign(dac, location);
            main.gameSigns.add(gameSign);

        }

    }

    public boolean isGameSignForLoc(Location loc){

        for(GameSign gameSign : main.gameSigns){
            if(gameSign.getLoc().getX() == loc.getX() && gameSign.getLoc().getY() == loc.getY() && gameSign.getLoc().getZ() == loc.getZ() && gameSign.getLoc().getWorld() == loc.getWorld()){
                return true;
            }
        }
        return false;
    }

    public boolean isGameSignForDac(DeACoudre dac){

        for(GameSign gameSign : main.gameSigns){
            if(gameSign.getDac().getName().equals(dac.getName())){
                return true;
            }
        }
        return false;
    }

    public List<GameSign> getSignFromLoc(Location loc){
        List<GameSign> gameSigns = new ArrayList<>();
        for(GameSign gameSign : main.gameSigns){
            if(gameSign.getLoc().getX() == loc.getX() && gameSign.getLoc().getY() == loc.getY() && gameSign.getLoc().getZ() == loc.getZ() && gameSign.getLoc().getWorld() == loc.getWorld()){
                gameSigns.add(gameSign);
            }
        }
        return gameSigns;
    }

    public List<GameSign> getSignFromDac(DeACoudre dac){
        List<GameSign> gameSigns = new ArrayList<>();
        for(GameSign gameSign : main.gameSigns){
            if(gameSign.getDac().getName().equals(dac.getName())){
                gameSigns.add(gameSign);
            }
        }
        return gameSigns;
    }

    public void resetGameSign(List<GameSign> gameSigns){

        for(GameSign gameSign : gameSigns) {
            Sign sign = (Sign) gameSign.getLoc().getBlock().getState();

            sign.setLine(0, ChatColor.AQUA + "Dé à coudre:");
            sign.setLine(1, ChatColor.AQUA + gameSign.getDac().getName());
            sign.setLine(2, ChatColor.GREEN + "0/" + gameSign.getDac().getMaxP());
            sign.setLine(3, ChatColor.BLUE + "Rejoindre");

            sign.update(true);
        }

    }

    public void resetGameSign(GameSign gameSign){

            Sign sign = (Sign) gameSign.getLoc().getBlock().getState();

            sign.setLine(0, ChatColor.AQUA + "Dé à coudre:");
            sign.setLine(1, ChatColor.AQUA + gameSign.getDac().getName());
            sign.setLine(2, ChatColor.GREEN + "0/" + gameSign.getDac().getMaxP());
            sign.setLine(3, ChatColor.BLUE + "Rejoindre");

            sign.update(true);


    }

    public void updateGameSigns(DeACoudre dac){
        for(GameSign gameSign : main.gameSigns){
            if(gameSign.getDac().getName().equals(dac.getName())){
                updateGameSign(gameSign);
            }
        }
    }

    public void updateGameSign(GameSign gameSign){

        DeACoudre dac = gameSign.getDac();
        Sign sign = (Sign)gameSign.getLoc().getBlock().getState();
        if (main.dacsManager.getInPlayers(dac).size() < dac.getMaxP()) {
            sign.setLine(2, ChatColor.GREEN + String.valueOf(main.dacsManager.getInPlayers(dac).size()) + "/" + dac.getMaxP());
            sign.update(true);
        } else if (main.dacsManager.getInPlayers(dac).size() == dac.getMaxP()) {
            sign.setLine(2, ChatColor.RED + "COMPLET");
            sign.update(true);
        }
    }

    public void resetGameSigns(){
        for(GameSign gameSign : main.gameSigns){
            resetGameSign(gameSign);
        }
    }

}
