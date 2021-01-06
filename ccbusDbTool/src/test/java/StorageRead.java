import ccbus.dbtool.backend.compiler.CodeGenerator;
import ccbus.dbtool.backend.compiler.generator.server.EntityDescriptor;
import ccbus.dbtool.backend.compiler.generator.server.MetaDescriptor;
import ccbus.dbtool.backend.compiler.util.PrintStream;
import ccbus.dbtool.config.parser.IniParser;
import ccbus.dbtool.intermediate.ParsedNamespaceTab;
import ccbus.dbtool.intermediate.icodeimpl.ICodeNodeClass;
import ccbus.dbtool.intermediate.icodeimpl.ICodeNodeTypeImpl;
import ccbus.dbtool.intermediate.routineimpl.*;
import ccbus.dbtool.util.EntityPrinter;
import ccbus.dbtool.util.Misc;
import ccbus.dbtool.util.Tool;
import ccbus.dbtool.backend.compiler.OutputOption;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;


public class StorageRead {
    public static final String USAGE="Usage: <source file path>";
    public static final String FILE_PREFIX="Meta_";

    EntityPrinter entityPrinter;
    ParsedNamespaceTab parsedNamespaceTabDone=new ParsedNamespaceTab();

    OutputOption outputOption=OutputOption.FILE;
    Tool tool;
    IniParser configParser;
    boolean single=false;

    public StorageRead()
    {

    }

    public static void main(String args[])
    {
        new StorageRead().run(args);
    }

    public void run(String args[])
    {
        try
        {
            tool=new Tool();
            configParser=tool.getConfigParser();

            entityPrinter=new EntityPrinter(System.out);

            int i=0;
            String filePath;
            if(i<args.length)
            {
                filePath=args[i];
            }
            else
            {
                throw new Exception();
            }


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

            compile(filePathMod);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println(USAGE);
        }
    }

    public void compile(String compilationUnitPath) throws Exception
    {
        FileReader fileReader=new FileReader(compilationUnitPath);
        System.out.println("Entity loaded: "+compilationUnitPath);
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
        String metaFilePath=Tool.instance().getConfigParser().getMetaPath()
                +className
                +".java";

        System.out.println("Generating entity meta: "+metaFilePath);
        CodeGenerator codeGenerator=new CodeGenerator(createPrintStream(metaFilePath),tool);
        EntityDescriptor entityDescriptor=new EntityDescriptor(codeGenerator);
        entityDescriptor.generate(code);
    }

    private PrintStream createPrintStream(String filePath)
    {
        PrintStream result=new PrintStream(System.out);

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
