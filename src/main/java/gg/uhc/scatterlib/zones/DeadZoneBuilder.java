package gg.uhc.scatterlib.zones;

import org.bukkit.Location;

public interface DeadZoneBuilder {

    /**
     * Build a DeadZone for the given location
     * @param location the location to use
     * @return the built deadzone
     */
    DeadZone buildForLocation(Location location);
}
