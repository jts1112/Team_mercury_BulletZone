package edu.unh.cs.cs619.bulletzone;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mock.*;
import static org.mockito.junit.MockitoJUnitRunner.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import edu.unh.cs.cs619.bulletzone.rest.BulletZoneRestClient;
import edu.unh.cs.cs619.bulletzone.util.BooleanWrapper;
import edu.unh.cs.cs619.bulletzone.util.LongWrapper;

@RunWith(MockitoJUnitRunner.class)

/**
 *
 * This Class Tests the User account Creation and Authentication for the Client Side of BulletZone
 *
 */
public class AuthenticationControllerTest {

    @Mock
    BulletZoneRestClient restClient;

    @InjectMocks
    AuthenticationController controller;

    @Test
    /**
     * Client test to test the scenario where a user successfuly logs in
     * The rest client returns A long wrappr that contains the User ID in it.
     * When the controller logs in it should get that same User ID as a result.
     */
    public void testLogin_Successful() {
        // Mock the restClient login response
        when(restClient.login("username", "password")).thenReturn(new LongWrapper(12345));

        // Perform the login
        long userId = controller.login("username", "password");

        // Verify the result
        assertEquals(12345, userId);
    }

    @Test
    /**
     * Client test to test the scenario where a user fails to login
     * If the restclients login in returned null then the controllers log in
     * Should return -1 on failure
     */
    public void testLogin_Failure() {
        // Mock the restClient login response
        when(restClient.login("username", "password")).thenReturn(null);

        // Perform the login
        long userId = controller.login("username", "password");

        // Verify the result
        assertEquals(-1, userId);
    }

    @Test
    /**
     * Test testing the scenario where a user successfully creating an account
     * Controller register should return true.
     */
    public void testRegister_Successful() {
        // Mock the restClient register response
        when(restClient.register("username", "password")).thenReturn(new BooleanWrapper(true));

        // Perform the registration
        boolean result = controller.register("username", "password");

        // Verify the result
        assertTrue(result);
    }

    @Test
    /**
     * This test will simulate a user account Creation failure
     * Controller register should return false
     */
    public void testRegister_Failure() {
        // Mock the restClient register response
        when(restClient.register("username", "password")).thenReturn(null);

        // Perform the registration
        boolean result = controller.register("username", "password");

        // Verify the result
        assertFalse(result);
    }
}