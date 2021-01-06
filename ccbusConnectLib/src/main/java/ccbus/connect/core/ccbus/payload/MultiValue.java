package ccbus.connect.core.ccbus.payload;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;

public class MultiValue<T> {
    public ArrayList<T> value=new ArrayList<>();

    public MultiValue() {
    }

    public MultiValue(ArrayList<T> valueList) {
        this.value = valueList;
    }

    @JsonSetter("value")
    public void setValueJson(ArrayList<T> v)
    {
        value=v;
    }

    public boolean setValue(T v)
    {
        if(this.contains(v))
        {
            return false;
        }
        value.add(v);
        return true;
    }

    public boolean removeValue(T v)
    {
        if(this.contains(v))
        {
            value.remove(value.indexOf(v));
            return true;
        }
        return false;
    }

    public boolean removeAll()
    {
        // could just create new ref valueList=new ArrayList<>(); ,but getValue should return copy
        for(int i=value.size()-1;i>=0;i--)
        {
            this.value.remove(i);
        }
        return true;
    }

    public boolean contains(T v)
    {
        return value.contains(v);
    }

    public ArrayList<T> getValue()
    {
        return value;
    }
}