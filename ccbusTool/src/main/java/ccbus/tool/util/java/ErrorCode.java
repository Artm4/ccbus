package ccbus.tool.util.java;

public enum ErrorCode
{
    TRANSLATION_ERROR("Translation error");

    String message;
    ErrorCode(String message)
    {
        this.message=message;
    }

    public String getMessage()
    {
        return message;
    }
}
