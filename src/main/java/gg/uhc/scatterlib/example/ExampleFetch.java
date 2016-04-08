package gg.uhc.scatterlib.example;

import gg.uhc.scatterlib.DefaultScatterer;
import gg.uhc.scatterlib.Scatterer;
import gg.uhc.scatterlib.exceptions.ScatterLocationException;
import gg.uhc.scatterlib.logic.RandomCircleScatterLogic;
import gg.uhc.scatterlib.zones.CircularDeadZoneBuilder;
import gg.uhc.scatterlib.zones.DeadZone;
import gg.uhc.scatterlib.zones.SquareDeadZoneBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

public class ExampleFetch {

    public void exampleFetch() {

        RandomCircleScatterLogic logic = new RandomCircleScatterLogic(new Random());
        logic.setCentre(new Location(Bukkit.getWorld("world"), 0, 0, 0));
        logic.setMaxAttempts(100);
        logic.setRadius(100);
        logic.addMaterials(Material.GRASS, Material.ACACIA_STAIRS, Material.SAND);

        Collection<? extends Player> players = Bukkit.getOnlinePlayers();

        List<DeadZone> deadZones = new ArrayList<DeadZone>();

        SquareDeadZoneBuilder builder = new SquareDeadZoneBuilder(100);

        DeadZone spawnZone = builder.buildForLocation(Bukkit.getWorld("world").getSpawnLocation());
        deadZones.add(spawnZone);

        CircularDeadZoneBuilder deadZoneForTeleports = new CircularDeadZoneBuilder(30);

        Scatterer scatterer = new DefaultScatterer(logic, deadZones, deadZoneForTeleports);

        try {
            List<Location> locations = scatterer.getScatterLocations(players.size());

            //shuffle locations + teleport players

        } catch (ScatterLocationException e) {
            //couldn't fetch all locations
        }
    }
}
