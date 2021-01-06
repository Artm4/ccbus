package ccbus.tool;

import ccbus.tool.compiler.Compiler;
import ccbus.tool.compiler.JavaToJava;
import ccbus.tool.compiler.JavaToReact;
import ccbus.tool.compiler.OutputOption;
import ccbus.tool.config.parser.IniParser;
import ccbus.tool.util.console.ConsoleOption;
import ccbus.tool.util.console.ConsoleOptionTuple;
import ccbus.tool.util.java.Misc;
import ccbus.tool.util.java.Tool;
import org.apache.maven.model.Model;
import org.apache.maven.model.Resource;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.Manifest;

public class CcbusConsole
{
    private String filePath="";
    private boolean toStdOut=false;
    private boolean single=false;
    private HashMap<ConsoleOption,ConsoleOptionTuple> consoleOptions=new HashMap<>();
    private URL consoleResource;
    String configurationPath="";
    IniParser configParser;
    ConsoleOptionTuple lastConsoleOptionTuple=null;


    public static void main(String[] args)
    {
        (new CcbusConsole()).run(args);
    }

    public void run(String[] args)
    {
        try
        {
            consoleResource=this.getClass().getClassLoader().getResource("console");
            if(null==consoleResource)
            {
                throw new Exception("Cannot read console resources. Please contact support!");
            }
            if(0==args.length)
            {
                printUsage();
                throw new Exception("Missing arguments");
            }
            for(int i=0;i<args.length;i++)
            {
                parseArgument(args[i]);
            }
            executeCommand();
        }
        catch (Exception e)
        {
            if(null!=e.getMessage())
            {
                System.err.println("Error: "+e.getMessage());
                e.printStackTrace();
            }
            else
            {
                System.err.println("Error");
                e.printStackTrace();
            }
        }
    }

    private void parseArgument(String arg)  throws Exception
    {
        String option="";
        String param="";
        if(arg.startsWith("--"))
        {
            option=arg.substring(2);
        }
        else
        if(arg.startsWith("-"))
        {
            option=arg.substring(1);
        }
        else
        {
            param=arg;
        }

        if(option.length()>0)
        {
            parseOption(option);
        }

        if(param.length()>0)
        {
            parseOptionParam(param);
        }
    }

    private void executeCommand() throws Exception
    {
        if(consoleOptions.containsKey(ConsoleOption.HELP_S))
        {
            printUsage();
            return;
        }

        if(consoleOptions.containsKey(ConsoleOption.VERSION_S))
        {
            printVersion();
            return;
        }

        if(consoleOptions.containsKey(ConsoleOption.GEN_S))
        {
            generateConfig(consoleOptions.get(ConsoleOption.GEN_S));
            return;
        }

        if(consoleOptions.containsKey(ConsoleOption.PRINT_S))
        {
            toStdout();
        }

        if(consoleOptions.containsKey(ConsoleOption.CONFIG_S))
        {
            loadConfig(consoleOptions.get(ConsoleOption.CONFIG_S));
        }

        // look for default configuration file
        if(0==configurationPath.length())
        {
            loadConfig(new ConsoleOptionTuple(ConsoleOption.CONFIG_S));
        }

        parseConfig();

        if(consoleOptions.containsKey(ConsoleOption.INSTALL_S))
        {
            setUpEnv(consoleOptions.get(ConsoleOption.INSTALL_S));
            return;
        }
        if(consoleOptions.containsKey(ConsoleOption.SINGLE_S))
        {
            single=true;
        }


        if(filePath.length()>0)
        {
            compileFile();
        }

    }

