package fk.retail.ip.requirement.internal.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;


/**
 * Created by nidhigupta.m on 26/01/17.
 */

@Entity
@XmlRootElement
@Getter
@Setter
@NoArgsConstructor
//todo: cleanup
@Table(name = "projection_states")
//@Table(name = "REQUIREMENT")
public class Requirement {

    private static final long serialVersionUID = 1L;

    //todo : add this field in projection_states in old db

    @Id
    private String id;
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
    private Integer poId;

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
        poId = other.poId;

        //TODO: legacy code
        projectionId = other.projectionId;
        panIndiaQuantity = other.panIndiaQuantity;
        sslId = other.sslId;
    }

    public long getGroup() {
        return this.requirementSnapshot.getGroup().getId();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Requirement) {
            return ((Requirement) other).getId().equals(id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
