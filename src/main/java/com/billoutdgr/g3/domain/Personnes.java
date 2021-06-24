package com.billoutdgr.g3.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Personnes.
 */
@Entity
@Table(name = "personnes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Personnes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;

    @Column(name = "nationnalite")
    private String nationnalite;

    @OneToMany(mappedBy = "est")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "est", "concours" }, allowSetters = true)
    private Set<Candidats> aDossiers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Personnes id(Long id) {
        this.id = id;
        return this;
    }

    public String getNom() {
        return this.nom;
    }

    public Personnes nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Personnes prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Personnes telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return this.email;
    }

    public Personnes email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationnalite() {
        return this.nationnalite;
    }

    public Personnes nationnalite(String nationnalite) {
        this.nationnalite = nationnalite;
        return this;
    }

    public void setNationnalite(String nationnalite) {
        this.nationnalite = nationnalite;
    }

    public Set<Candidats> getADossiers() {
        return this.aDossiers;
    }

    public Personnes aDossiers(Set<Candidats> candidats) {
        this.setADossiers(candidats);
        return this;
    }

    public Personnes addADossier(Candidats candidats) {
        this.aDossiers.add(candidats);
        candidats.setEst(this);
        return this;
    }

    public Personnes removeADossier(Candidats candidats) {
        this.aDossiers.remove(candidats);
        candidats.setEst(null);
        return this;
    }

    public void setADossiers(Set<Candidats> candidats) {
        if (this.aDossiers != null) {
            this.aDossiers.forEach(i -> i.setEst(null));
        }
        if (candidats != null) {
            candidats.forEach(i -> i.setEst(this));
        }
        this.aDossiers = candidats;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Personnes)) {
            return false;
        }
        return id != null && id.equals(((Personnes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Personnes{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            ", nationnalite='" + getNationnalite() + "'" +
            "}";
    }
}
