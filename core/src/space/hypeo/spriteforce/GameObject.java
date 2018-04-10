package space.hypeo.spriteforce;

import com.badlogic.gdx.math.Vector3;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by pichlermarc on 06.04.2018.
 */

public abstract class GameObject {

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    protected Vector3 position; //TODO: Replace with engine-agnostic verison.
    protected Collection<Behaviour> behaviour;
    protected GameLayer parent;

    /**
     * Creates a new instance of the GameObject Class.
     */
    public GameObject()
    {
        behaviour = new LinkedList<Behaviour>();
    }

    /**
     * Sets the Layer that's the parent of this GameObject
     * @param parent
     */
    public void setParent(GameLayer parent)
    {
        this.parent = parent;
    }

    /**
     * Defocuses the Layer this GameObject is assciated with.
     */
    public void defocusLayer()
    {
        this.parent.defocus();
    }

    /**
     * Adds behaviour to this GameObject and sets this object as parent.
     * @param behaviour Behaviour to add.
     */
    public void addBehaviour(Behaviour behaviour)
    {
        behaviour.setGameObject(this);
        this.behaviour.add(behaviour);
    }

    /**
     * Removes behaviuor from this GameObject.
     * @param behaviour
     */
    public void removeBehaviour(Behaviour behaviour)
    {
        this.behaviour.remove(behaviour);
    }

    /**
     * Update method called every frame.
     */
    public void update(float deltaTime)
    {
        for (Behaviour b:behaviour) {
            b.update(deltaTime);
        }
    }

    /**
     * Draws this GameObject.
     */
    public abstract void draw();

    /**
     * Sets the texture to be rendered on this GameObject's position.
     * @param texture
     */
    public abstract void setTexture(String texture);
}