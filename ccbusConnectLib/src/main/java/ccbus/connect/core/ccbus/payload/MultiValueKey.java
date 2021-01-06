package ccbus.connect.core.ccbus.payload;

import java.util.ArrayList;


/*
Not done well. Should be facade/decorator of MultiValue
 */
public class MultiValueKey<T extends IKeyData>{
    public MultiValue<T> value;

    public MultiValueKey(ArrayList<T> valueList) {
        this.value = new MultiValue<>(valueList);
    }

    public boolean setValue(T v)
    {
        return value.setValue(v);
    }

    public boolean removeValue(T v)
    {
        return value.removeValue(v);
    }

    public boolean removeAll()
    {
       return this.value.removeAll();
    }

    public boolean contains(IKeyData v)
    {
        ArrayList<T> arr=value.getValue();
        for(int i=0;i<arr.size();i++)
        {
            IKeyData item=arr.get(i);
            if(v.getKey()==item.getKey())
            {
                return true;
            }
        }
        //return valueList.contains(v);
        return false;
    }

    public ArrayList<T> getValue()
    {
        return value.getValue();
    }
}