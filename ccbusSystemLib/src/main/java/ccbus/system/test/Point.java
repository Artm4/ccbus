package ccbus.system.test;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
public class Point implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private int x=1;
    private Integer y;

    @OneToOne
    private Point oldPoint;

    private DateTime joda;

    public DateTime getJoda()
    {
        return joda;
    }

    public void setJoda(DateTime joda)
    {
        this.joda = joda;
    }

    public Point getOldPoint()
    {
        return oldPoint;
    }

    public void setOldPoint(Point oldPoint)
    {
        this.oldPoint = oldPoint;
    }

    public Point() {
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Long getId() {
        return id;
    }

    public int getX() {
         return x;
    }

    public int getY() {
         return y;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", this.x, this.y);
    }

    @ManyToOne(optional = false)
    private Shape shape;

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

}