package ccbus.tool.translator.java2reactserverjava;

import ccbus.tool.intermediate.Node;
import ccbus.tool.intermediate.SymTabEntry;
import ccbus.tool.intermediate.SymTabStack;
import ccbus.tool.parser.java.*;
import ccbus.tool.translator.RecursiveHostTranslator;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.util.java.Tool;

public class ClassOrInterfaceBody extends GenericTreeTranslator implements TreeTranslator,RecursiveHostTranslator
{
    Tool tool;

    private class InternalState
    {
        public boolean stateSequence;
        public boolean propSequence;
        public ASTClassOrInterfaceBody allocationExpressionBodyState;
        public ASTClassOrInterfaceBody allocationExpressionBodyProp;
        public ASTClassOrInterfaceBody allocationExpressionBodyPropInit;
    }

    public Node translate(Node node, TranslatedTree tree)
    {
        ASTClassOrInterfaceDeclaration classOrInterfaceDeclaration=(ASTClassOrInterfaceDeclaration)
                node.findFirstUpById(JavaParserTreeConstants.JJTCLASSORINTERFACEDECLARATION);
        ASTExtendsList extendsList=(ASTExtendsList)
                classOrInterfaceDeclaration.findFirstDownById(JavaParserTreeConstants.JJTEXTENDSLIST,2);

        if(null!=extendsList)
        {
            ASTIdentifier typeIdentifier=(ASTIdentifier)
                    extendsList.findFirstDownById(JavaParserTreeConstants.JJTIDENTIFIER,2);
            if(0==typeIdentifier.jjtGetFirstToken().image.compareTo("WorkerService"))
            {
                (new ClassOrInterfaceBodyService()).translate(node,tree);
                return null;
            }
        }

        InternalState internalState=new InternalState();
        Node resultChild;
        Node result=new ASTClassOrInterfaceBody(node.getId());
        tool=((Tool)tree.tool());
        ASTClassOrInterfaceBodyDeclaration state=createState(internalState);
        ASTClassOrInterfaceBodyDeclaration prop=createProp(internalState);
        ASTClassOrInterfaceBodyDeclaration propInit=createPropInit(internalState);

        for(int i=0;i<node.jjtGetNumChildren();i++)
        {
            // get ClassOrInterfaceBodyDeclaration and his only one child
            ASTClassOrInterfaceBodyDeclaration classOrInterfaceBodyDeclaration=
                    (ASTClassOrInterfaceBodyDeclaration )
                    node.jjtGetChild(i);

            ASTFieldDeclaration fieldDeclaration=
                    (ASTFieldDeclaration)
                    classOrInterfaceBodyDeclaration
                            .findFirstDownById(JavaParserTreeConstants.JJTFIELDDECLARATION,1);

            if(null!=fieldDeclaration)
            {
                tree.translateRecursive(classOrInterfaceBodyDeclaration,this);
                resultChild = translateBodyDeclaration(classOrInterfaceBodyDeclaration, tree,internalState);
                if (null != resultChild)
                {
                    result.add(resultChild);
                }
            }
            else
            {
                tree.translateRecursive(classOrInterfaceBodyDeclaration,this);
                result.add(classOrInterfaceBodyDeclaration);
            }
        }

        node.resetChildren();
        if(internalState.allocationExpressionBodyState.jjtGetNumChildren()>0)
        {
            node.add(state);
        }
        if(internalState.allocationExpressionBodyProp.jjtGetNumChildren()>0)
        {
            node.add(prop);
            tool.insertImport(node,
                tool.createImport(
                        tool.createName("ccbus","connect","core","prop_types","PropTypes")
                )
            );
        }
        if(internalState.allocationExpressionBodyPropInit.jjtGetNumChildren()>0)
        {
            node.add(propInit);
        }
        for (int i=0;i<result.jjtGetNumChildren();i++)
        {
            node.add(result.jjtGetChild(i));
        }

        return result;
    }

