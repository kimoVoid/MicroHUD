package me.kimovoid.microhud.data;

public class DataStorage {

    /**
     * This is where the client stores simple data,
     * mostly used for client-server compatibility.
     */
    public static final DataStorage INSTANCE = new DataStorage();

    public int ping = 0;
    public long seed = 0;

    public void resetStorage() {
        this.ping = 0;
        this.seed = 0;
    }
}
