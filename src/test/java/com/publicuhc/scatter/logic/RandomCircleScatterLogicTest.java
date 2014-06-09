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
public class RandomCircleScatterLogicTest {

    private RandomCircleScatterLogic logic;
    private Random mockRandom;
    private ScatterParameters parameters;
    private Location centre;

    @Before
    public void onStartup() throws NoSolidBlockException {
        mockRandom = mock(Random.class);
        logic = spy(new RandomCircleScatterLogic(mockRandom));
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
        //we want to test on the 90 degree angle at full radius
        when(mockRandom.nextDouble()).thenReturn(0.25D).thenReturn(1.0D);
        ScatterParameters parameters = new ScatterParameters().setMaxAttempts(1).setRadius(10).setCentre(centre);

        Location location = logic.getScatterLocation(new ArrayList<DeadZone>(), parameters);

        //20,0,-10 without the 0.5 offset
        Location expected = new Location(centre.getWorld(), 20.5, 0, -9.5);
        assertThat(location).isEqualTo(expected);

        //we want to test on the 45 degree angle at full radius
        when(mockRandom.nextDouble()).thenReturn(0.125D).thenReturn(1.0D);
        parameters = new ScatterParameters().setMaxAttempts(1).setRadius(7.07).setCentre(centre);

        location = logic.getScatterLocation(new ArrayList<DeadZone>(), parameters);

        //15, 0, -5 without the offset
        expected = new Location(centre.getWorld(), 15.5, 0, -4.5);
        assertThat(location).isEqualTo(expected);

        //test on 180 degree angle at half radius (radius is square rooted for uniform dist)
        when(mockRandom.nextDouble()).thenReturn(0.5D).thenReturn(0.25D);

        parameters = new ScatterParameters().setMaxAttempts(1).setRadius(10).setCentre(centre);

        location = logic.getScatterLocation(new ArrayList<DeadZone>(), parameters);

        //10, 0, -15 without the offset
        expected = new Location(centre.getWorld(), 10.5, 0, -14.5);
        assertThat(location).isEqualTo(expected);

        //test on 225 degree angle at half radius (radius is sqaure rooted for uniform dist
        when(mockRandom.nextDouble()).thenReturn(0.625D).thenReturn(0.25D);

        parameters = new ScatterParameters().setMaxAttempts(1).setRadius(14.14).setCentre(centre);

        location = logic.getScatterLocation(new ArrayList<DeadZone>(), parameters);

        //5, 0, -15 without the offset
        expected = new Location(centre.getWorld(), 5.5, 0, -14.5);
        assertThat(location).isEqualTo(expected);

        //test on 270 degree angle at 0 radius
        when(mockRandom.nextDouble()).thenReturn(0.75D).thenReturn(0D);

        parameters = new ScatterParameters().setMaxAttempts(1).setRadius(10).setCentre(centre);

        location = logic.getScatterLocation(new ArrayList<DeadZone>(), parameters);

        //10, 0, -10 without the offset
        expected = new Location(centre.getWorld(), 10.5, 0, -9.5);
        assertThat(location).isEqualTo(expected);

        //test on 315 degree angle at half radius (radius is rooted for uniform dist)
        when(mockRandom.nextDouble()).thenReturn(0.875D).thenReturn(0.25D);

        parameters = new ScatterParameters().setMaxAttempts(1).setRadius(14.14).setCentre(centre);

        location = logic.getScatterLocation(new ArrayList<DeadZone>(), parameters);

        //5, 0, -5 without the offset
        expected = new Location(centre.getWorld(), 5.5, 0, -4.5);
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
