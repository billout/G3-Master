package com.billoutdgr.g3.service.dto;

import com.billoutdgr.g3.domain.enumeration.TypeEpreuve;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.billoutdgr.g3.domain.Epreuves} entity.
 */
public class EpreuvesDTO implements Serializable {

    private Long id;

    private String code;

    private String libelle;

    private TypeEpreuve type;

    private ConcoursDTO compose;

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

    public TypeEpreuve getType() {
        return type;
    }

    public void setType(TypeEpreuve type) {
        this.type = type;
    }

    public ConcoursDTO getCompose() {
        return compose;
    }

    public void setCompose(ConcoursDTO compose) {
        this.compose = compose;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EpreuvesDTO)) {
            return false;
        }

        EpreuvesDTO epreuvesDTO = (EpreuvesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, epreuvesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EpreuvesDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", type='" + getType() + "'" +
            ", compose=" + getCompose() +
            "}";
    }
}
