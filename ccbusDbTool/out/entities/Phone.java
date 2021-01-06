package ccbus.out;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.beans.Transient;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Entity
public class Phone
{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;


    @NotNull
    private String phone;

    @OneToOne
    @JsonIgnore
    private Employee employee[];

    @Transient
    private Set<ActivityShifts,Set<ActivityShifts,String>> activityShifts;

    public enum Type
    {
        personal,
        work,
        beep;
    }

    public static List<Grade> getAll()
    {
        return Arrays.asList( FY,SHO,JuniorRegistrar,SeniorRegistrar,Consultant,CRF);
    }

    public enum Grade
    {
        FY,
        SHO,
        JuniorRegistrar,
        SeniorRegistrar,
        Consultant,
        CRF,
        Empty;

        public static List<Grade> getAll()
        {
            return Arrays.asList( FY,SHO,JuniorRegistrar,SeniorRegistrar,Consultant,CRF);
        }
    }

    @Enumerated(EnumType.STRING)
    private Type type;

    public Phone()
    {
    }

    public Phone(String phone, Employee employee,  Type type)
    {
        this.phone = phone;
        this.employee = employee;
        this.type = type;
    }



    public Set<ActivityShifts,Set<ActivityShifts,String>> getId()
    {
        return null;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    public Employee getEmployee()
    {
        return employee;
    }

    public void setEmployee(Employee employee)
    {
        this.employee = employee;
    }


    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    @Override
    public boolean equals(Object o)
    {
        Phone phone = (Phone) o;
        return this.id.equals(phone.getId());
    }
}
