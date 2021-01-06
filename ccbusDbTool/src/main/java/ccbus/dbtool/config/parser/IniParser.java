package ccbus.dbtool.config.parser;
import ccbus.dbtool.config.Parser;
import ccbus.dbtool.util.Misc;
import org.ini4j.Ini;

import java.io.IOException;
import java.io.Reader;


public class IniParser extends Parser
{
    public final static String ENTITY_SECTION="Entity";
    public final static String PROJECT_SECTION="Project";

    Ini ini;
    public IniParser(Reader stream) throws IOException
    {
        super(stream);
        ini = new Ini(stream);
    }

    public void parse()  throws Exception
    {
        Ini.Section project = ini.get(PROJECT_SECTION);
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


        Ini.Section entity = ini.get(ENTITY_SECTION);
        String[] entityPackage = entity .getAll("entityPackage", String[].class);

        entityIncludePath=Misc.packageToPath(entityPackage,projectSrcPath);

        metaPackage= Misc.genericPathRTrim(entity.get("metaPackage"));
        metaPackage=Misc.toSystemFileSeparator(metaPackage);
        metaPath=Misc.packageToPath(metaPackage,projectSrcPath);
    }


    private void validateProp(Object prop,String propName) throws Exception
    {
        if(null==prop)
        {
            throw new Exception("Configuration property "+propName+" mandatory");
        }
    }

}
