package ccbus.tool;

import ccbus.tool.compiler.generator.ecmascript.CodeGenerator;
import ccbus.tool.compiler.util.PrintStream;
import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.ecmascript.ASTCompilationUnit;
import ccbus.tool.translator.java2ecmascript.CompilationUnit;
import ccbus.tool.translator.java2ecmascript.TranslatedTree;
import ccbus.tool.translator.java2reactserverjava.CompilationUnitNodePair;
import ccbus.tool.util.java.Misc;
import ccbus.tool.util.java.Tool;

import java.io.IOException;

public class JavaTranslator {
    Tool tool;
    String rootCompilationUnitPath;

    public static void main(String[] args)
    {
        String configPath="configCcbusJava.ini";
        String compilationUnitPath="/home/art4/data/dev/java/ccbusdemo/src/main/java/ccbus/demo/ccbus/service/ServiceSome.java";
        //String compilationUnitPath="/home/art4/data/dev/java/ccbusdemo/src/main/java/ccbus/demo/ccbus/payload/packp/OtherPayload.java";
        JavaTranslator javaTranslator=new JavaTranslator(configPath,compilationUnitPath);
        javaTranslator.compileUnit(compilationUnitPath);
    }

    public JavaTranslator(String configPath,String compilationUnitPath) {
        try
        {
            tool=new Tool(configPath);
            rootCompilationUnitPath=compilationUnitPath;

        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Node compileUnit(String compilationUnitPath)
    {
        Node root=Misc.parseFile(compilationUnitPath,tool);

        ccbus.tool.translator.java2splitjava.CompilationUnit cu=
                new ccbus.tool.translator.java2splitjava.CompilationUnit();

        // Translate java to react java intermediate code
        CompilationUnitNodePair compilationUnitPair=
                cu.translateClientServer(root,new ccbus.tool.translator.java2splitjava.TranslatedTree(root,tool));

        compileUnit(compilationUnitPair.getClientCompilationUnit());
        //compileUnit(compilationUnitPair.getServerCompilationUnitList().get(0));
        //compileUnit(root);

        return root;
    }

    private void compileUnitFlat(Node root)
    {
        ccbus.tool.compiler.generator.javaflat.CodeGenerator codeGeneratorServer=
                new ccbus.tool.compiler.generator.javaflat.CodeGenerator(new PrintStream(System.out));
        codeGeneratorServer.generate(root);


    }

    private void compileUnit(Node root)
    {
        ccbus.tool.compiler.generator.java.CodeGenerator codeGeneratorServer=
                new ccbus.tool.compiler.generator.java.CodeGenerator(new PrintStream(System.out));
        codeGeneratorServer.generate(root);


    }


    private void compileUnitEcma(Node root)
    {

        CompilationUnit cuEcma=new CompilationUnit();
        ASTCompilationUnit compilationUnitEcma=(ASTCompilationUnit)cuEcma.translate(
                root,
                new TranslatedTree(root,tool));

        CodeGenerator codeGenerator=new CodeGenerator(new PrintStream(System.out));
        codeGenerator.generate(compilationUnitEcma);
    }

}
