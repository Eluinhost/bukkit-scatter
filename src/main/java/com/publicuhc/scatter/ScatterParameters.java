package com.publicuhc.scatter;

import org.bukkit.Material;

import java.util.List;

public class ScatterParameters {

    private Double m_radius = null;
    private Double m_playerRadius = null;

    /**
     * @return the radius for the scatter
     * @throws java.lang.IllegalStateException if radius wasnt set, check if set with isRadiusSet
     */
    public double getRadius() {
        if(m_radius == null) {
            throw new IllegalStateException();
        }
        return m_radius;
    }

    /**
     * @return whether the radius has been set or not
     */
    public boolean isRadiusSet() {
        return m_radius != null;
    }

    /**
     * @param radius the radius to set the scatter for
     * @return this, for chaining
     */
    public ScatterParameters setRadius(double radius) {
        m_radius = radius;
        return this;
    }

    /**
     * @return the player radius for the scatter
     * @throws java.lang.IllegalStateException if player radius wasnt set, check if set with isPlayerRadiusSet
     */
    public double getPlayerRadius() {
        if(m_playerRadius == null) {
            throw new IllegalStateException();
        }
        return m_playerRadius;
    }

    /**
     * @return whether the player radius has been set or not
     */
    public boolean isPlayerRadiusSet() {
        return m_playerRadius != null;
    }

    /**
     * @param radius the player radius to set the scatter for
     * @return this, for chaining
     */
    public ScatterParameters setPlayerRadius(double radius) {
        m_playerRadius = radius;
        return this;
    }


}
