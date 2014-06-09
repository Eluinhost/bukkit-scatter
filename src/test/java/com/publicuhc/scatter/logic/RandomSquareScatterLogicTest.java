package com.publicuhc.scatter.logic;

import com.publicuhc.scatter.ScatterParameters;
import com.publicuhc.scatter.exceptions.NoSolidBlockException;
import com.publicuhc.scatter.exceptions.ScatterConfigurationException;
import com.publicuhc.scatter.exceptions.ScatterLocationException;
import com.publicuhc.scatter.zones.DeadZone;
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
    private ScatterParameters parameters;
    private Location centre;

    @Before
    public void onStartup() throws NoSolidBlockException {
        mockRandom = mock(Random.class);
        logic = spy(new RandomSquareScatterLogic(mockRandom));
        World world = mock(World.class);
        centre = new Location(world, 10, 0, -10);
        doNothing().when(logic).setToHighestNonAir(any(Location.class));
    }

    @Test(expected = ScatterConfigurationException.class)
    public void testMaxAttemptsParameterNotSet() throws ScatterLocationException, ScatterConfigurationException {
        ScatterParameters parameters = new ScatterParameters().setCentre(centre).setRadius(10);
        logic.getScatterLocation(new ArrayList<DeadZone>(), parameters);
    }

    @Test(expected = ScatterConfigurationException.class)
    public void testRadiusNotSet() throws ScatterLocationException, ScatterConfigurationException {
        ScatterParameters parameters = new ScatterParameters().setMaxAttempts(100).setCentre(centre);
        logic.getScatterLocation(new ArrayList<DeadZone>(), parameters);
    }

    @Test(expected = ScatterConfigurationException.class)
    public void testCentreNotSet() throws ScatterLocationException, ScatterConfigurationException {
        ScatterParameters parameters = new ScatterParameters().setMaxAttempts(100).setRadius(100);
        logic.getScatterLocation(new ArrayList<DeadZone>(), parameters);
    }

    @Test
    public void testGetLocationsNoDeadZones() throws ScatterLocationException, ScatterConfigurationException {
        //test half side length X and Z
        when(mockRandom.nextDouble()).thenReturn(0.5D);
        ScatterParameters parameters = new ScatterParameters().setMaxAttempts(1).setRadius(10).setCentre(centre);

        Location location = logic.getScatterLocation(new ArrayList<DeadZone>(), parameters);

        //10,0,-10 without the 0.5 offset
        Location expected = new Location(centre.getWorld(), 10.5, 0, -9.5);
        assertThat(location).isEqualTo(expected);

        //test full side length X and Z
        when(mockRandom.nextDouble()).thenReturn(1.0D);

        location = logic.getScatterLocation(new ArrayList<DeadZone>(), parameters);

        //20, 0, 0 without the offset
        expected = new Location(centre.getWorld(), 20.5, 0, .5);
        assertThat(location).isEqualTo(expected);

        //test .8 length X and .4 length Z
        when(mockRandom.nextDouble()).thenReturn(0.8D).thenReturn(0.4D);

        location = logic.getScatterLocation(new ArrayList<DeadZone>(), parameters);

        //16, 0, -12 without the offset
        expected = new Location(centre.getWorld(), 16.5, 0, -11.5);
        assertThat(location).isEqualTo(expected);

        //test arbitary numbers
        when(mockRandom.nextDouble()).thenReturn(0.34784D).thenReturn(0.73847D);

        location = logic.getScatterLocation(new ArrayList<DeadZone>(), parameters);

        //6.9568, 0, -5.2306 without offset
        expected = new Location(centre.getWorld(), 6.5, 0, -5.5);
        assertThat(location).isEqualTo(expected);
    }

    @Test
    public void testAllWithinDeadZones() throws ScatterConfigurationException {
        ScatterParameters parameters = new ScatterParameters().setMaxAttempts(10).setRadius(10).setCentre(centre);

        DeadZone zone = mock(DeadZone.class);
        when(zone.isLocationAllowed(any(Location.class))).thenReturn(false);
        List<DeadZone> zones = new ArrayList<DeadZone>();
        zones.add(zone);

        try {
            logic.getScatterLocation(zones, parameters);
        } catch (ScatterLocationException e) {
            //this is what we expect
            verify(mockRandom, times(20)).nextDouble();
            return;
        }

        failBecauseExceptionWasNotThrown(ScatterLocationException.class);
    }
}
