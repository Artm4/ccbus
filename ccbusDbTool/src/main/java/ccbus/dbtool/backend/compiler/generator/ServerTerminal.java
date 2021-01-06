package ccbus.dbtool.backend.compiler.generator;

public enum ServerTerminal
{
    LEFT_BRACE("{"), RIGHT_BRACE("}"),SEMICOLON(";"),COMMA(","),
    ACCESS_PUBLIC("public"), TYPE_CLASS("class"), TYPE_ENUM("enum"),
    IMPORT("import"),PACKAGE("package");

    private String name;
    ServerTerminal(String name)
    {
        this.name=name;
    }

    public String toString()
    {
        return name;
    }
}
