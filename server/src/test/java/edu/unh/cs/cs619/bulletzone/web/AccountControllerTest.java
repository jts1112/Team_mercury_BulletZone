package edu.unh.cs.cs619.bulletzone.web;

import static org.junit.Assert.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mock.*;
import static org.mockito.junit.MockitoJUnitRunner.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.xml.crypto.Data;

import edu.unh.cs.cs619.bulletzone.datalayer.BulletZoneData;
import edu.unh.cs.cs619.bulletzone.datalayer.core.Entity;
import edu.unh.cs.cs619.bulletzone.datalayer.user.GameUser;
import edu.unh.cs.cs619.bulletzone.repository.DataRepository;
import edu.unh.cs.cs619.bulletzone.util.BooleanWrapper;
import edu.unh.cs.cs619.bulletzone.util.LongWrapper;

@RunWith(MockitoJUnitRunner.class)
/**
 * This is a class that will Test the Creation and Authorization of users on the server side.
 */
public class AccountControllerTest {

    @Mock
    DataRepository dataRepository;


    @InjectMocks
    AccountController controller;


    @Test
    /**
     * This Will test how the server behaves when a user successfully logs in.
     */
    public void test_AccountController_Login_Success() {
        BulletZoneData database = new BulletZoneData();

        GameUser validUser = database.users.createUser("Jack","username","password");

        when(dataRepository.validateUser("username","password",true)).thenReturn(validUser);

        // Invoke the login method
        ResponseEntity<LongWrapper> responseEntity = controller.login("username", "password");

        // Verify the response status code
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verify the response body (assuming LongWrapper contains the user ID)
        assertEquals(validUser.getId(), responseEntity.getBody().getResult());
    }



    @Test
    /**
     * This Will test how the server behaves when a user fails to logs in.
     */
    public void test_AccountController_Login_Failure() {

    }


    @Test
    /**
     * This Will test how the server behaves when a user successfully creates an account.
     */
    public void test_AccountController_Register_Success() {

    }

    @Test
    /**
     * This Will test how the server behaves when a user fails to create and Account.
     */
    public void test_AccountController_Register_Failure() {

    }

}