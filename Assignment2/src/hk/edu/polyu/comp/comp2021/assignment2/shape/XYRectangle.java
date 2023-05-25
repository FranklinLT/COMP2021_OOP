package hk.edu.polyu.comp.comp2021.assignment2.shape;

public class XYRectangle {
    private Point topLeft;
    private Point bottomRight;

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public XYRectangle(Point p1, Point p2){
        topLeft = p1;
        bottomRight = p2;
    }

    public String toString(){
        return "<" + topLeft.toString() + "," + bottomRight.toString() + ">";
    }

    public int area(){
        return (bottomRight.getX() - topLeft.getX()) * (topLeft.getY() - bottomRight.getY());
    }

    public void rotateClockwise(){
        int x_distance = bottomRight.getX() - topLeft.getX();
        int y_distance = topLeft.getY() - bottomRight.getY();
        int x_location = bottomRight.getX() - x_distance - y_distance;
        int y_location = bottomRight.getY() - (x_distance - y_distance);
        topLeft.set(x_location,y_location + x_distance);
        bottomRight.set(x_location + y_distance,y_location);
    }

    public void move(int deltaX, int deltaY){
        topLeft.set(topLeft.getX() + deltaX, topLeft.getY() + deltaY );
        bottomRight.set(bottomRight.getX() + deltaX, bottomRight.getY() + deltaY);
    }

    public boolean contains(Point p){
        return  (topLeft.getX() <= p.getX() && p.getX() <= bottomRight.getX()) &&
                (topLeft.getY() >= p.getY() && p.getY() >= bottomRight.getY());
    }

    public boolean contains(XYRectangle r){
        return contains(r.topLeft) && contains(r.bottomRight);
    }

    public boolean overlapsWith(XYRectangle r){
        int x_distance = r.bottomRight.getX() - r.topLeft.getX();
        Point topRight = new Point(r.topLeft.getX() + x_distance,r.topLeft.getY());
        Point bottomLeft = new Point(r.bottomRight.getX() - x_distance,r.bottomRight.getY());
        return contains(topRight) || contains(bottomLeft) || contains(r.topLeft) || contains(r.bottomRight);
    }
}

class Point{
    private int x;
    private int y;

    public Point(int x, int y) {
        set(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void set(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return "(" + getX() + "," + getY() + ")";
    }
}

