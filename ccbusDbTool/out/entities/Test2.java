package ccbus.out;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Phone
{
    private Long id;
    private String phone;
    private Employee employee;

    public Phone(String phone, Employee employee,  Type type)
            throws Exception
    {

    }

    public enum Type
    {
        personal,
        work,
        beep("Some",1,3);
    }
}
