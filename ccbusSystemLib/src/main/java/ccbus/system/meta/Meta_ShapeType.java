package ccbus.system.meta;
import ccbus.system.query.__MetaField;
import ccbus.system.query.__MetaClass;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.*;
import java.util.Collection;
import ccbus.system.test.ShapeType;


public class Meta_ShapeType<T>  extends __MetaClass
{
    public __MetaField<Long> id = new __MetaField<Long>(Long.class,"id",this);
    public __MetaField<ShapeType.ShapeTypeStatus> shapeTypeStatus = new __MetaField<ShapeType.ShapeTypeStatus>(ShapeType.ShapeTypeStatus.class,"shapeTypeStatus",this);
    public __MetaField<String> name = new __MetaField<String>(String.class,"name",this);
    public Meta_Shape<OneToMany> Shape;
    
    public Meta_ShapeType()
    {
        super();
        Shape = new Meta_Shape(this,"Shape","");
    }
    public Meta_ShapeType(__MetaClass parent,String path,String prevPath)
    {
        super(parent,path);
        if(!isRecursivePath(prevPath,path,"Shape"))
        {
            Shape = new Meta_Shape(this,"Shape",path);
        }
    }
}
