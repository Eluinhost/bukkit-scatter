package com.publicuhc.scatter.zones;

import org.bukkit.Location;

public class CircularDeadZone implements DeadZone {

    private final Location m_centre;
    private final double m_radiusSquared;

    /**
     * Creates a dead zone based on a centre location and a radius around it
     * @param centre the centre of the circle
     * @param radius the radius of the circle
     */
    public CircularDeadZone(Location centre, double radius) {
        m_centre = centre;
        m_radiusSquared = radius * radius;
    }

    @Override
    public boolean isLocationAllowed(Location location) {
        return !location.getWorld().equals(m_centre.getWorld()) || location.distanceSquared(m_centre) > m_radiusSquared;
    }
}
