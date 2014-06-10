package com.publicuhc.scatter;

import com.publicuhc.scatter.exceptions.ScatterLocationException;
import com.publicuhc.scatter.logic.ScatterLogic;
import com.publicuhc.scatter.zones.DeadZone;
import com.publicuhc.scatter.zones.DeadZoneBuilder;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class DefaultScatterer implements Scatterer{

    private ScatterLogic m_logic;
    private List<DeadZone> m_baseDeadZones;
    private DeadZoneBuilder m_deadZoneBuilder;

    /**
     * Make a scatterer with the options provided.
     *
     * @param logic the type of scatter we want to use
     * @param baseDeadZones the base deadzones, noone can scatter into these (you must provide deadzones around any player not being scattered if it is what you intend)
     * @param zoneBuilder a zone builder to use to create a new zone for every new potential teleport to stop 2 teleports being too close to each other
     */
    public DefaultScatterer(ScatterLogic logic, List<DeadZone> baseDeadZones, DeadZoneBuilder zoneBuilder) {
        m_logic = logic;
        m_baseDeadZones = baseDeadZones;
        m_deadZoneBuilder = zoneBuilder;
    }

    @Override
    public List<Location> getScatterLocations(int amount) throws ScatterLocationException {
        //clone the base list of deadzones so we can add our own to it
        List<DeadZone> currentZoneList = new ArrayList<DeadZone>(m_baseDeadZones);

        //make a list of final locations
        List<Location> newLocations = new ArrayList<Location>();

        //add the right amount of locations
        for(int i = 0; i<amount; i++) {
            //grab a location from the logic
            Location location = m_logic.getScatterLocation(currentZoneList);

            //add to the list
            newLocations.add(location);

            //create a new deadzone for the potential teleport to deny the next location
            DeadZone newDeadZone = m_deadZoneBuilder.buildForLocation(location);
            currentZoneList.add(newDeadZone);
        }

        //if we got here we have the right amount of locations
        return newLocations;
    }

    @Override
    public void setLogic(ScatterLogic logic) {
        m_logic = logic;
    }

    @Override
    public ScatterLogic getLogic() {
        return m_logic;
    }

    @Override
    public void setDeadZoneBuilder(DeadZoneBuilder builder) {
        m_deadZoneBuilder = builder;
    }

    @Override
    public DeadZoneBuilder getDeadZoneBuilder() {
        return m_deadZoneBuilder;
    }

    @Override
    public List<DeadZone> getBaseDeadZones() {
        return m_baseDeadZones;
    }

    @Override
    public void setBaseDeadZones(List<DeadZone> zones) {
        m_baseDeadZones = zones;
    }

    @Override
    public void addBaseDeadZone(DeadZone zone) {
        m_baseDeadZones.add(zone);
    }
}
