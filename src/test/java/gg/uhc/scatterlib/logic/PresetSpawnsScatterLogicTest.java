package gg.uhc.scatterlib.logic;

import gg.uhc.scatterlib.exceptions.ScatterLocationException;
import gg.uhc.scatterlib.zones.DeadZone;
import org.bukkit.Location;
import org.bukkit.World;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class PresetSpawnsScatterLogicTest {

    private PresetSpawnsScatterLogic logic;
    private Random mockRandom;
    private List<Location> spawns;

    @Before
    public void onStartup() {
        mockRandom = mock(Random.class);
        World mockWorld = mock(World.class);
        spawns = new ArrayList<Location>();
        spawns.add(new Location(mockWorld, -10, 0, -10));
        spawns.add(new Location(mockWorld, -10, 0, 0));
        spawns.add(new Location(mockWorld, -10, 0, 10));
        spawns.add(new Location(mockWorld, 0, 0, -10));
        spawns.add(new Location(mockWorld, 0, 0, 0));
        spawns.add(new Location(mockWorld, 0, 0, 10));
        spawns.add(new Location(mockWorld, 10, 0, -10));
        spawns.add(new Location(mockWorld, 10, 0, 0));
        spawns.add(new Location(mockWorld, 10, 0, 10));
        spawns.add(new Location(mockWorld, 0, 0, 0));

        logic = new PresetSpawnsScatterLogic(mockRandom);
        logic.setSpawnsList(spawns);
    }

    @Test
    public void testGetScatterLocationNoDeadZones() throws ScatterLocationException {
        when(mockRandom.nextInt(10)).thenReturn(4);

        Location location = logic.getScatterLocation(new ArrayList<DeadZone>());

        assertThat(location).isEqualTo(spawns.get(4));
        verify(mockRandom, times(1)).nextInt(10);
    }

    @Test
    public void testGetScatterLocationWithDeadZones() throws ScatterLocationException {
        when(mockRandom.nextInt(anyInt()))
                .thenReturn(4) //spawns[4]
                .thenReturn(7) //spawns[8]
                .thenReturn(5);//spawns[6]

        DeadZone mockDeadZone = mock(DeadZone.class);

        when(mockDeadZone.isLocationAllowed(spawns.get(4))).thenReturn(false);
        when(mockDeadZone.isLocationAllowed(spawns.get(8))).thenReturn(false);
        when(mockDeadZone.isLocationAllowed(spawns.get(6))).thenReturn(true);

        List<DeadZone> zones = new ArrayList<DeadZone>();
        zones.add(mockDeadZone);

        Location location = logic.getScatterLocation(new ArrayList<DeadZone>(zones));

        assertThat(location).isEqualTo(spawns.get(6));
        verify(mockRandom, times(3)).nextInt(anyInt());
    }

    @Test(expected = ScatterLocationException.class)
    public void testAllWithinDeadzones() throws ScatterLocationException {
        DeadZone mockDeadZone = mock(DeadZone.class);
        when(mockDeadZone.isLocationAllowed(any(Location.class))).thenReturn(false);
        List<DeadZone> zones = new ArrayList<DeadZone>();
        zones.add(mockDeadZone);

        logic.getScatterLocation(zones);
    }
}
