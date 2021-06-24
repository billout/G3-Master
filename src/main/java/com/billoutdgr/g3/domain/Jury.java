package com.billoutdgr.g3.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Jury.
 */
@Entity
@Table(name = "jury")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Jury implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @JsonIgnoreProperties(value = { "aDossiers" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Personnes president;

    @JsonIgnoreProperties(value = { "aDossiers" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Personnes membre1;

    @JsonIgnoreProperties(value = { "aDossiers" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Personnes membre2;

    @JsonIgnoreProperties(value = { "aDossiers" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Personnes membre3;

    @JsonIgnoreProperties(value = { "compose" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Epreuves corrige;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Jury id(Long id) {
        this.id = id;
        return this;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Jury libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Personnes getPresident() {
        return this.president;
    }

    public Jury president(Personnes personnes) {
        this.setPresident(personnes);
        return this;
    }

    public void setPresident(Personnes personnes) {
        this.president = personnes;
    }

    public Personnes getMembre1() {
        return this.membre1;
    }

    public Jury membre1(Personnes personnes) {
        this.setMembre1(personnes);
        return this;
    }

    public void setMembre1(Personnes personnes) {
        this.membre1 = personnes;
    }

    public Personnes getMembre2() {
        return this.membre2;
    }

    public Jury membre2(Personnes personnes) {
        this.setMembre2(personnes);
        return this;
    }

    public void setMembre2(Personnes personnes) {
        this.membre2 = personnes;
    }

    public Personnes getMembre3() {
        return this.membre3;
    }

    public Jury membre3(Personnes personnes) {
        this.setMembre3(personnes);
        return this;
    }

    public void setMembre3(Personnes personnes) {
        this.membre3 = personnes;
    }

    public Epreuves getCorrige() {
        return this.corrige;
    }

    public Jury corrige(Epreuves epreuves) {
        this.setCorrige(epreuves);
        return this;
    }

    public void setCorrige(Epreuves epreuves) {
        this.corrige = epreuves;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Jury)) {
            return false;
        }
        return id != null && id.equals(((Jury) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Jury{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
