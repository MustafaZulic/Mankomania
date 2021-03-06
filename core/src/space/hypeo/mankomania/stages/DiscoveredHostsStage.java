package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.minlog.Log;

import java.net.InetAddress;
import java.util.List;

import space.hypeo.mankomania.factories.ButtonFactory;
import space.hypeo.mankomania.player.PlayerManager;
import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;

public class DiscoveredHostsStage extends Stage {

    private List<InetAddress> foundHosts;

    /**
     * Creates new instance.
     * @param stageManager
     * @param viewport
     * @param stageFactory
     * @param playerManager
     */
    public DiscoveredHostsStage(StageManager stageManager, Viewport viewport, StageFactory stageFactory, PlayerManager playerManager) {
        super(viewport);
        
        foundHosts = playerManager.discoverHosts();

        // Create actors.
        RectangleActor background = new RectangleActor(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Label title = new Label("Discovered Hosts", skin);
        title.setFontScaleX(2);
        title.setFontScaleY(2);

        // show avaliable hosts as buttons in table
        Table layout = new Table();

        layout.setWidth(this.getWidth());
        layout.align(Align.center);
        layout.setPosition(0, this.getHeight() - 200);
        layout.padTop(50);
        layout.add(title).width(300).height(100);
        layout.row();

        Log.info("Discovered NetworkRegistration: Host-List contains:");

        if( foundHosts != null && ! foundHosts.isEmpty() ) {

            int index = 1;
            for (InetAddress hostAddr : foundHosts) {
                Log.info("  " + index + ". host: " + hostAddr);

                Button btnHost = new TextButton(index + ". " + hostAddr, skin);

                btnHost.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        Log.info("Try to connect to host " + hostAddr + "...");

                        playerManager.connectToHost(hostAddr);
                    }

                });

                layout.add(btnHost).width(300).height(100);
                layout.row();

                index++;
            }

        } else {
            Log.info("No hosts found!");

            Button btnNoHost = new TextButton("No Hosts found", skin);

            btnNoHost.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    stageManager.remove(DiscoveredHostsStage.this);
                    stageManager.push(stageFactory.getDiscoveredHostsStage(playerManager));
                }

            });

            layout.add(btnNoHost).width(300).height(100);
            layout.row();
        }

        Button btnBack = ButtonFactory.getButton("common/back_to_main_menu.png", "common/back_to_main_menu_clicked.png");
        btnBack.setTransform(true);
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.remove(DiscoveredHostsStage.this);
            }
        });

        layout.row();
        layout.add(btnBack).width(400).height(125);
        layout.row();

        // Set up background.
        background.setColor(237f/255f, 30f/255f, 121f/255f, 1f);

        // Add actors.
        this.addActor(background);
        this.addActor(layout);

    }
}
