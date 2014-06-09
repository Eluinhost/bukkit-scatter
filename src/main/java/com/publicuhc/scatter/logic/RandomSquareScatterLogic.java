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

public class RandomSquareScatterLogic extends ScatterLogic {

    public RandomSquareScatterLogic(Random random) {
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

            //Get the random coords within the box
            double xcoord = (getRandom().nextDouble() * scatterRadius * 2.0D) - scatterRadius;
            double zcoord = (getRandom().nextDouble() * scatterRadius * 2.0D) - scatterRadius;

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
        return "Random Square";
    }

    @Override
    public String getDescription() {
        return "Scatter randomly within a square";
    }
}
