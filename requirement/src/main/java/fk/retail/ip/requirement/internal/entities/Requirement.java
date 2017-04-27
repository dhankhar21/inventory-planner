package fk.retail.ip.requirement.internal.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;


import javax.persistence.*;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;


/**
 * Created by nidhigupta.m on 26/01/17.
 */

@Entity
@XmlRootElement
@Data
@NoArgsConstructor
//todo: cleanup
@Table(name = "projection_states")
//@Table(name = "REQUIREMENT")
public class Requirement {

//    @GenericGenerator(name="table-hilo-generator", strategy="org.hibernate.id.TableHiLoGenerator",
//            parameters = {
//                    @Parameter(name = "table", value = "hilo")
//            })
//
    @Id
//    @GeneratedValue(generator="table-hilo-generator")
//    @Access(AccessType.PROPERTY)
    private String id;

    private static final long serialVersionUID = 1L;

    //todo : add this field in projection_states in old db
    @NotNull
    private String fsn;

    @NotNull
    private String warehouse;

    //todo:cleanup
    @Column(name = "qty")
    private double quantity;

    private String supplier;

    private Integer mrp;

    private Integer app;

    //todo:cleanup
    @Column(name = "app_currency")
    private String currency;

    private Integer sla;

    private boolean international;

    @NotNull
    private String state;

    private String procType;


    //todo:cleanup
    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "is_current")
    private boolean current;

    //todo: cleanup
    @Column(name = "comment")
    private String overrideComment;

    private String createdBy;

    private String updatedBy;

    private Long sslId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "requirement_snapshot_id")
    private RequirementSnapshot requirementSnapshot;

    //todo: cleanup (fields for backward compatibilty)
    //TODO: legacy code
    @Column(name = "prev_state_id")
    private String previousStateId;

    //TODO: legacy code
    @Column(name = "pan_india")
    private Integer panIndiaQuantity;

    //TODO: legacy code
    @Column(name = "projection_id")
    private Long projectionId;

    //todo:cleanup
    private String mrpCurrency;

    public Requirement(String id) {
        this.id = id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date updatedAt;

    @NotNull
    @Version
    private Long version;

    @PrePersist
    private void beforePersist() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    private void beforeUpdate() {
        updatedAt = new Date();
    }

    public Requirement(Requirement other) {
        fsn = other.fsn;
        warehouse = other.warehouse;
        quantity = other.quantity;
        supplier = other.supplier;
        mrp = other.mrp;
        mrpCurrency = other.mrpCurrency;
        app = other.app;
        currency = other.currency;
        sla = other.sla;
        international = other.international;
        procType = other.procType;
        enabled = other.enabled;
        current = other.current;
        requirementSnapshot = other.requirementSnapshot;

        //TODO: legacy code
        projectionId = other.projectionId;
        panIndiaQuantity = other.panIndiaQuantity;
        sslId = other.sslId;
    }

    public long getGroup() {
        return this.requirementSnapshot.getGroup().getId();
    }
}
