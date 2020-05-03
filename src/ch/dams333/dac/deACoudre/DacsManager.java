package ch.dams333.dac.deACoudre;

import ch.dams333.dac.Dac;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;

public class DacsManager {
    Dac main;
    public DacsManager(Dac dac) {
        this.main = dac;
    }

    public boolean isDac(String name){
        for(DeACoudre dac : main.dacs){
            if(dac.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    public void removeDac(String name){
        for(DeACoudre dac : main.dacs){
            if(dac.getName().equalsIgnoreCase(name)){
                main.dacs.remove(dac);
                return;
            }
        }
    }

    public DeACoudre getDac(String name){
        for(DeACoudre dac : main.dacs){
            if(dac.getName().equalsIgnoreCase(name)){
                return dac;
            }
        }
        return null;
    }

    public void modifyDac(DeACoudre dac){
        for(DeACoudre dacMain : main.dacs){
            if(dac.getName().equalsIgnoreCase(dacMain.getName())){
                main.dacs.remove(dacMain);
                main.dacs.add(dac);
                return;
            }
        }
    }

    public boolean isInDac(Player p){

        for (DeACoudre dac : main.inDac.keySet()){
            for(Player pl : main.inDac.get(dac)){
                if(pl == p){
                    return true;
                }
            }
        }
        return false;
    }

    public DeACoudre getInDac(Player p){

        for(DeACoudre dac : main.inDac.keySet()){
            if(main.inDac.get(dac).contains(p)){
                return dac;
            }
        }
        return null;
    }

    public List<Player> getInPlayers(DeACoudre dac){
        for(DeACoudre deACoudre : main.dacs){
            if(dac.getName().equalsIgnoreCase(deACoudre.getName())){
                return main.inDac.get(dac);
            }
        }
        return null;
    }

    public void serialize(){

        main.getConfig().createSection("Dacs");
        for(DeACoudre dac : main.dacs){
            if(dac.getSpawn() != null && dac.getSpec() != null && dac.getBlock() != null) {
                ConfigurationSection sec = main.getConfig().getConfigurationSection("Dacs").createSection(dac.getName());
                ConfigurationSection locSpawn = sec.createSection("Spawn");
                locSpawn.set("World", dac.getSpawn().getWorld().getName());
                locSpawn.set("X", dac.getSpawn().getX());
                locSpawn.set("Y", dac.getSpawn().getY());
                locSpawn.set("Z", dac.getSpawn().getZ());
                locSpawn.set("Yaw", dac.getSpawn().getYaw());
                locSpawn.set("Pitch", dac.getSpawn().getPitch());
                ConfigurationSection locSpec = sec.createSection("Spectator");
                locSpec.set("World", dac.getSpec().getWorld().getName());
                locSpec.set("X", dac.getSpec().getX());
                locSpec.set("Y", dac.getSpec().getY());
                locSpec.set("Z", dac.getSpec().getZ());
                locSpec.set("Yaw", dac.getSpec().getYaw());
                locSpec.set("Pitch", dac.getSpec().getPitch());
                sec.set("Material", dac.getBlock().name());
                sec.set("Dac", dac.getDac());
                ConfigurationSection pl = sec.createSection("Players");
                pl.set("Min", dac.getMinP());
                pl.set("Max", dac.getMaxP());
                sec.set("Life", dac.getLife());
                sec.set("Time", dac.getTime());
                sec.set("TimeToStart", dac.getTimeStart());
            }
        }

    }

    public void deserialize(){

        for(String name : main.getConfig().getConfigurationSection("Dacs").getKeys(false)){

            ConfigurationSection sec = main.getConfig().getConfigurationSection("Dacs").getConfigurationSection(name);

            World worldS = Bukkit.getWorld(sec.getConfigurationSection("Spawn").getString("World"));
            Double xS = sec.getConfigurationSection("Spawn").getDouble("X");
            Double yS = sec.getConfigurationSection("Spawn").getDouble("Y");
            Double zS = sec.getConfigurationSection("Spawn").getDouble("Z");
            Float yawS = Float.valueOf(sec.getConfigurationSection("Spawn").getString("Yaw"));
            Float pitchS = Float.valueOf(sec.getConfigurationSection("Spawn").getString("Pitch"));
            Location spawn = new Location(worldS, xS, yS, zS, yawS, pitchS);

            World worldSp = Bukkit.getWorld(sec.getConfigurationSection("Spectator").getString("World"));
            Double xSp = sec.getConfigurationSection("Spectator").getDouble("X");
            Double ySp = sec.getConfigurationSection("Spectator").getDouble("Y");
            Double zSp = sec.getConfigurationSection("Spectator").getDouble("Z");
            Float yawSp = Float.valueOf(sec.getConfigurationSection("Spectator").getString("Yaw"));
            Float pitchSp = Float.valueOf(sec.getConfigurationSection("Spectator").getString("Pitch"));
            Location spectator = new Location(worldSp, xSp, ySp, zSp, yawSp, pitchSp);

            Material block = Material.valueOf(sec.getString("Material"));

            boolean dac = sec.getBoolean("Dac");

            int minP = sec.getConfigurationSection("Players").getInt("Min");
            int maxP = sec.getConfigurationSection("Players").getInt("Max");

            int life = sec.getInt("Life");
            int time = sec.getInt("Time");
            int timeStart = sec.getInt("TimeToStart");

            DeACoudre dacObject = new DeACoudre(spectator, spawn, name, block, dac, minP, maxP, life, time, timeStart);
            main.dacs.add(dacObject);

        }

    }
}
