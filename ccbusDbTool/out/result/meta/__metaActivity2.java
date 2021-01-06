package result.query;

import ccbus.dbtool.backend.compiler.query.MetaConfig;
import ccbus.dbtool.backend.compiler.query.MetaFactory;
import ccbus.dbtool.backend.compiler.query.MetaQuery;
import ccbus.dbtool.util.Misc;
import ccbus.dbtool.backend.compiler.query.MetaEntity;

public class __metaActivity2 implements MetaConfig
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
