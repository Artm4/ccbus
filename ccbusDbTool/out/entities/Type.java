package ccbus.out;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
public class Type
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String tag;

    public static final String Theatre="Theatre";
    public static final String LW="LW";
    public static final String GynaeOnc="GynaeOnc";
    public static final String OandGCover="OandGCover";
    public static final String Spare="Spare";
    public static final String Night="Night";
    public static final String Late="Late";
    public static final String Trainee="Trainee";

    public static final List<String> systemTypes= Arrays.asList(Theatre,LW,GynaeOnc,OandGCover,Spare,Night,Late,Trainee);

    public Type()
    {
    }

    public Type(String name, String tag)
    {
        this.name = name;
        this.tag = tag;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Type)) return false;
        Type type = (Type) o;
        return Objects.equals(id, type.id);
    }

    public static List<String> getSystemTypes()
    {
        return systemTypes;
    }

    public static List<String> getSystemTypesMandatory()
    {
        return Arrays.asList(Late,Night,Spare);
    }

    @Override
    public int hashCode()
    {

        return this.id.intValue();
    }
}
