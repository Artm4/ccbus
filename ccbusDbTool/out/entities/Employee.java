package ccbus.out;

import com.edatachase.rota.components.Daos;
import com.edatachase.rota.daos.AssignmentDao;
import com.edatachase.rota.daos.ShiftDao;
import com.edatachase.rota.libs.DayOfWeek;
import com.edatachase.rota.libs.GenerateColor;
import com.edatachase.rota.payloads.CronEmployeePayload;
import com.edatachase.rota.payloads.WorkingDayPayload;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
public class Employee
{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull
    private String firstName;

    public enum Deleted
    {
        Yes,
        No
    }

    @Enumerated(EnumType.STRING)
    private Deleted deleted = Deleted.No;

    private String lastName ="";

    private String initials ="";

    private String title ="";

    private Boolean excludeFromRota;

    public enum System
    {
        Yes,
        No
    }

    @Enumerated(EnumType.STRING)
    private System system = System.No;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.MERGE)
    @Fetch(FetchMode.JOIN)
    private Set<Phone>  phones;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.MERGE)
    @Fetch(FetchMode.JOIN)
    private Set<Email> emails;

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

    @Transient
    private Criteria gradeCriteria;

    @Enumerated(EnumType.STRING)
    private Grade grade=Grade.Empty;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private TrainingLevel trainingLevel;

    @Enumerated(EnumType.STRING)
    private Grade gradeOncall;

    @Transient
    private Set<CronEmployeePayload> cronActivities;


    @Transient
    private Criteria gradeOncallCriteria;

    public enum TrainingLevel
    {
        FY1,
        FY2,
        ST1,
        ST2,
        ST3,
        ST4,
        ST5,
        ST6,
        ST7,
    }

    @Transient
    private Criteria trainingLevelCriteria;

