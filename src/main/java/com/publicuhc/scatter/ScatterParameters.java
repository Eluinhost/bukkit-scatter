package com.publicuhc.scatter;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScatterParameters {

    private Double m_radius = null;
    private Integer m_maxAttempts = null;
    private Location m_centre = null;
    private List<Material> m_materials = new ArrayList<Material>();

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

    /**
     * @param materials the materials to add
     * @return this, for method chaining
     */
    public ScatterParameters addMaterials(Material... materials) {
        m_materials.addAll(Arrays.asList(materials));
        return this;
    }

    /**
     * @return whether any materials have been set or not
     */
    public boolean hasMaterials() {
        return m_materials.size() != 0;
    }

    /**
     * @return all of the materials
     */
    public List<Material> getMaterials() {
        return m_materials;
    }
}
