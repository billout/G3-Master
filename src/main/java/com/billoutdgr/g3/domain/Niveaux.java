package com.billoutdgr.g3.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Niveaux.
 */
@Entity
@Table(name = "niveaux")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Niveaux implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "code")
    private String code;

    @OneToMany(mappedBy = "niveaux")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "concours", "niveaux" }, allowSetters = true)
    private Set<Formations> formations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Niveaux id(Long id) {
        this.id = id;
        return this;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Niveaux libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCode() {
        return this.code;
    }

    public Niveaux code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Formations> getFormations() {
        return this.formations;
    }

    public Niveaux formations(Set<Formations> formations) {
        this.setFormations(formations);
        return this;
    }

    public Niveaux addFormations(Formations formations) {
        this.formations.add(formations);
        formations.setNiveaux(this);
        return this;
    }

    public Niveaux removeFormations(Formations formations) {
        this.formations.remove(formations);
        formations.setNiveaux(null);
        return this;
    }

    public void setFormations(Set<Formations> formations) {
        if (this.formations != null) {
            this.formations.forEach(i -> i.setNiveaux(null));
        }
        if (formations != null) {
            formations.forEach(i -> i.setNiveaux(this));
        }
        this.formations = formations;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Niveaux)) {
            return false;
        }
        return id != null && id.equals(((Niveaux) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Niveaux{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
