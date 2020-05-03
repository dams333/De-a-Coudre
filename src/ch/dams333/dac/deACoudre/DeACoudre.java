package ch.dams333.dac.deACoudre;


import org.bukkit.Location;
import org.bukkit.Material;

public class DeACoudre {

    private Location spec;
    private Location spawn;
    private String name;
    private Material block;

    public int getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(int timeStart) {
        this.timeStart = timeStart;
    }

    public DeACoudre(Location spec, Location spawn, String name, Material block, boolean dac, int minP, int maxP, int life, int time, int timeStart) {
        this.spec = spec;
        this.spawn = spawn;
        this.name = name;
        this.block = block;
        this.dac = dac;
        this.minP = minP;
        this.maxP = maxP;
        this.life = life;
        this.time = time;
        this.timeStart = timeStart;

    }

    private boolean dac;
    private int minP;
    private int maxP;
    private int life;
    private int time;
    private int timeStart;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setSpec(Location spec) {
        this.spec = spec;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public void setBlock(Material block) {
        this.block = block;
    }

    public void setDac(boolean dac) {
        this.dac = dac;
    }

    public void setMinP(int minP) {
        this.minP = minP;
    }

    public void setMaxP(int maxP) {
        this.maxP = maxP;
    }

    public Location getSpec() {

        return spec;
    }

    public Location getSpawn() {
        return spawn;
    }

    public String getName() {
        return name;
    }

    public Material getBlock() {
        return block;
    }

    public boolean getDac() {
        return dac;
    }

    public int getMinP() {
        return minP;
    }

    public int getMaxP() {
        return maxP;
    }

    public DeACoudre(String name) {

        this.name = name;
        this.life = 1;
        this.dac = true;
        this.maxP = 0;
        this.minP = 0;
        this.timeStart = 30;
    }

}
