package ccbus.dbtool;

import ccbus.dbtool.backend.compiler.CodeGenerator;
import ccbus.dbtool.backend.compiler.OutputOption;
import ccbus.dbtool.backend.compiler.generator.server.EntityDescriptor;
import ccbus.dbtool.backend.compiler.generator.server.MetaDescriptor;
import ccbus.dbtool.backend.compiler.util.PrintStream;
import ccbus.dbtool.config.parser.IniParser;
import ccbus.dbtool.intermediate.ParsedNamespaceTab;
import ccbus.dbtool.intermediate.icodeimpl.ICodeNodeClass;
import ccbus.dbtool.intermediate.icodeimpl.ICodeNodeTypeImpl;
import ccbus.dbtool.intermediate.routineimpl.ClassCode;
import ccbus.dbtool.intermediate.routineimpl.EntityCode;
import ccbus.dbtool.util.EntityPrinter;
import ccbus.dbtool.util.Misc;
import ccbus.dbtool.util.Tool;
import ccbus.dbtool.util.console.ConsoleOption;
import ccbus.dbtool.util.console.ConsoleOptionTuple;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

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
    public static final String FILE_PREFIX="Meta_";

    EntityPrinter entityPrinter;
    ParsedNamespaceTab parsedNamespaceTabDone=new ParsedNamespaceTab();
    Tool tool;
    OutputOption outputOption=OutputOption.FILE;


    public static void main(String[] args)
    {
        (new CcbusConsole()).run(args);
    }

    public void run(String[] args)
    {
        try
        {
            entityPrinter=new EntityPrinter(System.out);
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

        compile(filePathMod);

    }

    private void toStdout() throws Exception
    {
        outputOption=OutputOption.PRINT;
    }

    private void parseConfig() throws Exception
    {
        configParser=new IniParser(new FileReader(configurationPath));
        configParser.parse();

        tool=new Tool(configParser);
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
                filePath=Misc.toAbsolutePathString("")+"configCcbusDb.ini";
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

            String generatedPath=Paths.get(filePath).toAbsolutePath().toString();
            System.out.println("Configuration template generated with path: "+generatedPath);
        }
        catch (Exception e)
        {
            System.err.println("Error: Cannot create configuration file: "+filePath);
        }


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
                        .toURI()).getParent()+Misc.fileSeparator+"configCcbusDb.ini";
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

    public void compile(String compilationUnitPath) throws Exception
    {
        FileReader fileReader=new FileReader(compilationUnitPath);
        //System.out.println("Entity loaded: "+compilationUnitPath);
        System.out.println("Parsing: "+compilationUnitPath);
        EntityCode code=(EntityCode)tool.parseClass(fileReader);
        prepareMetaFile();
        compileUnit(code);
        compileMeta(code);

        if(single){return;}

        for(int i=0;i<1000;i++)
        {
            if(parsedNamespaceTabDone.size()==tool.parsedNamespaceTab().size())
            {
                break;
            }
            compileIterative();
        }
    }

    public void compileMeta(EntityCode code) throws Exception
    {
        String metaFilePath=tool.getConfigParser().getMetaPath()
                + MetaDescriptor.FILE_NAME
                +".java";

        // check if file exists before parsing
        ClassCode codeMeta = new ClassCode(new ICodeNodeClass(ICodeNodeTypeImpl.CLASS));

        if(Files.exists(Paths.get(metaFilePath)))
        {
            FileReader fileReader = new FileReader(metaFilePath);
            System.out.println("Root Meta file loaded: "+metaFilePath);
            try {
                codeMeta = (ClassCode) tool.parseClass(fileReader);
            } catch (Exception e) {
                fileReader.close();
                System.err.println("Please check whether file is proper java class : "+metaFilePath);
                throw e;
            }
        }
        System.out.println("Updating root meta file: "+metaFilePath);
        CodeGenerator codeGenerator=new CodeGenerator(createPrintStream(metaFilePath),tool);
        MetaDescriptor metaDescriptor=new MetaDescriptor(codeGenerator);
        metaDescriptor.generate(codeMeta,code);
    }

    private void prepareMetaFile()
    {
        if(outputOption==OutputOption.FILE)
        {
            Misc.prepareDirectories(tool.getConfigParser().getMetaPath());
        }
    }

    private void compileIterative() throws Exception
    {
        ParsedNamespaceTab parsedNamespaceTabIterate=new ParsedNamespaceTab();
        parsedNamespaceTabIterate.putAll(tool.parsedNamespaceTab());

        for(Map.Entry node : parsedNamespaceTabIterate.entrySet())
        {
            if(parsedNamespaceTabDone.containsKey(node.getKey()))
            {
                continue;
            }
            compileUnit((EntityCode) node.getValue());
            compileMeta((EntityCode) node.getValue());
            parsedNamespaceTabDone.put((String)node.getKey(),null);
        }
    }

    private void compileUnit(EntityCode code) throws Exception
    {
        entityPrinter.print(code);

        String className=FILE_PREFIX
                +code.getClassName();
        String metaFilePath=tool.getConfigParser().getMetaPath()
                +className
                +".java";

        System.out.println("Generating entity meta: "+metaFilePath);
        CodeGenerator codeGenerator=new CodeGenerator(createPrintStream(metaFilePath),tool);
        EntityDescriptor entityDescriptor=new EntityDescriptor(codeGenerator);
        entityDescriptor.generate(code);
    }

    private ccbus.dbtool.backend.compiler.util.PrintStream createPrintStream(String filePath)
    {
        ccbus.dbtool.backend.compiler.util.PrintStream result=new ccbus.dbtool.backend.compiler.util.PrintStream(System.out);

        if(outputOption==OutputOption.FILE)
        {
            try
            {
                result=new PrintStream(new FileOutputStream(filePath));
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }

        return result;
    }
}
