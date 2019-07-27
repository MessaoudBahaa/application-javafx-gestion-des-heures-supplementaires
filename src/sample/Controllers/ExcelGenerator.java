package sample.Controllers;

import javafx.scene.control.Alert;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sample.Classes.Employe;

import java.awt.*;
import java.io.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.List;


/**
 * Created by MB_Be on 9/27/2018.
 */
public class ExcelGenerator {

    public static boolean printExcelCell(XSSFWorkbook w,int sheetIndex,int Row,int cellIndex,String data){
        try{
        XSSFSheet worksheet = w.getSheetAt(sheetIndex);
        XSSFCell cell = worksheet.getRow(Row).getCell(cellIndex);
        cell.setCellValue(data);
        return true;
        }catch (Exception e){
            return false;
        }

    }
    public static boolean printExcelCellInt(XSSFWorkbook w,int sheetIndex,int Row,int cellIndex,double data){
        try{
        XSSFSheet worksheet = w.getSheetAt(sheetIndex);
        XSSFCell cell = worksheet.getRow(Row).getCell(cellIndex);
        cell.setCellValue(data);
        return true;
        }catch (Exception e){
            return false;
        }

    }
    public static boolean printExcelCellDate(XSSFWorkbook w,int sheetIndex,int Row,int cellIndex,LocalDate data){
        return printExcelCell(w,sheetIndex,Row,cellIndex,data.toString());

    }



    public static  void ajouterLigneEtatSup(XSSFWorkbook w,int sheetIndex,int row,LocalDate date, int H50,int H75 , int H100 , int Hrepo, int Htotal){



        printExcelCellDate(w,0,row,0,date);
        printExcelCellInt(w,0,row,1,H50);
        printExcelCellInt(w,0,row,2,H75);
        printExcelCellInt(w,0,row,3,H100);
        printExcelCellInt(w,0,row,4,Hrepo);
        printExcelCellInt(w,0,row,5,Htotal);


    }


    public static  void RemplirTableauSup(Employe employe
            ,Date date, int H50,int H75 , int H100 , int Hrepo, int Htotal , boolean openFile){
        FileInputStream fsIP= null;
        try {
            fsIP = new FileInputStream(new File("OutPut\\EtatsHeuresSupplementaires\\"+employe.getMatricule()+"_"+employe.getNom()+"_"+employe.getPrenom()+".xlsx"));

//Access the workbook
            XSSFWorkbook wb = new XSSFWorkbook(fsIP);

//Access the worksheet, so that we can update / modify it
            XSSFSheet sheet = wb.getSheetAt(0);

            int i=20;


            XSSFCell cell=null;
            cell = sheet.getRow(i).getCell(0);
            System.out.println(cell.toString()+cell.getStringCellValue()+ "       " + cell.getStringCellValue().toString());
           while (!cell.toString().equals("") && !cell.toString().equals("Total Général") ){
                System.out.println(i+"");
                i++;
                cell = sheet.getRow(i).getCell(0);
            }

            ajouterLigneEtatSup(wb,0,i,LocalDate.parse(date.toString()),H50,H75,H100,Hrepo,Htotal);


            wb.setForceFormulaRecalculation(true);

/*
            sheet = wb.getSheetAt(0);
            sheet.setSheetPassword("123456789", null);*/

            // sheet.enableLocking();

            /*
            cell.setCellFormula("=SUM(B21:B30)");
            cell = sheet.getRow(30 ).getCell(2);
            cell.setCellFormula("=SUM(C21:C30)");
            cell = sheet.getRow(30 ).getCell(3);
            cell.setCellFormula("=SUM(D21:D30)");
            cell = sheet.getRow(30 ).getCell(4);
            cell.setCellFormula("=SUM(E21:E30)");
            cell = sheet.getRow(30 ).getCell(5);

            cell.setCellFormula("=SUM(F21:F30)");*/
//Close the InputStream
            fsIP.close();
//Open FileOutputStream to write updates
            FileOutputStream output_file =new FileOutputStream(new File("OutPut\\EtatsHeuresSupplementaires\\"+employe.getMatricule()+"_"+employe.getNom()+"_"+employe.getPrenom()+".xlsx"));
            //write changes
            wb.write(output_file);
//close the stream
            output_file.close();


            if(openFile){
                File f = new File("OutPut\\EtatsHeuresSupplementaires\\"+employe.getMatricule()+"_"+employe.getNom()+"_"+employe.getPrenom()+".xlsx");
                Desktop.getDesktop().open(f);
            }
        } catch (RuntimeException e ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Attention");
            alert.setHeaderText("Acun employé selectionné !");
            alert.setContentText("S'il vous plait vous devez selectionner l'employé d'abbord");
            alert.showAndWait();

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void RemplirCoordonneesEmploye(Employe employe,String model, String destination
            ,boolean openFile)
    {


        FileInputStream fsIP= null;
        try {
            fsIP = new FileInputStream(new File("models\\"+model+".xlsx"));

//Access the workbook
        XSSFWorkbook wb = new XSSFWorkbook(fsIP);
//Access the worksheet, so that we can update / modify it


            printExcelCell(wb,0,10,3,employe.getMatricule());
            printExcelCell(wb,0,11,3,employe.getNom());
            printExcelCell(wb,0,12,3,employe.getPrenom());
            printExcelCell(wb,0,13,3,employe.getPoste());
            printExcelCell(wb,0,14,3,employe.getCategorie());
            printExcelCell(wb,0,15,3,employe.getDirection());
            printExcelCell(wb,0,16,3,employe.getDepartement());
            printExcelCell(wb,0,17,3,employe.getService());




//Close the InputStream
        fsIP.close();
//Open FileOutputStream to write updates
        FileOutputStream output_file =new FileOutputStream(new File("OutPut\\"+destination+"\\"+employe.getMatricule()+"_"+employe.getNom()+"_"+employe.getPrenom()+".xlsx"));
        //write changes
        wb.write(output_file);
//close the stream
        output_file.close();


            if(openFile){
                File f = new File("OutPut\\"+destination+"\\"+employe.getMatricule()+"_"+employe.getNom()+"_"+employe.getPrenom()+".xlsx");
                Desktop.getDesktop().open(f);
            }
        } catch (RuntimeException e ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Attention");
            alert.setHeaderText("Acun employé selectionné !");
            alert.setContentText("S'il vous plait vous devez selectionner l'employé d'abbord");
            alert.showAndWait();

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }







}
