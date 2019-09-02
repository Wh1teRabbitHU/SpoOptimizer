package hu.tamasruszka.spo.optimizer.model;

/**
 * Room capacity object. It contains the count number of the optimal amount of workers needed for a specific room based
 * on their skill levels
 */
@SuppressWarnings("unused")
public class Room {

    /**
     * Senior level worker count
     */
    private int senior;

    /**
     * Junior level worker count
     */
    private int junior;

    /**
     * Only needed for object serialization using the Jackson (JSON) library
     */
    public Room() {
    }

    public Room(int senior, int junior) {
        this.senior = senior;
        this.junior = junior;
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
        return "Room{" +
                "senior=" + senior +
                ", junior=" + junior +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (senior != room.senior) return false;
        return junior == room.junior;
    }

    @Override
    public int hashCode() {
        int result = senior;
        result = 31 * result + junior;
        return result;
    }
}
