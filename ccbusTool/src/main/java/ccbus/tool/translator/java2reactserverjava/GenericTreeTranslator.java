package ccbus.tool.translator.java2reactserverjava;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.java.*;
import ccbus.tool.translator.IRecursiveTranslator;
import ccbus.tool.translator.RecursiveHostTranslator;
import ccbus.tool.translator.TranslatedTree;
import ccbus.tool.translator.TreeTranslator;
import ccbus.tool.util.java.Tool;
import ccbus.tool.util.Pair;

import java.util.ArrayList;

public class GenericTreeTranslator implements TreeTranslator ,RecursiveHostTranslator
{

    public Node translate(Node node, TranslatedTree tree)
    {
        Node nodeResult=tree.translateRecursive(node,this);
        return nodeResult;
    }

    public Pair<Boolean, Node> translateNode(Node node, TranslatedTree tree, IRecursiveTranslator recursiveTranslator)
    {
        Pair<Boolean,Node> result;
        Tool tool=(Tool) tree.tool();
        switch (node.getId())
        {//
//            case JavaParserTreeConstants.JJTCLASSORINTERFACEDECLARATION:
//                result=new Pair<Boolean, Node>(false,tree.translate(JavaParserTreeConstants.JJTCLASSORINTERFACEDECLARATION,node));
//                break;
            case JavaParserTreeConstants.JJTANNOTATION:
                result=new Pair<Boolean, Node>(false,new ASTAnnotation(JavaParserTreeConstants.JJTANNOTATION));
                break;
            case JavaParserTreeConstants.JJTIMPORTDECLARATION:
                result=new Pair<Boolean, Node>(false,tree.translate(JavaParserTreeConstants.JJTIMPORTDECLARATION,node));
                break;
            case JavaParserTreeConstants.JJTPACKAGEDECLARATION:
                result=new Pair<Boolean, Node>(false,tree.translate(JavaParserTreeConstants.JJTPACKAGEDECLARATION,node));
                break;
            case JavaParserTreeConstants.JJTEXTENDSLIST:
                tool.lastExtendNode=node;
                result=recursiveTranslator.translateNode(node,tree,recursiveTranslator);
                break;
            case JavaParserTreeConstants.JJTCLASSORINTERFACEBODY:
                // push new namespace in stack
                Node extendNode=tool.lastExtendNode;
                tool.lastExtendNode=null;
                if(null!=extendNode)
                {
                    tool.pushExtendsSymTab((ASTExtendsList) extendNode);
                }
                tool.symTabStack().push(node.symTab());

                // assoc template param with extend type
                Node classOrInterfaceDeclaration=node.jjtGetParent();

                ASTTypeParameters typeParameters=(ASTTypeParameters)
                        classOrInterfaceDeclaration.findNextDownById(JavaParserTreeConstants.JJTTYPEPARAMETERS,1);
                pushTemplateParamsExtends(tool,typeParameters);

                // branch for service based on extend type
                result=new Pair<Boolean, Node>(false,tree.translate(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY,node));
                //end branch for service

                // end of namespace, pop from stack;
                tool.symTabStack().pop();

                // pop all extended namespaces

                if(null!=extendNode)
                {
                    tool.popExtendsSymTab((ASTExtendsList) extendNode);
                }
                // clean template params with extends
                tool.symTabStack().getTemplateParamToExtend().clear();
                break;
            case JavaParserTreeConstants.JJTPRIMARYEXPRESSION:
                result=new Pair<Boolean, Node>(false,tree.translate(JavaParserTreeConstants.JJTPRIMARYEXPRESSION,node));
                break;
            case JavaParserTreeConstants.JJTALLOCATIONEXPRESSION:
                result=new Pair<Boolean, Node>(false,tree.translate(JavaParserTreeConstants.JJTALLOCATIONEXPRESSION,node));
                break;
            case JavaParserTreeConstants.JJTMETHODDECLARATION:
                ((Tool)tree.tool()).symTabStack().push(node.symTab());
                ASTTypeParameters typeParametersMethod=(ASTTypeParameters)
                        node.findNextDownById(JavaParserTreeConstants.JJTTYPEPARAMETERS,1);
                pushTemplateParamsExtends(tool,typeParametersMethod);

                result=new Pair<Boolean, Node>(false,tree.translate(JavaParserTreeConstants.JJTMETHODDECLARATION,node));
                ((Tool)tree.tool()).symTabStack().pop();
                // clean template params with extends
                tool.symTabStack().getTemplateParamToExtend().clear();
                break;
            case JavaParserTreeConstants.JJTCONSTRUCTORDECLARATION:
                ((Tool)tree.tool()).symTabStack().push(node.symTab());
                result=new Pair<Boolean, Node>(false,tree.translate(JavaParserTreeConstants.JJTCONSTRUCTORDECLARATION,node));
                ((Tool)tree.tool()).symTabStack().pop();
                break;
            case JavaParserTreeConstants.JJTLAMDAEXPRESSION:
                result=new Pair<Boolean, Node>(false,tree.translate(JavaParserTreeConstants.JJTLAMDAEXPRESSION,node));
                break;
            default:
                result=recursiveTranslator.translateNode(node,tree,recursiveTranslator);
        }

        return result;
    }

    private void pushTemplateParamsExtends(Tool tool,ASTTypeParameters typeParameters)
    {
        if(null==typeParameters){return;}
        ASTTypeParameter typeParameter=(ASTTypeParameter)
                typeParameters.findNextDownById(JavaParserTreeConstants.JJTTYPEPARAMETER,1);

        while(null!=typeParameter)
        {
            ASTClassOrInterfaceType classOrInterfaceType=(ASTClassOrInterfaceType)
                    typeParameter.findFirstDownById(JavaParserTreeConstants.JJTCLASSORINTERFACETYPE,2);

            if(null!=classOrInterfaceType)
            {
                tool.symTabStack().getTemplateParamToExtend().put(typeParameter.image(),
                        classOrInterfaceType.jjtGetChild(0).image());
            }

            typeParameter=(ASTTypeParameter)
                    typeParameters.findNextDownById(JavaParserTreeConstants.JJTTYPEPARAMETER,1);
        }
    }
}
