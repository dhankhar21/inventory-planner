package fk.retail.ip.requirement.internal.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by agarwal.vaibhav on 18/04/17.
 */

@Entity
@Data
@Table(name = "requirement_event_log")
public class RequirementEventLog{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String entityId;
    private String attribute;
    private String oldValue;
    private String newValue;
    private String reason;
    private String userId;
    private String timestamp;
    private String eventType;

}
