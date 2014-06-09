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
     * Gets the Z distance from the radius and angle
     * @param radius the radius
     * @param angle the angle
     * @return the Z distance
     */
    public static double getZFromRadians(double radius, double angle) {
        return radius * StrictMath.sin(angle);
    }

    /**
     * Gets the X distance from the radius and angle
     * @param radius the radius
     * @param angle the angle
     * @return the X distance
     */
    public static double getXFromRadians(double radius, double angle) {
        return radius * StrictMath.cos(angle);
    }

    /**
     * Sets the X and Z values to the nearest centre of a block (0.5,0.5)
     * @param location the location to set
     */
    public static void setToNearestCentre(Location location) {
        location.setX(Math.floor(location.getX()) + X_CENTRE);
        location.setZ(Math.floor(location.getZ()) + Z_CENTRE);
    }

    /**
     * Sets the Y coordinate to the highest non air block at a location, starting at it's Y and moving down
     *
     * @param loc The location to use
     * @throws com.publicuhc.scatter.exceptions.NoSolidBlockException when there was no valid block found
     */
    public static void setToHighestNonAir(Location loc) throws NoSolidBlockException {
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
    public static boolean isLocationWithinDeadZones(Location location, Collection<DeadZone> deadZones) {
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
    public abstract List<Location> getScatterLocation(List<DeadZone> deadZones, ScatterParameters parameters) throws ScatterLocationException, ScatterConfigurationException;

    /**
     * @return the unique name of the scatter logic
     */
    public abstract String getID();

    /**
     * @return a short description of how we scatter
     */
    public abstract String getDescription();
}
