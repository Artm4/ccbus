package ccbus.dbtool;

import ccbus.dbtool.intermediate.ICode;
import ccbus.dbtool.intermediate.routineimpl.EntityCode;
import ccbus.dbtool.parser.JavaParser;
import ccbus.dbtool.util.Tool;


import java.io.File;
import java.io.FileReader;

public class ParseJava
{
    public static final String USAGE="Usage: [<source file path>]";

    public ParseJava()
    {
    }

    public static void main(String args[])
    {
        new ParseJava().run(args);
    }

    public void run(String args[])
    {
        try
        {

            int i=0;
            String sourcePath;
            if(i<args.length)
            {
                sourcePath=args[i];
            }
            else
            {
                throw new Exception();
            }

            File inputFile=new File(sourcePath);
            if(!inputFile.exists())
            {
                System.out.println("Input file '"+sourcePath+
                        " ' does not exist.");
                throw new Exception();
            }

            EntityCode code=(EntityCode)Tool.instance().parseClass(new FileReader(sourcePath));

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println(USAGE);
        }
    }
}
