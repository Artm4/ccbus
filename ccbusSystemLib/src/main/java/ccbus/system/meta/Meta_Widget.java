package ccbus.system.meta;
import ccbus.system.query.__MetaField;
import ccbus.system.query.__MetaClass;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.Date;
import ccbus.system.test.BaseEntity;
import javax.persistence.*;
import ccbus.system.test.Widget;


public class Meta_Widget<T>  extends __MetaClass
{
    public __MetaField<Long> id = new __MetaField<Long>(Long.class,"id",this);
    public __MetaField<Date> createdAt = new __MetaField<Date>(Date.class,"createdAt",this);
    public __MetaField<Date> updatedAt = new __MetaField<Date>(Date.class,"updatedAt",this);
    public __MetaField<Date> deletedAt = new __MetaField<Date>(Date.class,"deletedAt",this);
    public __MetaField<Boolean> deleted = new __MetaField<Boolean>(Boolean.class,"deleted",this);
    public __MetaField<String> widgetName = new __MetaField<String>(String.class,"widgetName",this);
    public __MetaField<String> widgetDescription = new __MetaField<String>(String.class,"widgetDescription",this);
    public __MetaField<String> widgetObject = new __MetaField<String>(String.class,"widgetObject",this);
    
    public Meta_Widget()
    {
        super();
    }
    public Meta_Widget(__MetaClass parent,String path,String prevPath)
    {
        super(parent,path);
    }
}
