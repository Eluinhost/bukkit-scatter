package com.publicuhc.scatter;

import com.publicuhc.scatter.exceptions.ScatterLocationException;
import com.publicuhc.scatter.logic.ScatterLogic;
import com.publicuhc.scatter.zones.DeadZone;
import com.publicuhc.scatter.zones.DeadZoneBuilder;
import org.bukkit.Location;

import java.util.List;

public interface Scatterer {

    /**
     * Attempt to find safe locations
     *
     * @return list of locations outside of provided deadzones and outside of zones created by the builder
     * @throws ScatterLocationException if a location wasn't able to be fetched
     */
    List<Location> getScatterLocations(int amount) throws ScatterLocationException;

    void setLogic(ScatterLogic logic);

    ScatterLogic getLogic();

    void setDeadZoneBuilder(DeadZoneBuilder builder);

    DeadZoneBuilder getDeadZoneBuilder();

    List<DeadZone> getBaseDeadZones();

    void setBaseDeadZones(List<DeadZone> zones);

    void addBaseDeadZone(DeadZone zone);
}
