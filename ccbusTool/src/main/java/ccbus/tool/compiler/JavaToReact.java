package ccbus.tool.compiler;

import ccbus.tool.compiler.generator.ecmascript.CodeGenerator;
import ccbus.tool.compiler.generator.ecmascript.ImportDeclaration;
import ccbus.tool.compiler.util.PrintStream;
import ccbus.tool.config.parser.IniParser;
import ccbus.tool.intermediate.Node;
import ccbus.tool.intermediate.ParsedNamespaceTab;
import ccbus.tool.parser.ecmascript.*;
import ccbus.tool.parser.java.JavaParser;
import ccbus.tool.parser.react.ParseException;
import ccbus.tool.parser.react.ReactParser;
import ccbus.tool.translator.java2ecmascript.CompilationUnit;
import ccbus.tool.translator.java2ecmascript.TranslatedTree;
import ccbus.tool.translator.java2reactserverjava.CompilationUnitNodePair;
import ccbus.tool.util.java.Misc;
import ccbus.tool.util.java.PostAction;
import ccbus.tool.util.java.Tool;
import ccbus.tool.util.react.TokenList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class JavaToReact implements Compiler
{
    Tool tool;
    JavaParser parser;
    String rootCompilationUnitPath;
    OutputOption outputOption=OutputOption.FILE;
    ParsedNamespaceTab parsedNamespaceTabDone=new ParsedNamespaceTab();
    boolean single=false;

    public JavaToReact(String configPath,String compilationUnitPath) throws Exception
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

    public JavaToReact(IniParser parser, String compilationUnitPath) throws Exception
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
        ccbus.tool.translator.java2reactserverjava.CompilationUnit cu=
                new ccbus.tool.translator.java2reactserverjava.CompilationUnit();
        // Translate java to react java intermediate code
        CompilationUnitNodePair compilationUnitPair=
                cu.translateClientServer(root,new ccbus.tool.translator.java2reactserverjava.TranslatedTree(root,tool));

        // Translate java intermediate to ecma
        CompilationUnit cuEcma=new CompilationUnit();
        ASTCompilationUnit compilationUnitEcma=(ASTCompilationUnit)cuEcma.translate(compilationUnitPair.getClientCompilationUnit(),new TranslatedTree(compilationUnitPair.getClientCompilationUnit(),tool));

        System.out.println();
        System.out.println("Translating:");
        System.out.println("CLIENT TARGET:");
        System.out.println("   "+tool.fetchClassName((ccbus.tool.parser.java.ASTCompilationUnit)compilationUnitPair.getClientCompilationUnit()));

        String filePath=getClientFilePath(
                (ccbus.tool.parser.java.ASTCompilationUnit)
                        compilationUnitPair.getClientCompilationUnit());
        System.out.println("   "+filePath);
        printLine();

        ReactParser parser=parseRender((ccbus.tool.parser.java.ASTCompilationUnit)
                compilationUnitPair.getClientCompilationUnit(),filePath);

        PrintStream printStream=createPrintStream(
                filePath
        );
        CodeGenerator codeGenerator=new CodeGenerator(printStream);

        //Remove duplicated imports , compare by import name, keep client
        prepareImportDeclaration(compilationUnitEcma,parser);

        if(tool.isComponentPackage( (ccbus.tool.parser.java.ASTCompilationUnit)
                compilationUnitPair.getClientCompilationUnit()))
        {
            generateRootRender(parser,codeGenerator);
        }
        generateRender(parser, codeGenerator);

        codeGenerator.generate(compilationUnitEcma);
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

    private void prepareImportDeclaration(ASTCompilationUnit compilationUnitEcma,ReactParser parser)
    {
        if(null==parser){return;}
        ASTImportDeclaration importDeclaration=(ASTImportDeclaration) compilationUnitEcma
                .findNextDownById(EcmaParserTreeConstants.JJTIMPORTDECLARATION,1);

        ArrayList<ASTImportDeclaration> forDetach=new ArrayList<>();

        while(null!=importDeclaration)
        {
            ASTIdentifier importNamed=
                    (ASTIdentifier)
                            importDeclaration.findFirstDownById(EcmaParserTreeConstants.JJTIDENTIFIER,2);

            if(parser.getImportedNames().containsKey(importNamed.image()))
            {
                forDetach.add(importDeclaration);
            }


            importDeclaration=(ASTImportDeclaration) compilationUnitEcma
                    .findNextDownById(EcmaParserTreeConstants.JJTIMPORTDECLARATION,1);
        }

        for(ASTImportDeclaration item:forDetach)
        {
            item.detach();
        }

        compilationUnitEcma.resetNextSearch();
    }

    private ReactParser parseRender(ccbus.tool.parser.java.ASTCompilationUnit compilationUnit,String filePath)
    {
//        ArrayList<String> relativePath = tool.parseCompilationUnitRelativePathClient(compilationUnit);
//        if (!relativePath.get(0).equals("components"))
//        {
//            return null;
//        }
        File file=new File(filePath);
        if(false==file.exists())
        {
            return null;
        }
        ReactParser parser = new ReactParser(filePath);
        try
        {
            parser.CompilationUnit();
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        ArrayList<TokenList> tokenList = parser.getRenderBodyList();
        ArrayList<TokenList> tokenListFunction = parser.getRenderFunctionBodyList();
        return parser;
    }

    private void generateRootRender(ReactParser parser,CodeGenerator codeGenerator)
    {
        if(parser==null)
        {
            PostAction<CodeGenerator, TokenList, CodeGenerator> postAction = new PostAction<>(codeGenerator, new TokenList(), (CodeGenerator n, TokenList tl) ->
            {
                codeGenerator.emitLn();
                codeGenerator.emitLn();
                codeGenerator.emit("render");
                codeGenerator.emitKind(EcmaParserConstants.LPAREN);
                codeGenerator.emitKind(EcmaParserConstants.RPAREN);
                codeGenerator.emitLn();
                codeGenerator.emitKind(EcmaParserConstants.LBRACE);

                codeGenerator.indentAdd();
                codeGenerator.emitLn();

                codeGenerator.emit("return(<span>!</span>)");
                codeGenerator.indentRemove();

                codeGenerator.emitLn();
                codeGenerator.emitKind(EcmaParserConstants.RBRACE);
                return codeGenerator;
            });
            codeGenerator.postActionMethodDeclaration.add(postAction);
            return;
        }
    }

    private void generateRender(ReactParser parser,CodeGenerator codeGenerator)
    {
        if(parser==null)
        {
            return;
        }

        for(TokenList tokens: parser.getRenderBodyList())
        {
            PostAction<CodeGenerator, TokenList, CodeGenerator> postAction = new PostAction<>(codeGenerator, tokens, (CodeGenerator n, TokenList tl) ->
            {
                codeGenerator.emitTokenList(tl);

                return codeGenerator;
            });
            codeGenerator.postActionMethodDeclaration.add(postAction);
        }

        for(TokenList tokens:parser.getRenderFunctionBodyList())
        {
            PostAction<CodeGenerator, TokenList, CodeGenerator> postAction = new PostAction<>(codeGenerator, tokens, (CodeGenerator n, TokenList tl) ->
            {
                codeGenerator.emit("function");
                codeGenerator.emitSpace();
                codeGenerator.emitTokenList(tl);

                return codeGenerator;
            });
            codeGenerator.postActionFunctionDeclaration.add(postAction);
        }

        for(TokenList tokens: parser.getImportList())
        {
            PostAction<CodeGenerator, TokenList, CodeGenerator> postAction = new PostAction<>(codeGenerator, tokens, (CodeGenerator n, TokenList tl) ->
            {
                codeGenerator.emitTokenList(tl);

                return codeGenerator;
            });
            codeGenerator.preClassDeclaration.add(postAction);
        }

    }

    private String getClientFilePath(ccbus.tool.parser.java.ASTCompilationUnit compilationUnit)
    {
        StringBuilder result=new StringBuilder();
        result.append(tool.getConfigParser().getClientSrcPath());

        ArrayList<String> relativePath=tool.parseCompilationUnitRelativePathClient(compilationUnit);

        result.append(Misc.buildPath(relativePath));
        result.append(tool.getFileSeparator());

        if(outputOption==OutputOption.FILE)
        {
            Misc.prepareDirectories(result.toString());
        }

        result.append(tool.fetchClassName(compilationUnit));
        result.append(".js");
        return result.toString();
    }

    private String getServerFilePath(ccbus.tool.parser.java.ASTCompilationUnit compilationUnit)
    {
        StringBuilder result=new StringBuilder();
        result.append(tool.getConfigParser().getProjectSrcPath());

        ArrayList<String> relativePath=tool.parseCompilationUnitRelativePathWorker(compilationUnit);
        List<String> path=Arrays.asList(tool.getServerWorkerPackageForPlatform());
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
