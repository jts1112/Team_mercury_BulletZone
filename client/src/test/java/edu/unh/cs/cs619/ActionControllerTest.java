package edu.unh.cs.cs619;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import edu.unh.cs.cs619.bulletzone.ActionController;
import edu.unh.cs.cs619.bulletzone.rest.BulletZoneRestClient;
import edu.unh.cs.cs619.bulletzone.util.LongWrapper;
import edu.unh.cs.cs619.bulletzone.util.ShakeDetector;
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

    @Mock
    private ShakeDetector shakeDetector;

    @Mock
    private Context context;

    private ActionController actionController;

    @Before
    public void setUp() {
        // Initialize the mock BulletZoneRestClient
        restClient = mock(BulletZoneRestClient.class);
        actionController = new ActionController();
        actionController.restClient = restClient;
    }

    @Test
    public void testJoin() {
        // Define the behavior of restClient
        when(restClient.join()).thenReturn(new LongWrapper(123));

        // Call the method to be tested
        long tankId = actionController.join();

        // Verify that the method returns the correct tank ID
        assertEquals(123, tankId);
    }

    @Test
    public void testOnButtonMove() {
        // Call the method to be tested
        actionController.onButtonMove(123, (byte) 1);

        // Verify that the restClient's move method is called with the correct parameters
        verify(restClient).move(123, (byte) 1);
    }

    @Test
    public void testOnButtonFire() {
        // Call the method to be tested
        actionController.onButtonFire(123);

        // Verify that the restClient's fire method is called with the correct parameter
        verify(restClient).fire(123);
    }

    @Test
    public void testSimulateShakeEvent() {
        // Create a mock Context
        Context mockContext = mock(Context.class);

        // Log mockContext to ensure it's not null
        System.out.println("Mock Context: " + mockContext);

        // Create a mock ActionController
        ActionController mockActionController = mock(ActionController.class);

        // Log mockActionController to ensure it's not null
        System.out.println("Mock ActionController: " + mockActionController);

        // Initialize the mock ActionController with the mock Context
        mockActionController.initialize(mockContext);


        // Create a mock SensorManager
        SensorManager mockSensorManager = mock(SensorManager.class);

        // Mock the getSystemService method of the Context to return the mock SensorManager
        when(mockContext.getSystemService(Context.SENSOR_SERVICE)).thenReturn(mockSensorManager);

        // Create a ShakeDetector instance with the mock Context
        ShakeDetector shakeDetector = new ShakeDetector(mockContext);

        // Log shakeDetector to ensure it's not null
        System.out.println("ShakeDetector: " + shakeDetector);

        // Set the mock ActionController as the ShakeDetector's listener
        shakeDetector.setOnShakeListener(() -> mockActionController.onButtonFire(anyLong()));

        // Simulate a shake event
        shakeDetector.simulateShake();

        // verify that the onButtonFire method of the mock ActionController is called
        verify(mockActionController).onButtonFire(anyLong());
    }

    @Test
    public void testLeave() {
        // Call the method to be tested
        actionController.leave(123);

        // Verify that the restClient's leave method is called with the correct parameter
        verify(restClient).leave(123);
    }
}
