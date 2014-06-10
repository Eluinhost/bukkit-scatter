package com.publicuhc.scatter.example;

import com.publicuhc.scatter.DefaultScatterer;
import com.publicuhc.scatter.Scatterer;
import com.publicuhc.scatter.exceptions.ScatterLocationException;
import com.publicuhc.scatter.logic.RandomCircleScatterLogic;
import com.publicuhc.scatter.logic.ScatterLogic;
import com.publicuhc.scatter.zones.CircularDeadZoneBuilder;
import com.publicuhc.scatter.zones.DeadZone;
import com.publicuhc.scatter.zones.SquareDeadZoneBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ExampleFetch {

    public void exampleFetch() {

        ScatterLogic logic = new RandomCircleScatterLogic(
                new Random(),
                new Location(Bukkit.getWorld("world"), 0, 0, 0),
                100,
                800,
                Material.GRASS, Material.ACACIA_STAIRS, Material.SAND
        );

        List<Player> players = Arrays.asList(Bukkit.getOnlinePlayers());

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
