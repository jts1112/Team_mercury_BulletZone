//package edu.unh.cs.cs619.bulletzone.datalayer;
//
//import org.greenrobot.eventbus.EventBus;
//
//import edu.unh.cs.cs619.bulletzone.model.events.GameEvent;
//import edu.unh.cs.cs619.bulletzone.util.GameEventCollectionWrapper;
//import edu.unh.cs.cs619.bulletzone.util.GridWrapper;
//
//import org.junit.runner.RunWith;
//
//import static org.hamcrest.Matchers.hasItem;
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.notNullValue;
//import static org.hamcrest.Matchers.nullValue;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//
//import edu.unh.cs.cs619.bulletzone.datalayer.account.BankAccount;
//import edu.unh.cs.cs619.bulletzone.datalayer.item.GameItem;
//import edu.unh.cs.cs619.bulletzone.datalayer.item.GameItemContainer;
//import edu.unh.cs.cs619.bulletzone.datalayer.itemType.ItemCategory;
//import edu.unh.cs.cs619.bulletzone.datalayer.itemType.ItemType;
//import edu.unh.cs.cs619.bulletzone.datalayer.user.GameUser;
//import edu.unh.cs.cs619.bulletzone.datalayer.user.UserAssociation;
//
//import static org.junit.Assert.*;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//
//
//@RunWith(MockitoJUnitRunner.class)
//public class GridPollerTaskTest {
//
//    @Mock
//    private BulletZoneRestClient mockRestClient;
//
//    @Mock
//    private EventBus mockEventBus;
//
//    @InjectMocks
//    private GridPollerTask gridPollerTask;
//
//    @Before
//    public void setup() {
//        // Initialize mocks and inject them into gridPollerTask
//    }
//
//    @Test
//    public void testEventPosting() throws Exception {
//        // Given
//        GameEventCollectionWrapper mockEventCollection = new GameEventCollectionWrapper();
//        when(mockRestClient.events(anyLong())).thenReturn(mockEventCollection);
//
//        // When
//        gridPollerTask.doPoll();
//
//        // Then
//        verify(mockEventBus, atLeastOnce()).post(any(GameEvent.class));
//
//    }
//}
