package com.publicuhc.scatter;

import org.bukkit.Location;

import java.util.UUID;

public class LocationMapping {

    private final Location m_scatterLocation;
    private final UUID m_playerUUID;

    public LocationMapping(Location location, UUID uuid) {
        m_scatterLocation = location;
        m_playerUUID = uuid;
    }

    public Location getScatterLocation() {
        return m_scatterLocation;
    }

    public UUID getUUID() {
        return m_playerUUID;
    }
}
