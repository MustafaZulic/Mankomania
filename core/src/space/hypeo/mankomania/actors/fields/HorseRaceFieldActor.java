package space.hypeo.mankomania.actors.fields;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.map.DetailActor;
import space.hypeo.mankomania.actors.player.PlayerActor;

/**
 * Created by manuelegger on 23.05.18.
 */

public class HorseRaceFieldActor extends FieldActor {
    private StageFactory stageFactory;
    private StageManager stageManager;

    /**
     * @param x             X position of the Actor.
     * @param y             Y position of the Actor.
     * @param width         Width of the Actor
     * @param height        Height of the Actor
     * @param price         Price of this field.
     * @param texture       Texture that represents the field on screen.
     * @param detailTexture Detail texture of this field.
     * @param detailActor   The image is shown inside, and replaced by detailTexture.
     */
    public HorseRaceFieldActor(float x, float y, float width, float height, int price, Texture texture, Texture detailTexture, DetailActor detailActor, StageFactory stageFactory, StageManager stageManager) {
        super(x, y, width, height, price, texture, detailTexture, detailActor);
        this.stageFactory = stageFactory;
        this.stageManager = stageManager;
    }
    public HorseRaceFieldActor(float x, float y, int price, Texture texture, DetailActor detailActor, StageManager stageManager, StageFactory stageFactory) {
        super(x, y, 40f, 40f, price, texture, new Texture("fields/loose_money.png"), detailActor);
        this.stageFactory = stageFactory;
        this.stageManager = stageManager;
    }

    @Override
    public void trigger(PlayerActor player) {
        stageManager.push(stageFactory.getHorseRaceStage(player));
    }
}
