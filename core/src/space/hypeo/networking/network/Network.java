package space.hypeo.networking.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import space.hypeo.networking.packages.PingRequest;
import space.hypeo.networking.packages.PingResponse;

/**
 * The class Network is a auxiliary class
 * to keep things common to both the client and server.
 */
public class Network {

    // communication port
    public static final int PORT_NO = 54555;

    // maximum number of players
    public static final int MAX_PLAYER = 5;

    // max time in milli-second to try to connect
    public static final int TIMEOUT_MS = 5000;

    public enum Role { host, client };

    /**
     * Register objects that are going to be sent over the network.
     */
    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(PingRequest.class);
        kryo.register(PingResponse.class);
    }

}