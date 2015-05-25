/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package table;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 *
 * @author harrison
 */
 public abstract class Schedule extends HBox{   
    //abstract void selectDate(Date date);
    private DatePicker checkInDatePicker;
    private final String pattern = "dd-MM-yyyy";
    private final String pattern2 = "dd/MM/yyyy";
    private GridPane schedualPane = new GridPane();
    LocalDate date;
    Schedule(){
        setStyle("-fx-padding: 15;");
        setSpacing(10);
        GridPane gridPane = new GridPane();
        Label[][] studentLabel = new Label[7][12];
        checkInDatePicker = new DatePicker();
        GridPane datepickerPane = new GridPane();

        gridPane.setHgap(10);
        gridPane.setVgap(10);
        checkInDatePicker.setShowWeekNumbers(true);
        checkInDatePicker.setValue(LocalDate.now());

        StringConverter converter = new StringConverter<LocalDate>() {

            DateTimeFormatter dateFormatter
                    = DateTimeFormatter.ofPattern(pattern);
            DateTimeFormatter dateFormatter2
                    = DateTimeFormatter.ofPattern(pattern2);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter2.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty() && string.contains("/")) {
                    return LocalDate.parse(string, dateFormatter2);
                } else if (string.contains("-")) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        checkInDatePicker.setConverter(converter);
        checkInDatePicker.setPromptText(pattern.toLowerCase());
        Label checkInlabel = new Label("Select Date:");
        datepickerPane.add(checkInlabel, 0, 0);
        datepickerPane.setHgap(10);
        datepickerPane.setVgap(10);
        GridPane.setHalignment(checkInlabel, HPos.LEFT);
        datepickerPane.add(checkInDatePicker, 0, 1);
        Button todayButton = new Button("Select Today's Date");
        Button enterDateButton = new Button("Select Date");
        datepickerPane.add(enterDateButton, 1, 1);
        datepickerPane.add(todayButton, 1, 2);
        getChildren().addAll(datepickerPane, gridPane);
        date = checkInDatePicker.getValue();
        Label monthLabel = new Label(date.getMonth().toString() + " " + date.getYear());
        monthLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        monthLabel.setMinWidth(125);
        monthLabel.setAlignment(Pos.CENTER);
        ArrayList<Label> dayLabels = new ArrayList<Label>();
        dayLabels.add(new Label("Monday"));
        dayLabels.add(new Label("Tuesday"));
        dayLabels.add(new Label("Wednesday"));
        dayLabels.add(new Label("Thursday"));
        dayLabels.add(new Label("Friday"));
        dayLabels.add(new Label("Saturday"));
        dayLabels.add(new Label("Sunday"));
        gridPane.add(monthLabel, 4, 0);
        ArrayList<Label> timeLabels = new ArrayList<Label>();

        for (int hour = 9; hour <= 20; hour++) {
            if (hour - 8 <= 7) {
                gridPane.add(dayLabels.get(hour - 8 - 1), hour - 8, 1);
                dayLabels.get(hour - 8 - 1).setFont(Font.font("Arial", FontWeight.BOLD, 15));
                dayLabels.get(hour - 8 - 1).setMinWidth(125);
                dayLabels.get(hour - 8 - 1).setAlignment(Pos.CENTER);
            }
            timeLabels.add(new Label(Integer.toString(hour) + ":00-" + Integer.toString(hour + 1) + ":00"));
            gridPane.add(timeLabels.get(hour - 9), 0, 2 + hour - 9);
            timeLabels.get(hour - 9).setFont(Font.font("Arial", FontWeight.BOLD, 15));
            timeLabels.get(hour - 9).setMinHeight(40);
            timeLabels.get(hour - 9).setAlignment(Pos.CENTER);
        }

        

        schedualPane.setHgap(10);
        schedualPane.setVgap(10);

        for (int col = 0; col <= 6; col++) {
            for (int row = 0; row <= 11; row++) {
                studentLabel[col][row] = makeHighlightable(new Label("test" + row + col));
                schedualPane.add(studentLabel[col][row], col, row);
                studentLabel[col][row].setFont(Font.font("Arial", FontWeight.BOLD, 15));
                studentLabel[col][row].setMinWidth(125);
                studentLabel[col][row].setMinHeight(40);
                studentLabel[col][row].setAlignment(Pos.CENTER);
            }
        }
        gridPane.add(schedualPane, 1, 2, 7, 12);

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        //  super.updateItem(item, empty);

                        if (MonthDay.from(item).equals(MonthDay.of(9, 25))) {
                            setTooltip(new Tooltip("Happy Birthday!"));
                            setStyle("-fx-background-color: #ff4444;");
                        }

                    }
                };
            }
        };

        checkInDatePicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                try{
                date = checkInDatePicker.getValue();
                monthLabel.setText(date.getMonth().toString() + " " + date.getYear());
                }
                catch(java.lang.NullPointerException error){
                    
                }
            }
        });

        checkInDatePicker.setDayCellFactory(dayCellFactory);
        checkInDatePicker.getDayCellFactory();
        
        todayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                checkInDatePicker.setValue(LocalDate.now());
            }
        });

    }

    public Label makeHighlightable(final Label label) {
        // final Group wrapGroup = new Group(node);
        label.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                label.setStyle("-fx-background-color: #CCFF99;");
            }
        });
        label.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                label.setStyle("-fx-background-color: white;");
            }
        });
        label.addEventFilter(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        System.out.println("x: " + mouseEvent.getX() + "y: " + mouseEvent.getY());
                        Stage stage = new Stage();
                        //Fill stage with content
                        stage.show();
                    }
                ;
        });
         return label;
    }
    public GridPane getSchedualGrid(){
        return schedualPane;
    }
    
    }


