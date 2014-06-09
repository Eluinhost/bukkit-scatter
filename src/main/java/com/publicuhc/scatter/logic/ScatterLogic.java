package com.publicuhc.scatter.logic;

import com.publicuhc.scatter.ScatterParameters;
import com.publicuhc.scatter.exceptions.ScatterConfigurationException;
import com.publicuhc.scatter.exceptions.ScatterLocationException;
import com.publicuhc.scatter.zones.DeadZone;
import org.bukkit.Location;

import java.util.List;
import java.util.Random;

public abstract class ScatterLogic {

    private final Random m_random;

    public ScatterLogic(Random random) {
        m_random = random;
    }

    public Random getRandom() {
        return m_random;
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
