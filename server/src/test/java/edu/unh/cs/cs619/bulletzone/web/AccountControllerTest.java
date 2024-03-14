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