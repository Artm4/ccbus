package ccbus.system.query;

import ccbus.system.meta.Meta_Point;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class __MetaClass
{
    protected String subPath;
    protected __MetaClass parent;

    protected int depth=0;

    public __MetaClass(){}

    public __MetaClass(__MetaClass parent,String subPath)
    {
        this.subPath = subPath;
        this.parent = parent;
        initSubPath();
    }

    private void initSubPath()
    {
        __MetaClass parent=this.parent;

        if(null!=parent)
        {
            depth=parent.depth+1;
        }

    }

    public boolean isRecursivePath(String prevPath,String path,String nextPath)
    {
        __MetaClass par=parent;
        while(null!=par)
        {
            if(this.getClass().equals(par.getClass()) &&
                (this.getPathList().contains(nextPath))
            )
            {
                return true;
            }
            par=par.parent;
        }
        return this.depth>5;
    }

    public boolean isRecursivePathClass(Class cls)
    {
        __MetaClass par=parent;
        while(null!=par)
        {
            if(this.getClass().equals(par.getClass()))
            {
                for(Field field:this.getClass().getDeclaredFields())
                {
                    if(field.getType().equals(cls))
                    {
                        return true;
                    }
                }
            }
            par=par.parent;
        }
        return this.depth>5;
    }

    public int getDepth()
    {
        return depth;
    }

    public __MetaClass getParent()
    {
        return parent;
    }

    public String getPathHash()
    {
        StringBuilder pathHash=new StringBuilder();
        __MetaClass par=this;
        while(null!=par && par.subPath!=null)
        {
            pathHash.append(par.subPath);
            pathHash.append("_");
            par=par.parent;
        }
        return pathHash.toString();
    }

    public String getSubPath()
    {
        return subPath;
    }

    private LinkedList<String> getPathList()
    {
        LinkedList<String> list=new LinkedList<>();

        __MetaClass par=this;
        while(null!=par && par.subPath!=null)
        {
            list.add(par.subPath);
            par=par.parent;
        }

        return list;
    }

    public <V,T> Path<T> path(String name, Root<V> root)
    {
        Path<T> x;

        if(null==parent)
        {
            x=root.get(name);
            return x;
        }

        LinkedList<String> list=getPathList();

        Iterator<String> iterator=list.descendingIterator();

        x=root.get(iterator.next());

        while(iterator.hasNext())
        {
            x=x.get(iterator.next());
        }

        x = x.get(name);

        return x;
    }


}
