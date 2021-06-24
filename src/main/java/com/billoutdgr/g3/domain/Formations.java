package com.billoutdgr.g3.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Formations.
 */
@Entity
@Table(name = "formations")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Formations implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "code")
    private String code;

    @ManyToOne
    @JsonIgnoreProperties(value = { "estComposes", "estPours", "estPars", "candidats" }, allowSetters = true)
    private Concours concours;

    @ManyToOne
    @JsonIgnoreProperties(value = { "formations" }, allowSetters = true)
    private Niveaux niveaux;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Formations id(Long id) {
        this.id = id;
        return this;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Formations libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCode() {
        return this.code;
    }

    public Formations code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Concours getConcours() {
        return this.concours;
    }

    public Formations concours(Concours concours) {
        this.setConcours(concours);
        return this;
    }

    public void setConcours(Concours concours) {
        this.concours = concours;
    }

    public Niveaux getNiveaux() {
        return this.niveaux;
    }

    public Formations niveaux(Niveaux niveaux) {
        this.setNiveaux(niveaux);
        return this;
    }

    public void setNiveaux(Niveaux niveaux) {
        this.niveaux = niveaux;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Formations)) {
            return false;
        }
        return id != null && id.equals(((Formations) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Formations{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
