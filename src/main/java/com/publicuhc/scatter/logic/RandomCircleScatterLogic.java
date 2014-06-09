package com.publicuhc.scatter.logic;

import com.publicuhc.scatter.ScatterParameters;
import com.publicuhc.scatter.exceptions.NoSolidBlockException;
import com.publicuhc.scatter.exceptions.ScatterConfigurationException;
import com.publicuhc.scatter.exceptions.ScatterLocationException;
import com.publicuhc.scatter.zones.DeadZone;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;
import java.util.Random;

public class RandomCircleScatterLogic extends ScatterLogic {

    public static final double MATH_TAU = Math.PI * 2;

    public RandomCircleScatterLogic(Random random) {
        super(random);
    }

    @Override
    public Location getScatterLocation(List<DeadZone> deadZones, ScatterParameters parameters) throws ScatterLocationException, ScatterConfigurationException {
        if(!parameters.isMaxAttemptsSet() || !parameters.isRadiusSet() || !parameters.isCentreSet()) {
            throw new ScatterConfigurationException();
        }
        Location centre = parameters.getCentre();
        int maxTries = parameters.getMaxAttempts();
        double scatterRadius = parameters.getRadius();

        for (int i = 0; i < maxTries; i++) {

            //get a random angle between 0 and TAU
            double randomAngle = getRandom().nextDouble() * MATH_TAU;

            //get a random radius for uniform circular distribution
            double radius = scatterRadius * Math.sqrt(getRandom().nextDouble());

            //Convert back to cartesian coords
            double xcoord = getXFromRadians(radius, randomAngle) + centre.getBlockX();
            double zcoord = getZFromRadians(radius, randomAngle) + centre.getBlockZ();

            //make a new location at world height at the coordinates
            Location scatterLocation = centre.clone();
            scatterLocation.setY(centre.getWorld().getMaxHeight());

            //add the offsets we generated
            scatterLocation.add(xcoord, 0, zcoord);

            //set the the nearest centre of a block
            setToNearestCentre(scatterLocation);

            //get the highest block in the Y coordinate
            try {
                setToHighestNonAir(scatterLocation);
            } catch (NoSolidBlockException e) {
                continue;
            }

            //if there are any mats set check that the block we have is a valid one
            if(parameters.hasMaterials()) {
                List<Material> allowedMats = parameters.getMaterials();
                Material mat = scatterLocation.getBlock().getType();

                if(!allowedMats.contains(mat)) {
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
