package ccbus.system.test;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
public class Shape {
    @GeneratedValue
    @Id
    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "shape")
    private Collection<Point> point;


    public Collection<Point> getPoint() {
        return point;
    }

    public void setPoint(Collection<Point> point) {
        this.point = point;
    }

    private Date date;

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    @ManyToOne(optional = false)
    private ShapeType shapeType;

    public ShapeType getShapeType()
    {
        return shapeType;
    }

    public void setShapeType(ShapeType shapeType)
    {
        this.shapeType = shapeType;
    }
}