//    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
//    @Fetch(FetchMode.JOIN)
//    private Set<EmployeeSupervisor> employees;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.MERGE)
    @Fetch(FetchMode.JOIN)
    private Set<EmployeeSupervisor> supervisors;

    private String color;

    @DateTimeFormat
    private Date dateCreated;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.MERGE)
    @Fetch(FetchMode.JOIN)
    private Set<SpecialInterest> specialInterests;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.MERGE)
    @Fetch(FetchMode.JOIN)
    @OrderBy("criteria_id ASC")
    private Set<EmployeeCriteria> employeeCriteria=new HashSet<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.MERGE)
    @Fetch(FetchMode.JOIN)
    private Set<WorkingDay> workingDays;

    @JsonIgnore
    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private Set<AssignmentEmployee> assignmentEmployees;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.MERGE)
    @Fetch(FetchMode.SUBSELECT)
    @JsonIgnore
    private Set<EmployeeGroup> employeeGroups;


    @Transient
    @JsonIgnore
    private List<Criteria>criteriaList=null;

    public enum Role
    {
        Consultant,
        Trainee,
        Non_Trainee;

        public static List<Role> getAll()
        {
            return Arrays.asList(Consultant,Trainee,Non_Trainee);
        }

    }

    @Transient
    private Criteria roleCriteria;

    public Criteria getGradeCriteria()
    {
        return gradeCriteria;
    }

    public Criteria getGradeOncallCriteria()
    {
        return gradeOncallCriteria;
    }

    public Criteria getTrainingLevelCriteria()
    {
        return trainingLevelCriteria;
    }

    public Criteria getRoleCriteria()
    {
        return roleCriteria;
    }

    public Type getTypeCriteria()
    {
        return typeCriteria;
    }

    public enum Type
    {
        SST,
        ATSM
    }

    @Transient
    private Type typeCriteria;

    @Transient
    @JsonIgnore
    private Set<Day> notWorkingDays=null;

    public Employee()
    {
        init();
    }

   public Employee(GenerateColor generateColor)
    {
        this.color = generateColor.getRandColor();
        init();
    }

    public Employee(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        GenerateColor generateColor = new GenerateColor();
        this.color = generateColor.getRandColor();
        this.dateCreated = new Date();
        init();
    }



    public Employee(String firstName, String lastName,  String initials, String title)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.initials=initials;
        this.title=title;
        GenerateColor generateColor = new GenerateColor();
        this.color = generateColor.getRandColor();
        init();
    }


    private void init()
    {
        initCriteria();
    }

    public void initCriteria()
    {
        if (null != this.employeeCriteria)
        {
            for (EmployeeCriteria employeeCriteria : this.employeeCriteria)
            {
                if (employeeCriteria.getCriteria().getCriteriaType().getTag().equals(CriteriaType.Tag.Grade.toString()))
                {
                    this.gradeCriteria = employeeCriteria.getCriteria();
                    try
                    {
                        this.grade = Grade.valueOf(employeeCriteria.getCriteria().getTag());
                    } catch (IllegalArgumentException ex)
                    {
                    }

                }
                if (employeeCriteria.getCriteria().getCriteriaType().getTag().equals(CriteriaType.Tag.Role.toString()))
                {

                    try
                    {
                        this.role = Role.valueOf(employeeCriteria.getCriteria().getTag());
                    } catch (IllegalArgumentException ex)
                    {
                    }
                }
            }
            if (null == this.gradeCriteria)
            {
                gradeCriteria = com.edatachase.rota.models.Criteria.createEmpty();
            }
            if (null == this.roleCriteria)
            {
                roleCriteria = com.edatachase.rota.models.Criteria.createEmpty();
            }
        }
    }

    public System getSystem()
    {
        return system;
    }

    public void setSystem(System system)
    {
        this.system = system;
    }

    public Deleted getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Deleted deleted)
    {
        this.deleted = deleted;
    }

    public Set<EmployeeGroup> getEmployeeGroups()
    {
        return employeeGroups;
    }

    public void addCriteria(Criteria criteria)
    {
        employeeCriteria.add(new EmployeeCriteria(this, criteria));
    }

    public Boolean getExcludeFromRota()
    {
        return excludeFromRota;
    }

    public void setExcludeFromRota(Boolean excludeFromRota)
    {
        this.excludeFromRota = excludeFromRota;
    }

    public Set<EmployeeCriteria> getEmployeeCriteria()
    {
        return employeeCriteria;
    }

    public void setEmployeeCriteria(Set<EmployeeCriteria> employeeCriteria)
    {
        this.employeeCriteria = employeeCriteria;
    }

    public String getInitials()
    {
        return initials;
    }

    public void setInitials(String initials)
    {
        this.initials = initials;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Grade getGradeOncall()
    {
        return gradeOncall;
    }

    public void setGradeOncall(Grade gradeOncall)
    {
        this.gradeOncall = gradeOncall;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public Set<Phone> getPhones()
    {
        return phones;
    }

    public void setPhones(Set<Phone> phones)
    {
        this.phones = phones;
    }

    public Set<Email> getEmails()
    {
        return emails;
    }

    public void setEmails(Set<Email> emails)
    {
        this.emails = emails;
    }

    public Grade getGrade()
    {
        return grade;
    }

    public void setGrade(Grade grade)
    {
        this.grade = grade;
    }

    public TrainingLevel getTrainingLevel()
    {
        return trainingLevel;
    }

    public void setTrainingLevel(TrainingLevel trainingLevel)
    {
        this.trainingLevel = trainingLevel;
    }


    public Set<EmployeeSupervisor> getSupervisors()
    {
        return supervisors;
    }

    public void setSupervisors(Set<EmployeeSupervisor> supervisors)
    {
        this.supervisors = supervisors;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public Date getDateCreated()
    {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public Set<SpecialInterest> getSpecialInterests()
    {
        return specialInterests;
    }

    public void setSpecialInterests(Set<SpecialInterest> specialInterests)
    {
        this.specialInterests = specialInterests;
    }

    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

    /**
     * Fetch weekday availability
     * return
     */
    public Set<WorkingDay> getWorkingDays()
    {
        return workingDays;
    }


    @JsonSetter("workingDays")
    public void setActivityShiftsTags(Set<WorkingDayPayload> workingDayPayloads)
    {
        Set<WorkingDay> workingDays = new HashSet<>();
        ShiftDao shiftDao = Daos.getInstance().getShiftDao();

        for (WorkingDayPayload workingDayPayload : workingDayPayloads)
        {
            Shift shift = shiftDao.findFirstByTag(workingDayPayload.getShift());
            WorkingDay workingDay = new WorkingDay(null,workingDayPayload.getDay(),shift);
            workingDays.add(workingDay);
        }

        this.workingDays = workingDays;
    }

    @JsonGetter("workingDays")
    public Set<WorkingDayPayload> getWorkingDaysTags()
    {
        if(null == workingDays)
        {
            return null;
        }

        Set<WorkingDayPayload> workingDayPayloads = new HashSet<>();

        for (WorkingDay workingDay : workingDays)
        {
            WorkingDayPayload workingDayPayload = new WorkingDayPayload(workingDay.getDay(),workingDay.getShift().getTag());
            workingDayPayloads.add(workingDayPayload);
        }

        return workingDayPayloads;
    }


    public void setWorkingDays(Set<WorkingDay> workingDays)
    {
        this.workingDays = workingDays;
    }

    /**
     * Fetch all weekdays where there is no day shift
     * return
     */
    public Set<Day> getNotWorkingDays()
    {
        if(null!=this.notWorkingDays)
        {
            return this.notWorkingDays;
        }
        this.notWorkingDays=new HashSet<>();
        for(WorkingDay wday:this.workingDays)
        {
            if(Shift.getNotWorking().contains(wday.getShift()))
            {
                notWorkingDays.add(wday.getDay());
            }
        }
        return this.notWorkingDays;
    }

    public int getNotWorkingDaysForPeriod(Date dateFrom,Date dateTo)
    {
        int notWorkindDaysCount=0;
        Set<Day> notWorkingDays=this.getNotWorkingDays();
        Date date=new Date(dateFrom.getTime());

        Day[] days=Day.values();
        while(date.getTime()<=dateTo.getTime())
        {
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            Day dayCurrent= DayOfWeek.getDayOfWeekCalendar(c.get(Calendar.DAY_OF_WEEK));
            if(1==c.get(Calendar.DAY_OF_WEEK) || 7==c.get(Calendar.DAY_OF_WEEK) || (notWorkingDays.size()>0 && notWorkingDays.contains(dayCurrent)))
            {
                notWorkindDaysCount++;
            }

            c.add(Calendar.DATE, 1);
            date = c.getTime();
        }

        return notWorkindDaysCount;
    }

    @Override
    public boolean equals(Object o)
    {
        Employee employee = (Employee) o;
        return this.id.equals(employee.getId());
    }

    @Override
    public int hashCode()
    {
        return this.getId().intValue();
    }

    public List<Shift> getShift(Date date)
    {
        AssignmentDao assignmentDao  = Daos.getInstance().getAssignmentDao();
        List<Shift> shifts = new ArrayList<>();
        List<Assignment.Tag> tags = new ArrayList<>();
        tags.add(Assignment.Tag.consultant);
        tags.add(Assignment.Tag.registrar);
        List<Assignment> assignments = assignmentDao.getOncall(this,date,tags);

        assignments.forEach(assignment ->{
            shifts.add(assignment.getShift());
        } );
        if (assignments.size() == 0 )
        {
            this.getWorkingDays().forEach(workingDay ->
            {
                if (workingDay.getDayInt() == date.getDay())
                {
                    shifts.add(workingDay.getShift());
                }
            });
        }
        if (shifts.size() == 0 && date.getDay() !=0  && date.getDay() !=6)
        {
            shifts.add(Shift.getDay());
        }
        return shifts;
    }

    @JsonIgnore
    public boolean isSeniour()
    {
        return this.grade.equals(Grade.SeniorRegistrar);
    }
    @JsonIgnore
    public boolean isJuniour()
    {
        return this.grade.equals(Grade.JuniorRegistrar);
    }
    @JsonIgnore
    public boolean isSho()
    {
        return this.grade.equals(Grade.SHO);
    }
    @JsonIgnore
    public boolean isFy()
    {
        return this.grade.equals(Grade.FY);
    }
    @JsonIgnore
    public boolean isConsultant()
    {
        return this.grade.equals(Grade.Consultant);
    }

    public Set<CronEmployeePayload> getCronActivities()
    {
        return cronActivities;
    }

    public void setCronActivities(Set<CronEmployeePayload> cronActivities)
    {
        this.cronActivities = cronActivities;
    }

    public List<Criteria> getCriteriaList()
    {
        if(null!=criteriaList)
        {
            return criteriaList;
        }

        criteriaList=new ArrayList<>();
        for(EmployeeCriteria item:employeeCriteria)
        {
            criteriaList.add(item.getCriteria());
        }
        return criteriaList;
    }

}
