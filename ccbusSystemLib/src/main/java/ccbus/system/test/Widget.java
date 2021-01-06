package ccbus.system.test;

import javax.persistence.*;

@Entity
@Table(name = "widgets")
public class Widget extends BaseEntity {

    @Column(name = "widget_name", nullable = false, columnDefinition = "VARCHAR(255)")
    private String widgetName = null;

    @Column(name = "widget_description", columnDefinition = "VARCHAR(255)")
    private String widgetDescription = null;

    @Column(name = "widget_object", columnDefinition = "VARCHAR(255)")
    private String widgetObject = null;


    //private Domain domain = null;

    public Widget() {
    }

    public String getWidgetName() {
        return widgetName;
    }

    public void setWidgetName(String widgetName) {
        this.widgetName = widgetName;
    }

    public String getWidgetDescription() {
        return widgetDescription;
    }

    public void setWidgetDescription(String widgetDescription) {
        this.widgetDescription = widgetDescription;
    }

    public String getWidgetObject() {
        return widgetObject;
    }

    public void setWidgetObject(String widgetObject) {
        this.widgetObject = widgetObject;
    }

}
