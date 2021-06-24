package com.billoutdgr.g3.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Concours.
 */
@Entity
@Table(name = "concours")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Concours implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "dt_ouverture")
    private LocalDate dtOuverture;

    @Column(name = "dt_cloture")
    private LocalDate dtCloture;

    @OneToMany(mappedBy = "compose")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "compose" }, allowSetters = true)
    private Set<Epreuves> estComposes = new HashSet<>();

    @OneToMany(mappedBy = "concours")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "concours", "niveaux" }, allowSetters = true)
    private Set<Formations> estPours = new HashSet<>();

    @OneToMany(mappedBy = "concours")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "concours" }, allowSetters = true)
    private Set<VoieAcces> estPars = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_concours__candidats",
        joinColumns = @JoinColumn(name = "concours_id"),
        inverseJoinColumns = @JoinColumn(name = "candidats_id")
    )
    @JsonIgnoreProperties(value = { "est", "concours" }, allowSetters = true)
    private Set<Candidats> candidats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Concours id(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public Concours code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Concours libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public LocalDate getDtOuverture() {
        return this.dtOuverture;
    }

    public Concours dtOuverture(LocalDate dtOuverture) {
        this.dtOuverture = dtOuverture;
        return this;
    }

    public void setDtOuverture(LocalDate dtOuverture) {
        this.dtOuverture = dtOuverture;
    }

    public LocalDate getDtCloture() {
        return this.dtCloture;
    }

    public Concours dtCloture(LocalDate dtCloture) {
        this.dtCloture = dtCloture;
        return this;
    }

    public void setDtCloture(LocalDate dtCloture) {
        this.dtCloture = dtCloture;
    }

    public Set<Epreuves> getEstComposes() {
        return this.estComposes;
    }

    public Concours estComposes(Set<Epreuves> epreuves) {
        this.setEstComposes(epreuves);
        return this;
    }

    public Concours addEstCompose(Epreuves epreuves) {
        this.estComposes.add(epreuves);
        epreuves.setCompose(this);
        return this;
    }

    public Concours removeEstCompose(Epreuves epreuves) {
        this.estComposes.remove(epreuves);
        epreuves.setCompose(null);
        return this;
    }

    public void setEstComposes(Set<Epreuves> epreuves) {
        if (this.estComposes != null) {
            this.estComposes.forEach(i -> i.setCompose(null));
        }
        if (epreuves != null) {
            epreuves.forEach(i -> i.setCompose(this));
        }
        this.estComposes = epreuves;
    }

    public Set<Formations> getEstPours() {
        return this.estPours;
    }

    public Concours estPours(Set<Formations> formations) {
        this.setEstPours(formations);
        return this;
    }

    public Concours addEstPour(Formations formations) {
        this.estPours.add(formations);
        formations.setConcours(this);
        return this;
    }

    public Concours removeEstPour(Formations formations) {
        this.estPours.remove(formations);
        formations.setConcours(null);
        return this;
    }

    public void setEstPours(Set<Formations> formations) {
        if (this.estPours != null) {
            this.estPours.forEach(i -> i.setConcours(null));
        }
        if (formations != null) {
            formations.forEach(i -> i.setConcours(this));
        }
        this.estPours = formations;
    }

    public Set<VoieAcces> getEstPars() {
        return this.estPars;
    }

    public Concours estPars(Set<VoieAcces> voieAcces) {
        this.setEstPars(voieAcces);
        return this;
    }

    public Concours addEstPar(VoieAcces voieAcces) {
        this.estPars.add(voieAcces);
        voieAcces.setConcours(this);
        return this;
    }

    public Concours removeEstPar(VoieAcces voieAcces) {
        this.estPars.remove(voieAcces);
        voieAcces.setConcours(null);
        return this;
    }

    public void setEstPars(Set<VoieAcces> voieAcces) {
        if (this.estPars != null) {
            this.estPars.forEach(i -> i.setConcours(null));
        }
        if (voieAcces != null) {
            voieAcces.forEach(i -> i.setConcours(this));
        }
        this.estPars = voieAcces;
    }

    public Set<Candidats> getCandidats() {
        return this.candidats;
    }

    public Concours candidats(Set<Candidats> candidats) {
        this.setCandidats(candidats);
        return this;
    }

    public Concours addCandidats(Candidats candidats) {
        this.candidats.add(candidats);
        candidats.getConcours().add(this);
        return this;
    }

    public Concours removeCandidats(Candidats candidats) {
        this.candidats.remove(candidats);
        candidats.getConcours().remove(this);
        return this;
    }

    public void setCandidats(Set<Candidats> candidats) {
        this.candidats = candidats;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Concours)) {
            return false;
        }
        return id != null && id.equals(((Concours) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Concours{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", dtOuverture='" + getDtOuverture() + "'" +
            ", dtCloture='" + getDtCloture() + "'" +
            "}";
    }
}
