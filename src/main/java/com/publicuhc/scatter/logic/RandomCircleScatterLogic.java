package com.publicuhc.scatter.logic;

import com.publicuhc.scatter.ScatterParameters;
import com.publicuhc.scatter.exceptions.ScatterConfigurationException;
import com.publicuhc.scatter.exceptions.ScatterLocationException;
import com.publicuhc.scatter.zones.DeadZone;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class RandomCircleScatterLogic implements ScatterLogic {

    @Override
    public List<Location> getScatterLocations(int amount, List<DeadZone> deadZones, ScatterParameters parameters) throws ScatterLocationException, ScatterConfigurationException {
        if(!parameters.isMaxAttemptsSet() || !parameters.isRadiusSet()) {
            throw new ScatterConfigurationException();
        }
        List<Location> scatterLocations = new ArrayList<Location>();
        //TODO get the locations
        //TODO how do we create a deadzone for each new location? Pass in a deadzone builder?
        return scatterLocations;
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
