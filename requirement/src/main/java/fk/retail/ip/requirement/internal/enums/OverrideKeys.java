package fk.retail.ip.requirement.internal.enums;

/**
 * Created by agarwal.vaibhav on 03/03/17.
 */
public enum OverrideKeys {

    SLA("sla"),
    QUANTITY("quantity"),
    APP("app"),
    SUPPLIER("supplier"),
    OVERRIDE_COMMENT("override_comment"),
    SUCCESS("success"),
    FAILURE("failure"),
    STATUS("status"),
    UPDATE("update");

    private String key;

    OverrideKeys(String key) {
        this.key = key;
    }

    @Override
    public String toString() { return this.key;}
}