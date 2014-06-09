package com.publicuhc.scatter;

import org.bukkit.Location;

public class ScatterParameters {

    private Double m_radius = null;
    private Double m_playerRadius = null;
    private Integer m_maxAttempts = null;
    private Location m_centre = null;

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

    /**
     * @return the max attempts per location
     * @throws java.lang.IllegalStateException if wasnt set, check if set with isMaxAttemptsSet
     */
    public int getMaxAttempts() {
        if(m_maxAttempts == null) {
            throw new IllegalStateException();
        }
        return m_maxAttempts;
    }

    /**
     *
     * @return whether the max attempts has been set or not
     */
    public boolean isMaxAttemptsSet() {
        return m_maxAttempts != null;
    }

    /**
     * @param attempts the max attempts per location
     * @return this, for method chaining
     */
    public ScatterParameters setMaxAttempts(int attempts) {
        m_maxAttempts = attempts;
        return this;
    }

    /**
     * @return the centre if set, null otherwise
     */
    public Location getCentre() {
        return m_centre;
    }

    /**
     * @param location the centre for the new scatter
     * @return this, for method chaining
     */
    public ScatterParameters setCentre(Location location) {
        m_centre = location;
        return this;
    }

    /**
     * @return whether the centre has been set or not
     */
    public boolean isCentreSet() {
        return m_centre != null;
    }
}
