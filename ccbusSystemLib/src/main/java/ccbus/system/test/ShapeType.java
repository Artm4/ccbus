package ccbus.system.test;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class ShapeType
{
    public enum ShapeTypeStatus
    {
        good,
        bad
    }

    private Long id;

    @GeneratedValue
    @Id
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }


    private ShapeTypeStatus shapeTypeStatus;

    @Basic
    @Enumerated(EnumType.STRING)
    public ShapeTypeStatus getShapeTypeStatus()
    {
        return shapeTypeStatus;
    }

    public void setShapeTypeStatus(ShapeTypeStatus shapeTypeStatus)
    {
        this.shapeTypeStatus = shapeTypeStatus;
    }

    private String name;

    @Basic
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    private Collection<Shape> shape;

    @OneToMany(mappedBy = "shapeType")
    public Collection<Shape> getShape()
    {
        return shape;
    }

    public void setShape(Collection<Shape> shape)
    {
        this.shape = shape;
    }
}
