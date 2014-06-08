package com.publicuhc.scatter.zones;

import org.bukkit.Location;
import org.bukkit.World;

public class WorldDeadZone implements DeadZone {

    private final World m_world;

    /**
     * Create a deadzone for an entire world
     * @param world the world to make a dead zone
     */
    public WorldDeadZone(World world) {
        m_world = world;
    }

    @Override
    public boolean isLocationAllowed(Location location) {
        return !location.getWorld().equals(m_world);
    }
}
