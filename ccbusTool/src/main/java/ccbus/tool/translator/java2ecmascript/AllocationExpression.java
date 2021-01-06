package ccbus.tool.translator.java2ecmascript;

import ccbus.tool.intermediate.Node;
import ccbus.tool.intermediate.SimpleNode;
import ccbus.tool.parser.ecmascript.*;
import ccbus.tool.parser.java.ASTClassOrInterfaceBody;
import ccbus.tool.parser.java.ASTFieldDeclaration;
import ccbus.tool.parser.java.ASTClassOrInterfaceBodyDeclaration;
import ccbus.tool.parser.java.ASTMethodDeclaration;
import ccbus.tool.parser.java.JavaParserTreeConstants;
import ccbus.tool.translator.RecursiveHostTranslator;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.util.java.Tool;

public class AllocationExpression extends GenericTreeTranslator implements TreeTranslator,RecursiveHostTranslator {
    public Node translate(Node node, TranslatedTree tree)
    {
        ASTClassOrInterfaceBody classOrInterfaceBody=
                (ASTClassOrInterfaceBody)
                node.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY,1);

        Node nodeResult;
        if(null!=classOrInterfaceBody)
        {
            nodeResult=translateToLiteral(node,tree,classOrInterfaceBody);
        }
        else
        {
            nodeResult=translateRecursive(node,tree);
        }
        return nodeResult;
    }

    private Node translateRecursive(Node node, TranslatedTree tree)
    {
        ASTAllocationExpression nodeResult=new ASTAllocationExpression(EcmaParserTreeConstants.JJTALLOCATIONEXPRESSION);

        for(int i=0;i<node.jjtGetNumChildren();i++)
        {
            Node nodeSubResult=tree.translateRecursive(node.jjtGetChild(i),this);
            nodeResult.add(nodeSubResult);

        }
        return nodeResult;
    }

    private Node translateToLiteral(Node node, TranslatedTree tree,ASTClassOrInterfaceBody classOrInterfaceBody)
    {
        Node nodeResult=new ASTLiteral(EcmaParserTreeConstants.JJTLITERALDECLARATION);
        ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclaration;
        // iterate through all field declarations
        do
        {
            classOrInterfaceBodyDeclaration=(ASTClassOrInterfaceBodyDeclaration)
                    classOrInterfaceBody.findNextDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION,1);
            if(null!=classOrInterfaceBodyDeclaration)
            {
                ASTFieldDeclaration fieldDeclaration=(ASTFieldDeclaration)
                        classOrInterfaceBodyDeclaration.findFirstDownById(JavaParserTreeConstants.JJTFIELDDECLARATION,1);
                if(null!=fieldDeclaration)
                {
                    translateField(fieldDeclaration, tree, (ASTLiteral) nodeResult);
                }
                else
                {
                    ASTMethodDeclaration methodDeclaration=(ASTMethodDeclaration)
                            classOrInterfaceBodyDeclaration.findFirstDownById(JavaParserTreeConstants.JJTMETHODDECLARATION,1);
                    if(null!=methodDeclaration)
                    {
                        ((Tool)tree.tool()).symTabStack().push(methodDeclaration.symTab());
                        translateMethod(methodDeclaration, tree, (ASTLiteral) nodeResult);
                        ((Tool)tree.tool()).symTabStack().pop();
                    }
                }

            }

        }
        while(null!=classOrInterfaceBodyDeclaration);
        return nodeResult;
    }

    private void translateField(ASTFieldDeclaration fieldDeclaration, TranslatedTree tree, ASTLiteral nodeResult)
    {
        // capture the type for the field declaration
        Node type=fieldDeclaration.findFirstDownById(JavaParserTreeConstants.JJTTYPE);

        ccbus.tool.parser.java.ASTVariableDeclarator variableDeclarator;

        // iterate through all comma separated variable declarations list
        do
        {
            variableDeclarator=
                    (ccbus.tool.parser.java.ASTVariableDeclarator)
                            fieldDeclaration.findNextDownById(JavaParserTreeConstants.JJTVARIABLEDECLARATOR,1);

            if(null==variableDeclarator)
            {
                break;
            }

            ccbus.tool.parser.java.ASTVariableDeclaratorId variableDeclaratorId=(ccbus.tool.parser.java.ASTVariableDeclaratorId)
                    variableDeclarator.findNextDownById(JavaParserTreeConstants.JJTVARIABLEDECLARATORID,1);

            // create copy of the type , because every variable could have bracket suffix which leads
            // to new type
            ASTType typeResult = (ASTType) tree.translate(JavaParserTreeConstants.JJTTYPE, type);

            // get the brackets
            for(int i=1;i<5;i++)
            {
                ccbus.tool.parser.java.ASTBracket bracket=(ccbus.tool.parser.java.ASTBracket)
                        variableDeclaratorId.findNextDownById(JavaParserTreeConstants.JJTBRACKET,1);

                if(null==bracket)
                {
                    break;
                }

                ccbus.tool.parser.ecmascript.ASTBracket bracketResult=(ccbus.tool.parser.ecmascript.ASTBracket)
                        tree.translate(JavaParserTreeConstants.JJTBRACKET,bracket);

                if(null!=typeResult)
                {
                    typeResult.add(bracketResult);
                }
            }

            // create field for literal and add type , variable id and init expression
            Node literalField=new ASTLiteralField(EcmaParserTreeConstants.JJTLITERALFIELD);

            // add type
            literalField.add(typeResult);

            ASTVariableDeclaratorId variableDeclaratorIdResult=
                    (ASTVariableDeclaratorId)
                            tree.translate(JavaParserTreeConstants.JJTVARIABLEDECLARATORID,variableDeclaratorId);

            // add var id
            literalField.add(variableDeclaratorIdResult);

            // add init expression if any
            ccbus.tool.parser.java.ASTVariableInitializer variableInitializer=
                    (ccbus.tool.parser.java.ASTVariableInitializer)
                            variableDeclarator.findFirstDownById(JavaParserTreeConstants.JJTVARIABLEINITIALIZER,1);

            if(null!=variableInitializer)
            {
                ccbus.tool.intermediate.SimpleNode variableInitializerResult =
                        (SimpleNode) tree.translateRecursive(variableInitializer, this);
                literalField.add(variableInitializerResult);
            }

            // add field to literal
            nodeResult.add(literalField);
        }
        while(null!=variableDeclarator);

    }

    private void translateMethod(ASTMethodDeclaration methodDeclaration, TranslatedTree tree, ASTLiteral nodeResult)
    {

        ccbus.tool.parser.java.ASTMethodDeclarator methodDeclarator=(ccbus.tool.parser.java.ASTMethodDeclarator)
                methodDeclaration.findNextDownById(JavaParserTreeConstants.JJTMETHODDECLARATOR,1);

        ccbus.tool.parser.java.ASTIdentifier identifier=(ccbus.tool.parser.java.ASTIdentifier)
                methodDeclarator.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,1);


        // create field for literal and add type , variable id and init expression
        Node literalField=new ASTLiteralField(EcmaParserTreeConstants.JJTLITERALFIELD);

        SimpleNode identifierResult=(SimpleNode)
                tree.translateSingleToken(identifier,EcmaParserTreeConstants.JJTIDENTIFIER);

        ASTVariableDeclaratorId variableDeclaratorIdResult=new ASTVariableDeclaratorId(EcmaParserTreeConstants.JJTVARIABLEDECLARATORID);
        variableDeclaratorIdResult.add(identifierResult);

        literalField.add(variableDeclaratorIdResult);


        ASTLiteralMethod literalMethod=new ASTLiteralMethod(EcmaParserTreeConstants.JJTLITERALMETHOD);


        ccbus.tool.parser.java.ASTFormalParameters formalParameters=
                (ccbus.tool.parser.java.ASTFormalParameters)
                methodDeclarator.findFirstDownById(JavaParserTreeConstants.JJTFORMALPARAMETERS,1);

        ASTFormalParameters formalParametersResult=(ASTFormalParameters)
            tree.translate(JavaParserTreeConstants.JJTFORMALPARAMETERS,formalParameters);

        literalMethod.add(formalParametersResult);


        ccbus.tool.parser.java.ASTBlock block=
                (ccbus.tool.parser.java.ASTBlock)
                        methodDeclaration.findFirstDownById(JavaParserTreeConstants.JJTBLOCK,1);

        ASTBlock blockResult=(ASTBlock)
                tree.translate(JavaParserTreeConstants.JJTBLOCK,block);

        literalMethod.add(blockResult);


        literalField.add(literalMethod);

        nodeResult.add(literalField);
    }
}
