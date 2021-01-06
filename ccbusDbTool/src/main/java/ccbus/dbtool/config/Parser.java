package ccbus.dbtool.config;

public abstract class Parser
{
    protected String[] entityIncludePath;
    protected String projectSrcPath;
    protected String projectPath;
    protected String projectRelativeSrcPath;
    protected String projectRootPackage;
    protected String projectRootPackagePath;
    protected String projectRootPackageSubPath;
    protected String metaPath;
    protected String metaPackage;

    public Parser(java.io.Reader stream)
    {
    }

    public void parse()  throws Exception
    {

    }

    public String[] getEntityIncludePath()
    {
        return entityIncludePath;
    }

    public String getProjectSrcPath()
    {
        return projectSrcPath;
    }

    public String getMetaPath()
    {
        return metaPath;
    }

    public String getMetaPackage()
    {
        return metaPackage;
    }

    public String getProjectPath()
    {
        return projectPath;
    }

    public String getProjectRelativeSrcPath()
    {
        return projectRelativeSrcPath;
    }

    public String getProjectRootPackage()
    {
        return projectRootPackage;
    }

    public String getProjectRootPackagePath()
    {
        return projectRootPackagePath;
    }

    public String getProjectRootPackageSubPath()
    {
        return projectRootPackageSubPath;
    }
}

