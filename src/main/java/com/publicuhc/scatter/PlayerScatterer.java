package com.publicuhc.scatter;

import com.publicuhc.scatter.logic.ScatterLogic;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class PlayerScatterer {

    private final ScatterLogic m_logic;
    private final List<WeakReference<Player>> m_players = new ArrayList<WeakReference<Player>>();

    public PlayerScatterer(ScatterLogic logic, List<Player> players) {
        m_logic = logic;
        for(Player player : players) {
            m_players.add(new WeakReference<Player>(player));
        }
    }

    public void scatter() {
        List<Location> locations = m_logic.getScatterLocations(m_players.size(), )
    }
}
