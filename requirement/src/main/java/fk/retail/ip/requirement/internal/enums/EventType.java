package fk.retail.ip.requirement.internal.enums;

/**
 * Created by agarwal.vaibhav on 24/04/17.
 */
public enum EventType {
    CREATE_REQUIREMENT(1),
    OVERRIDE(2),
    APPROVAL(3),
    CANCELLATION(4);

    private int eventValue;
    EventType(int type) {
        this.eventValue = type;
    }

    public int getEventValue() {
        return eventValue;
    }

}
