package ccbus.tool.util.console;

public enum ConsoleOption
{
    HELP_L("help"),HELP_S("h"),
    CONFIG_L("config"),CONFIG_S("c"),
    VERSION_L("version"),VERSION_S("v"),
    INSTALL_L("install"),INSTALL_S("i"),
    GEN_L("gen"),GEN_S("g"),
    PRINT_L("print"),PRINT_S("p"),
    SINGLE_L("single"),SINGLE_S("s");

    String message;

    ConsoleOption(String message)
    {
        this.message=message;
    }

    public String getMessage()
    {
        return message;
    }
}
