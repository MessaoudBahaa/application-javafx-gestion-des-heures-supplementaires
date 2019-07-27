package sample.Controllers;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sample.Classes.Employe;
import sample.Classes.EmployeT;
import sample.Classes.FXutils;
import sample.Classes.MultiDatePicker;


import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Scanner;


/**
 * Created by MB_Be on 9/5/2018.
 */
public class HomeController implements Initializable {

    public static Stage HomeStage;
    @FXML
    private Label test;

    @FXML
    private JFXTextField searchFeild;

    @FXML
    private Label matriculeF;

    @FXML
    private Label creditTotalLabel;

    @FXML
    private Label nomF;

    @FXML
    private Label prenomF;

    @FXML
    private Label typeEmploiF;

    @FXML
    private Label grilleSalaireF;

    @FXML
    private Label posteF;

    @FXML
    private Label directionF;

    @FXML
    private Label departementF;

    @FXML
    private Label serviceF;

    @FXML
    private Label dateEntreeF;

    @FXML
    private Label categorieF;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;

    private ObservableList<Employe> employes = FXCollections.observableArrayList();

    @FXML
    private JFXTreeTableView<Employe> employesTable;

    ///////////////////////////////  TAB PANE ELEMENTS  ///////////////////////////////////////////////////

    @FXML
    private  Button plusButton;

    @FXML
    private JFXCheckBox supDayCheck;

    @FXML
    private DatePicker supDate;

    @FXML
    private JFXTextField hsup50;

    @FXML
    private JFXTextField hsup75;

    @FXML
    private JFXTextField hsup100;

    @FXML
    private JFXTextField hsuprepos;

    @FXML
    private JFXButton InsererButtonSup;

    @FXML
    private JFXButton etatButton;

    @FXML
    private VBox recuperationVBox;
    
    @FXML
    private JFXCheckBox recupDayCheck;
    @FXML
    private JFXDatePicker recupDate;

    @FXML
    private JFXTextField recDu;

    @FXML
    private JFXTextField recAu;

    @FXML
    private  JFXCheckBox recup816check;

    @FXML
    private JFXButton InsererButtonRec;

    @FXML
    private JFXButton etatButton1;

    @FXML
    private JFXCheckBox payMonthCheck;

    @FXML
    private JFXDatePicker payDate;

    @FXML
    private JFXTextField hpay50;

    @FXML
    private JFXTextField hpay75;

    @FXML
    private JFXTextField hpay100;

    @FXML
    private JFXButton InsererButtonPay;

    @FXML
    private JFXButton etatButton2;

    @FXML
    private AnchorPane dateRangeVbox;



    //////////////////////////////   END TAB PANE ELEMENTS ////////////////////////////////////////////////////

    public static Employe employeCourant;
    public static Parent rt;

    private String                    pattern       = "yyyy-MM-dd";
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

    public ObservableList<LocalDate> selectedDates = FXCollections.observableArrayList();
    private    static boolean isAdmin = false;
    private static String userName = "Karim";
    private static String name = "Karim";
    private static String surname = "Karim";
    private static int idUser = 1 ;
    @FXML
    void drawerInOut(MouseEvent event) {
        /*HamburgerBackArrowBasicTransition burgerTask2 = new HamburgerBackArrowBasicTransition(hamburger);
        burgerTask2.setRate(-1);
        burgerTask2.setRate(burgerTask2.getRate() * -1);
        burgerTask2.play();
        if (drawer.isVisible()){
            drawer.close();
        }else {
            drawer.open();
        }*/
    }
    public static void SetUser(String _name, boolean _isAdmin) throws SQLException, ClassNotFoundException {
        userName = _name;
        isAdmin = _isAdmin;

        DB_CNX.getConnection();
        String sql = "Select id,name,surname  from user WHERE  user.username = '"+_name+"'";
        System.out.println(sql);
        ResultSet res = DB_CNX.statement.executeQuery(sql);
        while (res.next()){
            idUser= res.getInt("id");
            name= res.getString("name");
            surname= res.getString("surname");
        }
        System.out.println(idUser+name+surname);
        DB_CNX.closeConnection();

    }

