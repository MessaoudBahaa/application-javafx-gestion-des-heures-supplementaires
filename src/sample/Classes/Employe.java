package sample.Classes;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;

/**
 * Created by MB_Be on 9/12/2018.
 */
public class Employe extends RecursiveTreeObject<Employe> {
    private StringProperty matricule;
    private StringProperty nom;
    private StringProperty Prenom;
    private StringProperty date;
    private StringProperty poste;
    private StringProperty typeEmploi;
    private StringProperty categorie;
    private StringProperty grilleSalaire;
    private StringProperty direction;
    private StringProperty departement;
    private StringProperty service;

    public Employe(String matricule, String nom, String prenom, String date, String poste, String typeEmploi,String categorie, String grilleSalaire, String direction, String departement, String service) {
        this.matricule = new SimpleStringProperty(matricule);
        this.nom = new SimpleStringProperty(nom);
        Prenom = new SimpleStringProperty(prenom);
        this.date = new SimpleStringProperty(date);
        this.poste = new SimpleStringProperty(poste);
        this.typeEmploi = new SimpleStringProperty(typeEmploi);
        this.categorie = new SimpleStringProperty(categorie);
        this.grilleSalaire = new SimpleStringProperty(grilleSalaire);
        this.direction = new SimpleStringProperty(direction);
        this.departement = new SimpleStringProperty(departement);
        this.service = new SimpleStringProperty(service);
    }

    public String getMatricule() {
        return matricule.get();
    }

    public StringProperty matriculeProperty() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule.set(matricule);
    }

    public String getNom() {
        return nom.get();
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public String getCategorie() {
        return categorie.get();
    }

    public StringProperty categorieProperty() {
        return categorie;
    }

    public String getPrenom() {
        return Prenom.get();
    }

    public StringProperty prenomProperty() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        this.Prenom.set(prenom);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getPoste() {
        return poste.get();
    }

    public StringProperty posteProperty() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste.set(poste);
    }

    public String getTypeEmploi() {
        return typeEmploi.get();
    }

    public StringProperty typeEmploiProperty() {
        return typeEmploi;
    }

    public void setTypeEmploi(String typeEmploi) {
        this.typeEmploi.set(typeEmploi);
    }

    public String getGrilleSalaire() {
        return grilleSalaire.get();
    }

    public StringProperty grilleSalaireProperty() {
        return grilleSalaire;
    }

    public void setGrilleSalaire(String grilleSalaire) {
        this.grilleSalaire.set(grilleSalaire);
    }

    public String getDirection() {
        return direction.get();
    }

    public StringProperty directionProperty() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction.set(direction);
    }

    public String getDepartement() {
        return departement.get();
    }

    public StringProperty departementProperty() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement.set(departement);
    }

    public String getService() {
        return service.get();
    }

    public StringProperty serviceProperty() {
        return service;
    }

    public void setService(String service) {
        this.service.set(service);
    }
}
