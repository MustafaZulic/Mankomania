package space.hypeo.mankomania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.common.RectangleActor;
import space.hypeo.mankomania.actors.player.PlayerActor;
import space.hypeo.mankomania.game.EconomicStageLogic;

public class ClickerStageEndscreen extends Stage {

    private StageManager stageManager;
    private PlayerActor playerActor;
    private EconomicStageLogic eco;
    private int score;


    public ClickerStageEndscreen(Viewport viewport, StageManager stageManager,  PlayerActor playerActor, int score) {
        super(viewport);
        this.stageManager = stageManager;
        this.playerActor = playerActor;
        this.score = score;

        eco= new EconomicStageLogic(playerActor);


        setUpBackground();
        pay();
        setUpElements();
    }

    private void pay(){
        eco.payMoney(score);
    }

    private void setUpElements() {
       Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Table table = new Table();
        table.setWidth(this.getWidth());
        table.align(Align.bottom);
        table.setPosition(0, 200);


        Label nameLabel;
        Label moneyLabel;
        nameLabel = new Label("Du hast "+score +" ausgegeben", skin);
        nameLabel.setFontScale((float) 1.5);
        table.add(nameLabel).width(300).height(100).align(Align.center);
        table.row();

        TextButton buttonTrue;
        buttonTrue = new TextButton("Okay", skin);
        buttonTrue.addListener(buttonClick());
        table.add(buttonTrue).width(100);
        table.row();

        moneyLabel = new Label("your current money is: " + playerActor.getBalance() + "", skin);
        moneyLabel.setFontScale((float) 1.5);
        table.add(moneyLabel).width(300).height(100).align(Align.center);
        table.row();


        this.addActor(table);


    }


    public void setUpBackground() {

        RectangleActor background = new RectangleActor(0, 0, getViewport().getWorldWidth(), getViewport().getWorldHeight());
        // Set up background.
        background.setColor(237f / 255f, 30f / 255f, 121f / 255f, 1f);

        addActor(background);

    }

    private ClickListener buttonClick() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.remove(ClickerStageEndscreen.this);
            }
        };
    }
}
