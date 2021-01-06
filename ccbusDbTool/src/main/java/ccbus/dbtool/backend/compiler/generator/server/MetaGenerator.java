package ccbus.dbtool.backend.compiler.generator.server;

import ccbus.dbtool.backend.compiler.CodeGenerator;
import ccbus.dbtool.backend.compiler.generator.ServerTerminal;
import ccbus.dbtool.backend.compiler.query.MetaEntity;
import ccbus.dbtool.intermediate.ICodeNode;
import ccbus.dbtool.intermediate.icodeimpl.ICodeNodeKeyValue;
import ccbus.dbtool.intermediate.icodeimpl.ICodeNodeQuery;
import ccbus.dbtool.intermediate.icodeimpl.ICodeNodeTable;
import ccbus.dbtool.intermediate.routineimpl.NodeCode;
import ccbus.dbtool.util.Tool;


/*
package result.result.query;

import ccbus.dbtool.backend.compiler.result.query.MetaConfig;
import ccbus.dbtool.backend.compiler.result.query.MetaFactory;
import ccbus.dbtool.backend.compiler.result.query.MetaQuery;
import ccbus.dbtool.util.Misc;
import ccbus.dbtool.backend.compiler.result.query.MetaEntity;

public class __metaActivity implements MetaConfig
{
    @Override
    public MetaQuery meta()
    {
        return MetaFactory.createMeta(
                MetaFactory.createMetaEntity(
                        "Activity",
                        Misc.ofMap(
                        "SomeF","Some",
                        "SomeB","Other")
                ),
                MetaFactory.createMetaEntity(
                        "Phone",
                        Misc.ofMap(
                                "pSomeF","pSome",
                                "pSomeB","pOther")
                )

        );
    }
}
 */

public class MetaGenerator extends CodeGenerator
{
    public static final String FILE_PREFIX="__meta";

    public MetaGenerator(CodeGenerator parent)
    {
        super(parent);
    }

    public MetaGenerator()
    {
    }

    public void generate(NodeCode nodeCode) throws Exception
    {
        this.emitMetaQuery(nodeCode);
    }

    private void emitMetaQuery(NodeCode nodeCode) throws Exception
    {
        ICodeNodeQuery query=(ICodeNodeQuery )nodeCode.getNode();
        String className=FILE_PREFIX
                +query.getQueryName();
        String metaFilePath=Tool.instance().getConfigParser().getMetaPath()
                +className
                +".java";
        this.setOut(Tool.instance().openFileWriter(metaFilePath));

        this.emitPackage(Tool.instance().getConfigParser().getMetaPackage());
        this.emitNewLine();

        this.emitImport("ccbus.dbtool.backend.compiler.result.query.MetaConfig");
        this.emitImport("ccbus.dbtool.backend.compiler.result.query.MetaFactory");
        this.emitImport("ccbus.dbtool.backend.compiler.result.query.MetaQuery");
        this.emitImport("ccbus.dbtool.util.Misc");
        this.emitNewLine();

        this.emit("public class "+className+" implements MetaConfig");
        this.emit("{");

        this.emit("   @Override\n" +
                "    public MetaQuery meta()\n" +
                "    {"
        );

        this.emit("        return MetaFactory.createMeta(");

        this.emitMetaEntity(query.getFrom());
        if(query.getJoinList().getChildren().size()>0)
        {
            this.emitTerminal(ServerTerminal.COMMA);
        }
        this.emitNewLine();
        int i=0;
        for(ICodeNode join:query.getJoinList().getChildren())
        {
            ICodeNodeTable nodeTable=(ICodeNodeTable)join;
            emitMetaEntity(nodeTable);
            i++;
            if(i<query.getJoinList().getChildren().size())
            {
                this.emitTerminal(ServerTerminal.COMMA);
            }
            this.emitNewLine();

        }

        this.emit("        );");
        this.emit("     }");
        this.emit("}");

    }

    private void emitMetaEntity(ICodeNodeTable nodeTable)
    {
        this.emit("                MetaFactory.createMetaEntity(");
        this.emit("                  "+valueStringConstant(nodeTable.getTableName())+",");

        this.emitFieldList(nodeTable.getFieldList());

        this.emitNln("                )");
    }

    private void emitFieldList(ICodeNode fieldList)
    {
        if(fieldList.getChildren().size()==0)
        {
            return;
        }
        this.emit("                   Misc.ofMap(");
        int i=0;
        for(ICodeNode node:fieldList.getChildren())
        {
            ICodeNodeKeyValue keyValue=(ICodeNodeKeyValue)node;
            this.emitNln("                        "
                    +valueStringConstant(keyValue.getKey())+" ,"+valueStringConstant(keyValue.getValue()));
            i++;
            if(i<fieldList.getChildren().size())
            {
                this.emitTerminal(ServerTerminal.COMMA);
            }
            this.emitNewLine();

        }
        this.emit("                        )");
    }
}