    private void compileFile() throws Exception
    {
        String filePathMod=Misc.toAbsolutePathString(filePath);
        if(!Files.exists(Paths.get(filePathMod)) && !Paths.get(filePath).isAbsolute())
        {
            System.err.println("Error: Cannot read file from working directory: "+filePathMod);
            System.err.println("Searching another path...");
            filePathMod=configParser.getProjectSrcPath()+Misc.fileSeparator+filePath;

            if(!Files.exists(Paths.get(filePathMod)))
            {
                System.err.println("Error: Cannot read file from project src path: "+filePathMod);
                System.err.println("Searching another path...");
                filePathMod=configParser.getProjectRootPackagePath()+filePath;

                if(!Files.exists(Paths.get(filePathMod)))
                {
                    System.err.println("Error: Cannot read file from project root package path: "+filePathMod);
                }
            }
        }

        if(!Files.exists(Paths.get(filePathMod)))
        {
            throw new Exception("Cannot read file: "+filePathMod);
        }

        System.out.println("Compilation unit loaded: "+filePathMod);
        Compiler compiler;
        configParser=new IniParser(new FileReader(configurationPath));
        configParser.parse();
        if(0==configParser.getClientPlatform().compareTo("desktop"))
        {
            compiler=new JavaToJava(configParser,filePathMod);
        }
        else
        {
            compiler=new JavaToReact(configParser,filePathMod);
        }
        if(toStdOut)
        {
            compiler.setOutputOption(OutputOption.PRINT);
        }
        else
        {
            compiler.setOutputOption(OutputOption.FILE);
        }
        compiler.setSingle(single);
        compiler.compile();
    }

    private void toStdout() throws Exception
    {
        toStdOut=true;
    }

    private void parseConfig() throws Exception
    {
        configParser=new IniParser(new FileReader(configurationPath));
        configParser.parse();
    }

    private void parseOptionParam(String arg) throws Exception
    {
        if(null==lastConsoleOptionTuple||
                (!lastConsoleOptionTuple.hasArgument()))
        {
            parseFile(arg);
            return;
        }
        lastConsoleOptionTuple.setParam(arg);
        lastConsoleOptionTuple=null;
    }

    private void parseOption(String option) throws Exception
    {
        if(option.equals(ConsoleOption.CONFIG_L.getMessage())
                || option.equals(ConsoleOption.CONFIG_S.getMessage()))
        {
            lastConsoleOptionTuple=new ConsoleOptionTuple(ConsoleOption.CONFIG_S);
            consoleOptions.put(ConsoleOption.CONFIG_S,lastConsoleOptionTuple);
        }
        else
        if(option.equals(ConsoleOption.GEN_L.getMessage())
                || option.equals(ConsoleOption.GEN_S.getMessage()))
        {
            lastConsoleOptionTuple=new ConsoleOptionTuple(ConsoleOption.GEN_S);
            consoleOptions.put(ConsoleOption.GEN_S,lastConsoleOptionTuple);
        }
        else
        if(option.equals(ConsoleOption.HELP_L.getMessage())
                || option.equals(ConsoleOption.HELP_S.getMessage()))
        {
            lastConsoleOptionTuple=new ConsoleOptionTuple(ConsoleOption.HELP_S);
            consoleOptions.put(ConsoleOption.HELP_S,lastConsoleOptionTuple);
        }
        else
        if(option.equals(ConsoleOption.INSTALL_L.getMessage())
                || option.equals(ConsoleOption.INSTALL_S.getMessage()))
        {
            lastConsoleOptionTuple=new ConsoleOptionTuple(ConsoleOption.INSTALL_S);
            consoleOptions.put(ConsoleOption.INSTALL_S,lastConsoleOptionTuple);
        }
        else
        if(option.equals(ConsoleOption.VERSION_L.getMessage())
                || option.equals(ConsoleOption.VERSION_S.getMessage()))
        {
            lastConsoleOptionTuple=new ConsoleOptionTuple(ConsoleOption.VERSION_S);
            consoleOptions.put(ConsoleOption.VERSION_S,lastConsoleOptionTuple);
        }
        else
        if(option.equals(ConsoleOption.PRINT_L.getMessage())
                || option.equals(ConsoleOption.PRINT_S.getMessage()))
        {
            lastConsoleOptionTuple=new ConsoleOptionTuple(ConsoleOption.PRINT_S);
            consoleOptions.put(ConsoleOption.PRINT_S,lastConsoleOptionTuple);
        }
        else
        if(option.equals(ConsoleOption.SINGLE_L.getMessage())
                || option.equals(ConsoleOption.SINGLE_S.getMessage()))
        {
            lastConsoleOptionTuple=new ConsoleOptionTuple(ConsoleOption.SINGLE_S);
            consoleOptions.put(ConsoleOption.SINGLE_S,lastConsoleOptionTuple);
        }
        else
        {
            throw new Exception();
        }
    }

