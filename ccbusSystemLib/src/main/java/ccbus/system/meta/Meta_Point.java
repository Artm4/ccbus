package ccbus.system.meta;
import ccbus.system.query.__MetaField;
import ccbus.system.query.__MetaClass;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.joda.time.DateTime;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import ccbus.system.test.Point;


public class Meta_Point<T>  extends __MetaClass
{
    public __MetaField<Long> id = new __MetaField<Long>(Long.class,"id",this);
    public __MetaField<Integer> x = new __MetaField<Integer>(Integer.class,"x",this);
    public __MetaField<Integer> y = new __MetaField<Integer>(Integer.class,"y",this);
    public __MetaField<DateTime> joda = new __MetaField<DateTime>(DateTime.class,"joda",this);
    public Meta_Point<OneToOne> OldPoint;
    public Meta_Shape<ManyToOne> Shape;
    
    public Meta_Point()
    {
        super();
        OldPoint = new Meta_Point(this,"oldPoint","");
        Shape = new Meta_Shape(this,"shape","");
    }
    public Meta_Point(__MetaClass parent,String path,String prevPath)
    {
        super(parent,path);
        if(!isRecursivePathClass(Meta_Point.class))
        {
            OldPoint = new Meta_Point(this,"oldPoint",path);
        }
        if(!isRecursivePathClass(Meta_Shape.class))
        {
            Shape = new Meta_Shape(this,"shape",path);
        }
    }
}
