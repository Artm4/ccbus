package ccbus.out;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Shape {
    private Long id;

    @GeneratedValue
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    Collection<Point> point;

    @OneToMany(mappedBy = "shape")
    public java.util.Set<Point> getPoint() {
        return point;
    }

    public void setPoint(Collection<Point> point) {
        this.point = point;
    }
}
