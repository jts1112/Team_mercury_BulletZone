package edu.unh.cs.cs619.bulletzone.web;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.client.RestClientException;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.entities.Dropship;
import edu.unh.cs.cs619.bulletzone.model.entities.Tank;
import edu.unh.cs.cs619.bulletzone.model.EntityDoesNotExistException;

import edu.unh.cs.cs619.bulletzone.repository.InMemoryGameRepository;
import edu.unh.cs.cs619.bulletzone.util.LongWrapper;

import jakarta.servlet.http.HttpServletRequest;

/**
 * This class runs basic tests regarding how the server behaves when the User Joins and Leaves.
 * Tests ran will both have success and failure scenarios
 */
public class Server_Join_and_Leave_test {

    MockMvc mockMvc;
    @Mock
    private InMemoryGameRepository repo;
    @InjectMocks
    private GamesController gamesController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(gamesController).build();
    }

    @Test
    /**
     * This Tests the Scenario when a user successfully joins.
     */
    public void Server_userJoinSucessScenario() {

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        Dropship ship = new Dropship(123,Direction.Up,request.getRemoteAddr());
        when(repo.join(request.getRemoteAddr())).thenReturn(ship);

        ResponseEntity<LongWrapper> serverResponse = gamesController.join(request);

        assertEquals(HttpStatus.CREATED, serverResponse.getStatusCode());

        // Can check that the server response isnt null
        assertNotNull(serverResponse.getBody());

        // make sure the server respose contains the created Tanks ID.
        assertEquals(serverResponse.getBody().getResult(), ship.getId());

        // Optionally, you can check if the tank ID is a positive number
        assertTrue(serverResponse.getBody().getResult() > 0);


    }

    @Test
    /**
     *
     * This Test Simulates the server responding to a User failing TO join.
     * A restClient Exception should be thrown
     *
     */
    public void Server_userJoinFailureScenario() {

        // Create Mock HTTPserverlet request and have it return a fake IP
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        // Force the join method to throw a restclientException to simulate a Join Failure
        RestClientException restClientException = new RestClientException("Failed Rest Client Exception");
        when(repo.join(request.getRemoteAddr())).thenThrow(restClientException);

        // Simulate the Join method being called with the restclientException being thrown
        ResponseEntity<LongWrapper> serverResponse = gamesController.join(request);

        // Join method should return null.
        assertEquals(null, serverResponse);

    }


    @Test
    public void Server_userLeaveSuccessScenario() throws EntityDoesNotExistException {

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        Tank tank = new Tank(123,Direction.Up,request.getRemoteAddr());

        HttpStatus serverResponse = gamesController.leave(tank.getId());

        // server returned response should be OK
        assertEquals(HttpStatus.OK, serverResponse);

        // Can check that the server response isnt null
        assertNotNull(serverResponse);
    }


    @Test
    public void Server_userLeaveFailureScenario() throws EntityDoesNotExistException {

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        Tank tank = new Tank(123,Direction.Up,request.getRemoteAddr());

        HttpStatus serverResponse = gamesController.leave(tank.getId());

        // server returned response should be OK
        assertEquals(HttpStatus.OK, serverResponse);

        // Can check that the server response isnt null
        assertNotNull(serverResponse);
    }

}
