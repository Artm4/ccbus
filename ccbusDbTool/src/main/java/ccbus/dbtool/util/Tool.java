package ccbus.dbtool.util;

import ccbus.dbtool.config.parser.IniParser;
import ccbus.dbtool.intermediate.*;
import ccbus.dbtool.intermediate.icodeimpl.ICodeNodeClass;
import ccbus.dbtool.intermediate.routineimpl.ClassCode;
import ccbus.dbtool.intermediate.routineimpl.EntityCode;
import ccbus.dbtool.intermediate.routineimpl.RelationCode;
import ccbus.dbtool.parser.Entity;
import ccbus.dbtool.parser.JavaParser;

import java.io.*;
import java.util.HashMap;

public class Tool
{
    static Tool instance;
    private String fileSeparator="/";
    IniParser configParser;
    private ParsedNamespaceTab parsedNamespaceTab;
    private HashMap<String,Boolean> parsingFile=new HashMap();
    public static Tool instance() throws Exception
    {
        if(null==instance)
        {
            instance=new Tool();
        }
        return instance;
    }

    public Tool() throws Exception
    {
        configParser=new IniParser(new FileReader("config.ini"));
        configParser.parse();
        this.fileSeparator = System.getProperty("file.separator");
        this.parsedNamespaceTab=new ParsedNamespaceTab();
    }

    public Tool(IniParser iniParser) throws Exception
    {
        configParser=iniParser;
        this.fileSeparator = System.getProperty("file.separator");
        this.parsedNamespaceTab=new ParsedNamespaceTab();
    }

    public ParsedNamespaceTab parsedNamespaceTab(){return parsedNamespaceTab;}

    public RoutineCode parseClass(Reader stream) throws Exception
    {
        return parseClass(stream,false);
    }

    public RoutineCode parseClass(Reader stream,boolean base) throws Exception
    {
        Entity parser = new Entity(stream);
        parser.init();
        parser.input();
        ICode program=parser.getProgramCode();

        RoutineParser routineParser=new RoutineParser(base);
        for(ICodeNode node:program.getRoot().getChildren())
        {
            RoutineCode code=routineParser.parse((ICodeNodeClass) node);
            parseBase(code,(ICodeNodeClass) node);
            if(code instanceof EntityCode)
            {
                EntityCode entity=(EntityCode) code;
                for(RelationCode relCode: entity.getRelFields())
                {
                    String className=relCode.getRelationType();
                    String filePath=this.fullFilePath(className);
                    if(parsingFile.containsKey(filePath))
                    {
                        continue;
                    }
                    parsingFile.put(filePath,true);
                    System.out.println("Parsing: "+filePath);
                    RoutineCode relRoutineCode=this.parseClass(new FileReader(filePath));
                    relCode.setClassCode((EntityCode) relRoutineCode);
                    parsedNamespaceTab().enter(className,relRoutineCode);
                }
                return code;
            }
            else
            {
                return code;
            }
        }
        throw new Exception("Class cannot be parsed");
    }


    protected RoutineCode parseBase(RoutineCode derrived,ICodeNodeClass codeClass) throws Exception
    {
        String baseName=codeClass.getBaseName();
        if(baseName==null)
        {
            return null;
        }
        String filePath=this.fullFilePath(baseName);
        RoutineCode baseCode=this.parseClass(new FileReader(filePath),true);

        // should merge derrived with baseCode, baseCode should be prefixed to all derrived props;
        derrived.mergePrepend(baseCode);
        return baseCode;
    }


    private String fullFilePath(String className) throws Exception
    {
        for(String entityPath:configParser.getEntityIncludePath())
        {
            String classFile=entityPath+fileSeparator+className+".java";
            File inputFile=new File(classFile);
            if(inputFile.exists())
            {
                return classFile;
            }

        }
        throw new Exception("Cannot find class ["+className+"]. Please check include path in config.ini");
    }

    public PrintStream openFileWriter(String fileName) throws FileNotFoundException,IOException
    {
        File file=new File(fileName);
        if(!file.exists())
        {
            file.createNewFile();
        }
        // copy file with revision
        else
        {
            File newFile=new File(Misc.revisionFilePath(fileName));
            file.renameTo(newFile);
        }
        // Open a new assembly file for writing.
        PrintStream fileWriter =
            new PrintStream(file);

        return fileWriter;
    }

    public IniParser getConfigParser()
    {
        return configParser;
    }
}
