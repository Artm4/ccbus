package ccbus.tool.translator.java2reactserverjava;

import ccbus.tool.intermediate.*;
import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.java.*;
import ccbus.tool.translator.RecursiveHostTranslator;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.util.java.Tool;

public class PrimaryExpression extends GenericTreeTranslator implements TreeTranslator,RecursiveHostTranslator
{
    SymTabStack symTabStack;
    Tool tool;

    public Node translate(Node node, TranslatedTree tree)
    {
        ASTPrimaryPrefix primaryPrefixResult=new ASTPrimaryPrefix(JavaParserTreeConstants.JJTPRIMARYPREFIX);
        ASTPrimarySuffix primarySuffixResult=new ASTPrimarySuffix(JavaParserTreeConstants.JJTPRIMARYSUFFIX);
        tool=((Tool)tree.tool());
        symTabStack=tool.symTabStack();

        translateBuildInTypeCall(node,tree);
        node.resetNextSearch();

        ASTPrimaryPrefix primaryPrefix=
                (ASTPrimaryPrefix)
                        node.findFirstDownById(JavaParserTreeConstants.JJTPRIMARYPREFIX,1);

        ASTPrimarySuffix primarySuffix=
                (ASTPrimarySuffix)
                        node.findNextDownById(JavaParserTreeConstants.JJTPRIMARYSUFFIX,1);

        // Allocation expression, no class access
        ASTAllocationExpression allocationExpression=(ASTAllocationExpression)
                primaryPrefix.findFirstDownById(JavaParserTreeConstants.JJTALLOCATIONEXPRESSION,1);
        if(null!=allocationExpression)
        {
            tree.translateRecursive(allocationExpression,this);
            return null;
        }

        if(null==primarySuffix)
        {
            primarySuffix=new ASTPrimarySuffix(JavaParserTreeConstants.JJTPRIMARYSUFFIX);
        }

        validate(primaryPrefix,primarySuffix);

        // changes only prefix
        translateName(tree,primaryPrefixResult,primarySuffixResult,primaryPrefix,primarySuffix);

        // changes only suffix
        if(primarySuffix.jjtGetNumChildren()>0)
        {
            translateThis(tree, primaryPrefixResult, primarySuffixResult, primaryPrefix, primarySuffix);
        }

        if(primaryPrefixResult.jjtGetNumChildren()>0)
        {
            primaryPrefix.resetChildren();//??? losing tree
            for (int i = 0; i < primaryPrefixResult.jjtGetNumChildren(); i++)
            {
                primaryPrefix.add(primaryPrefixResult.jjtGetChild(i));
            }
        }

        if(primarySuffixResult.jjtGetNumChildren()>0)
        {
            primarySuffix.resetChildren();
            for (int i = 0; i < primarySuffixResult.jjtGetNumChildren(); i++)
            {
                primarySuffix.add(primarySuffixResult.jjtGetChild(i));
            }
        }

        // Translate recursive the prefix;
        for (int i = 0; i < primaryPrefix.jjtGetNumChildren(); i++) {
            tree.translateRecursive(primaryPrefix.jjtGetChild(i), this);
        }

        // Translate recursive the suffix;
        for (int i = 0; i < primarySuffix.jjtGetNumChildren(); i++) {
            tree.translateRecursive(primarySuffix.jjtGetChild(i), this);
        }


        // There might be more suffix
        primarySuffix=
                (ASTPrimarySuffix)
                        node.findNextDownById(JavaParserTreeConstants.JJTPRIMARYSUFFIX,1);

        while(null!=primarySuffix)
        {
            tree.translateRecursive(primarySuffix,this);
            primarySuffix=
                    (ASTPrimarySuffix)
                            node.findNextDownById(JavaParserTreeConstants.JJTPRIMARYSUFFIX,1);
        }


        node.resetNextSearch();
        return null;
    }

    private void translateBuildInTypeCall(Node node,TranslatedTree tree)
    {
        ASTArguments arguments=
                (ASTArguments)
                        node.findFirstDownById(JavaParserTreeConstants.JJTARGUMENTS,2);

        // there is no call
        if(null==arguments)
        {
            return;
        }

        ASTIdentifier next=
                (ASTIdentifier)
                node.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER,3);

