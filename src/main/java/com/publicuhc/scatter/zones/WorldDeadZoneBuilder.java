package com.publicuhc.scatter.zones;

import org.bukkit.Location;
import org.bukkit.World;

public class WorldDeadZoneBuilder implements DeadZoneBuilder {

    private final World m_world;

    /**
     * Creates deadzones for an entire world
     * @param world the world to use
     */
    public WorldDeadZoneBuilder(World world) {
        m_world = world;
    }

    /**
     * @param location the location, ignored as we have set parameters
     * @return the built deadzone
     */
    @Override
    public DeadZone buildForLocation(Location location) {
        return new WorldDeadZone(m_world);
    }


    private class WorldDeadZone implements DeadZone {

        private final World m_world;

        /**
         * Create a deadzone for an entire world
         * @param world the world to make a dead zone
         */
        protected WorldDeadZone(World world) {
            m_world = world;
        }

        @Override
        public boolean isLocationAllowed(Location location) {
            return !location.getWorld().getName().equals(m_world.getName());
        }
    }
}
