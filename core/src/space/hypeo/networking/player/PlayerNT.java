package space.hypeo.networking.player;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.minlog.Log;

import space.hypeo.mankomania.IDeviceStateSubscriber;
import space.hypeo.mankomania.player.IPlayerConnector;
import space.hypeo.mankomania.player.PlayerManager;
import space.hypeo.mankomania.player.PlayerSkeleton;
import space.hypeo.networking.endpoint.IClientConnector;
import space.hypeo.networking.endpoint.IEndpoint;
import space.hypeo.networking.endpoint.IHostConnector;
import space.hypeo.networking.network.Role;

/**
 * This class is a wrapper class for an endpoint.
 * An endpoint can be server or client.
 * The class represents the network connection of the current player and
 * communicates with other endpoints.
 */
public class PlayerNT implements IPlayerConnector, IDeviceStateSubscriber {
    private final PlayerManager playerManager;
    private final IEndpoint endpoint;

    public PlayerNT(final PlayerManager playerManager, final IEndpoint endpoint) {
        this.playerManager = playerManager;
        this.endpoint = endpoint;
    }

    public IEndpoint getEndpoint() {
        return endpoint;
    }

    @Override
    public void endTurn() {
        throw new UnsupportedOperationException("PlayerNT: Method 'endturn()' not implemented yet!");
    }

    @Override
    public void onPause() {
        endpoint.stop();
    }

    @Override
    public void onStop() {
        endpoint.stop();
    }

    public void stop() {
        endpoint.stop();
    }

    public void close() {
        endpoint.close();
    }

    @Override
    public void broadCastLobby() {
        endpoint.broadCastLobby();
    }

    public void kickPlayerFromLobby(PlayerSkeleton playerToKick) {
        if(playerManager.getRole() == Role.HOST) {
            Log.info("PlayerNT: Try to kick player " + playerToKick);
            IHostConnector host = (IHostConnector) endpoint;
            host.sendOrderToCloseConnection(playerToKick);
        }
    }

    public void startGame() {
        if(playerManager.getRole() == Role.HOST) {
            Log.info("PlayerNT: Start the Game");
            IHostConnector host = (IHostConnector) endpoint;
            host.startGame();
        }
    }

    @Override
    public void sendHorseRaceResult(String horseName) {
        endpoint.sendHorseRaceResult(horseName);
    }

    @Override
    public void sendRouletteResult(int slotId) {
        endpoint.sendRouletteResult(slotId);
    }

    /**
     * Connects the client to the host.
     * @param hostAddr IP address of host
     */
    public void connectToHost(InetAddress hostAddr) {
        Role role = playerManager.getRole();

        if(role == Role.CLIENT) {
            /* NOTE: it is important to show the LobbyStage before update it! */
            playerManager.showLobbyStage();

            IClientConnector client = (IClientConnector) endpoint;
            client.connectToHost(hostAddr);
            Log.info(role + ": PlayerNT: Connect to host " + hostAddr);

        } else {
            Log.info(role + ": PLayerNT: Can NOT connect to myself!");
        }
    }

    @Override
    public void disconnect() {
        endpoint.disconnect();
        if(playerManager.getRole() == Role.HOST) {
            /* wait till every client has diconnected */
            while (playerManager.getLobby().size() > 1) { /* LOOP BODY EMPTY */ }
            Log.info("Now lobby is empty!");
        }
    }

    /**
     * Discovers the network for available hosts.
     * @return list of IP addresses
     */
    public List<InetAddress> discoverHosts() {
        Role role = playerManager.getRole();

        if(role == Role.CLIENT) {
            Log.info(role + ": Discover available hosts in network...");
            IClientConnector client = (IClientConnector) endpoint;
            return client.discoverHosts();
        } else {
            Log.info(role + ": No need for discover hosts!");
            return new ArrayList<>();
        }
    }


}