    private void parseFile(String filePath) throws Exception
    {
        this.filePath=filePath;
    }


    public BufferedReader getResourceAsStream(String resourceUri)
    {
        InputStream in = getClass().getResourceAsStream(resourceUri);
        if(null==in)
        {
            return null;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        return bufferedReader;
    }


    private void printUsage()
    {
        try
        {
            Misc.printFile(getResourceAsStream("/console/usage.txt"));
        }
        catch (Exception e)
        {
            System.err.println("Cannot read console resources usage.txt. Please contact support!");
        }
    }

    private void printVersion()
    {
        try
        {
            String implVersion=getClass().getPackage().getImplementationVersion();
            if(null==implVersion)
            {
                MavenXpp3Reader reader = new MavenXpp3Reader();
                Model model=null;
                model = reader.read(new FileReader("pom.xml"));
                if(null!=model)
                {
                    System.out.println("App: " + model.getGroupId());
                    System.out.println("Version: " + model.getVersion());
                    System.out.println("Description: " + model.getDescription());

                }
            }
            else
            {
                //could read manifests and find the right one by this.getClass().getClassLoader().getResources("/META-INF/MANIFEST.MF")
                //Manifest manifest=new Manifest(mainfestFile);
                System.out.println("App: " + getClass().getPackage().getName());
                System.out.println("Version: " + getClass().getPackage().getImplementationVersion());
                System.out.println("Description: " + getClass().getPackage().getImplementationTitle());

            }

        }
        catch (Exception e)
        {
            System.err.println("Error: Cannot read pom.xml resource. Please contact support!");
        }
    }

    private void generateConfig(ConsoleOptionTuple consoleOptionTuple)
    {
        BufferedReader srcPath = getResourceAsStream("/console/config.ini");;
        String filePath=consoleOptionTuple.getParam();
        try
        {
            if(filePath.length()==0)
            {
                // create file in working directory
                filePath=Misc.toAbsolutePathString("")+"configCcbus.ini";
            }
            else
            {
                // create file by relative or absolute path
                filePath=Misc.toAbsolutePathString(filePath);
            }

            if(Files.exists(Paths.get(filePath)))
            {
                System.out.println("Configuration file already exist. Skipping...");
                return;
            }
        }
        catch (Exception e)
        {
            System.err.println("Error: IO error."+e.getMessage()+". Please contact support!");
            return;
        }

        try
        {
            Misc.copyFile(srcPath, filePath);
        }
        catch (Exception e)
        {
            System.err.println("Error: Cannot create configuration file: "+filePath);
        }

        String generatedPath=Paths.get(filePath).toAbsolutePath().toString();
        System.out.println("Configuration template generated with path: "+generatedPath);

    }

    private void loadConfig(ConsoleOptionTuple consoleOptionTuple) throws Exception
    {
        String filePath=consoleOptionTuple.getParam();
        try
        {
            // set path relative to installation directory
            if(filePath.length()==0)
            {
                filePath=new File(CcbusConsole.class.getProtectionDomain().getCodeSource().getLocation()
                        .toURI()).getParent()+Misc.fileSeparator+"configCcbus.ini";
            }
            else
            {
                filePath=Misc.toAbsolutePathString(filePath);
            }
        }
        catch (Exception e)
        {
            throw new Exception("IO error."+e.getMessage()+". Please contact support!");
        }

        configurationPath=filePath;
        if(!Files.exists(Paths.get(configurationPath)))
        {
            throw new Exception("Cannot load configuration file: "+configurationPath+". Please check file path or file permissions!");
        }
        System.out.println("Configuration file loaded: "+filePath);
    }

    private void setUpEnv(ConsoleOptionTuple consoleOptionTuple) throws Exception
    {
        setUpClientEnv(consoleOptionTuple);
        setUpServerEnv(consoleOptionTuple);
    }

    private void setUpClientEnv(ConsoleOptionTuple consoleOptionTuple) throws Exception
    {
        String clientSrcPath=configParser.getClientSrcPath();
        Misc.prepareDirectories(clientSrcPath);

        String clientProjectPath=configParser.getClientProjectPath();

        BufferedReader ccbusConfigFile = getResourceAsStream("/console/client/ccbus-config.js");
        String clientCcbusConfigFile=clientSrcPath+Misc.fileSeparator+"ccbus-config.js";

        HashMap<String,String> replacePairConfigFile=(HashMap<String,String>)Misc.ofMap("%PLATFORM%",configParser.getClientPlatform());
        buildTemplateFile(replacePairConfigFile,ccbusConfigFile,clientCcbusConfigFile);


        String clientJsConfig=clientProjectPath+Misc.fileSeparator+"jsconfig.json";
        BufferedReader ccbusJsConfig = getResourceAsStream("/console/client/jsconfig.json");

        HashMap<String,String> replacePair=(HashMap<String,String>)Misc.ofMap("%SRC%",configParser.getClientRelativeSrcPath());
        buildTemplateFile(replacePair,ccbusJsConfig,clientJsConfig);

    }

    private void setUpServerEnv(ConsoleOptionTuple consoleOptionTuple) throws Exception
    {
        String serverSrcPath=Misc.rtrim(configParser.getProjectRootPackagePath(),Misc.fileSeparator);
        String controllersPath="controllers";

        String[][] pathTree={
            {Tool.connectPackageTag,"payload"},
            {Tool.connectPackageTag,"service"},
            {Tool.connectPackageTag,"mobile","components"},
            {Tool.connectPackageTag,"web","components"},
            {Tool.connectPackageTag,"worker","mobile"},
            {Tool.connectPackageTag,"worker","web"},
            {Tool.connectPackageTag,"worker","service"},
            {controllersPath}
        };

        for(int i=0;i<pathTree.length;i++)
        {
            String dir=serverSrcPath+Misc.fileSeparator+Misc.buildPath(pathTree[i]);
            if(Files.exists(Paths.get(dir)))
            {
                System.out.println(dir+" already exist. Skipping...");
            }
            else
            {
                boolean result=Misc.prepareDirectories(dir);
                if(!result)
                {
                    throw new Exception("Failed to create directory"+dir);
                }
                System.out.println(dir+" created.");
            }
        }

        String CcbusPackage=configParser.getProjectRootPackage()+"."+Tool.connectPackageTag;

        BufferedReader ccbusController=getResourceAsStream("/console/server/CcbusConnectRest.java");
        String serverController=serverSrcPath+Misc.fileSeparator+controllersPath+Misc.fileSeparator+"CcbusConnectRest.java";

        HashMap<String,String> replacePairController=(HashMap<String,String>)
                Misc.ofMap("%PACKAGE_NAME%",configParser.getProjectRootPackage()+"."+controllersPath,
                        "%WORKER_HANDLER_PACKAGE_NAME%",CcbusPackage);

        buildTemplateFile(replacePairController,ccbusController,serverController);


        BufferedReader ccbusWorkerHandler=getResourceAsStream("/console/server/WorkerHandler.java");
        String serverWorkerHandler=serverSrcPath+Misc.fileSeparator+Tool.connectPackageTag+Misc.fileSeparator+"WorkerHandler.java";

        HashMap<String,String> replacePairWorkerHandler=(HashMap<String,String>)
                Misc.ofMap("%PACKAGE_NAME%",CcbusPackage);

        buildTemplateFile(replacePairWorkerHandler,ccbusWorkerHandler,serverWorkerHandler);
    }

    private void buildTemplateFile(HashMap<String,String> replacePair, BufferedReader srcFilePath, String dstFilePath) throws Exception
    {
        if(Files.exists(Paths.get(dstFilePath)))
        {
            System.out.println(dstFilePath+" already exists. Skipping...");
        }
        else
        {
            Misc.buildFileTemplate(replacePair,srcFilePath,dstFilePath);
            System.out.println(dstFilePath+" generated.");
        }
    }
}
