public abstract class GameObject {

    protected int x;
    protected int y;
    protected boolean dead;

    public int getX(){
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean isDead() {
        return dead;
    }
    public void setDead(boolean a) {
        this.dead = a;
    }
}
