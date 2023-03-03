public class Enemy
{
    double x;
    double y;
    double speed;
    boolean visible;
    boolean turnLeft;
    int type;

    public Enemy(double x, double y, double speed, boolean visible, boolean turnLeft , int type)
    {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.visible = visible;
        this.turnLeft = turnLeft;
        this.type = type;
    }
}