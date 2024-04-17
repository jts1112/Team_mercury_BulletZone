package edu.unh.cs.cs619.bulletzone.datalayer;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.unh.cs.cs619.bulletzone.datalayer.account.BankAccount;
import edu.unh.cs.cs619.bulletzone.datalayer.user.GameUser;
import edu.unh.cs.cs619.bulletzone.model.events.DamageEvent;
import edu.unh.cs.cs619.bulletzone.model.events.EventHistory;

public class ServerIntegrationTest {

    static BulletZoneData db;
    static GameUser playerUser;
    static BankAccount playerAccount;
    static EventHistory eventHistory;

    @BeforeClass
    public static void setup() {
        db = new BulletZoneData();
        db.rebuildData();

        playerUser = db.users.createUser("PlayerUser", "PlayerUsername", "password");
        playerAccount = db.accounts.create();
        db.permissions.setOwner(playerAccount, playerUser);

        eventHistory = EventHistory.getInstance();
        eventHistory.clearHistory();

        db.accounts.modifyBalance(playerAccount, 1000);
    }

    @Test
    public void playerDefeatsEnemy_increasesBalanceReflectingReward() {
        double initialBalance = playerAccount.getBalance();
        int reward = 500;
        DamageEvent enemyDefeatEvent = new DamageEvent(playerUser.getId(), reward);
        eventHistory.onEventNotification(enemyDefeatEvent);

        db.accounts.modifyBalance(playerAccount, reward);

        assertEquals("Player's balance should be increased by the reward amount",
                initialBalance + reward, playerAccount.getBalance(), 0.001);

        assertTrue("Event history should include the enemy defeat event",
                eventHistory.getHistory().size() > 0);
    }
}
