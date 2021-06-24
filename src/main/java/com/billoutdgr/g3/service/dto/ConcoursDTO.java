package com.billoutdgr.g3.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.billoutdgr.g3.domain.Concours} entity.
 */
public class ConcoursDTO implements Serializable {

    private Long id;

    private String code;

    private String libelle;

    private LocalDate dtOuverture;

    private LocalDate dtCloture;

    private Set<CandidatsDTO> candidats = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public LocalDate getDtOuverture() {
        return dtOuverture;
    }

    public void setDtOuverture(LocalDate dtOuverture) {
        this.dtOuverture = dtOuverture;
    }

    public LocalDate getDtCloture() {
        return dtCloture;
    }

    public void setDtCloture(LocalDate dtCloture) {
        this.dtCloture = dtCloture;
    }

    public Set<CandidatsDTO> getCandidats() {
        return candidats;
    }

    public void setCandidats(Set<CandidatsDTO> candidats) {
        this.candidats = candidats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConcoursDTO)) {
            return false;
        }

        ConcoursDTO concoursDTO = (ConcoursDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, concoursDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConcoursDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", dtOuverture='" + getDtOuverture() + "'" +
            ", dtCloture='" + getDtCloture() + "'" +
            ", candidats=" + getCandidats() +
            "}";
    }
}
