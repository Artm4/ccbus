package ccbus.out;

import java.util.Date;

@Entity
public class Activity
{
    @Id
    private Integer id;
    @OneToOne
    private Type type;
    @GeneratedValue(strategy = GenerationType.AUTO)
    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    private java.util.Set<Phone> phones,idA;
    @Enumerated(EnumType.STRING)
    private Deleted deleted = Deleted.No;

    @Enumerated("Some type",1,Obj.Type)
    private User user;

    @DateTimeFormat
    private Date scheduleEndDate;


    @Entity
    public class Activity
    {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private java.util.Set<Phone> id,idA;
        @Enumerated(EnumType.STRING)
        private Deleted deleted = Deleted.No;

        @Enumerated("Some type",1,Obj.Type)
        private User user;
    }
}

