package com.billoutdgr.g3.service.dto;

import com.billoutdgr.g3.domain.enumeration.Statut;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.billoutdgr.g3.domain.Candidats} entity.
 */
public class CandidatsDTO implements Serializable {

    private Long id;

    private String identifiant;

    private Statut etat;

    private PersonnesDTO est;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public Statut getEtat() {
        return etat;
    }

    public void setEtat(Statut etat) {
        this.etat = etat;
    }

    public PersonnesDTO getEst() {
        return est;
    }

    public void setEst(PersonnesDTO est) {
        this.est = est;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CandidatsDTO)) {
            return false;
        }

        CandidatsDTO candidatsDTO = (CandidatsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, candidatsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CandidatsDTO{" +
            "id=" + getId() +
            ", identifiant='" + getIdentifiant() + "'" +
            ", etat='" + getEtat() + "'" +
            ", est=" + getEst() +
            "}";
    }
}
