package ccbus.tool.compiler;

import ccbus.tool.compiler.generator.java.CodeGenerator;
import ccbus.tool.compiler.util.PrintStream;
import ccbus.tool.config.parser.IniParser;
import ccbus.tool.intermediate.Node;
import ccbus.tool.intermediate.ParsedNamespaceTab;
import ccbus.tool.parser.java.*;
import ccbus.tool.parser.java.JavaParser;

import ccbus.tool.translator.java2reactserverjava.CompilationNodeValue;
import ccbus.tool.translator.java2splitjava.CompilationUnit;

import ccbus.tool.translator.java2reactserverjava.CompilationUnitNodePair;
import ccbus.tool.util.java.Misc;

import ccbus.tool.util.java.Tool;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JavaToJava implements Compiler
{
    Tool tool;
    JavaParser parser;
    String rootCompilationUnitPath;
    OutputOption outputOption=OutputOption.FILE;
    ParsedNamespaceTab parsedNamespaceTabDone=new ParsedNamespaceTab();
    boolean single=false;

    public JavaToJava(String configPath, String compilationUnitPath) throws Exception
    {
        try
        {
            tool=new Tool(configPath);
            rootCompilationUnitPath=compilationUnitPath;

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public JavaToJava(IniParser parser, String compilationUnitPath) throws Exception
    {
        try
        {
            tool=new Tool(parser);
            rootCompilationUnitPath=compilationUnitPath;

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void compile()
    {
        Node target=compileUnit(rootCompilationUnitPath);

        if(single){return;}

        for(int i=0;i<1000;i++)
        {
            if(parsedNamespaceTabDone.size()==tool.parsedNamespaceTab().size())
            {
                break;
            }
            compileIterative(target);
        }
    }

    private void compileIterative(Node target)
    {
        ParsedNamespaceTab parsedNamespaceTabIterate=new ParsedNamespaceTab();
        parsedNamespaceTabIterate.putAll(tool.parsedNamespaceTab());

        for(Map.Entry node : parsedNamespaceTabIterate.entrySet())
        {
            if(parsedNamespaceTabDone.containsKey(node.getKey()))
            {
                continue;
            }
            ccbus.tool.parser.java.ASTCompilationUnit compilationUnit=
                    (ccbus.tool.parser.java.ASTCompilationUnit )
                            node.getValue();
            if(target!=(Node) node.getValue() &&
                    (tool.isComponentPackage(compilationUnit)
                            ||
                            tool.isPayloadPackage(compilationUnit))
                    )
            {
                compileUnit((Node) node.getValue());
            }
            parsedNamespaceTabDone.put((String)node.getKey(),null);
        }
    }

    private Node compileUnit(String compilationUnitPath)
    {
        Node root=Misc.parseFile(compilationUnitPath,tool);
        compileUnit(root);
        return root;
    }

    private void compileUnit(Node root)
    {
        ccbus.tool.translator.java2splitjava.CompilationUnit cu=
                new ccbus.tool.translator.java2splitjava.CompilationUnit();
        // Translate java to react java intermediate code
        CompilationUnitNodePair compilationUnitPair=
                cu.translateClientServer(root,new ccbus.tool.translator.java2splitjava.TranslatedTree(root,tool));

        // Translate java intermediate to ecma
        CompilationUnit cuEcma=new CompilationUnit();
        ASTCompilationUnit compilationUnit=(ASTCompilationUnit)compilationUnitPair.getClientCompilationUnit();

        System.out.println();
        System.out.println("Translating:");
        System.out.println("CLIENT TARGET:");
        System.out.println("   "+tool.fetchClassName((ccbus.tool.parser.java.ASTCompilationUnit)compilationUnitPair.getClientCompilationUnit()));

        String filePath=getClientFilePath(
                (ccbus.tool.parser.java.ASTCompilationUnit)
                        compilationUnitPair.getClientCompilationUnit());
        System.out.println("   "+filePath);
        printLine();



        PrintStream printStream=createPrintStream(
                filePath
        );
        CodeGenerator codeGenerator=new CodeGenerator(printStream);


        codeGenerator.generate(compilationUnit);
        printLine();


        int i=0;
        for(Node n:compilationUnitPair.getServerCompilationUnitList())
        {
            System.out.println("SERVER CLASS:"+i);
            System.out.println("   "+tool.fetchClassName((ccbus.tool.parser.java.ASTCompilationUnit)n));
            printLine();

            printStream=createPrintStream(
                    getServerFilePath(
                            (ccbus.tool.parser.java.ASTCompilationUnit)
                                    n)
            );

            ccbus.tool.compiler.generator.java.CodeGenerator codeGeneratorServer=
                    new ccbus.tool.compiler.generator.java.CodeGenerator(printStream);
            codeGeneratorServer.generate(n);
            i++;

            printLine();
        }
    }

    private String getClientFilePath(ccbus.tool.parser.java.ASTCompilationUnit compilationUnit)
    {
        StringBuilder result=new StringBuilder();
        result.append(tool.getConfigParser().getClientSrcPath());

        ArrayList<String> relativePath=((CompilationNodeValue)compilationUnit.jjtGetValue()).getRelativePath();

        result.append(Misc.buildPath(relativePath));
        result.append(tool.getFileSeparator());

        if(outputOption==OutputOption.FILE)
        {
            Misc.prepareDirectories(result.toString());
        }

        result.append(tool.fetchClassName(compilationUnit));
        result.append(".java");
        return result.toString();
    }

    private String getServerFilePath(ccbus.tool.parser.java.ASTCompilationUnit compilationUnit)
    {
        StringBuilder result=new StringBuilder();
        result.append(tool.getConfigParser().getProjectSrcPath());

        ArrayList<String> relativePath=tool.parseCompilationUnitRelativePathWorker(compilationUnit);
        List<String> path=Arrays.asList(tool.getServerWorkerPackage());
        result.append(Misc.buildPath(path));
        if(relativePath.size()>0)
        {
            result.append(Misc.buildPath(relativePath));
        }
        result.append(tool.getFileSeparator());

        if(outputOption==OutputOption.FILE)
        {
            Misc.prepareDirectory(result.toString());
        }

        result.append(tool.fetchClassName(compilationUnit));
        result.append(".java");
        return result.toString();
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

    private void printLine()
    {
        if(outputOption==OutputOption.PRINT)
        {
            System.out.println();
            System.out.println("-------------");
        }
    }

    public void setOutputOption(OutputOption outputOption) {
        this.outputOption = outputOption;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }
}
