package hu.tamasruszka.spo.optimizer.service;

import hu.tamasruszka.spo.optimizer.model.Room;
import hu.tamasruszka.spo.optimizer.model.StructureInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This service contains all the business logic for the cleaner REST api endpoints
 */
@Service
public class CleanerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CleanerService.class);

    /**
     * This is the main method for optimizing room capacities. With the given structure information object it will
     * calculate the optimal amount of janitors based on their capacity values. At least one Senior worker needs to
     * be assigned to every room to supervise the junior janitors work.
     *
     * @param information The incoming information object about the workers and the rooms
     * @return The list of the optimized rooms
     * @throws NullPointerException if the input StructureInformation parameter is null
     * @throws RuntimeException     if the senior capacity is smaller or equals than the junior
     * @throws RuntimeException     if one of the capacity values are smaller or equals than zero
     * @throws RuntimeException     if the rooms attribute is empty in the input StructureInformation parameter
     */
    public List<Room> doOptimization(StructureInformation information) {
        if (information == null) {
            throw new NullPointerException("StructureInformation cannot be null when optimizing the workers count!");
        }

        if (information.getSenior() <= information.getJunior()) {
            throw new RuntimeException("The given worker capacity values are wrong! Senior workers needs to have more capacities than juniors!");
        }

        if (information.getSenior() <= 0) {
            throw new RuntimeException("The given senior capacity value is not valid!");
        }

        if (information.getJunior() <= 0) {
            throw new RuntimeException("The given junior capacity value is not valid!");
        }

        if (information.getRooms().size() == 0) {
            throw new RuntimeException("No room data provided for optimization!");
        }

        LOGGER.debug("Starting optimization process with the given structure information={}", information);

        List<Room> optimizedRooms = new ArrayList<>();

        // calculating the optimized worker numbers for each room
        for (int roomSize : information.getRooms()) {
            int seniorCount = roomSize / information.getSenior() + 1;
            int juniorCount = 0;
            int currentOvercapacity = getOvercapacity(information, seniorCount, juniorCount, roomSize);
            int minOvercapacity = currentOvercapacity;

            Room room = new Room(seniorCount, juniorCount);

            // One senior needed in every team, so it stops when it reach 1 senior count
            while (seniorCount > 0) {

                // If it finds a more optimal overcapacity, then it update the room variable
                if (minOvercapacity > currentOvercapacity) {
                    minOvercapacity = currentOvercapacity;

                    room.setSenior(seniorCount);
                    room.setJunior(juniorCount);
                }

                // If it reaches the absolute best value (0, no overcapacity), then stops the iteration. (performance)
                if (currentOvercapacity == 0) {
                    break;
                }

                seniorCount--;
                juniorCount++;

                currentOvercapacity = getOvercapacity(information, seniorCount, juniorCount, roomSize);

                // After decreasing the senior count it maybe need more than one junior to fill the rooms capacity
                while (currentOvercapacity < 0) {
                    juniorCount++;

                    currentOvercapacity = getOvercapacity(information, seniorCount, juniorCount, roomSize);
                }
            }

            optimizedRooms.add(room);
        }

        LOGGER.debug("Optimization successfully finished, result={}", optimizedRooms);

        return optimizedRooms;
    }

    /**
     * Getting overcapacity value based on the current janitor setup. If the number is negative or zero then there is
     * no overcapacity, need to add more worker to the room.
     *
     * @param information  The endpoint's incoming information, it has the capacity values
     * @param seniorCount  Current count of the senior worker
     * @param juniorCount  Current count of the junior worker
     * @param roomCapacity The maximum capacity of the room
     * @return The overcapacity value
     * @throws NullPointerException If the input StructureInformation parameter is null
     */
    private int getOvercapacity(StructureInformation information, int seniorCount, int juniorCount, int roomCapacity) {
        if (information == null) {
            throw new NullPointerException("StructureInformation cannot be null when calculating the overcapacity value!");
        }

        return seniorCount * information.getSenior() + juniorCount * information.getJunior() - roomCapacity;
    }
}
