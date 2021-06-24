package com.billoutdgr.g3.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VoieAcces.
 */
@Entity
@Table(name = "voie_acces")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VoieAcces implements Serializable {

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

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VoieAcces id(Long id) {
        this.id = id;
        return this;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public VoieAcces libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCode() {
        return this.code;
    }

    public VoieAcces code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Concours getConcours() {
        return this.concours;
    }

    public VoieAcces concours(Concours concours) {
        this.setConcours(concours);
        return this;
    }

    public void setConcours(Concours concours) {
        this.concours = concours;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VoieAcces)) {
            return false;
        }
        return id != null && id.equals(((VoieAcces) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoieAcces{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
