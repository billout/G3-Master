package com.billoutdgr.g3.domain;

import com.billoutdgr.g3.domain.enumeration.TypeEpreuve;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Epreuves.
 */
@Entity
@Table(name = "epreuves")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Epreuves implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeEpreuve type;

    @ManyToOne
    @JsonIgnoreProperties(value = { "estComposes", "estPours", "estPars", "candidats" }, allowSetters = true)
    private Concours compose;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Epreuves id(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public Epreuves code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Epreuves libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public TypeEpreuve getType() {
        return this.type;
    }

    public Epreuves type(TypeEpreuve type) {
        this.type = type;
        return this;
    }

    public void setType(TypeEpreuve type) {
        this.type = type;
    }

    public Concours getCompose() {
        return this.compose;
    }

    public Epreuves compose(Concours concours) {
        this.setCompose(concours);
        return this;
    }

    public void setCompose(Concours concours) {
        this.compose = concours;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Epreuves)) {
            return false;
        }
        return id != null && id.equals(((Epreuves) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Epreuves{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
