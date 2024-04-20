package edu.unh.cs.cs619.bulletzone.web;
import edu.unh.cs.cs619.bulletzone.datalayer.user.GameUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.unh.cs.cs619.bulletzone.repository.DataRepository;
import edu.unh.cs.cs619.bulletzone.repository.DataRepositoryFactory;
import edu.unh.cs.cs619.bulletzone.util.BooleanWrapper;
import edu.unh.cs.cs619.bulletzone.util.LongWrapper;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

/**
 * Handles account creation.
 * Contains register and login endpoints.
 */
@RestController
@RequestMapping(value = "/games/account")
public class AccountController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final DataRepository data;

    @Autowired
    public AccountController() {
        this.data = DataRepositoryFactory.getInstance();
    }

    /**
     * Handles a PUT request to register a new user account
     *
     * @param name The username
     * @param password The password
     * @return a response w/ success boolean
     */
    @RequestMapping(method = RequestMethod.PUT, value = "register/{name}/{password}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<BooleanWrapper> register(@PathVariable String name,
                                                   @PathVariable String password,
                                                   HttpServletRequest request) {
        log.debug("Register '" + name + "' with password '" + password + "'");

        String ipAddress = request.getRemoteAddr();

        Optional<GameUser> user = data.validateUser(name, password, ipAddress, true);
        if (user.isPresent()) {
            return new ResponseEntity<>(new BooleanWrapper(true),
                    HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new BooleanWrapper(false),
                    HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Handles a PUT request to login a user
     *
     * @param name The username
     * @param password The password
     * @return a response w/ the user ID with HttpStatus.OK if valid (or -1 with HttpStatus.UNAUTHORIZED if invalid)
     */
    @RequestMapping(method = RequestMethod.PUT, value = "login/{name}/{password}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<LongWrapper> login(
            @PathVariable String name, @PathVariable String password)
    {
        log.debug("Login '" + name + "' with password '" + password + "'");
        Optional<GameUser> userData = data.validateUser(name, password, false);
        GameUser user;
        int accountBalance;
        if (userData.isPresent()) {
            user = userData.get();
            accountBalance = (int) data.getBankBalance(user);
            System.out.println("User " + user.getId() + " has a balance of " + accountBalance);
        } else {
            accountBalance = 1000;
            System.out.println("User not found, creating new user with balance of " + accountBalance);
        }
        Optional<GameUser> optionalGameUser;
        optionalGameUser = userData;

        if (optionalGameUser.isPresent()) {
            GameUser gameUser = optionalGameUser.get();
            LongWrapper longWrapper = new LongWrapper(gameUser.getId(), accountBalance, 0);
            ResponseEntity<LongWrapper> responseEntity;
            responseEntity = new ResponseEntity<>(longWrapper, HttpStatus.OK);
            return responseEntity;
        } else {
            LongWrapper longWrapper = new LongWrapper(-1);
            ResponseEntity<LongWrapper> responseEntity;
            responseEntity = new ResponseEntity<>(longWrapper, HttpStatus.UNAUTHORIZED);
            return responseEntity;
        }
    }


    /**
     * Handles a PUT request to register a new user account
     *
     * @param name The username
     * @param password The password
     * @return a response w/ success boolean
     */
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<BooleanWrapper> register(@PathVariable String name,
                                                   @PathVariable String password) {
        log.debug("Register '" + name + "' with password '" + password + "'");

        // Return the response (true if account created)
        if (data.validateUser(name, password, true).isPresent()) {
            return new ResponseEntity<BooleanWrapper>(new BooleanWrapper(true),
                    HttpStatus.CREATED);
        } else {
            return new ResponseEntity<BooleanWrapper>(new BooleanWrapper(false),
                    HttpStatus.UNAUTHORIZED);
        }
    }

}
