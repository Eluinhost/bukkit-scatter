package com.publicuhc.scatter.logic;

import com.publicuhc.scatter.ScatterParameters;
import com.publicuhc.scatter.exceptions.NoSolidBlockException;
import com.publicuhc.scatter.exceptions.ScatterConfigurationException;
import com.publicuhc.scatter.exceptions.ScatterLocationException;
import com.publicuhc.scatter.zones.DeadZone;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public abstract class ScatterLogic {

    private final Random m_random;

    public static final double X_CENTRE = 0.5D;
    public static final double Z_CENTRE = 0.5D;

    public ScatterLogic(Random random) {
        m_random = random;
    }

    public Random getRandom() {
        return m_random;
    }

    /**
     * Gets the Z distance from the radius and angle, accurate to 2 decimal places using rounding mode ROUND_HALF_UP
     * @param radius the radius
     * @param angle the angle
     * @return the Z distance
     */
    public BigDecimal getZFromRadians(double radius, double angle) {
        BigDecimal zLength = new BigDecimal(StrictMath.cos(angle)).multiply(new BigDecimal(radius));
        return zLength.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Gets the X distance from the radius and angle, accurate to 2 decimal places using rounding mode ROUND_HALF_UP
     * @param radius the radius
     * @param angle the angle
     * @return the X distance
     */
    public BigDecimal getXFromRadians(double radius, double angle) {
        BigDecimal xLength = new BigDecimal(StrictMath.sin(angle)).multiply(new BigDecimal(radius));
        return xLength.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Sets the X and Z values to the nearest centre of a block (0.5,0.5)
     * @param location the location to set
     */
    public void setToNearestCentre(Location location) {
        location.setX(StrictMath.floor(location.getX()) + X_CENTRE);
        location.setZ(StrictMath.floor(location.getZ()) + Z_CENTRE);
    }

    /**
     * Sets the Y coordinate to the highest non air block at a location, starting at it's Y and moving down
     *
     * @param loc The location to use
     * @throws com.publicuhc.scatter.exceptions.NoSolidBlockException when there was no valid block found
     */
    public void setToHighestNonAir(Location loc) throws NoSolidBlockException {
        //Load the chunk first so the world is generated
        if (!loc.getChunk().isLoaded()) {
            loc.getChunk().load(true);
        }

        Block block = loc.getBlock();
        while(block != null) {
            //set the Y if we find a non-air block
            if(block.getType() != Material.AIR) {
                loc.setY(block.getY());
                return;
            }
            //keep falling down
            block = block.getRelative(BlockFace.DOWN);
        }

        //no non-air blocks were found all the way down
        throw new NoSolidBlockException();
    }

    /**
     * @param location the location to check against
     * @param deadZones all of the deadzones to check
     * @return true if location is in deadzone, false otherwise
     */
    public boolean isLocationWithinDeadZones(Location location, Collection<DeadZone> deadZones) {
        for(DeadZone deadZone : deadZones) {
            if(!deadZone.isLocationAllowed(location)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get a list of valid scatter locations
     * @param deadZones the list of zones for which spawning should be disallowed
     * @return a valid location
     * @throws com.publicuhc.scatter.exceptions.ScatterLocationException on being not able to get a valid location
     * @throws com.publicuhc.scatter.exceptions.ScatterConfigurationException if needed parameters in ScatterParameters are missing
     */
    public abstract Location getScatterLocation(List<DeadZone> deadZones, ScatterParameters parameters) throws ScatterLocationException, ScatterConfigurationException;

    /**
     * @return the unique name of the scatter logic
     */
    public abstract String getID();

    /**
     * @return a short description of how we scatter
     */
    public abstract String getDescription();
}
