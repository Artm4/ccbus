package ccbus.dbtool.intermediate.routineparser;

import java.util.HashMap;

public enum IdentifierType
{
    // Fields.
    TRANSIENT("Transient"),

    // Relations
    ONE_TO_MANY("OneToMany"), MANY_TO_ONE("ManyToOne"),
    ONE_TO_ONE("OneToOne"),

    // Scalar, object type
    OLONG("Long"),OINTEGER("Integer"),OFLOAT("Float"),ODOUBLE("Double"),
    LONG("long"),INT("int"),FLOAT("float"),DOUBLE("double"),

    DATE("Date"),DATE_TIME("DateTime"),

    // Collection type
    SET("Set"),COLLECTION("Collection"),HASH("Hash"),HASH_MAP("HashMap"),
    ENUM_CONSTANT("enum")
    ;

    public static HashMap<String,IdentifierType> stringToType=new HashMap<>();

    static
    {
        IdentifierType[] values=IdentifierType.values();
        for(IdentifierType value:values)
        {
            stringToType.put(value.getName(),value);
        }
    }

    private String name;
    IdentifierType(String name)
    {
        this.name=name;
    }
    public String getName()
    {
        return name;
    }

    public static IdentifierType getRelationByName(String name)
    {
        return stringToType.get(name);
    }
}