    private ASTClassOrInterfaceBodyDeclaration createState(InternalState internalState)
    {
        ASTClassOrInterfaceBodyDeclaration node=tool.createLiteral("state");
        internalState.allocationExpressionBodyState=(ASTClassOrInterfaceBody)
                node.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY,20);
        return node;
    }

    private ASTClassOrInterfaceBodyDeclaration createProp(InternalState internalState)
    {
        ASTClassOrInterfaceBodyDeclaration node=tool.createLiteral("propTypes");
        internalState.allocationExpressionBodyProp=(ASTClassOrInterfaceBody)
                node.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY,20);

        ASTModifiers modifiers=new ASTModifiers(JavaParserTreeConstants.JJTMODIFIERS);
        ASTModifier modifier=new ASTModifier(JavaParserTreeConstants.JJTMODIFIER);
        modifiers.add(modifier);

        ccbus.tool.parser.java.Token tokenIdentifier= ccbus.tool.parser.java.Token.newToken(
                JavaParserConstants.STATIC, "static");
        modifier.jjtSetFirstToken(tokenIdentifier);
        modifier.jjtSetLastToken(tokenIdentifier);
        node.add(modifiers);
        return node;
    }

    private ASTClassOrInterfaceBodyDeclaration createPropInit(InternalState internalState)
    {
        ASTClassOrInterfaceBodyDeclaration node=tool.createLiteral("defaultProps");
        internalState.allocationExpressionBodyPropInit=(ASTClassOrInterfaceBody)
                node.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY,20);

        ASTModifiers modifiers=new ASTModifiers(JavaParserTreeConstants.JJTMODIFIERS);
        ASTModifier modifier=new ASTModifier(JavaParserTreeConstants.JJTMODIFIER);
        modifiers.add(modifier);

        ccbus.tool.parser.java.Token tokenIdentifier= ccbus.tool.parser.java.Token.newToken(
                JavaParserConstants.STATIC, "static");
        modifier.jjtSetFirstToken(tokenIdentifier);
        modifier.jjtSetLastToken(tokenIdentifier);
        node.add(modifiers);
        return node;
    }

    private Node translateBodyDeclaration(Node node,TranslatedTree tree,InternalState internalState)
    {
        Node result = new ASTClassOrInterfaceBodyDeclaration(node.getId());
        for (int i = 0; i < node.jjtGetNumChildren(); i++)
        {
            Node currentNode = node.jjtGetChild(i);
            result.add(currentNode);
            if (JavaParserTreeConstants.JJTMODIFIERS == currentNode.getId())
            {
                ASTAnnotation annotation = (ASTAnnotation)
                        currentNode.findNextDownById(JavaParserTreeConstants.JJTANNOTATION, 2);

                while (null != annotation)
                {
                    if (annotation.findLeaf().image().equals("State"))
                    {
                        internalState.stateSequence = true;
                    }
                    if (annotation.findLeaf().image().equals("StateEnd"))
                    {
                        internalState.stateSequence = false;
                    }

                    if (annotation.findLeaf().image().equals("Prop"))
                    {
                        internalState.propSequence = true;
                    }
                    if (annotation.findLeaf().image().equals("PropEnd"))
                    {
                        internalState.propSequence = false;
                    }
                    annotation = (ASTAnnotation)
                            currentNode.findNextDownById(JavaParserTreeConstants.JJTANNOTATION, 2);
                }
            }
            SymTabStack symTabStack=((Tool)tree.tool()).symTabStack();
            if(internalState.stateSequence && JavaParserTreeConstants.JJTFIELDDECLARATION == currentNode.getId())
            {
                ASTIdentifier identifier=(ASTIdentifier)
                        currentNode.findNextDownById(JavaParserTreeConstants.JJTVARIABLEDECLARATORID,2)
                        .findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER,1);

                SymTabEntry symTabEntry=symTabStack.lookup(identifier.image());
                symTabEntry.setAttribute(SymTabKeyImpl.STATE_FIELD,SymTabKeyImpl.STATE_FIELD);

                ASTClassOrInterfaceBodyDeclaration stateBodyDeclaration =
                        new ASTClassOrInterfaceBodyDeclaration(JavaParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION);
                stateBodyDeclaration.add(currentNode);
                internalState.allocationExpressionBodyState.add(stateBodyDeclaration);
                return null;
            }

            if(internalState.propSequence && JavaParserTreeConstants.JJTFIELDDECLARATION == currentNode.getId())
            {
                ASTIdentifier identifier=(ASTIdentifier)
                        currentNode.findNextDownById(JavaParserTreeConstants.JJTVARIABLEDECLARATORID,2)
                                .findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER,1);

                SymTabEntry symTabEntry=symTabStack.lookup(identifier.image());
                symTabEntry.setAttribute(SymTabKeyImpl.PROP_FIELD,SymTabKeyImpl.PROP_FIELD);

                ASTType type=(ASTType)
                        currentNode.findFirstDownById(JavaParserTreeConstants.JJTTYPE,1);

                ASTVariableInitializer variableInitializer=(ASTVariableInitializer)
                        currentNode.findFirstDownById(JavaParserTreeConstants.JJTVARIABLEINITIALIZER,3);

                ASTVariableInitializer variableInitializerCopy=null;
                // there could be already variableInitializer, add if no any
                if(null==variableInitializer)
                {
                    variableInitializer=new ASTVariableInitializer(JavaParserTreeConstants.JJTVARIABLEINITIALIZER);
                    ASTVariableDeclarator variableDeclarator=(ASTVariableDeclarator)
                            currentNode.findFirstDownById(JavaParserTreeConstants.JJTVARIABLEDECLARATOR,1);
                    variableDeclarator.add(variableInitializer);
                }
                else
                {
                    variableInitializerCopy=(ASTVariableInitializer) tool.copyNodeState(variableInitializer,new ASTVariableInitializer(JavaParserTreeConstants.JJTVARIABLEINITIALIZER));
                    for(int j=0;j<variableInitializer.jjtGetNumChildren();j++)
                    {
                        variableInitializerCopy.add(variableInitializer.jjtGetChild(j));
                    }
                }
                ASTExpression expression=new ASTExpression(JavaParserTreeConstants.JJTEXPRESSION);
                Node name=((Tool) tree.tool()).allowedType().translate((ASTFieldDeclaration) currentNode);
                expression.add(name);
                variableInitializer.resetChildren();
                variableInitializer.add(expression);

                ASTClassOrInterfaceBodyDeclaration propBodyDeclaration =
                        new ASTClassOrInterfaceBodyDeclaration(JavaParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION);
                propBodyDeclaration.add(currentNode);

                internalState.allocationExpressionBodyProp.add(propBodyDeclaration);

                if(null==variableInitializerCopy)
                {
                    return null;
                }

                /* set default props */
                ASTClassOrInterfaceBodyDeclaration propInitFieldDeclaration=tool.createFieldDeclaration(identifier.image(),type,variableInitializerCopy);
                internalState.allocationExpressionBodyPropInit.add(propInitFieldDeclaration);

                return null;
            }
        }

        return result;
    }
}
