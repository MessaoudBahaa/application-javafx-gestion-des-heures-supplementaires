package sample.Classes;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by MB_Be on 9/12/2018.
 */
public class EmployeT extends RecursiveTreeObject<EmployeT> {
    public StringProperty Matricule;
     public StringProperty Nom;
     public StringProperty Prenom;

    public EmployeT(String matricule, String nom, String prenom) {
       this.Matricule= new SimpleStringProperty(matricule);
       this.Nom= new SimpleStringProperty(nom);
       this.Prenom= new SimpleStringProperty(prenom);

    }


}
