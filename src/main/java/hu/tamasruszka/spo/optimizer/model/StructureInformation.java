package hu.tamasruszka.spo.optimizer.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Input parameter's class for the cleaner optimization endpoint. It contains the size of the rooms and the capacity
 * numbers based on the workers skill level.
 */
public class StructureInformation {

    /**
     * Rooms size list. Needed for the optimization process
     */
    private final List<Integer> rooms = new ArrayList<>();

    /**
     * Capacity number for the senior skill level
     */
    private int senior;

    /**
     * Capacity number for the junior skill level
     */
    private int junior;

    public List<Integer> getRooms() {
        return rooms;
    }

    /**
     * Adding rooms to the inner rooms attribute. null values are excluded
     *
     * @param roomSize Various length room size input parameter
     */
    public void addRooms(Integer... roomSize) {
        rooms.addAll(Arrays
                .stream(roomSize)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new))
        );
    }

    public int getSenior() {
        return senior;
    }

    public void setSenior(int senior) {
        this.senior = senior;
    }

    public int getJunior() {
        return junior;
    }

    public void setJunior(int junior) {
        this.junior = junior;
    }

    @Override
    public String toString() {
        return "StructureInformation{" +
                "rooms=" + rooms +
                ", senior=" + senior +
                ", junior=" + junior +
                '}';
    }
}
