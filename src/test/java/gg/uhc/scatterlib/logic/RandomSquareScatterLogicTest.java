package gg.uhc.scatterlib.logic;

import gg.uhc.scatterlib.exceptions.NoSolidBlockException;
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
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
public class RandomSquareScatterLogicTest {

    private RandomSquareScatterLogic logic;
    private Random mockRandom;
    private Location centre;

    @Before
    public void onStartup() throws NoSolidBlockException {
        mockRandom = mock(Random.class);
        World world = mock(World.class);
        when(world.getMaxHeight()).thenReturn(1);
        centre = new Location(world, 10, 0, -10);
        logic = spy(new RandomSquareScatterLogic(mockRandom));
        logic.setCentre(centre);
        logic.setMaxAttempts(1);
        logic.setRadius(10);

        doNothing().when(logic).setToHighestNonAir(any(Location.class));
    }

    @Test
    public void testGetLocationsNoDeadZones() throws ScatterLocationException {
        //test half side length X and Z
        when(mockRandom.nextDouble()).thenReturn(0.5D);

        Location location = logic.getScatterLocation(new ArrayList<DeadZone>());

        //10,0,-10 without the 0.5 offset
        Location expected = new Location(centre.getWorld(), 10.5, 0, -9.5);
        assertThat(location).isEqualTo(expected);

        //test full side length X and Z
        when(mockRandom.nextDouble()).thenReturn(1.0D);

        location = logic.getScatterLocation(new ArrayList<DeadZone>());

        //20, 0, 0 without the offset
        expected = new Location(centre.getWorld(), 20.5, 0, .5);
        assertThat(location).isEqualTo(expected);

        //test .8 length X and .4 length Z
        when(mockRandom.nextDouble()).thenReturn(0.8D).thenReturn(0.4D);

        location = logic.getScatterLocation(new ArrayList<DeadZone>());

        //16, 0, -12 without the offset
        expected = new Location(centre.getWorld(), 16.5, 0, -11.5);
        assertThat(location).isEqualTo(expected);

        //test arbitary numbers
        when(mockRandom.nextDouble()).thenReturn(0.34784D).thenReturn(0.73847D);

        location = logic.getScatterLocation(new ArrayList<DeadZone>());

        //6.9568, 0, -5.2306 without offset
        expected = new Location(centre.getWorld(), 6.5, 0, -5.5);
        assertThat(location).isEqualTo(expected);
    }

    @Test
    public void testAllWithinDeadZones()  {
        DeadZone zone = mock(DeadZone.class);
        when(zone.isLocationAllowed(any(Location.class))).thenReturn(false);
        List<DeadZone> zones = new ArrayList<DeadZone>();
        zones.add(zone);

        logic.setMaxAttempts(10);

        try {
            logic.getScatterLocation(zones);
        } catch (ScatterLocationException e) {
            //this is what we expect
            verify(mockRandom, times(20)).nextDouble();
            return;
        }

        failBecauseExceptionWasNotThrown(ScatterLocationException.class);
    }
}
