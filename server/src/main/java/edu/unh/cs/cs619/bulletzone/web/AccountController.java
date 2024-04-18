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
import edu.unh.cs.cs619.bulletzone.util.BooleanWrapper;
import edu.unh.cs.cs619.bulletzone.util.LongWrapper;

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
    public AccountController(DataRepository repo) {
        this.data = repo;
    }

    /**
     * Handles a PUT request to register a new user account
     *
     * @param name The username
     * @param password The password
     * @return a response w/ success boolean
     */
    @RequestMapping(method = RequestMethod.PUT, value = "register/{name}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<BooleanWrapper> register(@PathVariable String name, @PathVariable String password)
    {
        // Log the request
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
    public ResponseEntity<LongWrapper> login(@PathVariable String name, @PathVariable String password)
    {
        // Log the request
        log.debug("Login '" + name + "' with password '" + password + "'");
        // Return the response (return user ID if valid login), -1 if not
        Optional<GameUser> userData = data.validateUser(name, password, false);
        GameUser user;
        int bal;
        if (userData.isPresent()) {
            user = userData.get();
            bal = (int) data.getBankBalance(user);
        } else {
            bal = 1000;
        }
        return userData.map(gameUser -> new ResponseEntity<>(new LongWrapper(gameUser.getId(), bal, 0),
                        HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new LongWrapper(-1),
                        HttpStatus.UNAUTHORIZED));
    }

}
