package io.quarkus.hibernate.orm.rest.data.panache.deployment.entity;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class EmptyListItem extends PanacheEntityBase {

    @Id
    @GeneratedValue
    private Long cid;

    public String name;

    @ManyToOne(optional = false)
    public Collection collection;

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    @JsonbTransient // Avoid infinite loop when serializing
    public Collection getCollection() {
        return collection;
    }

}
