package space.hypeo.mankomania.actors.fields;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import space.hypeo.mankomania.GameTest;
import space.hypeo.mankomania.actors.player.PlayerActor;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by pichlermarc on 11.05.2018.
 */
public class FieldActorTest extends GameTest {
    private List<FieldActor> fieldActors;

    private static final float X_POS = 10f;
    private static final float Y_POS = 20f;
    private static final float WIDTH = 30f;
    private static final float HEIGHT = 40f;
    private static final float FUZZ_FACTOR = 0.001f;
    private static final int PRICE = 100;

    @Mock
    private Texture texture;
    @Mock
    private Texture detailTexture;
    @Mock
    private Image detailImage;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        fieldActors = new ArrayList<>();
        for (int count = 0; count < 10; count++) {
            fieldActors.add(new FieldActor(X_POS, Y_POS, WIDTH, HEIGHT, PRICE, texture, detailTexture, detailImage) {
                @Override
                public void trigger(PlayerActor player) {
                }
            });
        }
    }

    @Test
    public void createFieldActor() {
        assertEquals(fieldActors.get(0).getX(), X_POS, FUZZ_FACTOR);
        assertEquals(fieldActors.get(0).getY(), Y_POS, FUZZ_FACTOR);
        assertEquals(fieldActors.get(0).getWidth(), WIDTH, FUZZ_FACTOR);
        assertEquals(fieldActors.get(0).getHeight(), HEIGHT, FUZZ_FACTOR);
        assertEquals(fieldActors.get(0).getPrice(), PRICE);
    }

    @Test
    public void showFieldDetail() {
        fieldActors.get(0).showFieldDetail();
        verify(detailImage).setDrawable(any(SpriteDrawable.class));
    }

    @Test
    public void getFollowingField() {
        for (int count = 0; count + 1 < fieldActors.size(); count++)
            fieldActors.get(count).setNextField(fieldActors.get(count + 1));
        for (int count = 0; count < fieldActors.size(); count++)
            assertEquals(fieldActors.get(count), fieldActors.get(0).getFollowingField(count));
    }

    @Test
    public void setPrice() {
        fieldActors.get(0).setPrice(200);
        assertEquals(fieldActors.get(0).getPrice(), 200);
    }
}