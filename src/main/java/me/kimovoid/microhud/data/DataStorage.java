package me.kimovoid.microhud.data;

public class DataStorage {

    /**
     * This is where the client stores simple data,
     * mostly used for client-server compatibility.
     */
    public static final DataStorage INSTANCE = new DataStorage();

    public int ping = 0;
    public long seed = 0;

    public int serverMemUsed = -1;
    public int serverMemAllocated = -1;
    public int serverMemMax = -1;

    public void resetStorage() {
        this.ping = 0;
        this.seed = 0;

        this.serverMemUsed = -1;
        this.serverMemAllocated = -1;
        this.serverMemMax = -1;
    }
}