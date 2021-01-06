package ccbus.out;

import com.edatachase.rota.components.Daos;
import com.edatachase.rota.daos.ShiftDao;
import com.edatachase.rota.libs.GenerateColor;
import com.edatachase.rota.libs.ResponseSchedulePreview;
import com.edatachase.rota.payloads.ActivityShiftsPayload;
import com.edatachase.rota.payloads.CronEmployeePayload;
import com.edatachase.rota.payloads.CronEmployeePayloadActivity;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.support.CronSequenceGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class Activity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @OneToOne
    private Type type;

    @OneToOne
    private Expression expression;

    @Enumerated(EnumType.STRING)
    private Deleted deleted = Deleted.No;

    private String color;

    @DateTimeFormat
    private Date dateCreated;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private Set<ActivityShifts> activityShifts;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private Set<ActivitySpeciality> activitySpecialities;

    @DateTimeFormat
    private Date scheduleStartDate = new Date();

    @DateTimeFormat
    private Date scheduleEndDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.MERGE)
    @Fetch(FetchMode.JOIN)
    private Set<CronEmployee> cronEmployees;

    @JsonIgnore
    @OneToMany(mappedBy = "activity",fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private Set<AssignmentActivity> assignmentActivities;

    @Transient
    private Hashtable<String, List<Employee>> hashtable = new Hashtable<>();

    private Boolean isSession = false;

    @OneToMany(mappedBy = "activity",fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JsonIgnore
    private Set<Activity> sessionActivities;

    @Transient
    private Set<CronEmployeePayloadActivity> cronActivities;

    @OneToOne
    private Activity activity;

    @OneToOne
    @JsonIgnore
    private Employee employee;


    public Activity()
    {
    }

    public Activity(GenerateColor generateColor)
    {
        this.color = generateColor.getRandColor();
    }

    public Activity(String name, Type type, Date scheduleStartDate)
    {
        this.name = name;
        this.type = type;
        GenerateColor generateColor = new GenerateColor();
        this.color = generateColor.getRandColor();
        this.dateCreated = new Date();
        this.scheduleStartDate = scheduleStartDate;
    }

    public Set<CronEmployeePayloadActivity> getCronActivities()
    {
        return cronActivities;
    }

    public void setCronActivities(Set<CronEmployeePayloadActivity> cronActivities)
    {
        this.cronActivities = cronActivities;
    }

    public Employee getEmployee()
    {
        return employee;
    }

    public void setEmployee(Employee employee)
    {
        this.employee = employee;
    }

    public Set<Activity> getSessionActivities()
    {
        return sessionActivities;
    }

    public void setSessionActivities(Set<Activity> sessionActivities)
    {
        this.sessionActivities = sessionActivities;
    }

    public Activity getActivity()
    {
        return activity;
    }

    public void setActivity(Activity activity)
    {
        this.activity = activity;
    }

    public Boolean getSession()
    {
        return isSession;
    }

    public void setSession(Boolean session)
    {
        isSession = session;
    }

    public Expression getExpression()
    {
        return expression;
    }

    public void setExpression(Expression expression)
    {
        this.expression = expression;
    }

    public Date getScheduleEndDate()
    {
        return scheduleEndDate;
    }

    public void setScheduleEndDate(Date scheduleEndDate)
    {
        this.scheduleEndDate = scheduleEndDate;
    }

    public Set<CronEmployee> getCronEmployees()
    {
        return cronEmployees;
    }

    public void setCronEmployees(Set<CronEmployee> cronEmployees)
    {
        this.cronEmployees = cronEmployees;
    }

    public Set<AssignmentActivity> getAssignmentActivities()
    {
        return assignmentActivities;
    }

    public void setAssignmentActivities(Set<AssignmentActivity> assignmentActivities)
    {
        this.assignmentActivities = assignmentActivities;
    }

    public Set<ActivitySpeciality> getActivitySpecialities()
    {
        return activitySpecialities;
    }

    public void setActivitySpecialities(Set<ActivitySpeciality> activitySpecialities)
    {
        this.activitySpecialities = activitySpecialities;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getScheduleStartDate()
    {
        return scheduleStartDate;
    }

    public void setScheduleStartDate(Date scheduleStartDate)
    {
        this.scheduleStartDate = scheduleStartDate;
    }

    public Deleted getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Deleted deleted)
    {
        this.deleted = deleted;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
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

    public Set<ActivityShifts> getActivityShifts()
    {
        return activityShifts;
    }

    @JsonGetter("activityShifts")
    public Set<ActivityShiftsPayload> getActivityShiftsTags()
    {
        Set<ActivityShiftsPayload> activityShiftsPayloads = new HashSet<>();
        if(null == activityShifts)
        {
            return null;
        }
        for (ActivityShifts activityShift : activityShifts)
        {

            ActivityShiftsPayload activityShiftsPayload = new ActivityShiftsPayload(activityShift.getShift().getTag(),activityShift.getCron());
            activityShiftsPayloads.add(activityShiftsPayload);
        }

        return activityShiftsPayloads;
    }

    @JsonGetter("cronEmployees")
    public Set<CronEmployeePayload> getCronEmployeesShiftsTags()
    {
        Set<CronEmployeePayload> cronEmployeePayloads = new HashSet<>();
        if(null == cronEmployees)
        {
            return null;
        }
        for (CronEmployee cronEmployee : cronEmployees)
        {
            CronEmployeePayload cronEmployeePayload = new CronEmployeePayload(cronEmployee.getShift().getTag(),cronEmployee.getCron(),cronEmployee.getEmployee());
            cronEmployeePayloads.add(cronEmployeePayload);
        }

        return cronEmployeePayloads;
    }

    public void setActivityShifts(Set<ActivityShifts> activityShifts)
    {
        this.activityShifts = activityShifts;
    }

    @Override
    public boolean equals(Object o)
    {
        Activity activity = (Activity) o;
        return activity.getId().equals(this.getId());
    }

    public Hashtable<String, ResponseSchedulePreview> preview(Date previewStartDate, Date previewEndDate)
    {
        Hashtable<String, ResponseSchedulePreview> responseSchedulePreviews = new Hashtable<>();

        Iterator<ActivityShifts> it = this.getActivityShifts().iterator();
        while (it.hasNext())
        {
            ActivityShifts next = it.next();
            Iterator<Cron> itCron = next.getCron().iterator();
            while (itCron.hasNext())
            {
                Cron nextCron = itCron.next();

                if (nextCron.getType() == Cron.Type.monthly)
                {
                    CronSequenceGenerator cron = new CronSequenceGenerator(nextCron.getExpression());

                    Date cronDate = previewStartDate;
                    if (cronDate.compareTo(this.scheduleStartDate) >= 0)
                    {
                        Date endDate;
                        if (this.scheduleEndDate == null)
                        {
                             endDate = cronDate;
                        }
                        else
                        {
                            endDate = this.scheduleEndDate;
                        }

                        while (cronDate.compareTo(previewEndDate) <= 0 && cronDate.compareTo(endDate) <= 0)
                        {
                            cronDate = cron.next(cronDate);
                            String key = new SimpleDateFormat("yyyy-MM-dd").format(cronDate);
                            if (responseSchedulePreviews.containsKey(key))
                            {
                                if (responseSchedulePreviews.get(key).getType() == Cron.Type.monthly)
                                {
                                    responseSchedulePreviews.get(key).getShifts().add(next.getShift());
                                }

                            } else
                            {

                                List<Shift> shifts = new ArrayList<>();
                                shifts.add(next.getShift());
                                responseSchedulePreviews.put(key, new ResponseSchedulePreview(Cron.Type.monthly, shifts));
                            }
                        }
                    }
                }
            }

        }


        Iterator<ActivityShifts> it2 = this.getActivityShifts().iterator();
        while (it2.hasNext())
        {
            ActivityShifts next = it2.next();
            Iterator<Cron> itCron = next.getCron().iterator();
            while (itCron.hasNext())
            {
                Cron nextCron = itCron.next();

                if (nextCron.getType() == Cron.Type.weekly)
                {
                    CronSequenceGenerator cron = new CronSequenceGenerator(nextCron.getExpression());

                    Date cronDate = previewStartDate;
                    DateTime dateFrom = new DateTime(this.getScheduleStartDate());
                    Date endDate;
                    if (this.scheduleEndDate == null)
                    {
                        endDate = previewEndDate;
                    }
                    else
                    {
                        endDate = this.scheduleEndDate;
                    }
                    do
                    {
                        DateTime dateTo = new DateTime(cronDate);
                        if (cronDate.compareTo(this.scheduleStartDate) >= 0)
                        {
                            if ((dateTo.getWeekOfWeekyear() % nextCron.getTotalWeeks()) == nextCron.getWeekNumber())
                            {
                                String key = new SimpleDateFormat("yyyy-MM-dd").format(cronDate);
                                if (responseSchedulePreviews.containsKey(key))
                                {
                                    if (responseSchedulePreviews.get(key).getType() == Cron.Type.weekly && (!responseSchedulePreviews.get(key).getShifts().contains(next.getShift())))
                                    {
                                        responseSchedulePreviews.get(key).getShifts().add(next.getShift());
                                    }

                                } else
                                {

                                    List<Shift> shifts = new ArrayList<>();
                                    shifts.add(next.getShift());
                                    responseSchedulePreviews.put(key, new ResponseSchedulePreview(Cron.Type.weekly, shifts));
                                }
                            }
                        }
                        cronDate = cron.next(cronDate);

                    }
                    while (cronDate.compareTo(previewEndDate) <= 0&& cronDate.compareTo(endDate) <= 0);
                }
            }

        }


        return responseSchedulePreviews;
    }

    public List<Employee> getActivityConsultantsHashed(Date date, Shift shift)
    {
        String key = new SimpleDateFormat("yyyy-MM-dd-E").format(date)+" "+shift.getTag().toString();
        if(this.hashtable.contains(key))
        {
            return this.hashtable.get(key);
        }
        return getActivityConsultants(date,shift);
    }


    public List<Employee> getActivityConsultants(Date date, Shift shift)
    {
        Iterator<CronEmployee> it2 = this.getCronEmployees().iterator();
        String key = new SimpleDateFormat("yyyy-MM-dd-E").format(date)+" "+shift.getTag().toString();

        while (it2.hasNext())
        {
            CronEmployee next = it2.next();

            Cron nextCron = next.getCron();


                CronSequenceGenerator cron = new CronSequenceGenerator(nextCron.getExpression());
                Date cronDate = this.scheduleStartDate;
                DateTime dateFrom = new DateTime(this.getScheduleStartDate());
                Date endDate;
                if (this.scheduleEndDate == null)
                {
                  DateTime  dateTo2 = new DateTime(date);
                    endDate = dateTo2.plusDays(1).toDate();
                } else
                {
                    endDate = this.scheduleEndDate;
                }
                do
                {
                    DateTime dateTo = new DateTime(cronDate);
                    if (cronDate.compareTo(this.scheduleStartDate) >= 0)
                    {
                        if ((dateTo.getWeekOfWeekyear() % nextCron.getTotalWeeks()) == nextCron.getWeekNumber())
                        {
                            String keyCron = new SimpleDateFormat("yyyy-MM-dd-E").format(cronDate)+" "+next.getShift().getTag().toString();
                            keyCron.concat(next.getShift().getTag().toString());
                            if (key.contentEquals(keyCron) )//&& next.getShift().getTag().equals(shift.getTag()))
                            {
                                if (hashtable.containsKey(keyCron))
                                {
                                    if (! hashtable.get(keyCron).contains(next.getEmployee()))
                                    {
                                        hashtable.get(keyCron).add(next.getEmployee());
                                    }
                                }
                                else
                                {
                                    List<Employee> employees = new ArrayList<>();
                                    employees.add(next.getEmployee());
                                    hashtable.put(keyCron,employees);
                                }
                            }
                        }
                    }
                    cronDate = cron.next(cronDate);

                }
                while (!(cronDate.compareTo(endDate) > 0) );//&& (cronDate.compareTo(date) <= 0));
            }

        List<Employee> employeeList = hashtable.get(key);
        List<Employee> consultants = new ArrayList<>();
        if(null!=employeeList)
        {
            for (Employee employee : employeeList)
            {
                if (employee.isConsultant())
                {
                    consultants.add(employee);
                }
            }
        }

        return consultants;
    }

    @JsonSetter("activityShifts")
    public void setActivityShiftsTags(Set<ActivityShiftsPayload> activityShiftsTags)
    {
        if (activityShiftsTags.isEmpty())
        {
            this.activityShifts = null;
        }
        else
        {
            Set<ActivityShifts> activityShifts = new HashSet<>();
            ShiftDao shiftDao = Daos.getInstance().getShiftDao();
            for (ActivityShiftsPayload activityShiftsTag : activityShiftsTags)
            {
                Shift shift = shiftDao.findFirstByTag(activityShiftsTag.getShift());
                ActivityShifts activityShifts1 = new ActivityShifts(shift, null, activityShiftsTag.getCron());
                activityShifts.add(activityShifts1);
            }

            this.activityShifts = activityShifts;
        }
    }


    @JsonSetter("cronEmployees")
    public void setcronEmployeesShiftsTags(Set<CronEmployeePayload> cronEmployeePayloads)
    {
        if (cronEmployeePayloads.isEmpty())
        {
            this.cronEmployees = null;
        }
        else
        {
            Set<CronEmployee> cronEmployees = new HashSet<>();
            ShiftDao shiftDao = Daos.getInstance().getShiftDao();

            for (CronEmployeePayload cronEmployeePayload : cronEmployeePayloads)
            {
                Shift shift = shiftDao.findFirstByTag(cronEmployeePayload.getShift());
                CronEmployee cronEmployee = new CronEmployee(cronEmployeePayload.getEmployee(), cronEmployeePayload.getCron(), null, shift);
                cronEmployees.add(cronEmployee);
            }

            this.cronEmployees = cronEmployees;
        }
    }

    public enum Deleted
    {
        Yes,
        No
    }
}
