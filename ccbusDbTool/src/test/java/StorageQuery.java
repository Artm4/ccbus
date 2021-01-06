import ccbus.dbtool.backend.compiler.generator.server.MetaGenerator;
import ccbus.dbtool.intermediate.icodeimpl.ICodeNodeQuery;
import ccbus.dbtool.intermediate.routineimpl.NodeCode;
import ccbus.dbtool.intermediate.routineparser.QueryParser;
import ccbus.dbtool.parser.Query;

import java.io.File;
import java.io.FileReader;

public class StorageQuery
{
    public static final String USAGE="Usage: <result.query name> [<source file path>]";


    public StorageQuery()
    {
    }

    public static void main(String args[])
    {
        new StorageQuery().run(args);
    }

    public void run(String args[])
    {
        try
        {
            String sourcePath="";
            String queryName="";
            if(0<args.length)
            {
                queryName=args[0];
            }
            if(1<args.length)
            {
                sourcePath=args[1];
            }

            if(queryName.length()==0)
            {
                throw new Exception("Query name requred!");
            }


            Query parser;
            if(sourcePath.length()>0)
            {
                parser = new Query(new FileReader(sourcePath));
                File inputFile=new File(sourcePath);
                if(!inputFile.exists())
                {
                    System.out.println("Input file '"+sourcePath+
                            " ' does not exist.");
                    throw new Exception();
                }
            }
            else
            {
                parser = new Query(System.in);
            }

            parser.init();
            parser.input();
            ICodeNodeQuery query=(ICodeNodeQuery) parser.getProgramCode().getRoot();
            query.setQueryName(queryName);

            // Should validate the result.query and expand it if no specific fields set

            QueryParser nodeParser=new QueryParser();
            NodeCode code=nodeParser.parse(query);

            ((ICodeNodeQuery)code.getNode()).getJoinList().getChildren();
            MetaGenerator generator=new MetaGenerator();
            //generator.generate(code);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println(USAGE);
        }
    }
}
