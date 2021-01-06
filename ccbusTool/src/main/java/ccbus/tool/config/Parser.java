package ccbus.tool.config;

public abstract class Parser
{
    protected String projectPath;
    protected String projectSrcPath;
    protected String projectRelativeSrcPath;
    protected String projectRootPackage;
    protected String projectRootPackagePath;
    protected String projectRootPackageSubPath;

    protected String connectLibProjectPath;
    protected String connectLibRelativeSrcPath;
    protected String connectLibSrcPath;

    protected String clientProjectPath;
    protected String clientRelativeSrcPath;
    protected String clientSrcPath;
    protected String clientPlatform;
    protected String clientRootPackage;

    public Parser(java.io.Reader stream)
    {
    }

    public void parse() throws Exception
    {

    }

    public String getProjectSrcPath()
    {
        return projectSrcPath;
    }

    public String getProjectRootPackagePath()
    {
        return projectRootPackagePath;
    }

    public String getProjectRootPackage()
    {
        return projectRootPackage;
    }

    public String getConnectLibSrcPath()
    {
        return connectLibSrcPath;
    }

    public String getProjectRootPackageSubPath()
    {
        return projectRootPackageSubPath;
    }

    public String getClientSrcPath()
    {
        return clientSrcPath;
    }

    public String getProjectPath()
    {
        return projectPath;
    }

    public String getConnectLibProjectPath()
    {
        return connectLibProjectPath;
    }

    public String getClientProjectPath()
    {
        return clientProjectPath;
    }

    public String getProjectRelativeSrcPath()
    {
        return projectRelativeSrcPath;
    }

    public String getConnectLibRelativeSrcPath()
    {
        return connectLibRelativeSrcPath;
    }

    public String getClientRelativeSrcPath()
    {
        return clientRelativeSrcPath;
    }

    public String getClientPlatform()
    {
        return clientPlatform;
    }

    public String getClientRootPackage()
    {
        return clientRootPackage;
    }
}

