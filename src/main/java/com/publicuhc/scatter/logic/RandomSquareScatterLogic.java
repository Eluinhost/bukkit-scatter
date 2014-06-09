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

public class RandomSquareScatterLogic extends ScatterLogic {

    private final int m_maxAttempts;
    private final double m_radius;
    private final Location m_centre;
    private final List<Material>  m_materials = new ArrayList<Material>();

    public RandomSquareScatterLogic(Random random, Location centre, int maxAttempts, double radius, Material... allowedMaterials) {
        super(random);
        m_maxAttempts = maxAttempts;
        m_radius = radius;
        m_centre = centre;
        m_materials.addAll(Arrays.asList(allowedMaterials));
    }

    @Override
    public Location getScatterLocation(List<DeadZone> deadZones) throws ScatterLocationException {
        for (int i = 0; i < m_maxAttempts; i++) {

            //Get the random coords within the box
            double xcoord = new BigDecimal((getRandom().nextDouble() * m_radius * 2.0D) - m_radius).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            double zcoord = new BigDecimal((getRandom().nextDouble() * m_radius * 2.0D) - m_radius).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

            //make a new location at world height at the coordinates
            Location scatterLocation = m_centre.clone();
            scatterLocation.setY(m_centre.getWorld().getMaxHeight());

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
            if(m_materials.size() > 0) {
                Material mat = scatterLocation.getBlock().getType();

                if(!m_materials.contains(mat)) {
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
