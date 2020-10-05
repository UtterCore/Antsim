package Simulator.Organisms.Creatures;

public class Legs extends Limb {

    public static final int DIR_UP = 0;
    public static final int DIR_DOWN = 180;
    public static final int DIR_RIGHT = 90;
    public static final int DIR_LEFT = 270;

    private float speed;
    private float maxSpeed;
    private boolean isImmobile;

    public Legs(Creature owner) {
        super(owner);
        setSpeed(1);
        maxSpeed = 1;
    }

    public void setImmobile(boolean immobile) {
        isImmobile = immobile;
    }

    public boolean isImmobile() {
        return isImmobile;
    }

    protected void moveForward() {
        if (isImmobile) {
            return;
        }

        switch ((int)getOwner().getRotation()) {
            case DIR_UP: {
                getOwner().getPosition().setY(getOwner().getPosition().getY() - speed);
                break;
            }
            case DIR_RIGHT: {
                getOwner().getPosition().setX(getOwner().getPosition().getX() + speed);
                break;
            }
            case DIR_DOWN: {
                getOwner().getPosition().setY(getOwner().getPosition().getY() + speed);
                break;
            }
            case DIR_LEFT: {
                getOwner().getPosition().setX(getOwner().getPosition().getX() - speed);
                break;
            }
        }
    }

    protected void moveBackward() {

        if (isImmobile) {
            return;
        }

        switch ((int)getOwner().getRotation()) {
            case DIR_UP: {
                getOwner().getPosition().setY(getOwner().getPosition().getY() + speed);
                break;
            }
            case DIR_RIGHT: {
                getOwner().getPosition().setX(getOwner().getPosition().getX() - speed);
                break;
            }
            case DIR_DOWN: {
                getOwner().getPosition().setY(getOwner().getPosition().getY() - speed);
                break;
            }
            case DIR_LEFT: {
                getOwner().getPosition().setX(getOwner().getPosition().getX() + speed);
                break;
            }
        }
    }

    protected void rotate(float direction) {

        if (isImmobile) {
            return;
        }

        getOwner().setRotation(getOwner().getRotation() + direction);
    }


    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

}
