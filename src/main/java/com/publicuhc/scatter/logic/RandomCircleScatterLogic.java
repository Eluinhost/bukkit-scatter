package com.publicuhc.scatter.logic;

import com.publicuhc.scatter.exceptions.NoSolidBlockException;
import com.publicuhc.scatter.exceptions.ScatterLocationException;
import com.publicuhc.scatter.zones.DeadZone;
import org.bukkit.Location;
import org.bukkit.Material;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomCircleScatterLogic extends ScatterLogic {

    public static final double MATH_TAU = Math.PI * 2;

    private int m_maxAttempts;
    private double m_radius;
    private Location m_centre;
    private List<Material>  m_materials = new ArrayList<Material>();

    public RandomCircleScatterLogic(Random random, Location centre, int maxAttempts, double radius, Material... allowedMaterials) {
        super(random);
        m_maxAttempts = maxAttempts;
        m_radius = radius;
        m_centre = centre;
        m_materials.addAll(Arrays.asList(allowedMaterials));
    }

    public int getMaxAttempts() {
        return m_maxAttempts;
    }

    public RandomCircleScatterLogic setMaxAttempts(int attempts) {
        m_maxAttempts = attempts;
        return this;
    }

    public double getRadius() {
        return m_radius;
    }

    public RandomCircleScatterLogic setRadius(double radius) {
        m_radius = radius;
        return this;
    }

    public Location getCentre() {
        return m_centre;
    }

    public RandomCircleScatterLogic setCentre(Location centre) {
        m_centre = centre;
        return this;
    }

    public List<Material> getMaterials() {
        return m_materials;
    }

    public RandomCircleScatterLogic setMaterials(List<Material> materials) {
        m_materials = materials;
        return this;
    }

    public RandomCircleScatterLogic addMaterials(Material... materials) {
        m_materials.addAll(Arrays.asList(materials));
        return this;
    }


    @Override
    public Location getScatterLocation(List<DeadZone> deadZones) throws ScatterLocationException {
        for (int i = 0; i < getMaxAttempts(); i++) {

            //get a random angle between 0 and TAU
            double randomAngle = getRandom().nextDouble() * MATH_TAU;

            //get a random radius for uniform circular distribution
            double radius = getRadius() * StrictMath.sqrt(getRandom().nextDouble());

            //Convert back to cartesian coords and the nearest .1
            BigDecimal xcoord = getXFromRadians(radius, randomAngle);
            BigDecimal zcoord = getZFromRadians(radius, randomAngle);

            //make a new location at world height at the coordinates
            Location scatterLocation = getCentre().clone();
            scatterLocation.setY(getCentre().getWorld().getMaxHeight());

            //add the offsets we generated
            scatterLocation.add(xcoord.doubleValue(), 0, zcoord.doubleValue());

            //set the the nearest centre of a block
            setToNearestCentre(scatterLocation);

            //get the highest block in the Y coordinate
            try {
                setToHighestNonAir(scatterLocation);
            } catch (NoSolidBlockException e) {
                continue;
            }

            //if there are any mats set check that the block we have is a valid one
            if(getMaterials().size() > 0) {
                Material mat = scatterLocation.getBlock().getType();

                if(!getMaterials().contains(mat)) {
                    continue;
                }
            }

            //is it a valid spawn location outside of deadzones?
            if(isLocationWithinDeadZones(scatterLocation, deadZones)) {
                continue;
            }

            //valid teleport, return
            return scatterLocation;
        }

        //no locations found
        throw new ScatterLocationException();
    }

    @Override
    public String getID() {
        return "Random Circle";
    }

    @Override
    public String getDescription() {
        return "Scatter randomly within a circle";
    }
}
