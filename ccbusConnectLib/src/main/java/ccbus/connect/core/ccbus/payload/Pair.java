package ccbus.connect.core.ccbus.payload;

public class Pair extends IKeyData {
    public String key, value;

    public Pair(String key, String value){
        this.key = key;
        this.value = value;
    }

    public Pair(Long key, String value){
        this.key = String.valueOf(key);
        this.value = value;
    }

    public Pair(Integer key, String value){
        this.key = String.valueOf(key);
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }
}