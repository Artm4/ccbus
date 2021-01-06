package ccbus.tool.util.java;

import ccbus.tool.intermediate.Node;
import ccbus.tool.parser.java.*;
import ccbus.tool.util.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Misc
{
    public static String fileSeparator=System.getProperty("file.separator");
    public static String eol = System.getProperty("line.separator", "\n");
    public static String workingDirectory=Paths.get("").toAbsolutePath().toString();
    public static String genericSeparator="/";

    public static <T,E> Map<T,E> ofMap(T argA,E argB,Object ...args)
    {
        Map map=new HashMap<T,E>();
        map.put(argA,argB);
        for(int i=0;i<args.length;i+=2)
        {
            map.put(args[i],args[i+1]);
        }

        return map;
    }
    public static String packageToPath(String packageName,String basePath)
    {
        String prefix="";
        if(basePath.length()>0)
        {
            prefix=rtrim(basePath,fileSeparator)+fileSeparator;
        }
        return prefix+packageName.replace(".",fileSeparator)+fileSeparator;
    }

    public static String buildPath(String[] subPath)
    {
        return String.join(fileSeparator,subPath);
    }

    public static String[] packageToPath(String[] packages,String basePath)
    {
        String[] pathList=new String[packages.length];
        for(int i=0;i<packages.length;i++)
        {
            pathList[i]=packageToPath(packages[i],basePath);
        }
        return pathList;
    }

    public static String toSystemFileSeparator(String str)
    {
        String result="";
        if(fileSeparator.equals("/"))
        {
            result=str.replaceAll("\\\\","/");
        }
        else
        {
            result=str.replaceAll("/","\\\\");
        }

        return result;
    }

    public static String genericPathTrim(String str)
    {
        return trim(trim(str,"/"),"\\");
    }

    public static String genericPathLTrim(String str)
    {
        return ltrim(ltrim(str,"/"),"\\");
    }


    public static String genericPathRTrim(String str)
    {
        return rtrim(rtrim(str,"/"),"\\");
    }

    public static String rtrim(String str,String ch)
    {
        if(str.length()>0 && str.endsWith(ch))
        {
            return str.substring(0,str.lastIndexOf(ch));
        }
        return str;
    }

    public static String ltrim(String str,String ch)
    {
        if(str.length()>0 && str.startsWith(ch))
        {
            return str.substring(str.indexOf(ch));
        }
        return str;
    }

    public static String trim(String str,String ch)
    {
        return ltrim(rtrim(str,ch),ch);
    }

    public static String revisionFilePath(String filePath)
    {
        int pos=filePath.lastIndexOf(".");
        long currentTime=System.currentTimeMillis() / 1000;
        if(pos<0)
        {
            return filePath+String.valueOf(currentTime);
        }
        String ext=filePath.substring(pos);
        String fileNoExt=filePath.substring(0,pos);

        return fileNoExt+String.valueOf(currentTime)+ext;
    }

    public static Node parseFile(String file, Tool tool)
    {
        Node lookupNode=tool.parsedNamespaceTab().lookup(file);
        if(null!=lookupNode)
        {
            return lookupNode;
        }
        System.out.println("Parsing: "+file);
        JavaParser parser=new JavaParser(file,tool);
        try {
            parser.CompilationUnit();
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            System.out.println("Java Parser Version 1.1:  Encountered errors during parse.");
            //e.printStackTrace();
        }
        Node rootNode=parser.getTree().rootNode();
        tool.parsedNamespaceTab().enter(file,rootNode);
        return rootNode;
    }

    public static Node parsePackageName(ASTName name,Tool tool)
    {
        Pair<String, String> filePair=extractPackageName(name,tool);
        String file=absolutePath(filePair.getKey(),filePair.getValue(),tool);
        return parseFile(file,tool);
    }

    public static String absolutePath(String relativeFilePath,Tool tool)
    {
        relativeFilePath=trim(relativeFilePath,tool.getFileSeparator());
        String rootPath=tool.getConfigParser().getConnectLibSrcPath();

        if (relativeFilePath.startsWith(tool.getConfigParser().getProjectRootPackageSubPath()+tool.connectPackageTag))
        {
            rootPath=tool.getConfigParser().getProjectSrcPath();
        }
        return ltrim(rootPath+tool.getFileSeparator()+relativeFilePath,tool.getFileSeparator());
    }

    public static String absolutePath(String relativePath,String fileName,Tool tool)
    {
        return absolutePath(
                trim(relativePath,tool.getFileSeparator())
                +tool.getFileSeparator()
                +fileName,
                tool
        );
    }

    public static Pair<String,String> extractPackageName(Node name,Tool tool)
    {
        return extractPackageName(name,tool,false);
    }

    public static Pair<String,String> extractPackageNameWild(Node name,Tool tool)
    {
        return extractPackageName(name,tool,true);
    }

    public static Pair<String,String> extractPackageName(Node name,Tool tool,boolean wildImport)
    {
        StringBuffer buffer=new StringBuffer();
        String fileName=new String();
        for(int i=0;i<name.jjtGetNumChildren();i++)
        {
            if(name.jjtGetChild(i).getId()!=JavaParserTreeConstants.JJTIDENTIFIER)
            {
                continue;
            }
            String subPath=name.jjtGetChild(i).image();
            if(i==(name.jjtGetNumChildren()-1) && false==wildImport)
            {
                fileName=subPath+".java";
            }
            else
            {
                buffer.append(subPath);
                buffer.append(fileSeparator);
            }
        }
        return new Pair(rtrim(buffer.toString(),tool.getFileSeparator()),fileName);
    }

    public static String nameNodeToPackageString(Node name)
    {
        StringBuffer result=new StringBuffer();

        Node prevId=null;
        name.resetNextSearch();
        ASTIdentifier identifier=(ASTIdentifier)
            name.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER,1);

        result.append(identifier.image());
        identifier=(ASTIdentifier)name.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER,1);

        while(null!=identifier)
        {
            if(null!=prevId)
            {
                result.append(".");
                result.append(prevId.image());
            }
            prevId=identifier;
            identifier=(ASTIdentifier)name.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER,1);
        }

        name.resetNextSearch();
        return result.toString();
    }

    public static String nameNodeToString(Node name)
    {
        StringBuffer result=new StringBuffer();

        name.resetNextSearch();
        ASTIdentifier identifier=(ASTIdentifier)
                name.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER,1);

        while(null!=identifier)
        {
            result.append(identifier.image());
            identifier=(ASTIdentifier)name.findNextDownById(JavaParserTreeConstants.JJTIDENTIFIER,1);
            if(null!=identifier)
            {
                result.append(".");
            }
        }

        name.resetNextSearch();
        return result.toString();
    }

    public static String buildPath(List<String> pathList)
    {
        String result="";
        result=fileSeparator+trim(String.join(fileSeparator,pathList),fileSeparator);
        return result;
    }

    public static boolean prepareDirectory(String dir)
    {
        File directory = new File(dir);

        if (!directory.exists()) {
            return directory.mkdir();
        }
        return false;
    }

    /**
     * Create directories recursively
     * @param dir
     * @return
     */
    public static boolean prepareDirectories(String dir)
    {
        File directory = new File(dir);

        if (!directory.exists()) {
            return directory.mkdirs();
        }
        return false;
    }

    static String add_escapes(String str) {
        StringBuffer retval = new StringBuffer();
        char ch;
        for (int i = 0; i < str.length(); i++) {
            switch (str.charAt(i))
            {
                case 0 :
                    continue;
                case '\b':
                    retval.append("\\b");
                    continue;
                case '\t':
                    retval.append("\\t");
                    continue;
                case '\n':
                    retval.append("\\n");
                    continue;
                case '\f':
                    retval.append("\\f");
                    continue;
                case '\r':
                    retval.append("\\r");
                    continue;
                case '\"':
                    retval.append("\\\"");
                    continue;
                case '\'':
                    retval.append("\\\'");
                    continue;
                case '\\':
                    retval.append("\\\\");
                    continue;
                default:
                    if ((ch = str.charAt(i)) < 0x20 || ch > 0x7e) {
                        String s = "0000" + Integer.toString(ch, 16);
                        retval.append("\\u" + s.substring(s.length() - 4, s.length()));
                    } else {
                        retval.append(ch);
                    }
                    continue;
            }
        }
        return retval.toString();
    }

    public static void printFile(String filePath) throws Exception
    {
        BufferedReader bufferedReader=new BufferedReader(new FileReader(filePath));
        String line;
        while((line=bufferedReader.readLine())!=null)
        {
            System.out.println(line);
        }
    }

    public static void printFile(BufferedReader bufferedReader) throws Exception
    {
        printFile(bufferedReader,System.out);
    }

    public static void printFile(BufferedReader bufferedReader,PrintStream printStream) throws Exception
    {
        String line;
        while((line=bufferedReader.readLine())!=null)
        {
            printStream.println(line);
        }
    }

    public static void saveFile(BufferedReader bufferedReader,String dst) throws Exception
    {
        printFile(bufferedReader,new PrintStream(new FileOutputStream(dst)));
    }


    public static void copyFile(String srcPath,String dstPath) throws Exception
    {
        Path pathSrc=Paths.get(srcPath);
        Path pathDst=Paths.get(dstPath);
        Files.copy(pathSrc,pathDst);
    }

    public static void copyFile(BufferedReader bufferedReader,String dstPath) throws Exception
    {
        saveFile(bufferedReader,dstPath);
    }

    public static String toAbsolutePathString(String filePath) throws Exception
    {
        Path path=Paths.get(filePath);
        if(path.isAbsolute())
        {
            return filePath;
        }
        String absolutePath=workingDirectory+fileSeparator+filePath;
        return absolutePath;
    }

    public static Path toAbsolutePath(String filePath) throws Exception
    {
        return Paths.get(toAbsolutePathString(filePath));
    }

    /**
     * Works slowly but good for small templates
     * @param replacePair
     * @param srcPath
     * @param dstPath
     * @throws Exception
     */
    public static void buildFileTemplate(HashMap<String,String> replacePair,BufferedReader srcPath,String dstPath) throws Exception
    {
        BufferedReader bufferedReader=srcPath;
        String line;
        File destination=new File(dstPath);
        FileOutputStream outputStream=new FileOutputStream(destination);
        PrintStream printStream = new PrintStream(outputStream);
        while((line=bufferedReader.readLine())!=null)
        {
            for(String key:replacePair.keySet())
            {
                line=line.replaceAll(key,replacePair.get(key));
            }
            printStream.println(line);
        }
    }


}