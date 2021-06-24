package com.billoutdgr.g3.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.billoutdgr.g3.domain.VoieAcces} entity.
 */
public class VoieAccesDTO implements Serializable {

    private Long id;

    private String libelle;

    private String code;

    private ConcoursDTO concours;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ConcoursDTO getConcours() {
        return concours;
    }

    public void setConcours(ConcoursDTO concours) {
        this.concours = concours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VoieAccesDTO)) {
            return false;
        }

        VoieAccesDTO voieAccesDTO = (VoieAccesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, voieAccesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoieAccesDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", code='" + getCode() + "'" +
            ", concours=" + getConcours() +
            "}";
    }
}
