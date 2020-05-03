package ch.dams333.dac.sign;

import ch.dams333.dac.deACoudre.DeACoudre;
import org.bukkit.Location;

public class GameSign {

    private DeACoudre dac;
    private Location loc;

    public DeACoudre getDac() {
        return dac;
    }

    public Location getLoc() {
        return loc;
    }

    public GameSign(DeACoudre dac, Location loc) {

        this.dac = dac;
        this.loc = loc;
    }
}
