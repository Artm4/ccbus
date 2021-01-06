package ccbus.dbtool.intermediate;

public enum TypeFormImpl
{
    SCALAR, ENUMERATION, SUBRANGE, ARRAY, CLASS, INTERFACE, TEMPLATE_PARAM;
    public String toString()
    {
        return super.toString().toLowerCase();
    }
}
