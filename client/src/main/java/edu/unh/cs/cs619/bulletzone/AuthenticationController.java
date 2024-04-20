package edu.unh.cs.cs619.bulletzone;

import android.util.Log;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import edu.unh.cs.cs619.bulletzone.events.GameData;
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
        GameData gameData = GameData.getInstance();
        try {
            LongWrapper result = future.get(10, TimeUnit.SECONDS);
            if (result == null) {
                return -1;
            }
            gameData.setPlayerCredits(result.getResult2());
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
        Future<BooleanWrapper> future = executorService.submit(() -> restClient.register(username, password));

        try {
            BooleanWrapper result = future.get(10, TimeUnit.SECONDS);
            if (result == null) {
                return true;
            }
        } catch (TimeoutException e) {
            Log.d("AuthenticationActvity", "Registration operation timed out");
            future.cancel(true);
            return false;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
        return false;
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
