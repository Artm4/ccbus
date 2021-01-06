package ccbus.system.meta;
import ccbus.system.query.__MetaField;
import ccbus.system.query.__MetaClass;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import ccbus.system.test.Shape;


public class Meta_Shape<T>  extends __MetaClass
{
    public __MetaField<Long> id = new __MetaField<Long>(Long.class,"id",this);
    public __MetaField<Date> date = new __MetaField<Date>(Date.class,"date",this);
    public Meta_Point<OneToMany> Point;
    public Meta_ShapeType<ManyToOne> ShapeType;
    
    public Meta_Shape()
    {
        super();
        Point = new Meta_Point(this,"point","");
        ShapeType = new Meta_ShapeType(this,"shapeType","");
    }
    public Meta_Shape(__MetaClass parent,String path,String prevPath)
    {
        super(parent,path);
        if(!isRecursivePath(prevPath,path,"point"))
        {
            Point = new Meta_Point(this,"point",path);
        }
        if(!isRecursivePath(prevPath,path,"shapeType"))
        {
            ShapeType = new Meta_ShapeType(this,"shapeType",path);
        }
    }
}
