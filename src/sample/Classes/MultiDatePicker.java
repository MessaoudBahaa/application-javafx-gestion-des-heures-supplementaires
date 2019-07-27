package sample.Classes;

/**
 * Created by md on 5.04.2016.
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.StringConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MultiDatePicker {





    public static VBox createScene() {
        Button                    addButton     = new Button("+");
        Button                    removeButton  = new Button("-");
        ObservableList<LocalDate> selectedDates = FXCollections.observableArrayList();
        ListView<LocalDate>       dateList      = new ListView<>(selectedDates);
        String                    pattern       = "yyyy-MM-dd";
        DateTimeFormatter         dateFormatter = DateTimeFormatter.ofPattern(pattern);
        DatePicker                datePicker    = new DatePicker();
        datePicker.setShowWeekNumbers(true);

        datePicker.setOnAction(event -> System.out.println("Selected date: " + datePicker.getValue()));
        datePicker.setPromptText(pattern);
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return (date == null) ? "" : dateFormatter.format(date);
            }

            @Override
            public LocalDate fromString(String string) {
                return ((string == null) || string.isEmpty()) ? null : LocalDate.parse(string, dateFormatter);
            }
        });

        datePicker.setOnKeyPressed((javafx.scene.input.KeyEvent ke) -> {
            FXCollections.sort(selectedDates);

            KeyCode keyCode = ke.getCode();
            if (keyCode.equals(KeyCode.ALT)){
                selectedDates.remove(datePicker.getValue());
            }

            else if (keyCode.equals(KeyCode.CONTROL)){
                if (!selectedDates.contains(datePicker.getValue())) {
                    selectedDates.add(datePicker.getValue());
                }
            }
            else if (keyCode.equals(KeyCode.SHIFT)){
                LocalDate delocalDate = selectedDates.get(selectedDates.size()-1);
                LocalDate aulocalDate = datePicker.getValue();
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
            datePicker.show();
        });

        datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
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

        // TODO: Hide text field of the date picker combo. Show dropdown directly on clicking "+" button.
        // TODO: Keep dropdown of the date picker combo open until intentionally clicking some other where.

        dateList.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE) {
                removeSelectedDates(selectedDates, dateList);
            }
        });
        dateList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        removeButton.disableProperty().bind(dateList.getSelectionModel().selectedItemProperty().isNull());
        addButton.setOnAction(event -> {
            Popup popup = new Popup();
            popup.getContent().add(datePicker);
            //popup.setAutoHide(true);
            Window window = addButton.getScene().getWindow();
            Bounds bounds = addButton.localToScene(addButton.getBoundsInLocal());
            double x      = window.getX() + bounds.getMinX();
            double y      = window.getY() + bounds.getMinY() + bounds.getHeight() + 5;
            popup.show(addButton, addButton.getLayoutX() , addButton.getLayoutY());
            datePicker.show();
        });

        removeButton.setOnAction(event -> selectedDates.clear());

        HBox buttons = new HBox(addButton, removeButton);
        return new VBox(buttons,datePicker);
    }

    private static boolean removeSelectedDates(ObservableList<LocalDate> selectedDates, ListView<LocalDate> dateList) {
        return selectedDates.removeAll(dateList.getSelectionModel().getSelectedItems());
    }

}
