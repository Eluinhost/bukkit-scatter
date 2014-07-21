package com.publicuhc.scatter.logic;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class StandardScatterLogic extends ScatterLogic {

    protected int m_maxAttempts;
    protected double m_radius;
    protected Location m_centre;
    protected List<Material> m_materials = new ArrayList<Material>();

    public StandardScatterLogic(Random random, Location centre, int maxAttempts, double radius, Material... allowedMaterials)
    {
        super(random);
        m_maxAttempts = maxAttempts;
        m_radius = radius;
        m_centre = centre;
        m_materials.addAll(Arrays.asList(allowedMaterials));
    }

    public StandardScatterLogic(Random random)
    {
        super(random);
    }

    public int getMaxAttempts()
    {
        return m_maxAttempts;
    }

    public StandardScatterLogic setMaxAttempts(int attempts)
    {
        m_maxAttempts = attempts;
        return this;
    }

    public double getRadius()
    {
        return m_radius;
    }

    public StandardScatterLogic setRadius(double radius)
    {
        m_radius = radius;
        return this;
    }

    public Location getCentre() {
        return m_centre;
    }

    public StandardScatterLogic setCentre(Location centre)
    {
        m_centre = centre;
        return this;
    }

    public List<Material> getMaterials() {
        return m_materials;
    }

    public StandardScatterLogic setMaterials(List<Material> materials)
    {
        m_materials = materials;
        return this;
    }

    public StandardScatterLogic addMaterials(Material... materials)
    {
        m_materials.addAll(Arrays.asList(materials));
        return this;
    }
}
