package edu.unh.cs.cs619.bulletzone;

import android.content.Context;
import android.util.Log;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;
import org.junit.rules.Timeout;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import edu.unh.cs.cs619.bulletzone.rest.BulletZoneRestClient;
import edu.unh.cs.cs619.bulletzone.util.BooleanWrapper;
import edu.unh.cs.cs619.bulletzone.util.LongWrapper;

@EBean
public class AuthenticationController {
    @RestService
    BulletZoneRestClient restClient;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();



    /**
     * Constructor for InputHandler
     * [Feel free to add arguments and initialization as needed]
     */
    public AuthenticationController() {
        //note: any work that needs to be done with an annotated item like @RestService or @Bean
        //      will not work here, but should instead go into a method below marked
        //      with the @AfterInject annotation.
    }

    @AfterInject
    public void afterInject() {
        //Any initialization involving components annotated with things like @RestService or @Bean
        //goes here.
    }
    
    /**
     * Uses restClient to login.
     *
     * @param username Username provided by user.
     * @param password Password for account provided by user.
     */
    public long login(String username, String password) {
        Future<LongWrapper> future = executorService.submit(() -> restClient.login(username, password));

        try {
            LongWrapper result = future.get(10, TimeUnit.SECONDS);
            if (result == null) {
                return -1;
            }
            return result.getResult();
        } catch (TimeoutException e) {
            Log.d("AuthenticatonActivty", "Loging operation timed out");
            future.cancel(true);
            return -2;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Uses restClient to register.
     *
     * @param username New username provided by user.
     * @param password Password for new account provided by user.
     */
    public boolean register(String username, String password) {
        BooleanWrapper result = restClient.register(username, password);
        if (result == null) {
            return false;
        }
        return result.isResult();
    }

    /**
     * Helper for testing
     *
     * @param restClientPassed tested restClient
     */
    public void initialize(BulletZoneRestClient restClientPassed) {
        restClient = restClientPassed;
    }

}