    public void constructTable(){

        ///////////////////////// NEW COLUMN FOR THE TABLE VIEW/////////////////////////////////////////
        JFXTreeTableColumn<Employe,String> matEmployeColumn = new JFXTreeTableColumn<>("Matricule");
        matEmployeColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Employe, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Employe, String> param) {
                return param.getValue().getValue().matriculeProperty();
            }
        });
        ///////////////////////// NEW COLUMN FOR THE TABLE VIEW/////////////////////////////////////////
        JFXTreeTableColumn<Employe,String> nomColumn = new JFXTreeTableColumn<>("Nom");
        nomColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Employe, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Employe, String> param) {
                return param.getValue().getValue().nomProperty();
            }
        });
        ///////////////////////// NEW COLUMN FOR THE TABLE VIEW/////////////////////////////////////////
        JFXTreeTableColumn<Employe,String> prenomColumn = new JFXTreeTableColumn<>("Prénom");
        prenomColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Employe, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Employe, String> param) {
                return param.getValue().getValue().prenomProperty();
            }
        });
        ///////////////////////// NEW COLUMN FOR THE TABLE VIEW/////////////////////////////////////////
        JFXTreeTableColumn<Employe,String> dateColumn = new JFXTreeTableColumn<>("Date d'entrée");
        dateColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Employe, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Employe, String> param) {
                return param.getValue().getValue().dateProperty();
            }
        });
        ///////////////////////// NEW COLUMN FOR THE TABLE VIEW/////////////////////////////////////////
        JFXTreeTableColumn<Employe,String> posteColumn = new JFXTreeTableColumn<>("poste");
        posteColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Employe, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Employe, String> param) {
                return param.getValue().getValue().posteProperty();
            }
        });
        ///////////////////////// NEW COLUMN FOR THE TABLE VIEW/////////////////////////////////////////
        JFXTreeTableColumn<Employe,String> typeEmploiColumn = new JFXTreeTableColumn<>("Type d'emploi");
        typeEmploiColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Employe, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Employe, String> param) {
                return param.getValue().getValue().typeEmploiProperty();
            }
        });
        ///////////////////////// NEW COLUMN FOR THE TABLE VIEW/////////////////////////////////////////
        JFXTreeTableColumn<Employe,String> categorieColumn = new JFXTreeTableColumn<>("Catégorie");
        categorieColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Employe, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Employe, String> param) {
                return param.getValue().getValue().categorieProperty();
            }
        });
        ///////////////////////// NEW COLUMN FOR THE TABLE VIEW/////////////////////////////////////////
        JFXTreeTableColumn<Employe,String> grilleSalaireColumn = new JFXTreeTableColumn<>("Grille salaire");
        grilleSalaireColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Employe, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Employe, String> param) {
                return param.getValue().getValue().grilleSalaireProperty();
            }
        });
        ///////////////////////// NEW COLUMN FOR THE TABLE VIEW/////////////////////////////////////////
        JFXTreeTableColumn<Employe,String> directionColumn = new JFXTreeTableColumn<>("Direction");
        directionColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Employe, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Employe, String> param) {
                return param.getValue().getValue().directionProperty();
            }
        });
        ///////////////////////// NEW COLUMN FOR THE TABLE VIEW/////////////////////////////////////////
        JFXTreeTableColumn<Employe,String> departementColumn = new JFXTreeTableColumn<>("Departement");
        departementColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Employe, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Employe, String> param) {
                return param.getValue().getValue().departementProperty();
            }
        });
        ///////////////////////// NEW COLUMN FOR THE TABLE VIEW/////////////////////////////////////////
        JFXTreeTableColumn<Employe,String> serviceColumn = new JFXTreeTableColumn<>("Service");
        serviceColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Employe, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Employe, String> param) {
                return param.getValue().getValue().serviceProperty();
            }
        });


        final TreeItem<Employe> root = new RecursiveTreeItem<Employe>(employes,RecursiveTreeObject::getChildren);
        employesTable.setRoot(root);
        employesTable.setShowRoot(false);
        employesTable.getColumns().setAll(matEmployeColumn,nomColumn,prenomColumn,dateColumn,posteColumn,directionColumn,departementColumn,serviceColumn,grilleSalaireColumn,typeEmploiColumn);

        employesTable.getSelectionModel().selectedItemProperty().addListener((Observable, oldValue, newValue) -> {

            if(newValue !=null){
                employeCourant = new Employe(newValue.getValue().getMatricule()
                        ,newValue.getValue().getNom()
                        ,newValue.getValue().getPrenom()
                        ,newValue.getValue().getDate()
                        ,newValue.getValue().getPoste()
                        ,newValue.getValue().getTypeEmploi()
                        ,newValue.getValue().getCategorie()
                        ,newValue.getValue().getGrilleSalaire()
                        ,newValue.getValue().getDirection()
                        ,newValue.getValue().getDepartement()
                        ,newValue.getValue().getService()
                        );
                System.out.println(newValue.getValue().getNom());
                matriculeF.setText(newValue.getValue().getMatricule());
                nomF.setText(newValue.getValue().getNom());
                prenomF.setText(newValue.getValue().getPrenom());
                grilleSalaireF.setText(newValue.getValue().getGrilleSalaire());
                typeEmploiF.setText(newValue.getValue().getTypeEmploi());
                posteF.setText(newValue.getValue().getPoste());
                directionF.setText(newValue.getValue().getDirection());
                departementF.setText(newValue.getValue().getDepartement());
                serviceF.setText(newValue.getValue().getService());
                dateEntreeF.setText(newValue.getValue().getDate());
                categorieF.setText(newValue.getValue().getCategorie());
                refreshCredits();
            }
        });

    }

    public  void refreshCredits(){
        try {
            double total=calculer();
            if (total>=0){
                System.out.println("u r safe ");
                employesTable.setId("employeTableV");
                creditTotalLabel.setText(String.format("%.2f", total)+"");
                creditTotalLabel.setId("creditHeuresV");

            }else{
                employesTable.setId("employeTableR");
                creditTotalLabel.setText(String.format("%.2f", (-total))+"");
                creditTotalLabel.setId("creditHeuresR");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void getAllEmployes() throws SQLException, ClassNotFoundException {
        DB_CNX.getConnection();
        String sql = "SELECT * FROM employe WHERE 1";
        ResultSet res=  DB_CNX.statement.executeQuery(sql);
        while(res.next()){
            employes.add(new Employe(res.getString("Matricule")
                    ,res.getString("Nom"),res.getString("Prénom")
                    ,res.getString("DateEntree"),res.getString("Poste")
                    ,res.getString("TypeEmploi"),res.getString("Categorie")
                    , res.getString("GrilleSalaire"),res.getString("Direction")
                    ,res.getString("Departement"),res.getString("Service")));
        }
        DB_CNX.closeConnection();



    }

    @FXML
    void search(KeyEvent event) {
        employesTable.setPredicate(elm -> {
            Boolean bl=elm.getValue().matriculeProperty().getValue().contains(searchFeild.getText())
                    || elm.getValue().nomProperty().getValue().toUpperCase().contains(searchFeild.getText().toUpperCase())
                    || elm.getValue().prenomProperty().getValue().toUpperCase().contains(searchFeild.getText().toUpperCase())
                    || elm.getValue().directionProperty().getValue().toUpperCase().contains(searchFeild.getText().toUpperCase())
                    || elm.getValue().departementProperty().getValue().toUpperCase().contains(searchFeild.getText().toUpperCase())
                    || elm.getValue().serviceProperty().getValue().toUpperCase().contains(searchFeild.getText().toUpperCase())
                    || elm.getValue().categorieProperty().getValue().toUpperCase().contains(searchFeild.getText().toUpperCase())
                    || elm.getValue().posteProperty().getValue().toUpperCase().contains(searchFeild.getText().toUpperCase())
                    || elm.getValue().typeEmploiProperty().getValue().toUpperCase().contains(searchFeild.getText().toUpperCase());

            return bl;
        });
    }

    @FXML
    void genererEtatPay(ActionEvent event) {
        System.out.println("genarating paied state ! ... ");
    }

    @FXML
    void genererEtatRecu(ActionEvent event) throws SQLException, ClassNotFoundException {
        System.out.println("genarating recup state ! ... ");
        calculer();
    }

    @FXML
    void genererEtatSup(ActionEvent event) throws IOException {

        System.out.println("genarating suppp state ! ... " );

        try {
            Process p = Runtime.getRuntime().exec("pwd");//Windows command, use "ls -oa" for UNIX
            Scanner sc = new Scanner(p.getInputStream());
            while (sc.hasNext()) System.out.println(sc.nextLine());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        ExcelGenerator.RemplirCoordonneesEmploye(employeCourant,"ModeleEtatSup","EtatsHeuresSupplementaires",false);




        try {
            DB_CNX.getConnection();

            String sql ="SELECT dateS, H50,H75,H100,Hrepos, HTot,HtotM FROM drh_test.lignsup where month(dateS)=month('"+supDate.getValue()+"' ) and idEmp="+employeCourant.getMatricule()+";";
            System.out.println(sql);
            ResultSet res = DB_CNX.statement.executeQuery(sql);

            while (res.next()) {
                System.out.println(res.getDate("dateS"));
                ExcelGenerator.RemplirTableauSup(employeCourant,res.getDate("dateS"),res.getInt("H50"),res.getInt("H75"),res.getInt("H100"),res.getInt("Hrepos"),res.getInt("Htot"), false);
            }
        DB_CNX.closeConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        File f = new File("OutPut\\EtatsHeuresSupplementaires\\"+employeCourant.getMatricule()+"_"+employeCourant.getNom()+"_"+employeCourant.getPrenom()+".xlsx");
        Desktop.getDesktop().open(f);






    }

    @FXML
    void ajouterPay(ActionEvent event)  {
        int h50=getInt(hpay50.getText())
                ,h75=getInt(hpay75.getText())
                ,h100=getInt(hpay100.getText());
        String sql;
        try{
            if (payMonthCheck.isSelected() || payDate.getValue()==null) {
                sql = "INSERT INTO lignhpay (idEmp, idUser, dateHP, createdAt, updatedAt, H50, H75, H100)" +
                        " VALUES (" + employeCourant.getMatricule() + "," + idUser + ", '2000-01-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, " + h50 + ", " + h75 + ", " + h100 + ");";
            }else{
                sql = "INSERT INTO lignhpay (idEmp, idUser, dateHP, createdAt, updatedAt, H50, H75, H100)" +
                        " VALUES (" + employeCourant.getMatricule() + "," + idUser + ", '"+payDate.getValue().toString()+"', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, " + h50 + ", " + h75 + ", " + h100 + ");";

            }

        }
        catch (RuntimeException e ){
            e.printStackTrace();
            sql ="";
        }

        System.out.println(sql);
        try {

            DB_CNX.getConnection();
            DB_CNX.statement.executeUpdate(sql);
            DB_CNX.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        refreshCredits();
         }

    @FXML
    void ajouterRec(ActionEvent event) {
        int du= getInt(recDu.getText())
                , au=getInt(recAu.getText());
        String sql;
        try {
            if(!(recup816check.isSelected()))
            {

                if (recupDayCheck.isSelected() || recupDate.getValue()==null){
                sql = "INSERT INTO lignrec (idEmp, idUser, dateR, createdAt, updatedAt, du, au)" +
                        " VALUES ("+employeCourant.getMatricule()+", "+idUser+", '2000-01-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, "+du+", "+au+");";
                }else {
                    sql = "INSERT INTO lignrec (idEmp, idUser, dateR, createdAt, updatedAt, du, au)" +
                            " VALUES ("+employeCourant.getMatricule()+", "+idUser+", '"+recupDate.getValue().toString()+"' , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, "+du+", "+au+");";

                }

            }else{
                if (recupDayCheck.isSelected() || recupDate.getValue()==null){
                    sql = "INSERT INTO lignrec (idEmp, idUser, dateR, createdAt, updatedAt)" +
                            " VALUES ("+employeCourant.getMatricule()+", "+idUser+", '2000-01-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);";
                }else {
                    sql = "INSERT INTO lignrec (idEmp, idUser, dateR, createdAt, updatedAt)" +
                            " VALUES ("+employeCourant.getMatricule()+", "+idUser+", '"+recupDate.getValue().toString()+"', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);";

                }

            }


        }catch (RuntimeException e ){
            System.out.println("please select an employee to add hours ");
            e.printStackTrace();
            sql="";
        }
        System.out.println(sql);
        try {
            DB_CNX.getConnection();
            DB_CNX.statement.executeUpdate(sql);
            DB_CNX.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        refreshCredits();
    }

    @FXML
    void ajouterSup(ActionEvent event) {

        int h50=getInt(hsup50.getText())
                ,h75=getInt(hsup75.getText())
                ,h100=getInt(hsup100.getText())
                ,hrepos=getInt(hsuprepos.getText());
        String sql;
        try {
            if (supDate.getValue() == null || supDayCheck.isSelected()) {
                sql = "INSERT INTO drh_test.lignsup (idEmp, idUser, dateS,H50, H75,H100, HRepos)" +
                        " VALUES (" + employeCourant.getMatricule() + ", " + idUser + ", '2000-01-01', " + h50 + "," + h75 + ", " + h100 + ", " + hrepos + ");";
            } else {
                sql = "INSERT INTO drh_test.lignsup (idEmp, idUser, dateS,H50, H75,H100, HRepos)" +
                        " VALUES (" + employeCourant.getMatricule() + ", " + idUser + ", '" + supDate.getValue().toString() + "', " + h50 + "," + h75 + ", " + h100 + ", " + hrepos + ");";

            }
        }catch (RuntimeException e ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Attention");
            alert.setHeaderText("Acun employé selectionné !");
            alert.setContentText("S'il vous plait vous devez selectionner l'employé d'abbord");
            alert.showAndWait();
            sql="";
        }

        System.out.println(sql);
        try {
            DB_CNX.getConnection();


            DB_CNX.statement.executeUpdate(sql);

            DB_CNX.closeConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        refreshCredits();
    }


    public int getInt(String test){
        try{
            return Integer.parseInt(test.trim());
        }catch(Exception e){
            return 0;
        }
    }


    public double calculer() throws SQLException, ClassNotFoundException {
        double Hrestantes=0,Hsup=0,Hrecup=0,Hpay=0;
        String sqlTotalSup,sqlTotalRecup, sqlTotalPay ;
        ResultSet resSup,resRecup,resPay;
        DB_CNX.getConnection();
        sqlTotalSup = "SELECT idEmp as mat, SUM(HTot) as totalParEmp , SUM(HTotM) as totalMajoreParEmp  " +
                " FROM lignSup where idEmp = "+employeCourant.getMatricule()+
                " GROUP BY mat" +
                ";";

        sqlTotalRecup="SELECT idEmp as mat, SUM(HTot) as totalParEmp " +
                " FROM lignrec where idEmp = " +employeCourant.getMatricule()+
                " GROUP BY mat" +
                ";";
        sqlTotalPay="SELECT idEmp as mat, SUM(HTot) as totalParEmp , SUM(HTotM) as totalMajoreParEmp\n" +
                " FROM lignhpay where idEmp = " +employeCourant.getMatricule()+
                " GROUP BY mat\n" +
                ";";
        System.out.println(sqlTotalSup);
        resSup=DB_CNX.statement.executeQuery(sqlTotalSup);
        while(resSup.next() ) {Hsup= resSup.getDouble("TotalMajoreParEmp");}

        resRecup=DB_CNX.statement.executeQuery(sqlTotalRecup);
        while(resRecup.next()){Hrecup= resRecup.getInt("totalParEmp");}


        resPay=DB_CNX.statement.executeQuery(sqlTotalPay);
        while(resPay.next()){Hpay=resPay.getDouble("totalMajoreParEmp");}

        Hrestantes=Hsup-Hrecup-Hpay;
        System.out.println(Hrestantes);
        DB_CNX.closeConnection();

        return Hrestantes;
    }

    public void clearRecupDates(){
        selectedDates.clear();
    }
    public void chooseRecupDates(){
        Popup popup = new Popup();
        popup.getContent().add(recupDate);
        popup.setAutoHide(true);
        Window window = plusButton.getScene().getWindow();
        Bounds bounds = plusButton.localToScene(plusButton.getBoundsInLocal());
        double x      = window.getX() + bounds.getMinX();
        double y      = window.getY() + bounds.getMinY() + bounds.getHeight() + 5;
        popup.show(plusButton, plusButton.getLayoutX() , plusButton.getLayoutY());
        recupDate.show();

    }
    public  void manageCalendar( javafx.scene.input.KeyEvent ke){
        recupDate.show();
        if (selectedDates!=null){
            FXCollections.sort(selectedDates);
        }
        System.out.println("key pressed");
        KeyCode keyCode = ke.getCode();
        if (keyCode.equals(KeyCode.ALT)){
            selectedDates.remove(recupDate.getValue());
        }

        else if (keyCode.equals(KeyCode.CONTROL)){
            if (!selectedDates.contains(recupDate.getValue())) {
                selectedDates.add(recupDate.getValue());
            }
        }
        else if (keyCode.equals(KeyCode.SHIFT)){
            LocalDate delocalDate = selectedDates.get(selectedDates.size()-1);
            LocalDate aulocalDate = recupDate.getValue();
            System.out.println(delocalDate.compareTo(aulocalDate));
            System.out.println(delocalDate);

            System.out.println(delocalDate);
            while (delocalDate.compareTo(aulocalDate)<=0){
                if (!selectedDates.contains(delocalDate)) {
                    selectedDates.add(delocalDate);
                }
                delocalDate = delocalDate.plusDays(1);
                System.out.println(delocalDate);
                System.out.println(delocalDate.toString());
            }
        }
        else if (keyCode.equals(KeyCode.DELETE)){
            selectedDates.clear();
        }
        recupDate.show();


    }

    public void refreshRecupDate(){
        //recupDate.hide();
        recupDate.show();
    }

    private static boolean removeSelectedDates(ObservableList<LocalDate> selectedDates, ListView<LocalDate> dateList) {
        return selectedDates.removeAll(dateList.getSelectionModel().getSelectedItems());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /////////////////////////////////// Initialisation de la date //////////////////////////////////////////////

        recupDate.setShowWeekNumbers(true);

        recupDate.setOnAction(event ->{
            System.out.println("Selected date: " + recupDate.getValue());
                recupDate.show();
            });
        recupDate.setPromptText(pattern);
        recupDate.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return (date == null) ? "" : dateFormatter.format(date);
            }

            @Override
            public LocalDate fromString(String string) {
                return ((string == null) || string.isEmpty()) ? null : LocalDate.parse(string, dateFormatter);
            }
        });
        recupDate.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        boolean alreadySelected = selectedDates.contains(item);
                        //setDisable(alreadySelected);
                        setStyle(alreadySelected ? "-fx-background-color: #09a30f;" : "");
                    }
                };
            }
        });

        //recupDate.setOnKeyPressed((javafx.scene.input.KeyEvent ke)->{

       // });
        /////////////////////////////////////////////////////////////////////////////////////////


        try {
            VBox box = FXMLLoader.load(getClass().getResource("../Views/DrawerContent.fxml"));
            drawer.setSidePane(box);
            HamburgerBackArrowBasicTransition burgerTask2 = new HamburgerBackArrowBasicTransition(hamburger);
            burgerTask2.setRate(-1);

            hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (EventHandler<MouseEvent>) (event) -> {
                burgerTask2.setRate(burgerTask2.getRate() * -1);
                burgerTask2.play();

                if (drawer.isOpened()){
                    drawer.close();
                }else {
                    drawer.open();
                }
            });

            ////////////////////////////////////////////







            ////////////////////////////////////

            VBox box2= (VBox) box.getChildren().get(1);
            Label label= (Label) box2.getChildren().get(1);
            label.setText(userName);


            System.out.println(label.getText());
            ///////////////////////////////////
            for (Node node : box.getChildren()){
                if (node.getAccessibleText() !=null){
                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        switch ( node.getAccessibleText()){
                            case "deconnect":
                                System.out.println("deconnecting");
                                HomeStage.close();
                                Main.stage.show();
                                break;
                            case "exit":
                                System.out.println("exiting");
                                System.exit(0);
                                break;

                        }



                    });

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }



        ///// Charger touts les employes de la base de donnee
        try {
            getAllEmployes();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // construire la table des employes --- table view
        constructTable();



//        test.setText( "Welcome Mr " + userName + "\n"+ isAdmin );
    }
    Stage stage;
    public  void Main(Stage stage){
        this.stage=stage;

    }
}
