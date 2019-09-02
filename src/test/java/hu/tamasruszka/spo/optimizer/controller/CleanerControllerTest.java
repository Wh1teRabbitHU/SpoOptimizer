package hu.tamasruszka.spo.optimizer.controller;

import hu.tamasruszka.spo.optimizer.model.Room;
import hu.tamasruszka.spo.optimizer.model.StructureInformation;
import hu.tamasruszka.spo.optimizer.model.http.HttpResponseObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test methods for testing the CleanerController API endpoints
 */
public class CleanerControllerTest extends BaseControllerTest {

    /**
     * Testing the optimization endpoint. Using the test data provided to the task description
     */
    @Test
    public void testOptimizationTask() {
        String endpointUrl = getServerRootUrl() + "/cleaner/optimization";
        StructureInformation information1 = new StructureInformation();
        StructureInformation information2 = new StructureInformation();

        information1.addRooms(35, 21, 17, 28);
        information1.setSenior(10);
        information1.setJunior(6);

        information2.addRooms(24, 28);
        information2.setSenior(11);
        information2.setJunior(6);

        List<Room> expectedRooms1 = new ArrayList<>();
        List<Room> expectedRooms2 = new ArrayList<>();

        expectedRooms1.add(new Room(3, 1));
        expectedRooms1.add(new Room(1, 2));
        expectedRooms1.add(new Room(2, 0));
        expectedRooms1.add(new Room(1, 3));

        expectedRooms2.add(new Room(2, 1));
        expectedRooms2.add(new Room(2, 1));

        HttpResponseObject<List<Room>> responseObject1 = postJsonReturnsList(endpointUrl, information1, Room.class);
        HttpResponseObject<List<Room>> responseObject2 = postJsonReturnsList(endpointUrl, information2, Room.class);

        List<Room> calculatedRooms1 = responseObject1.getResponse();
        List<Room> calculatedRooms2 = responseObject2.getResponse();

        Assert.assertEquals(calculatedRooms1.size(), expectedRooms1.size());
        Assert.assertEquals(calculatedRooms2.size(), expectedRooms2.size());

        for (int i = 0; i < calculatedRooms1.size(); i++) {
            Room calculatedRoom = calculatedRooms1.get(i);
            Room expectedRoom = expectedRooms1.get(i);

            Assert.assertEquals(calculatedRoom, expectedRoom);
        }

        for (int i = 0; i < calculatedRooms2.size(); i++) {
            Room calculatedRoom = calculatedRooms2.get(i);
            Room expectedRoom = expectedRooms2.get(i);

            Assert.assertEquals(calculatedRoom, expectedRoom);
        }
    }

    /**
     * Testing the optimization endpoint. Checking for error handling
     */
    @Test
    public void testOptimizationErrorHandling() {
        String endpointUrl = getServerRootUrl() + "/cleaner/optimization";
        StructureInformation information1 = new StructureInformation();
        StructureInformation information2 = new StructureInformation();
        StructureInformation information3 = new StructureInformation();
        StructureInformation information4 = new StructureInformation();

        information1.addRooms(35, 21, 17, 28);
        information1.setSenior(1);
        information1.setJunior(10);

        information2.addRooms(24, 28);
        information2.setSenior(-1);
        information2.setJunior(-2);

        information3.addRooms(24, 28);
        information3.setSenior(11);
        information3.setJunior(-1);

        information4.setSenior(11);
        information4.setJunior(6);

        HttpResponseObject<List<Room>> responseObject1 = postJsonReturnsList(endpointUrl, information1, Room.class);
        HttpResponseObject<List<Room>> responseObject2 = postJsonReturnsList(endpointUrl, information2, Room.class);
        HttpResponseObject<List<Room>> responseObject3 = postJsonReturnsList(endpointUrl, information3, Room.class);
        HttpResponseObject<List<Room>> responseObject4 = postJsonReturnsList(endpointUrl, information4, Room.class);

        Assert.assertEquals(responseObject1.getStatusCode(), 500);
        Assert.assertEquals(responseObject2.getStatusCode(), 500);
        Assert.assertEquals(responseObject3.getStatusCode(), 500);
        Assert.assertEquals(responseObject4.getStatusCode(), 500);
    }

}
