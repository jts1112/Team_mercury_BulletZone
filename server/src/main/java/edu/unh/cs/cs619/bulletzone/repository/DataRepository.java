package edu.unh.cs.cs619.bulletzone.repository;

import edu.unh.cs.cs619.bulletzone.datalayer.account.BankAccount;
import edu.unh.cs.cs619.bulletzone.web.AccountController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import edu.unh.cs.cs619.bulletzone.datalayer.BulletZoneData;
import edu.unh.cs.cs619.bulletzone.datalayer.user.GameUser;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This class provides tailored access to objects that are needed by the REST API/Controller
 * classes. The idea is that it will interface with a BulletZoneData instance as well as
 * any other objects it needs to answer requests having to do with users, items, accounts,
 * permissions, and other things that are related to what is stored in the database.
 *
 * The convention is that actual objects will be returned by the DataRepository so that internal
 * objects can make effective use of the results as well as the Controllers. This means that
 * all API/Controller classes will need to translate these objects into the strings they need
 * to communicate information back to the caller.
 */
//Note that the @Component annotation below causes an instance of a DataRepository to be
//created and used for the Controller classes in the "web" package.
@Component
public class DataRepository {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);
    private BulletZoneData bzdata;

    DataRepository() {
        //TODO: Replace database name, username, and password with what's appropriate for your group
//        String url = "jdbc:mysql://stman1.cs.unh.edu:3306/cs6190";
//        String username = "mdp";
//        String password = "Drag56kes";
//
//        bzdata = new BulletZoneData(url, username, password);
        bzdata = new BulletZoneData(); //just use in-memory database
    }

    /**
     * Stub for a method that would create a user or validate the user. [You don't have
     * to do it this way--feel free to make other methods if you like!]
     * @param username Username for the user to create or validate
     * @param password Password for the user
     * @param create true if the user should be created, or false otherwise
     * @return GameUser corresponding to the username/password if successful, null otherwise
     */
    public Optional<GameUser> validateUser(String username, String password, String ipAddress, boolean create) {
        //TODO: something that invokes users.createUser(name, password) or
        //      users.validateLogin(name, password) as appropriate, maybe does other bookkeeping

        if (create) {
            GameUser returnedUser = bzdata.users.createUser(username, username, password, ipAddress);
            if (returnedUser != null) {
                if (!setupInitialBankAccount(returnedUser)) {
                    log.warn("Failed to perform initial bank account for user: " + returnedUser.getUsername());
                    if (!setupInitialBankAccount(returnedUser)) {
                        // we've failed initial setup on the user's bank account too many times and have thus failed to
                        // create the user, attempting to delete the user and returning nothing
                        String tempUsername = returnedUser.getUsername();
                        if (bzdata.users.delete(returnedUser.getId())) {
                            log.warn("Failed to create user: " + tempUsername + ", successfully deleted");
                        } else {
                            log.error("Failed to create and cleanup user: " + tempUsername);
                        };
                        return Optional.empty();
                    }
                }
                return Optional.of(returnedUser);
            } else {
                return Optional.empty();
            }
        } else {
            GameUser returnedUser = bzdata.users.validateLogin(username, password);
            if (returnedUser != null) {
                return Optional.of(returnedUser);
            } else {
                return Optional.empty();
            }
        }
    }

    public Optional<GameUser> validateUser(String username, String password, boolean create) {
        //TODO: something that invokes users.createUser(name, password) or
        //      users.validateLogin(name, password) as appropriate, maybe does other bookkeeping

        if (create) {
            GameUser returnedUser = bzdata.users.createUser(username, username, password);
            if (returnedUser != null) {
                if (!setupInitialBankAccount(returnedUser)) {
                    log.warn("Failed to perform initial bank account for user: " + returnedUser.getUsername());
                    if (!setupInitialBankAccount(returnedUser)) {
                        // we've failed initial setup on the user's bank account too many times and have thus failed to
                        // create the user, attempting to delete the user and returning nothing
                        String tempUsername = returnedUser.getUsername();
                        if (bzdata.users.delete(returnedUser.getId())) {
                            log.warn("Failed to create user: " + tempUsername + ", successfully deleted");
                        } else {
                            log.error("Failed to create and cleanup user: " + tempUsername);
                        };
                        return Optional.empty();
                    }
                }
                return Optional.of(returnedUser);
            } else {
                return Optional.empty();
            }
        } else {
            GameUser returnedUser = bzdata.users.validateLogin(username, password);
            if (returnedUser != null) {
                return Optional.of(returnedUser);
            } else {
                return Optional.empty();
            }
        }
    }

    /**
     * Only for use during user creation
     * steps:
     *   1) creates a bank account
     *   2) sets the user as account owner
     *   3) sets account balance to 1000
     * on failing any of the above steps the bank account is deleted and the method returns false to indicate failure
     * @param user GameUser receiving their first account
     * @return boolean indicating success
     */
    private boolean setupInitialBankAccount(GameUser user) {
        // As duplicate users won't make it to this step of account creation we don't need to check that the user has no
        // bank accounts as long as this method is only used here which is important as checking all accounts is expensive
        BankAccount bankAccount = bzdata.accounts.create();
        if (bankAccount != null) {
            bankAccount.setOwner(user);
            user.setAccountId(bankAccount.getId());
            boolean balanceSet = bzdata.accounts.modifyBalance(bankAccount, 1000);
            if (balanceSet) {
                log.debug("successfully initialized user's bank account with id: " + bankAccount.getId());
                return true;
            } else {
                if (bzdata.accounts.delete(bankAccount.getId())) {
                    log.warn("Failed to initialize bank account for user: " + user.getUsername() + ", successfully deleted");
                } else {
                    log.error("Failed to initialize and cleanup bank account " + bankAccount.getId() + " for user: " + bankAccount);
                };
                return false;
            }
        } else {
            log.error("Failed to create bank account for user: " + user.getUsername());
            return false;
        }
    }

    public double getBankBalance(GameUser user) {
        return bzdata.accounts.getAccount(user.getAccountId()).getBalance();
    }

    public boolean modifyBalance(int accountId, double amount) {
        BankAccount account = bzdata.accounts.getAccount(accountId);
        if (account != null) {
            return bzdata.accounts.modifyBalance(account, amount);
        }
        return false;
    }

    public GameUser getUser(String ip) {
        Stream<GameUser> stream = bzdata.users.getUsers().stream();
        Stream<GameUser> filteredStream = stream.filter(u -> u.getIpAddress().equals(ip));
        Optional<GameUser> user = filteredStream.findFirst();
        return user.orElse(null);
    }
}
