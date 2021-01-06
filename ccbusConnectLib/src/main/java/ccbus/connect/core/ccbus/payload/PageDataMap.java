package ccbus.connect.core.ccbus.payload;

import java.util.HashMap;

public class PageDataMap<T extends IKeyData>
{
    HashMap<String,IKeyData> map=new HashMap<>();
    public PageDataMap(PageData<T> pageData)
    {
        for(int i=0;i<pageData.getDataSize();i++)
        {
            IKeyData item=pageData.getItem(i);
            map.put(item.getKey(),item);
        }
    }

    IKeyData getItem(String key)
    {
        return map.get(key);
    }
    boolean contains(String key)
    {
        return map.containsKey(key);
    }
}
