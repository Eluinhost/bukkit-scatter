package com.publicuhc.scatter.logic;

import com.publicuhc.scatter.ScatterParameters;
import com.publicuhc.scatter.exceptions.ScatterLocationException;
import com.publicuhc.scatter.zones.DeadZone;
import org.bukkit.Location;

import java.util.List;

public interface ScatterLogic {

    /**
     * Get a list of valid scatter locations
     * @param amount the amount of locations needed
     * @param deadZones the list of zones in which spawning should be disallowed
     * @return list of locations
     * @throws com.publicuhc.scatter.exceptions.ScatterLocationException on being not able to get a valid location
     */
    List<Location> getScatterLocations(int amount, List<DeadZone> deadZones, ScatterParameters parameters) throws ScatterLocationException;

    /**
     * @return the unique name of the scatter logic
     */
    String getID();

    /**
     * @return a short description of how we scatter
     */
    String getDescription();
}
