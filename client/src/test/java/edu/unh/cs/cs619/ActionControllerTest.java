package edu.unh.cs.cs619;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import edu.unh.cs.cs619.bulletzone.ActionController;
import edu.unh.cs.cs619.bulletzone.R;
import edu.unh.cs.cs619.bulletzone.rest.BulletZoneRestClient;
import edu.unh.cs.cs619.bulletzone.util.LongWrapper;
import edu.unh.cs.cs619.bulletzone.util.ShakeDetector;
import edu.unh.cs.cs619.bulletzone.util.UnitIds;
import kotlin.Unit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ActionControllerTest {

    @Mock
    private BulletZoneRestClient restClient;

    private ActionController actionController;

    @Before
    public void setUp() {
        // Initialize the mock BulletZoneRestClient
        restClient = mock(BulletZoneRestClient.class);
        UnitIds ids = UnitIds.getInstance();
        ids.setIds(121, 122, 123);
        actionController = new ActionController();
        actionController.setCurrentUnitId(123);
        actionController.restClient = restClient;
        actionController.Ids = ids;
    }

    @Test
    public void test_ActionController_Join() {
        // Define the behavior of restClient
        when(restClient.join()).thenReturn(new LongWrapper(123, 124, 125));

        // Call the method to be tested
        long dropshipId = actionController.join();

        // Verify that the method returns the correct tank ID
        assertEquals(123, dropshipId);
    }

    @Test
    public void test_ActionController_OnButtonMove() {
        // Call the method to be tested
        actionController.onButtonMove((byte) 4);

        // Verify that the restClient's move method is called with the correct parameters
        verify(restClient).move(123, (byte) 4);
    }

    @Test
    public void OnButtonFire_NormalTankFire_CallsFireOnTankId() {
        // Call the method to be tested

        actionController.onButtonFire();

        // Verify that the restClient's fire method is called with the correct parameter
        verify(restClient).fire(123);
    }

    @Test
    public void OnButtonFire_DropshipFire_CallsFireWithBulletType3() {
        // change id to dropship
        actionController.setCurrentUnitId(121);
        actionController.onButtonFire();

        // Verify that the restClient's fire method is called with the correct parameter
        verify(restClient).fire(121, (byte) 3);
    }

    @Test
    public void OnButtonEject_NormalTankEject_CallsEjectOnTankId() {
        // Call the method to be tested

        actionController.onButtonEjectPowerUp();

        // Verify that the restClient's fire method is called with the correct parameter
        verify(restClient).ejectPowerUp(123);
    }

    @Test
    public void ShakeDetector_OnShakeFire_ShakeEventReturnsFire() {
        // Create a mock Context, actioncontroller
        Context mockContext = mock(Context.class);
        ActionController mockActionController = mock(ActionController.class);
        mockActionController.initialize(mockContext);
        mockActionController.setCurrentUnitId(123);

        // Create a mock SensorManager
        SensorManager mockSensorManager = mock(SensorManager.class);
        // Mock the getSystemService method of the Context to return the mock SensorManager
        when(mockContext.getSystemService(Context.SENSOR_SERVICE)).thenReturn(mockSensorManager);
        ShakeDetector shakeDetector = new ShakeDetector(mockContext);
        // Log shakeDetector to ensure it's not null
        System.out.println("ShakeDetector: " + shakeDetector);

        // Set the mock ActionController as the ShakeDetector's listener
        shakeDetector.setOnShakeListener(() -> mockActionController.onButtonFire());

        // Simulate a shake event
        shakeDetector.simulateShake();

        // verify that the onButtonFire method of the mock ActionController is called
        verify(mockActionController).onButtonFire();
    }

    @Test
    public void test_ActionController_Leave() {
        // Call the method to be tested
        actionController.leave(123);

        // Verify that the restClient's leave method is called with the correct parameter
        verify(restClient).leave(123);
    }
}
