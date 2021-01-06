package ccbus.tool.config.parser;
import ccbus.tool.config.Parser;
import ccbus.tool.util.java.Misc;
import ccbus.tool.util.java.Tool;
import org.ini4j.Ini;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;


public class IniParser extends Parser
{
    public final static String SERVER_SECTION="Server";
    public final static String CLIENT_SECTION="Client";
    public final static String CCBUS_SECTION="Ccbus";

    Ini ini;
    public IniParser(Reader stream) throws IOException
    {
        super(stream);
        ini = new Ini(stream);
    }

    public void parse() throws Exception
    {
        Ini.Section project = ini.get(SERVER_SECTION);
        validateProp(project.get("projectPath"),"projectPath");
        projectPath=Misc.genericPathRTrim(project.get("projectPath"));
        projectPath=Misc.toSystemFileSeparator(projectPath);

        validateProp(project.get("relativeSrcPath"),"relativeSrcPath");
        projectRelativeSrcPath=Misc.genericPathTrim(project.get("relativeSrcPath"));
        projectRelativeSrcPath=Misc.toSystemFileSeparator(projectRelativeSrcPath);
        projectSrcPath=projectPath+Misc.fileSeparator+projectRelativeSrcPath;

        validateProp(project.get("rootPackage"),"rootPackage");
        projectRootPackage=Misc.genericPathRTrim(project.get("rootPackage"));
        projectRootPackagePath=Misc.packageToPath(projectRootPackage,projectSrcPath);
        projectRootPackageSubPath=Misc.packageToPath(projectRootPackage,"");


        Ini.Section client = ini.get(CLIENT_SECTION);

        validateProp(client.get("projectPath"),"projectPath");
        clientProjectPath=Misc.genericPathRTrim(client.get("projectPath"));
        clientProjectPath=Misc.toSystemFileSeparator(clientProjectPath);

        validateProp(client.get("relativeSrcPath"),"relativeSrcPath");
        clientRelativeSrcPath=Misc.genericPathTrim(client.get("relativeSrcPath"));
        clientRelativeSrcPath=Misc.toSystemFileSeparator(clientRelativeSrcPath);
        clientSrcPath=clientProjectPath+Misc.fileSeparator+clientRelativeSrcPath;
        clientPlatform=client.get("platform");
        validateClientPlatform(clientPlatform,"platform");
        if(0==clientPlatform.compareTo("desktop"))
        {
            validateProp(client.get("rootPackage"),"rootPackage");
            clientRootPackage=Misc.genericPathRTrim(client.get("rootPackage"));
        }


        Ini.Section ccbus = ini.get(CCBUS_SECTION);

        validateProp(project.get("projectPath"),"projectPath");
        connectLibProjectPath=Misc.genericPathRTrim(ccbus.get("projectPath"));
        connectLibProjectPath=Misc.toSystemFileSeparator(connectLibProjectPath);

        validateProp(project.get("relativeSrcPath"),"relativeSrcPath");
        connectLibRelativeSrcPath=Misc.genericPathTrim(ccbus.get("relativeSrcPath"));
        connectLibRelativeSrcPath=Misc.toSystemFileSeparator(connectLibRelativeSrcPath);
        connectLibSrcPath=connectLibProjectPath+Misc.fileSeparator+connectLibRelativeSrcPath;
    }

    private void validateProp(Object prop,String propName) throws Exception
    {
        if(null==prop)
        {
            throw new Exception("Configuration property "+propName+" mandatory");
        }
    }

    private void validateClientPlatform(Object prop,String propName) throws Exception
    {
        if(null==prop)
        {
            clientPlatform="web";
        }
        if(!Arrays.asList(Tool.platformSupported).contains(clientPlatform))
        {
            throw new Exception("Configuration property "+propName+" should be: web | mobile");
        }
    }

}
