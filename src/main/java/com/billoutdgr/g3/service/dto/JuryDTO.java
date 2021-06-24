package com.billoutdgr.g3.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.billoutdgr.g3.domain.Jury} entity.
 */
public class JuryDTO implements Serializable {

    private Long id;

    private String libelle;

    private PersonnesDTO president;

    private PersonnesDTO membre1;

    private PersonnesDTO membre2;

    private PersonnesDTO membre3;

    private EpreuvesDTO corrige;

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

    public PersonnesDTO getPresident() {
        return president;
    }

    public void setPresident(PersonnesDTO president) {
        this.president = president;
    }

    public PersonnesDTO getMembre1() {
        return membre1;
    }

    public void setMembre1(PersonnesDTO membre1) {
        this.membre1 = membre1;
    }

    public PersonnesDTO getMembre2() {
        return membre2;
    }

    public void setMembre2(PersonnesDTO membre2) {
        this.membre2 = membre2;
    }

    public PersonnesDTO getMembre3() {
        return membre3;
    }

    public void setMembre3(PersonnesDTO membre3) {
        this.membre3 = membre3;
    }

    public EpreuvesDTO getCorrige() {
        return corrige;
    }

    public void setCorrige(EpreuvesDTO corrige) {
        this.corrige = corrige;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JuryDTO)) {
            return false;
        }

        JuryDTO juryDTO = (JuryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, juryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JuryDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", president=" + getPresident() +
            ", membre1=" + getMembre1() +
            ", membre2=" + getMembre2() +
            ", membre3=" + getMembre3() +
            ", corrige=" + getCorrige() +
            "}";
    }
}
