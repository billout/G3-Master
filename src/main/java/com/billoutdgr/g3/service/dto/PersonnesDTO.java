package com.billoutdgr.g3.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.billoutdgr.g3.domain.Personnes} entity.
 */
public class PersonnesDTO implements Serializable {

    private Long id;

    private String nom;

    private String prenom;

    private String telephone;

    private String email;

    private String nationnalite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationnalite() {
        return nationnalite;
    }

    public void setNationnalite(String nationnalite) {
        this.nationnalite = nationnalite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonnesDTO)) {
            return false;
        }

        PersonnesDTO personnesDTO = (PersonnesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, personnesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonnesDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            ", nationnalite='" + getNationnalite() + "'" +
            "}";
    }
}
