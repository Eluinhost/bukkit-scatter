package com.publicuhc.scatter.logic;

import com.publicuhc.scatter.exceptions.ScatterLocationException;
import com.publicuhc.scatter.zones.DeadZone;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PresetSpawnsScatterLogic extends ScatterLogic {

    private final List<Location> m_spawns;

    public PresetSpawnsScatterLogic(Random random, List<Location> spawns) {
        super(random);
        m_spawns = spawns;
    }

    @Override
    public Location getScatterLocation(List<DeadZone> deadZones) throws ScatterLocationException {
        List<Location> spawns = new ArrayList<Location>(m_spawns);

        while(spawns.size() > 0) {
            int randomIndex = getRandom().nextInt(spawns.size());

            Location spawnLocation = spawns.get(randomIndex);

            if(isLocationWithinDeadZones(spawnLocation, deadZones)) {
                spawns.remove(spawnLocation);
                continue;
            }

            return  spawnLocation;
        }

        //all the spawn points are in deadzones or no locations set
        throw new ScatterLocationException();
    }

    @Override
    public String getID() {
        return "Preset Spawns";
    }

    @Override
    public String getDescription() {
        return "Spawn on predefined spawn points";
    }
}
