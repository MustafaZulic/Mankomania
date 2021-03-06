package space.hypeo.networking.packages;

import space.hypeo.mankomania.player.PlayerSkeleton;

/**
 * This class is only a wrapper class for player to send over the network and invoke right action.
 */
public class PlayerHost extends PlayerSkeleton {

    /* NOTE: default constructor is required for network traffic */
    public PlayerHost() {
        super();
    }

    public PlayerHost(String nickname) {
        super(nickname);
    }

    public PlayerHost(PlayerSkeleton p) {
        super(p);
    }
}
