package edu.unh.cs.cs619.bulletzone.web;

import static org.junit.Assert.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mock.*;
import static org.mockito.junit.MockitoJUnitRunner.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import edu.unh.cs.cs619.bulletzone.datalayer.BulletZoneData;
import edu.unh.cs.cs619.bulletzone.datalayer.core.Entity;
import edu.unh.cs.cs619.bulletzone.datalayer.user.GameUser;
import edu.unh.cs.cs619.bulletzone.repository.DataRepository;
import edu.unh.cs.cs619.bulletzone.util.BooleanWrapper;
import edu.unh.cs.cs619.bulletzone.util.LongWrapper;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
/**
 * This is a class that will Test the Creation and Authorization of users on the server side.
 */
public class AccountControllerTest {

    @Mock
    DataRepository dataRepository;


    @InjectMocks
    AccountController controller;

    private BulletZoneData database;

    @Before
    public void setup() {
        database = new BulletZoneData();
    }

    @After
    public void cleanup(){
        database.refresh();
    }


    @Test
    /**
     * This Will test how the server behaves when a user successfully logs in.
     */
    public void test_AccountController_Login_Success() {
        //BulletZoneData database = new BulletZoneData();

        GameUser validUser = database.users.createUser("Jack","username","password");

        //when(dataRepository.validateUser("username","password",false)).thenReturn(Optional.ofNullable(validUser));
        // Invoke the login method
        ResponseEntity<LongWrapper> responseEntity = controller.login("username", "password");

        // Verify the response status code is OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verify the response body (assuming LongWrapper contains the user ID)
        //assert validUser != null;
        assert responseEntity.getBody() != null;
        //assertEquals(validUser.getId(), responseEntity.getBody().getResult());
    }



    @Test
    /**
     * This Will test how the server behaves when a user fails to logs in.
     */
    public void test_AccountController_Login_Failure() {

//        BulletZoneData database = new BulletZoneData();

        GameUser validUser = database.users.createUser("Jack","username1","password1");

        //when(dataRepository.validateUser("username","password",false)).thenReturn(Optional.empty());

        // Invoke the login method
        ResponseEntity<LongWrapper> responseEntity = controller.login("username", "password");

        // Verify the response status code is Unauthorized
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

    }


    @Test
    /**
     * This Will test how the server behaves when a user successfully creates an account.
     */
    public void test_AccountController_Register_Success() {

//        BulletZoneData database = new BulletZoneData();

        GameUser validUser = database.users.createUser("Jack","username","password");

        //when(dataRepository.validateUser("username","password",true)).thenReturn(Optional.ofNullable(validUser));

        // Invoke the login method
        ResponseEntity<BooleanWrapper> responseEntity = controller.register("username", "password");

        // Verify the response status code is CREATED
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // Verify the response body (assuming LongWrapper contains the user ID)
        assert validUser != null;
        assert responseEntity.getBody() != null;

        database.users.delete(validUser.getId());
    }


    @Test
    /**
     * This Will test how the server behaves when a user fails to create and Account.
     */
    public void test_AccountController_Register_Failure() {

//        BulletZoneData database = new BulletZoneData();

        GameUser validUser = database.users.createUser("Jack","username","password");
//
        //when(dataRepository.validateUser("username","password",true)).thenReturn(Optional.empty());

        // Invoke the login method
        ResponseEntity<BooleanWrapper> responseEntity = controller.register("username", "password");

        // Verify the response status code is UNAUTHORIZED
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        // Verify the response body (assuming LongWrapper contains the user ID)
    }

}