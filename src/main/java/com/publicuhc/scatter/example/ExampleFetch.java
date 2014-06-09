package com.publicuhc.scatter.example;

import com.publicuhc.scatter.LocationMapping;
import com.publicuhc.scatter.PlayerScatterer;
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

        PlayerScatterer scatterer = new PlayerScatterer(logic, players, deadZones, deadZoneForTeleports);

        try {
            List<LocationMapping> mappings = scatterer.getScatterLocations();

            //mappings is UUID=>Location for all the provided players, just need to teleport them as needed
        } catch (ScatterLocationException e) {
            //couldn't fetch all locations
        }
    }
}
