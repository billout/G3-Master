package com.billoutdgr.g3.domain;

import com.billoutdgr.g3.domain.enumeration.Statut;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Candidats.
 */
@Entity
@Table(name = "candidats")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Candidats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "identifiant")
    private String identifiant;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat")
    private Statut etat;

    @ManyToOne
    @JsonIgnoreProperties(value = { "aDossiers" }, allowSetters = true)
    private Personnes est;

    @ManyToMany(mappedBy = "candidats")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "estComposes", "estPours", "estPars", "candidats" }, allowSetters = true)
    private Set<Concours> concours = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Candidats id(Long id) {
        this.id = id;
        return this;
    }

    public String getIdentifiant() {
        return this.identifiant;
    }

    public Candidats identifiant(String identifiant) {
        this.identifiant = identifiant;
        return this;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public Statut getEtat() {
        return this.etat;
    }

    public Candidats etat(Statut etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(Statut etat) {
        this.etat = etat;
    }

    public Personnes getEst() {
        return this.est;
    }

    public Candidats est(Personnes personnes) {
        this.setEst(personnes);
        return this;
    }

    public void setEst(Personnes personnes) {
        this.est = personnes;
    }

    public Set<Concours> getConcours() {
        return this.concours;
    }

    public Candidats concours(Set<Concours> concours) {
        this.setConcours(concours);
        return this;
    }

    public Candidats addConcours(Concours concours) {
        this.concours.add(concours);
        concours.getCandidats().add(this);
        return this;
    }

    public Candidats removeConcours(Concours concours) {
        this.concours.remove(concours);
        concours.getCandidats().remove(this);
        return this;
    }

    public void setConcours(Set<Concours> concours) {
        if (this.concours != null) {
            this.concours.forEach(i -> i.removeCandidats(this));
        }
        if (concours != null) {
            concours.forEach(i -> i.addCandidats(this));
        }
        this.concours = concours;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Candidats)) {
            return false;
        }
        return id != null && id.equals(((Candidats) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Candidats{" +
            "id=" + getId() +
            ", identifiant='" + getIdentifiant() + "'" +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
