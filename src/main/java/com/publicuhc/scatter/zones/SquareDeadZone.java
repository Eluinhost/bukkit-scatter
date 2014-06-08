package com.publicuhc.scatter.zones;

import org.bukkit.Location;

public class SquareDeadZone implements DeadZone {

    private final Location m_centre;
    private final double m_minX;
    private final double m_maxX;
    private final double m_minZ;
    private final double m_maxZ;

    /**
     * Creates a dead zone based on a centre location and a radius around it
     * @param centre the centre of the square
     * @param sideLength the length of 1 side of the square
     */
    public SquareDeadZone(Location centre, double sideLength) {
        m_centre = centre;
        double radius = sideLength / 2.0D;
        m_minX = m_centre.getX() - radius;
        m_minZ = m_centre.getZ() - radius;
        m_maxX = m_centre.getX() + radius;
        m_maxZ = m_centre.getZ() + radius;
    }

    @Override
    public boolean isLocationAllowed(Location location) {
        return !location.getWorld().equals(m_centre.getWorld())
                || location.getX() > m_maxX
                || location.getX() < m_minX
                || location.getZ() > m_maxZ
                || location.getZ() < m_minZ;
    }
}
