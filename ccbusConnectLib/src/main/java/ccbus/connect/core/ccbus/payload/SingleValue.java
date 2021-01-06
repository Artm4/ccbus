package ccbus.connect.core.ccbus.payload;

import com.fasterxml.jackson.annotation.JsonSetter;

public class SingleValue {
    public String value;

    public SingleValue(String value) {
        this.value = value;
    }

    public SingleValue() {
    }

    public String getValue() {
        return value;
    }

    @JsonSetter("value")
    public void setValue(String value) {
        this.value = value;
    }

    public void setValue(Float value) {
        this.value = Float.toString(value);
    }

    public void setValue(Double value) {
        this.value = Double.toString(value);
    }

    public void setValue(char value) {
        this.value = String.valueOf(value);
    }

    public void setValue(Integer value)
    {
        this.value = Integer.toString(value);
    }

    public boolean equals(String value)
    {
        return 0==this.value.compareTo(value);
    }
}
