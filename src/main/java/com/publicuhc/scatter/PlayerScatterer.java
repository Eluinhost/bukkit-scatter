package com.publicuhc.scatter;

import com.publicuhc.scatter.exceptions.ScatterLocationException;
import com.publicuhc.scatter.logic.ScatterLogic;
import com.publicuhc.scatter.zones.DeadZone;
import com.publicuhc.scatter.zones.DeadZoneBuilder;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerScatterer {

    private final ScatterLogic m_logic;
    private final Map<UUID, Location> m_startingLocations = new HashMap<UUID, Location>();
    private final List<DeadZone> m_baseDeadZones;
    private final DeadZoneBuilder m_deadZoneBuilder;

    /**
     * Make a scatterer for the players provided.
     * <p>
     *     <b>NOTE:</b> This should only be used once, create a new one if you want things to work as intended
     * </p>
     * @param logic the type of scatter we want to use
     * @param players the players we want to scatter
     * @param baseDeadZones the base deadzones, noone can scatter into these (you must provide deadzones around any player not being scattered if it is what you intend)
     * @param zoneBuilder a zone builder to use to create a new zone for every new potential teleport to stop 2 teleports being too close to each other
     */
    public PlayerScatterer(ScatterLogic logic, List<Player> players, List<DeadZone> baseDeadZones, DeadZoneBuilder zoneBuilder) {
        m_logic = logic;
        m_baseDeadZones = baseDeadZones;
        m_deadZoneBuilder = zoneBuilder;
        for(Player player : players) {
            m_startingLocations.put(player.getUniqueId(), player.getLocation());
        }
    }

    /**
     * Attempt to find safe locations
     * @return list of location mappings for all the player's provided
     * @throws ScatterLocationException if a location wasn't able to be fetched
     */
    public List<LocationMapping> getScatterLocations() throws ScatterLocationException {
        //clone the base list for the scatter
        List<DeadZone> currentZoneList = new ArrayList<DeadZone>(m_baseDeadZones);

        //make a list of final teleports
        List<LocationMapping> newLocations = new ArrayList<LocationMapping>();

        //for every player
        for(Map.Entry<UUID, Location> entry : m_startingLocations.entrySet()) {

            //grab a location from the logic
            Location location = m_logic.getScatterLocation(currentZoneList);

            //create a mapping from it to teleport later
            LocationMapping mapping = new LocationMapping(location, entry.getKey());

            //create a new deadzone for the potential teleport to deny the next location
            DeadZone newDeadZone = m_deadZoneBuilder.buildForLocation(location);
            currentZoneList.add(newDeadZone);
        }

        //if we got here we have a mapping for every player
        return newLocations;
    }
}