        ASTIdentifier identifier=next;
        int depth=0;
        while(null!=next)
        {
            next=(ASTIdentifier)
                        node.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER,3);
            depth++;
        }


        // It is method call to this which should be component.
        if(1==depth)
        {
            return;
        }

        node.resetNextSearch();
        identifier=
                (ASTIdentifier)
                        node.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER,3);

        SymTabEntry symTabEntry=symTabStack.lookup(identifier.image());
        if(null==symTabEntry)
        {
            // check if it is static call
            SymTab symTabNamespace=symTabStack.lookupSymTab(identifier.image());
            if(null!=symTabNamespace)
            {
                identifier=(ASTIdentifier)
                        node.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER,3);
                symTabEntry=symTabNamespace.lookup(identifier.image());
            }
            if(symTabEntry==null)
            {
                // should throw error
                tool.errorTranslate(identifier, "Name not declared");
            }
        }

        if(!symTabEntry.hasAttribute(ccbus.tool.intermediate.symtabimpl.SymTabKeyImpl.REFERENCE_TYPE))
        {
            // It is not reference skip it
            return;
        }

        String referenceType=(String)
                symTabEntry.getAttribute(ccbus.tool.intermediate.symtabimpl.SymTabKeyImpl.REFERENCE_TYPE);

        String referenceTypePrevious=referenceType;


        while(null!=identifier)
        {
            ASTIdentifier identifierPrevious=identifier;
            identifier=
                    (ASTIdentifier)
                            node.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER,3);
            if(null==identifier)
            {
                // this is last identifier
                translateCallMethodBuildIn(referenceTypePrevious,identifierPrevious,arguments);
            }
            else
            {
                SymTab symTabNamespace=symTabStack.lookupSymTab(referenceType);
                if(null==symTabNamespace)
                {
                    // should throw error
                    tool.errorTranslate(identifier,"Reference Type \"" +referenceType+"\" not declared");
                }
                symTabEntry=symTabNamespace.lookup(identifier.image());
                if(null==symTabEntry)
                {
                    // should throw error
                    tool.errorTranslate(identifier,"Name not declared");
                }

                if(symTabEntry.hasAttribute(ccbus.tool.intermediate.symtabimpl.SymTabKeyImpl.REFERENCE_TYPE))
                {
                    referenceTypePrevious=referenceType;
                    referenceType = (String)
                            symTabEntry.getAttribute(ccbus.tool.intermediate.symtabimpl.SymTabKeyImpl.REFERENCE_TYPE);
                }
                else {
                    // It is not reference
                    if(symTabEntry.hasAttribute(ccbus.tool.intermediate.symtabimpl.SymTabKeyImpl.TYPE_NAME))
                    {
                        referenceTypePrevious=referenceType;
                        referenceType=(String)
                                symTabEntry.getAttribute(ccbus.tool.intermediate.symtabimpl.SymTabKeyImpl.TYPE_NAME);
                    }
                    // It is method
                    else
                    {
                        referenceTypePrevious=referenceType;
                    }
                }
            }
        }
    }

    private void translateCallMethodBuildIn(String buildInType,ASTIdentifier identifier,ASTArguments arguments)
    {
        // lang supported types
        if(buildInType.equals("String"))
        {
            translateString(identifier,arguments);
            return;
        }
        String importName=symTabStack.lookupImport(buildInType);
        // not predefined type
        if(null==importName)
        {
            return;
        }
        // Should create sym table based on full name with package path, not just type name.
        buildInType=importName;
        if(buildInType.equals("java.util.ArrayList")||buildInType.equals("java.util.List"))
        {
            translateArrayList(identifier,arguments);
        }
        else
        if(buildInType.equals("java.util.HashMap"))
        {
            translateHashMap(identifier,arguments);
        }
        else
        if(buildInType.equals("java.lang.String"))
        {
            translateString(identifier,arguments);
        }
        else
        if(buildInType.equals("java.util.function.Function") || buildInType.equals("java.util.function.BiFunction"))
        {
            translateFunction(identifier,arguments);
        }
        else
        if(buildInType.equals("org.joda.time.DateTime") || buildInType.equals("java.util.Date"))
        {
            translateDate(identifier,arguments);
        }
    }

    private void translateDate(ASTIdentifier identifier,ASTArguments arguments)
    {
        String name=identifier.image();

        ccbus.tool.parser.java.Token tokenIdentifier= ccbus.tool.parser.java.Token.newToken(
                JavaParserConstants.IDENTIFIER, "");

        if(name.equals("toString"))
        {
            tokenIdentifier.image="toISOString";
        }
        else
        {
            // should throw error
            tool.errorTranslate(identifier,"Date method not supported");
        }

        identifier.jjtSetFirstToken(tokenIdentifier);
        identifier.jjtSetLastToken(tokenIdentifier);
    }

    private void translateFunction(ASTIdentifier identifier,ASTArguments arguments)
    {
        String name=identifier.image();

        if(name.equals("apply"))
        {
            tool.createToken(JavaParserConstants.IDENTIFIER,"",identifier);

            ASTPrimarySuffix parent=(ASTPrimarySuffix)arguments.jjtGetParent();
            ASTPrimaryExpression primaryExpression=(ASTPrimaryExpression) parent.jjtGetParent();

            identifier.detach();

            this.detachLastDot(primaryExpression);
        }
        else
        {
            // should throw error
            tool.errorTranslate(identifier,"Function method not supported");
        }
    }

    private void translateString(ASTIdentifier identifier,ASTArguments arguments)
    {
        String name=identifier.image();

        ccbus.tool.parser.java.Token tokenIdentifier= ccbus.tool.parser.java.Token.newToken(
                JavaParserConstants.IDENTIFIER, "");

        if(name.equals("length"))
        {
            tokenIdentifier.image="length";
            Node parent=arguments.jjtGetParent();
            arguments.detach();
            if(0==parent.jjtGetNumChildren())
            {
                parent.detach();
            }
        }
        else
        if(name.equals("matches"))
        {
            tokenIdentifier.image="match";
        }
        else
        if(name.equals("replace"))
        {
            tokenIdentifier.image="replace";
        }
        else
        if(name.equals("split"))
        {
            tokenIdentifier.image="split";
        }
        else
        if(name.equals("join"))
        {
            tokenIdentifier.image="concat";
        }
        else
        if(name.equals("compareTo"))
        {
            tokenIdentifier.image="localeCompare";
        }
        else
        {
            // should throw error
            tool.errorTranslate(identifier,"String method not supported");
        }

        identifier.jjtSetFirstToken(tokenIdentifier);
        identifier.jjtSetLastToken(tokenIdentifier);
    }

    private void detachLastDot(ASTPrimaryExpression primaryExpression)
    {
        ASTDotToken dotToken;
        ASTDotToken dotTokenLast=null;
        primaryExpression.resetNextSearch();
        do
        {
            dotToken=
                    (ASTDotToken)
                            primaryExpression.findNextDownById(JavaParserTreeConstants.JJTDOTTOKEN,3);
            if(null!=dotToken)
            {
                dotTokenLast=dotToken;
            }
        }
        while(null!=dotToken);

        if(null!=dotTokenLast)
        {
            Node parentDotToken=dotTokenLast.jjtGetParent();
            dotTokenLast.detach();
            if(0==parentDotToken.jjtGetNumChildren())
            {
                parentDotToken.detach();
            }
        }
    }

    private void translateArrayList(ASTIdentifier identifier,ASTArguments arguments)
    {
        String name=identifier.image();

        ccbus.tool.parser.java.Token tokenIdentifier= ccbus.tool.parser.java.Token.newToken(
                JavaParserConstants.IDENTIFIER, "");

        if(name.equals("size"))
        {
            tokenIdentifier.image="length";
            Node parent=arguments.jjtGetParent();
            arguments.detach();
            if(0==parent.jjtGetNumChildren())
            {
                parent.detach();
            }
        }
        else
        if(name.equals("add"))
        {
            tokenIdentifier.image="push";
        }
        else
        if(name.equals("subList"))
        {
            tokenIdentifier.image="slice";
        }
        else
        if(name.equals("indexOf"))
        {
            tokenIdentifier.image="indexOf";
        }
        else
        if(name.equals("contains"))
        {
            tokenIdentifier.image="includes";
        }
        else
        if(name.equals("get"))
        {
            ASTArgumentList argumentList=(ASTArgumentList)
                    arguments.findFirstDownById(JavaParserTreeConstants.JJTARGUMENTLIST,1);
            ASTExpression expression=
                    (ASTExpression)
                    argumentList.findFirstDownById(JavaParserTreeConstants.JJTEXPRESSION,1);
            ASTPrimarySuffix parent=(ASTPrimarySuffix)arguments.jjtGetParent();
            ASTPrimaryExpression primaryExpression=(ASTPrimaryExpression) parent.jjtGetParent();

            identifier.detach();
            arguments.detach();

            this.detachLastDot(primaryExpression);

            parent.add(tool.createNodeLBracket());
            parent.add(expression);
            parent.add(tool.createNodeRBracket());

            return;

        }
        else
        if(name.equals("remove"))
        {
            tokenIdentifier.image="splice";
            ASTArgumentList argumentList=(ASTArgumentList)
                arguments.findFirstDownById(JavaParserTreeConstants.JJTARGUMENTLIST,1);

            ASTExpression result=new ASTExpression(JavaParserTreeConstants.JJTEXPRESSION);
            ASTLiteral literal=new ASTLiteral(JavaParserTreeConstants.JJTLITERAL);
            ccbus.tool.parser.java.Token tokenLiteral= ccbus.tool.parser.java.Token.newToken(
                    JavaParserConstants.INTEGER_LITERAL, "1");
            literal.jjtSetFirstToken(tokenLiteral);
            literal.jjtSetLastToken(tokenLiteral);

            result.add(tool.createNodeComma());
            result.add(literal);

            argumentList.add(result);
        }
        else
        {
            // should throw error
            tool.errorTranslate(identifier,"ArrayList method not supported");
        }
        identifier.jjtSetFirstToken(tokenIdentifier);
        identifier.jjtSetLastToken(tokenIdentifier);
    }

    private void translateHashMap(ASTIdentifier identifier,ASTArguments arguments)
    {
        String name=identifier.image();

        ccbus.tool.parser.java.Token tokenIdentifier= ccbus.tool.parser.java.Token.newToken(
                JavaParserConstants.IDENTIFIER, "");

        identifier.jjtSetFirstToken(tokenIdentifier);
        identifier.jjtSetLastToken(tokenIdentifier);

        if(name.equals("put"))
        {
            tokenIdentifier.image="set";
        }
        else
        if(name.equals("containsKey"))
        {
            tokenIdentifier.image="has";
        }
        else
        if(name.equals("get"))
        {
            tokenIdentifier.image="get";
        }
        else
        if(name.equals("remove"))
        {
            tokenIdentifier.image="delete";
        }

        else
        {
            // should throw error
            tool.errorTranslate(identifier,"HashMap method not supported");
        }
    }

    private void translateThis(TranslatedTree tree,ASTPrimaryPrefix primaryPrefixResult,ASTPrimarySuffix primarySuffixResult,ASTPrimaryPrefix primaryPrefix,ASTPrimarySuffix primarySuffix)
    {
        ASTThisToken thisToken=
                (ASTThisToken)
                        primaryPrefix.findFirstDownById(JavaParserTreeConstants.JJTTHISTOKEN,1);

        if(null==thisToken)
        {
            return;
        }

        primaryPrefixResult.add(thisToken);

        ASTIdentifier identifier=
                (ASTIdentifier)
                        primarySuffix.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,2);

        translateAddStatePropPrefix(tree,null,primarySuffixResult,identifier,primaryPrefix);

        for(int i=0;i<primarySuffix.jjtGetNumChildren();i++)
        {
            primarySuffixResult.add(primarySuffix.jjtGetChild(i));
        }
    }


    private void translateName(TranslatedTree tree,ASTPrimaryPrefix primaryPrefixResult,
                               ASTPrimarySuffix primarySuffixResult,ASTPrimaryPrefix primaryPrefix,
                               ASTPrimarySuffix primarySuffix)
    {
        ASTName name=
                (ASTName)
                        primaryPrefix.findFirstDownById(JavaParserTreeConstants.JJTNAME,1);

        if(null==name)
        {
            return;
        }
        ASTIdentifier identifier=
                (ASTIdentifier)
                        name.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,1);

        translateAddThisPrefix(tree,primaryPrefixResult,identifier);
        translateAddStatePropPrefix(tree,primaryPrefixResult,null,identifier,primaryPrefix);

        for(int i=0;i<name.jjtGetNumChildren();i++)
        {
            primaryPrefixResult.add(name.jjtGetChild(i));
        }

        for(int i=0;i<primarySuffix.jjtGetNumChildren();i++)
        {
            primarySuffixResult.add(primarySuffix.jjtGetChild(i));
        }
    }

    private void translateAddThisPrefix(TranslatedTree tree,ASTPrimaryPrefix primaryPrefixResult,ASTIdentifier identifier)
    {
        SymTabEntry symTabEntry=symTabStack.lookup(identifier.image());
        if(null==symTabEntry)
        {
            if(null!=symTabStack.lookupSymTab(identifier.image()))
            {
                // it is class/interface no need of this prefix
                return;
            }
            tool.errorTranslate(identifier,"Name not declared");
        }

        // it is in method declaration TO CHECK
        if(!symTabEntry.getSymTab().isClass())
        {
            return;
        }

        ASTThisToken thisToken=new ASTThisToken(JavaParserTreeConstants.JJTTHISTOKEN);
        ccbus.tool.parser.java.Token tokenIdentifier= ccbus.tool.parser.java.Token.newToken(
                JavaParserConstants.THIS,   "this");
        thisToken.jjtSetFirstToken(tokenIdentifier);
        thisToken.jjtSetLastToken(tokenIdentifier);

        ASTDotToken dotNode=((Tool)tree.tool()).createNodeDot();

        primaryPrefixResult.add(thisToken);
        primaryPrefixResult.add(dotNode);

    }

    private Node translateAddStatePropPrefix(TranslatedTree tree,ASTPrimaryPrefix primaryPrefixResult,ASTPrimarySuffix primarySuffixResult,ASTIdentifier identifier,ASTPrimaryPrefix primaryPrefix)
    {
        if(tool.isMethodCall(identifier))
        {
            //return null;
        }

        SymTabEntry symTabEntry=symTabStack.lookup(identifier.image());

        String prefixIdentifier="";

        ASTThisToken thisToken=
                (ASTThisToken)
                        primaryPrefix.findFirstDownById(JavaParserTreeConstants.JJTTHISTOKEN,1);


        if (null == symTabEntry)
        {
            if (null != symTabStack.lookupSymTab(identifier.image()))
            {
                // it is class/interface name should implement taking static/method reference
                return null;
            }

            // should throw error
            tool.errorTranslate(identifier,"Name not declared");
        }

        if(null!=thisToken)
        {
            symTabEntry=symTabStack.lookupSkipTop(identifier.image());
        }

        if (symTabEntry.hasAttribute(SymTabKeyImpl.STATE_FIELD))
        {
            prefixIdentifier = "state";
        }

        if (symTabEntry.hasAttribute(SymTabKeyImpl.PROP_FIELD))
        {
            prefixIdentifier = "props";
        }


        if(prefixIdentifier.length()>0)
        {
            ASTIdentifier identifierResult=((Tool)tree.tool()).createNodeIdentifier(prefixIdentifier);

            ASTDotToken dotNode=((Tool)tree.tool()).createNodeDot();

            if(null!=primaryPrefixResult)
            {
                primaryPrefixResult.add(identifierResult);
                primaryPrefixResult.add(dotNode);
            }
            else
            {
                primarySuffixResult.add(dotNode);
                primarySuffixResult.add(identifierResult);
            }
        }
        return null;
    }

    private void validate(ASTPrimaryPrefix primaryPrefix,ASTPrimarySuffix primarySuffix)
    {
        ASTSuperToken superToken=
                (ASTSuperToken)
                        primaryPrefix.findFirstDownById(JavaParserTreeConstants.JJTSUPERTOKEN,1);

        if(null!=superToken)
        {
            // should throw error
            tool.errorTranslate(superToken,"Super token not allowed");
        }

        ASTClassToken classToken=
                (ASTClassToken)
                        primaryPrefix.findFirstDownById(JavaParserTreeConstants.JJTCLASSTOKEN,1);

        if(null!=classToken)
        {
            // should throw error
            tool.errorTranslate(classToken,"Class token not allowed");
        }

        ASTThisToken thisToken=
                (ASTThisToken)
                        primaryPrefix.findFirstDownById(JavaParserTreeConstants.JJTTHISTOKEN,1);

        if(null!=thisToken)
        {
            if(primaryPrefix.jjtGetChild(0)!=thisToken)
            {
                // should throw error
                tool.errorTranslate(thisToken,"Class prefix not allowed");
            }
        }
    }
}
