package space.hypeo.networking.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;

import space.hypeo.networking.IClientConnector;
import space.hypeo.networking.IPlayerConnector;
import space.hypeo.networking.PlayerInfo;
import space.hypeo.networking.network.Network;
import space.hypeo.networking.packages.PingRequest;
import space.hypeo.networking.packages.PingResponse;

public class MClient implements IPlayerConnector, IClientConnector {

    private com.esotericsoftware.kryonet.Client client;

    private PlayerInfo hostInfo = null;
    private List<InetAddress> discoveredHosts = null;
    private InetAddress connectedToHost = null;

    private long startPingRequest = 0;

    private class ClientListener extends Listener {

        /**
         * If has connected to host
         * @param connection
         */
        @Override
        public void connected(Connection connection) {
            super.connected(connection);

            hostInfo = new PlayerInfo(connection, Network.Role.host);
            connectedToHost = connection.getRemoteAddressTCP().getAddress();
        }

        /**
         * If has diconnected from
         * @param connection
         */
        @Override
        public void disconnected(Connection connection) {
            super.disconnected(connection);
            hostInfo = null;
        }

        /**
         * If has reveived a package from host
         * @param connection
         * @param object
         */
        @Override
        public void received(Connection connection, Object object) {
            super.received(connection, object);

            if( object instanceof PingResponse) {
                PingResponse pingResponse = (PingResponse) object;
                System.out.println("Ping time [ms] = " + (startPingRequest - pingResponse.getTime()));
            }
        }
    }

    @Override
    public boolean joinGame(String playerID) {
        return false;
    }

    @Override
    public void startClient() {

        String firstHostFound = "";

        client = new Client();
        client.start();

        // TODO: execute discoverHosts() from outside?
        //this.discoverHosts();


    }

    @Override
    public List<InetAddress> discoverHosts() {
        discoveredHosts = client.discoverHosts(Network.PORT_NO, Network.TIMEOUT_MS);
        return discoveredHosts;
    }

    public void connectToHost(InetAddress hostAddress) {

        if( client != null && discoveredHosts != null && discoveredHosts.contains(hostAddress) ) {
            try {
                client.connect(Network.TIMEOUT_MS, hostAddress.getHostAddress(), Network.PORT_NO);
            } catch (IOException e) {
                e.printStackTrace();
            }

            client.addListener(new ClientListener());

            Network.register(client);

            pingServer();

            // TODO: wait for response
            /*while( true ) {
            }*/
        }
    }

    /**
     * Sends a PingRequest to server.
     */
    public void pingServer() {
        PingRequest pingRequest = new PingRequest();
        startPingRequest = pingRequest.getTime();

        client.sendTCP(pingRequest);
    }

    @Override
    public void changeBalance(String playerID, int amount) {

    }

    @Override
    public void movePlayer(String playerID, int position) {

    }

    @Override
    public void endTurn() {

    }

    @Override
    public int getPlayerBalance(String playerID) {
        return 0;
    }

    @Override
    public int getPlayerPosition(String playerID) {
        return 0;
    }

    @Override
    public String getCurrentPlayerID() {
        return null;
    }

    @Override
    public HashMap<String, PlayerInfo> registeredPlayers() {
        return null;
    }
}