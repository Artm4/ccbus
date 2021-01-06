package ccbus.system.misc;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CFile {
    public static String fileSeparator=System.getProperty("file.separator");
    public static String eol = System.getProperty("line.separator", "\n");
    public static String workingDirectory=Paths.get("").toAbsolutePath().toString();
    public static String genericSeparator="/";

    public static <T,E> Map<T,E> ofMap(T argA, E argB, Object ...args)
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

    public static String buildPath(List<String> pathList)
    {
        return String.join(fileSeparator,pathList);
    }

    public static String buildPath(String... pathList)
    {
        return String.join(fileSeparator,pathList);
    }

    public static boolean prepareDirectory(String dir)
    {
        java.io.File directory = new java.io.File(dir);

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
        java.io.File directory = new java.io.File(dir);

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

    public static void printFile(BufferedReader bufferedReader, PrintStream printStream) throws Exception
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
        //String absolutePath=workingDirectory+fileSeparator+filePath;
        //return absolutePath;
        return path.toAbsolutePath().toString();
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
        java.io.File destination=new java.io.File(dstPath);
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
