package table;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 *
 * @author harrison
 */
public class Table extends Application {

    private TableView<Person> table
            = new TableView<Person>();
    private ObservableList<Person> data
            = FXCollections.observableArrayList(
                    new Person("Jacob", "Herp", "jacob", "Jacob", "5142342345", "5142342345", "1", "0", "5142342345", "5142342345", "3", "3"),
                    new Person("Isabella", "Herp", ".@.com", "Jacob", "5142342345", "5142342345", "1", "0", "5142342345", "5142342345", "3", "3"),
                    new Person("Ethan", "Herp", ".@.com", "Jacob", "5142342345", "5142342345", "1", "0", "5142342345", "5142342345", "3", "3"),
                    new Person("Emma", "Jones", "emma..com", "Jacob", "5142342345", "5142342345", "1", "0", "5142342345", "5142342345", "3", "3"),
                    new Person("Michael", "Brown", ".@.com", "Jacob", "5142342345", "5142342345", "1", "0", "5142342345", "5142342345", "3", "3"));
    ObservableList<String> studentList
            = FXCollections.observableArrayList();

    final HBox hb = new HBox();
    TabPane tabPane = new TabPane();
    Tab studentListtab = new Tab();
    Tab schedualTab = new Tab();
    Tab studentDataTab = createViewStudentDataTab(new Tab());
    //  Tab editStudentDataTab = editStudentDataPanel(new Tab());
 //   private DatePicker checkInDatePicker;
 //   private final String pattern = "dd-MM-yyyy";
  //  private final String pattern2 = "dd/MM/yyyy";
   // LocalDate date;
    static int indexstudentpos;
    boolean programRequired;
    String goodString;
    Label globalLabel;

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
       // stage.setTitle("Table View Sample");
        Label[][] studentLabel = new Label[7][12];
        studentListtab.setClosable(false);
        schedualTab.setClosable(false);
        Label studentListLabel = new Label("Student List");
        table.setEditable(true);
        table.setMinWidth(700);
        table.setMinHeight(700);
        double colWidth = 220;
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(colWidth);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Person, String>>() {
                    @Override
                    public void handle(CellEditEvent<Person, String> t) {
                        ((Person) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setFirstName(t.getNewValue());
                    }
                });
        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(colWidth);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("lastName"));
        TableColumn middleNameCol
                = new TableColumn("Middle Name");
        middleNameCol.setMinWidth(colWidth);
        middleNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("middleName"));
        TableColumn addressCol = new TableColumn("Address");
        addressCol.setMinWidth(colWidth);
        addressCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("address"));
        TableColumn phoneNumberCol = new TableColumn("Phone number");
        phoneNumberCol.setMinWidth(colWidth);
        TableColumn phoneHomeCol = new TableColumn("Home");
        phoneHomeCol.setMinWidth(colWidth / 2);
        phoneHomeCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("homeNumber"));
        TableColumn cellPhoneCol = new TableColumn("Cell");
        cellPhoneCol.setMinWidth(colWidth / 2);
        cellPhoneCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("cellNumber"));
        phoneNumberCol.getColumns().addAll(phoneHomeCol, cellPhoneCol);
        TableColumn moduleNumberCol = new TableColumn("Current Module Number");
        moduleNumberCol.setMinWidth(colWidth);
        moduleNumberCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("moduleNumber"));
        TableColumn practiceNumberCol = new TableColumn("Current Practice Number");
        practiceNumberCol.setMinWidth(colWidth);
        practiceNumberCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("practiceNumber"));
        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, middleNameCol, addressCol, phoneNumberCol, moduleNumberCol, practiceNumberCol);
        final TextField addFirstName = new TextField();
        addFirstName.setPromptText("First Name");
        addFirstName.setMaxWidth(firstNameCol.getPrefWidth());
        final TextField addMiddleName = new TextField();
        addMiddleName.setPromptText("Middle Name");
        addMiddleName.setMaxWidth(middleNameCol.getPrefWidth());
        final TextField addLastName = new TextField();
        addLastName.setPromptText("Last Name");
        addLastName.setMaxWidth(lastNameCol.getPrefWidth());
        final TextField addAddress = new TextField();
        addAddress.setPromptText("Address");
        addAddress.setMaxWidth(addressCol.getPrefWidth());
        final TextField addhomeNumber = new TextField();
        addhomeNumber.setPromptText("Home Number");
        addhomeNumber.setMaxWidth(phoneHomeCol.getPrefWidth());
        final TextField addcellNumber = new TextField();
        addcellNumber.setPromptText("Cell Number");
        addcellNumber.setMaxWidth(cellPhoneCol.getPrefWidth());

        final TextField addmoduleNumber = new TextField();
        addmoduleNumber.setPromptText("Initial Module Number");
        addmoduleNumber.setMaxWidth(moduleNumberCol.getPrefWidth());

        final TextField addpracticeNumber = new TextField();
        addpracticeNumber.setPromptText("Initial Practice Number");
        addpracticeNumber.setMaxWidth(practiceNumberCol.getPrefWidth());
        final Button addButton = new Button("Add");

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                data.add(new Person(
                        addLastName.getText(),
                        addFirstName.getText(),
                        addMiddleName.getText(),
                        addAddress.getText(),
                        addhomeNumber.getText(),
                        addcellNumber.getText(),
                        addmoduleNumber.getText(),
                        addpracticeNumber.getText(),
                        "random", "random", "", ""));

                studentList.add(addFirstName.getText() + " " + addMiddleName.getText() + " " + addLastName.getText());

                data.add(new Person("", "", "", "", "", "", "", "", "", "", "", ""));
                addFirstName.clear();
                addLastName.clear();
                addMiddleName.clear();
                addAddress.clear();
                addhomeNumber.clear();
                addcellNumber.clear();
                addmoduleNumber.clear();
                addpracticeNumber.clear();

            }
        });
        hb.getChildren().addAll(addFirstName, addLastName, addMiddleName, addAddress, addhomeNumber, addcellNumber, addmoduleNumber, addpracticeNumber, addButton);
        hb.setSpacing(3);
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(studentListLabel, table, hb);

        Label checkInlabel = new Label("Select Date:");
        GridPane.setHalignment(checkInlabel, HPos.LEFT);
        Button todayButton = new Button("Select Today's Date");
        Button enterDateButton = new Button("Select Date");
        Schedule schedual = new Schedule() {};
        schedualTab.setContent(schedual);
        studentListtab.setText("Student Info");
        schedualTab.setText("Schedual");
        studentDataTab.setText("Student Data");
        studentListtab.setContent(vbox);
        tabPane.getTabs().addAll(studentDataTab, studentListtab, schedualTab);
        ((Group) scene.getRoot()).getChildren().addAll(tabPane);
        stage.setScene(scene);
        stage.show();

    }

    private boolean isThreeDigits(TextField textfield) {
        return textfield.getText().length() == 3;
    }

    private boolean isFourDigits(TextField textfield) {
        return textfield.getText().length() == 4;
    }

    public void setFirstLetterCapital(TextField textfield) {

        textfield.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (textfield.getText().length() == 1) {
                    textfield.setText(textfield.getText().toUpperCase());
                    textfield.positionCaret(1);

                }
            }
        });
    }

    private HBox addTelephoneNumberHBox() {
        HBox hbox = new HBox();
        TextField areaCode = new TextField();
        TextField threeDigits = new TextField();
        TextField fourDigits = new TextField();
        areaCode.setMaxWidth(50);
        threeDigits.setMaxWidth(50);
        fourDigits.setMaxWidth(60);
        areaCode.setPrefColumnCount(3);
        hbox.setSpacing(10);
        areaCode.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (areaCode.getText().length() >= 3 && e.getCode() != KeyCode.BACK_SPACE && e.getCode() != KeyCode.DELETE) {
                    threeDigits.requestFocus();
                    
                }
            }
        });
        threeDigits.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (threeDigits.getText().length() >= 3 && e.getCode() != KeyCode.BACK_SPACE && e.getCode() != KeyCode.DELETE) {
                    fourDigits.requestFocus();
                }
            }
        });
        hbox.getChildren().addAll(areaCode, threeDigits, fourDigits);
        return hbox;
    }

    private Accordion addCoursesAccordian() {
        Accordion accordionProgram = new Accordion();

        TitledPane Phase1 = new TitledPane();
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("First Name: "), 0, 0);
        grid.add(new TextField(), 1, 0);
        grid.add(new Label("Last Name: "), 0, 1);
        grid.add(new TextField(), 1, 1);
        grid.add(new Label("Email: "), 0, 2);
        grid.add(new TextField(), 1, 2);
        Phase1.setText("Phase 1");
        Phase1.setContent(grid);

        TitledPane Phase2 = new TitledPane();
        GridPane grid2 = new GridPane();
        grid2.setVgap(4);
        grid2.setPadding(new Insets(5, 5, 5, 5));
        grid2.add(new Label("First Name: "), 0, 0);
        grid2.add(new TextField(), 1, 0);
        grid2.add(new Label("Last Name: "), 0, 1);
        grid2.add(new TextField(), 1, 1);
        grid2.add(new Label("Email: "), 0, 2);
        grid2.add(new TextField(), 1, 2);
        Phase2.setText("Phase 2");
        Phase2.setContent(grid2);

        TitledPane Phase3 = new TitledPane();
        GridPane grid3 = new GridPane();
        grid3.setVgap(4);
        grid3.setPadding(new Insets(5, 5, 5, 5));
        grid3.add(new Label("First Name: "), 0, 0);
        grid3.add(new TextField(), 1, 0);
        grid3.add(new Label("Last Name: "), 0, 1);
        grid3.add(new TextField(), 1, 1);
        grid3.add(new Label("Email: "), 0, 2);
        grid3.add(new TextField(), 1, 2);
        Phase3.setText("Phase 3");
        Phase3.setContent(grid3);

        TitledPane Phase4 = new TitledPane();
        GridPane grid4 = new GridPane();
        grid4.setVgap(4);
        grid4.setPadding(new Insets(5, 5, 5, 5));
        grid4.add(new Label("First Name: "), 0, 0);
        grid4.add(new TextField(), 1, 0);
        grid4.add(new Label("Last Name: "), 0, 1);
        grid4.add(new TextField(), 1, 1);
        grid4.add(new Label("Email: "), 0, 2);
        grid4.add(new TextField(), 1, 2);
        Phase4.setText("Phase 4");
        Phase4.setContent(grid4);

        accordionProgram.getPanes().addAll(Phase1, Phase2, Phase3, Phase4);
        accordionProgram.setExpandedPane(Phase1);
        accordionProgram.setExpandedPane(Phase2);

        return accordionProgram;

    }

    private boolean containsLetter(TextField textfield) {

        String string = textfield.getText().toUpperCase();
        for (int ch = 32; ch <= 47; ch++) {
            if (string.contains(String.valueOf((char) ch))) {
                return true;
            }
        }
        for (int ch = 58; ch <= 96; ch++) {
            if (string.contains(String.valueOf((char) ch))) {
                return true;
            }
        }
        for (int ch = 123; ch <= 126; ch++) {
            if (string.contains(String.valueOf((char) ch))) {
                return true;
            }
        }

        return false;
    }

    private boolean containsDigit(String string) {

        if (string.contains("0")) {
            return true;
        } else if (string.contains("1")) {
            return true;
        } else if (string.contains("2")) {
            return true;
        } else if (string.contains("3")) {
            return true;
        } else if (string.contains("4")) {
            return true;
        } else if (string.contains("5")) {
            return true;
        } else if (string.contains("6")) {
            return true;
        } else if (string.contains("7")) {
            return true;
        } else if (string.contains("8")) {
            return true;
        } else {
            return string.contains("9");
        }
    }

    private Tab createViewStudentDataTab(Tab tab) {

        GridPane gridpane = new GridPane();
        configureBorder(gridpane);
        ToggleGroup group = new ToggleGroup();
        //  RadioButton editRadioButton = new RadioButton("Edit");
        RadioButton viewRadioButton = new RadioButton("View Student");
        RadioButton addStudentRadioButton = new RadioButton("Add student");
        //   editRadioButton.setToggleGroup(group);
        viewRadioButton.setToggleGroup(group);
        addStudentRadioButton.setToggleGroup(group);
        viewRadioButton.setSelected(true);
        HBox hboxToggleGroup = new HBox();

        hboxToggleGroup.getChildren().addAll(viewRadioButton, addStudentRadioButton);
        configureBorder(gridpane);
        configureBorder(hboxToggleGroup);

        final TextField addFirstName = new TextField();
        addFirstName.setPromptText("First Name");
        setFirstLetterCapital(addFirstName);
        final TextField addMiddleName = new TextField();
        setFirstLetterCapital(addMiddleName);
        addMiddleName.setPromptText("Middle Name");
        final TextField addLastName = new TextField();
        setFirstLetterCapital(addLastName);
        addLastName.setPromptText("Last Name");
        final TextField addAddress = new TextField();
        addAddress.setPromptText("Address");
        HBox homeTelephoneVBox = addTelephoneNumberHBox();
        ObservableList<Node> homevboxNodes = homeTelephoneVBox.getChildren();
        TextField areaCode = (TextField) homevboxNodes.get(0);
        TextField threeDigits = (TextField) homevboxNodes.get(1);
        TextField fourDigits = (TextField) homevboxNodes.get(2);
        //gridpane.add(homeTelephoneVBox, 0, 5);
        HBox cellVBox = addTelephoneNumberHBox();
        ObservableList<Node> cellvboxNodes = cellVBox.getChildren();
        TextField cellareaCode = (TextField) cellvboxNodes.get(0);
        TextField cellthreeDigits = (TextField) cellvboxNodes.get(1);
        TextField cellfourDigits = (TextField) cellvboxNodes.get(2);
        // gridpane.add(cellVBox, 0, 6);
        //final TextField addhomeNumber = new TextField();
        // addhomeNumber.setPromptText("Home Number");
        //final TextField addcellNumber = new TextField();
        //  addcellNumber.setPromptText("Cell Number");
        ObservableList<String> moduleOptions
                = FXCollections.observableArrayList(
                        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
                );
        final ComboBox addmoduleNumber = new ComboBox(moduleOptions);
        addmoduleNumber.setPromptText("Initial Module Number");
        
        ObservableList<String> practiceOptions
                = FXCollections.observableArrayList(
                        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"
                );
        final ComboBox addpracticeNumber = new ComboBox(practiceOptions);
        addpracticeNumber.setPromptText("Initial Practice Number");

        final Button addButton = new Button("Add Student");
        addButton.setMinWidth(100);
        ObservableList<String> languageOptions
                = FXCollections.observableArrayList(
                        "Français",
                        "English"
                );
        ComboBox languageCombo = new ComboBox(languageOptions);
        languageCombo.setPromptText("Language");

        ObservableList<String> programOptions
                = FXCollections.observableArrayList(
                        "Old Program",
                        "New Program"
                );

        ComboBox programCombo = new ComboBox(programOptions);
        programCombo.setPromptText("Select Program");

        final TextField addPrice = new TextField();
        addPrice.setPromptText("Total price to pay");

        final TextField addDeposite = new TextField();
        addDeposite.setPromptText("Amount client payed");
        Label[] infoLabelList = new Label[12];

        for (int index = 0; index < 12; index++) {
            infoLabelList[index] = new Label();
            gridpane.add(infoLabelList[index], 1, 2 + index);
        }

        for (int index = 0; index < data.size(); index++) {
            String student = data.get(index).getFirstName() + " " + data.get(index).getMiddleName() + " " + data.get(index).getLastName();
            studentList.add(student);

        }
        ComboBox studentListCombo = new ComboBox(studentList);
        AutoCompleteComboBoxListener comboBoxobject = new AutoCompleteComboBoxListener<>(studentListCombo);
        tab.setContent(gridpane);
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        gridpane.add(studentListCombo, 0, 0);
        gridpane.add(hboxToggleGroup, 0, 1);
        gridpane.add(addFirstName, 0, 2);
        gridpane.add(addMiddleName, 0, 3);
        gridpane.add(addLastName, 0, 4);
        gridpane.add(addAddress, 0, 5);
        gridpane.add(homeTelephoneVBox, 0, 6);
        gridpane.add(cellVBox, 0, 7);
        gridpane.add(programCombo, 0, 8);
        gridpane.add(addmoduleNumber, 0, 9);
        gridpane.add(addpracticeNumber, 0, 10);
        gridpane.add(languageCombo, 0, 11);
        gridpane.add(addPrice, 0, 12);
        gridpane.add(addDeposite, 0, 13);
        gridpane.add(addButton, 0, 14);

        infoLabelList[0].setText("First Name: ");
        infoLabelList[1].setText("Middle Name: ");
        infoLabelList[2].setText("Last Name: ");
        infoLabelList[3].setText("Address: ");
        infoLabelList[4].setText("Home Number");
        infoLabelList[5].setText("Cell Number: ");
        infoLabelList[6].setText("Program: ");
        infoLabelList[7].setText("Current Module Number: ");
        infoLabelList[8].setText("Practice Number: ");
        infoLabelList[9].setText("Language: ");
        infoLabelList[10].setText("Total Price: ");
        infoLabelList[11].setText("Amount payed: ");

        studentListCombo.setEditable(true);
        studentListCombo.setPromptText("Select Student (First Middle Last Name)");
        programCombo.setDisable(true);
        addFirstName.setDisable(true);
        addMiddleName.setDisable(true);
        addLastName.setDisable(true);
        addAddress.setDisable(true);
        areaCode.setDisable(true);
        threeDigits.setDisable(true);
        fourDigits.setDisable(true);
        cellareaCode.setDisable(true);
        cellthreeDigits.setDisable(true);
        cellfourDigits.setDisable(true);
        programCombo.setDisable(true);
        addmoduleNumber.setDisable(true);
        addpracticeNumber.setDisable(true);
        languageCombo.setDisable(true);
        addPrice.setDisable(true);
        addDeposite.setDisable(true);
        addButton.setDisable(true);

        viewRadioButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                infoLabelList[0].setText("First Name: ");
                infoLabelList[1].setText("Middle Name: ");
                infoLabelList[2].setText("Last Name: ");
                infoLabelList[3].setText("Address: ");
                infoLabelList[4].setText("Home Number");
                infoLabelList[5].setText("Cell Number: ");
                infoLabelList[6].setText("Program: ");
                infoLabelList[7].setText("Current Module Number: ");
                infoLabelList[8].setText("Practice Number: ");
                infoLabelList[9].setText("Language: ");
                infoLabelList[10].setText("Total Price: ");
                infoLabelList[11].setText("Amount payed: ");
                for (int index = 0; index < 12; index++) {
                    infoLabelList[index].setTextFill(Color.BLACK);
                }
                studentListCombo.setDisable(false);
                addFirstName.setDisable(true);
                addMiddleName.setDisable(true);
                addLastName.setDisable(true);
                addAddress.setDisable(true);
                areaCode.setDisable(true);
                threeDigits.setDisable(true);
                fourDigits.setDisable(true);
                cellareaCode.setDisable(true);
                cellthreeDigits.setDisable(true);
                cellfourDigits.setDisable(true);
                programCombo.setDisable(true);
                addmoduleNumber.setDisable(true);
                addpracticeNumber.setDisable(true);
                languageCombo.setDisable(true);
                addPrice.setDisable(true);
                addDeposite.setDisable(true);
                addButton.setDisable(true);

            }
        });

        addStudentRadioButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                studentListCombo.setValue(null);
                studentListCombo.setDisable(true);
                addFirstName.setDisable(false);
                addMiddleName.setDisable(false);
                addLastName.setDisable(false);
                addAddress.setDisable(false);
                areaCode.setDisable(false);
                threeDigits.setDisable(false);
                fourDigits.setDisable(false);
                cellareaCode.setDisable(false);
                cellthreeDigits.setDisable(false);
                cellfourDigits.setDisable(false);
                programCombo.setDisable(false);
                addmoduleNumber.setDisable(false);
                addpracticeNumber.setDisable(false);
                languageCombo.setDisable(false);
                addPrice.setDisable(false);
                addDeposite.setDisable(false);
                addButton.setDisable(false);
                infoLabelList[0].setText("First Name: ");
                infoLabelList[1].setText("Middle Name: ");
                infoLabelList[2].setText("Last Name: ");
                infoLabelList[3].setText("Address: ");
                infoLabelList[4].setText("Home Number");
                infoLabelList[5].setText("Cell Number: ");
                infoLabelList[6].setText("Program: ");
                infoLabelList[7].setText("Current Module Number: ");
                infoLabelList[8].setText("Practice Number: ");
                infoLabelList[9].setText("Language: ");
                infoLabelList[10].setText("Total Price: ");
                infoLabelList[11].setText("Amount payed: ");
            }
        });

        studentListCombo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (comboBoxobject.keypressed == false) {
                    indexstudentpos = studentListCombo.getSelectionModel().getSelectedIndex();
                } else {
                    ArrayList<Integer> list = comboBoxobject.getCheckList();
                    for (int index = 0; index < list.size(); index++) {
                        String name = data.get(list.get(index)).getFirstName() + " " + data.get(list.get(index)).getMiddleName() + " " + data.get(list.get(index)).getLastName();
                        if (name.equals(studentListCombo.valueProperty().getValue())) {
                            indexstudentpos = list.get(index);
                            break;
                        }
                    }

                }
                infoLabelList[0].setText("First Name: " + data.get(indexstudentpos).firstName.getValue());
                infoLabelList[1].setText("Middle Name: " + data.get(indexstudentpos).middleName.getValue());
                infoLabelList[2].setText("Last Name: " + data.get(indexstudentpos).lastName.getValue());
                infoLabelList[3].setText("Address: " + data.get(indexstudentpos).address.getValue());
                infoLabelList[4].setText("Home Number: (" + data.get(indexstudentpos).homeNumber.getValue().substring(0, 3) + ")"
                        + "-" + data.get(indexstudentpos).homeNumber.getValue().substring(3, 6) + "-"
                        + data.get(indexstudentpos).homeNumber.getValue().substring(6, 10));
                infoLabelList[5].setText("Cell Number: (" + data.get(indexstudentpos).cellNumber.getValue().substring(0, 3) + ")" + "-"
                        + data.get(indexstudentpos).cellNumber.getValue().substring(3, 6)
                        + "-" + data.get(indexstudentpos).cellNumber.getValue().substring(6, 10));
                infoLabelList[6].setText("Program: " + data.get(indexstudentpos).program.getValue());
                infoLabelList[7].setText("Current Module Number: " + data.get(indexstudentpos).moduleNumber.getValue());
                infoLabelList[8].setText("Practice Number: " + data.get(indexstudentpos).practiceNumber.getValue());
                infoLabelList[9].setText("Language: " + data.get(indexstudentpos).language.getValue());
                infoLabelList[10].setText("Total Price: " + data.get(indexstudentpos).finalPaymentcost.getValue());
                infoLabelList[11].setText("Amount payed: " + data.get(indexstudentpos).currentPaymentAmount.getValue());
            }
        });
        programCombo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                if (programCombo.getValue() == "Old Program") {
                    addmoduleNumber.setDisable(true);
                    addpracticeNumber.setDisable(true);
                    programRequired = false;

                } else {
                    addmoduleNumber.setDisable(false);
                    addpracticeNumber.setDisable(false);
                    programRequired = true;

                }
            }
        });

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                boolean textCorrect = true;
                for (int index = 0; index < 12; index++) {
                    infoLabelList[index].setText("");
                }

                if (addLastName.getText().isEmpty() || containsDigit(addLastName.getText())) {
                    infoLabelList[2].setTextFill(Color.RED);
                    infoLabelList[2].setText("*Invalid Last Name");
                    textCorrect = false;
                }
                if (containsDigit(addMiddleName.getText())) {
                    infoLabelList[1].setTextFill(Color.RED);
                    infoLabelList[1].setText("*Invalid Middle Name");
                    textCorrect = false;
                }

                if (addFirstName.getText().isEmpty() || containsDigit(addFirstName.getText())) {
                    infoLabelList[0].setTextFill(Color.RED);
                    infoLabelList[0].setText("*Invalid First Name");
                    textCorrect = false;
                }

                if (addAddress.getText().isEmpty()) {
                    infoLabelList[3].setTextFill(Color.RED);
                    infoLabelList[3].setText("*Address Needed");
                    textCorrect = false;
                }

                if (areaCode.getText().isEmpty() || threeDigits.getText().isEmpty()
                        || fourDigits.getText().isEmpty() || containsLetter(areaCode)
                        || containsLetter(threeDigits) || containsLetter(fourDigits) || !isThreeDigits(areaCode) || !isThreeDigits(threeDigits) || !isFourDigits(fourDigits)) {
                    infoLabelList[4].setTextFill(Color.RED);
                    infoLabelList[4].setText("*Invalid Home Number");
                    textCorrect = false;
                }

                if (containsLetter(cellareaCode) || containsLetter(cellthreeDigits) || containsLetter(cellfourDigits)
                        || !isThreeDigits(cellareaCode) || !isThreeDigits(cellthreeDigits) || !isFourDigits(cellfourDigits)) {
                    infoLabelList[5].setTextFill(Color.RED);
                    infoLabelList[5].setText("*Invalid Cell Number");
                    textCorrect = false;
                }

                if (addmoduleNumber.getSelectionModel().getSelectedItem() == null && programRequired) {
                    infoLabelList[7].setTextFill(Color.RED);
                    infoLabelList[7].setText("*Invalid present module Number: 0 if starting");
                    textCorrect = false;
                }

                if (addDeposite.getText().isEmpty() || containsLetter(addDeposite)) {
                    infoLabelList[11].setTextFill(Color.RED);
                    infoLabelList[11].setText("*Invalid Deposite Amount");
                    textCorrect = false;
                }
                try {
                    if (addPrice.getText().isEmpty() || containsLetter(addPrice)) {
                        infoLabelList[10].setTextFill(Color.RED);
                        infoLabelList[10].setText("*Invalid Total Price");
                        textCorrect = false;
                    } else if (Integer.parseInt(addPrice.getText()) < Integer.parseInt(addDeposite.getText())) {
                        infoLabelList[10].setTextFill(Color.RED);
                        infoLabelList[11].setTextFill(Color.RED);
                        infoLabelList[11].setText("*Deposite greater than total cost");
                        infoLabelList[10].setText("*Total cost is less than deposite amount");
                        textCorrect = false;
                    }
                } catch (NumberFormatException error) { //error if letters are typed in only one textbox.

                }

                if (addpracticeNumber.getSelectionModel().getSelectedItem() == null && programRequired) {
                    infoLabelList[8].setTextFill(Color.RED);
                    infoLabelList[8].setText("*Practice number needed: 0 if starting");
                    textCorrect = false;
                }

                if (programCombo.getSelectionModel().getSelectedItem() == null) {
                    infoLabelList[6].setTextFill(Color.RED);
                    infoLabelList[6].setText("*Program needed");
                    textCorrect = false;
                }
                if (languageCombo.getSelectionModel().getSelectedItem() == null) {
                    infoLabelList[9].setTextFill(Color.RED);
                    infoLabelList[9].setText("*Language Needed");
                    textCorrect = false;

                }

                if (addStudentRadioButton.isSelected() && textCorrect) {
                    //  try {

                    for (int index = 0; index < 12; index++) {
                        infoLabelList[index].setTextFill(Color.BLACK);
                    }
                    /*  infoLabelList[0].setText("First Name: " + addFirstName.getText());
                     infoLabelList[1].setText("Middle Name: " + addMiddleName.getText());
                     infoLabelList[2].setText("Last Name: " + addLastName.getText());
                     infoLabelList[3].setText("Address: " + addAddress.getText());
                     infoLabelList[4].setText("Home Number: (" + areaCode.getText() + ")" + "-" + threeDigits.getText() + "-" + fourDigits.getText());
                     infoLabelList[5].setText("Cell Number: (" + cellareaCode.getText() + ")" + "-" + cellthreeDigits.getText() + "-" + cellfourDigits.getText());
                     infoLabelList[6].setText("Program: " + programCombo.getSelectionModel().getSelectedItem().toString());
                     infoLabelList[7].setText("Current Module Number: " + addmoduleNumber.getText());
                     infoLabelList[8].setText("Practice Number: " + addpracticeNumber.getText());
                     infoLabelList[9].setText("Language: " + languageCombo.getSelectionModel().getSelectedItem().toString());
                     infoLabelList[10].setText("Total Price: " + addPrice.getText());
                     infoLabelList[11].setText("Amount payed: " + addDeposite.getText());
                     */
                    isInfoCorrect(addLastName,
                            addFirstName,
                            addMiddleName,
                            addAddress,
                            areaCode, threeDigits, fourDigits,
                            cellareaCode, cellthreeDigits, cellfourDigits,
                            addmoduleNumber,
                            addpracticeNumber,
                            languageCombo, programCombo, addDeposite, addPrice);
                    infoLabelList[0].setText("First Name: ");
                    infoLabelList[1].setText("Middle Name: ");
                    infoLabelList[2].setText("Last Name: ");
                    infoLabelList[3].setText("Address: ");
                    infoLabelList[4].setText("Home Number");
                    infoLabelList[5].setText("Cell Number: ");
                    infoLabelList[6].setText("Program: ");
                    infoLabelList[7].setText("Current Module Number: ");
                    infoLabelList[8].setText("Practice Number: ");
                    infoLabelList[9].setText("Language: ");
                    infoLabelList[10].setText("Total Price: ");
                    infoLabelList[11].setText("Amount payed: ");

                    //  } catch (NullPointerException error) {
                    //   }
                } //else {
                // return;
                //  }
               /* addFirstName.clear();
                 addLastName.clear();
                 addMiddleName.clear();
                 addAddress.clear();
                 areaCode.clear();
                 threeDigits.clear();
                 fourDigits.clear();
                 cellareaCode.clear();
                 cellthreeDigits.clear();
                 cellfourDigits.clear();
                 addmoduleNumber.clear();
                 addpracticeNumber.clear();
                 languageCombo.setValue(null);
                 programCombo.setValue(null);
                 addDeposite.clear();
                 addPrice.clear();*/
            }
        });

        fourDigits.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (fourDigits.getText().length() >= 4 && e.getCode() != KeyCode.BACK_SPACE && e.getCode() != KeyCode.DELETE) {
                    cellareaCode.requestFocus();
                }
            }
        });
        cellfourDigits.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (cellfourDigits.getText().length() >= 4 && e.getCode() != KeyCode.BACK_SPACE && e.getCode() != KeyCode.DELETE) {
                    programCombo.requestFocus();
                }
            }
        });

        return tab;
    }

    private void isInfoCorrect(TextField lastName,
            TextField firstName,
            TextField middleName,
            TextField address,
            TextField areaCode,
            TextField threeDigits,
            TextField fourDigits,
            TextField cellAreaCode,
            TextField cellthreeDigits,
            TextField cellfourDigits,
            ComboBox moduleNumber,
            ComboBox practiceNumber,
            ComboBox language, ComboBox program, TextField deposite, TextField totalCost) {
        GridPane gridPane = new GridPane();
        ConfirmWindow studentdata = new ConfirmWindow("Personal Information Confirmation", gridPane, 344, 300);
        Label[] infoLabelList = new Label[12];
        studentdata.show();
        for (int index = 0; index < 12; index++) {
            infoLabelList[index] = new Label();
            infoLabelList[index].setTextFill(Color.BLACK);
            gridPane.add(infoLabelList[index], 0, index);
        }
        String homeNumber = "(" + areaCode.getText() + ")" + "-" + threeDigits.getText() + "-" + fourDigits.getText();
        String cellNumber = "(" + cellAreaCode.getText() + ")" + "-" + cellthreeDigits.getText() + "-" + cellfourDigits.getText();
        infoLabelList[0].setText("First Name: " + firstName.getText());
        infoLabelList[1].setText("Middle Name: " + middleName.getText());
        infoLabelList[2].setText("Last Name: " + lastName.getText());
        infoLabelList[3].setText("Address: " + address.getText());
        infoLabelList[4].setText("Home Number: " + homeNumber);
        infoLabelList[5].setText("Cell Number: " + cellNumber);
        infoLabelList[6].setText("Program: " + program.getSelectionModel().getSelectedItem().toString());
        try {
            infoLabelList[7].setText("Current Module Number: " + moduleNumber.getSelectionModel().getSelectedItem().toString());
            infoLabelList[8].setText("Practice Number: " + practiceNumber.getSelectionModel().getSelectedItem().toString());
        } catch (NullPointerException error) { //if these comboboxes are not selected then there is an error
            infoLabelList[7].setText("Current Module Number: None");
            infoLabelList[8].setText("Practice Number: None");
        }
        infoLabelList[9].setText("Language: " + language.getSelectionModel().getSelectedItem().toString());
        infoLabelList[10].setText("Total Price: " + totalCost.getText());
        infoLabelList[11].setText("Deposite: " + deposite.getText());
        studentdata.getOkButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (moduleNumber.getSelectionModel().getSelectedItem() == null) {
                    data.add(new Person(
                            lastName.getText(),
                            firstName.getText(),
                            middleName.getText(),
                            address.getText(),
                            areaCode.getText() + threeDigits.getText() + fourDigits.getText(),
                            cellAreaCode.getText() + cellthreeDigits.getText() + cellfourDigits.getText(),
                            "None",
                            "None",
                            language.getSelectionModel().getSelectedItem().toString(), program.getSelectionModel().getSelectedItem().toString(), deposite.getText(), totalCost.getText()));
                } else {
                    data.add(new Person(
                            lastName.getText(),
                            firstName.getText(),
                            middleName.getText(),
                            address.getText(),
                            areaCode.getText() + threeDigits.getText() + fourDigits.getText(),
                            cellAreaCode.getText() + cellthreeDigits.getText() + cellfourDigits.getText(),
                            moduleNumber.getSelectionModel().getSelectedItem().toString(),
                            practiceNumber.getSelectionModel().getSelectedItem().toString(),
                            language.getSelectionModel().getSelectedItem().toString(), program.getSelectionModel().getSelectedItem().toString(), deposite.getText(), totalCost.getText()));
                }
                studentList.add(firstName.getText() + " " + middleName.getText() + " " + lastName.getText());
                firstName.clear();
                lastName.clear();
                middleName.clear();
                address.clear();
                areaCode.clear();
                threeDigits.clear();
                fourDigits.clear();
                cellAreaCode.clear();
                cellthreeDigits.clear();
                cellfourDigits.clear();
                moduleNumber.setValue(null);
                practiceNumber.setValue(null);
                language.setValue(null);
                program.setValue(null);
                deposite.clear();
                totalCost.clear();
                studentdata.close();
            }
        });

        studentdata.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                studentdata.close();

            }
        });

    }

    /* private Tab editStudentDataPanel(Tab tab) {
     GridPane gridpane = new GridPane();
     Label infotLabel = new Label("Student Info");
     Label paymentLabel = new Label("Student Data");
     gridpane.add(infotLabel, 0, 0);
     gridpane.add(paymentLabel, 2, 0);
     configureBorder(gridpane);
     gridpane.setHgap(50);
     tab.setContent(gridpane);

     final TextField addFirstName = new TextField();
     setFirstLetterCapital(addFirstName);
     addFirstName.setPromptText("First Name");
     final TextField addMiddleName = new TextField();
     setFirstLetterCapital(addMiddleName);
     addMiddleName.setPromptText("Middle Name");
     final TextField addLastName = new TextField();
     setFirstLetterCapital(addLastName);
     addLastName.setPromptText("Last Name");
     final TextField addAddress = new TextField();
     addAddress.setPromptText("Address");
     final TextField addhomeNumber = new TextField();
     addhomeNumber.setPromptText("Home Number");
     final TextField addcellNumber = new TextField();
     addcellNumber.setPromptText("Cell Number");

     final TextField addmoduleNumber = new TextField();
     addmoduleNumber.setPromptText("Initial Module Number");

     final TextField addpracticeNumber = new TextField();
     addpracticeNumber.setPromptText("Initial Practice Number");
     final Button addButton = new Button("Add");
     addButton.setMinWidth(100);
     ObservableList<String> languageOptions
     = FXCollections.observableArrayList(
     "Français",
     "English"
     );
     ComboBox languageCombo = new ComboBox(languageOptions);
     languageCombo.setPromptText("Language");

     ObservableList<String> programOptions
     = FXCollections.observableArrayList(
     "Old Program",
     "New Program"
     );

     ComboBox programCombo = new ComboBox(programOptions);
     programCombo.setPromptText("Select Program");

     final TextField addPrice = new TextField();
     addPrice.setPromptText("Total price to pay");

     final TextField addDeposite = new TextField();
     addDeposite.setPromptText("Amount client payed");

     Label[] infoLabelList = new Label[12];

     for (int index = 0; index < 12; index++) {
     infoLabelList[index] = new Label();
     gridpane.add(infoLabelList[index], 1, 1 + index);
     }
     tab.setContent(gridpane);
     gridpane.setHgap(10);
     gridpane.setVgap(10);
     gridpane.add(addFirstName, 0, 1);
     gridpane.add(addMiddleName, 0, 2);
     gridpane.add(addLastName, 0, 3);
     gridpane.add(addAddress, 0, 4);
     HBox homeTelephoneVBox = addTelephoneNumberHBox();
     ObservableList<Node> homevboxNodes = homeTelephoneVBox.getChildren();
     TextField areaCode = (TextField) homevboxNodes.get(0);
     TextField threeDigits = (TextField) homevboxNodes.get(1);
     TextField fourDigits = (TextField) homevboxNodes.get(2);
     gridpane.add(homeTelephoneVBox, 0, 5);
     HBox cellVBox = addTelephoneNumberHBox();
     ObservableList<Node> cellvboxNodes = cellVBox.getChildren();
     TextField cellareaCode = (TextField) cellvboxNodes.get(0);
     TextField cellthreeDigits = (TextField) cellvboxNodes.get(1);
     TextField cellfourDigits = (TextField) cellvboxNodes.get(2);
     gridpane.add(cellVBox, 0, 6);
     gridpane.add(programCombo, 0, 7);
     gridpane.add(addmoduleNumber, 0, 8);
     gridpane.add(addpracticeNumber, 0, 9);
     gridpane.add(languageCombo, 0, 10);
     gridpane.add(addPrice, 0, 11);
     gridpane.add(addDeposite, 0, 12);
     gridpane.add(addButton, 0, 13);
     gridpane.add(addCoursesAccordian(), 4, 0, 1, 9);
     programCombo.setOnAction(new EventHandler<ActionEvent>() {
     @Override
     public void handle(ActionEvent e) {

     if (programCombo.getValue() == "Old Program") {
     addmoduleNumber.setDisable(true);
     addpracticeNumber.setDisable(true);
     programRequired = false;

     } else {
     addmoduleNumber.setDisable(false);
     addpracticeNumber.setDisable(false);
     programRequired = true;

     }
     }
     });

     addButton.setOnAction(new EventHandler<ActionEvent>() {
     @Override
     public void handle(ActionEvent e) {
     boolean textCorrect = true;
     for (int index = 0; index < 12; index++) {
     infoLabelList[index].setText("");
     }

     if (addLastName.getText().isEmpty() || containsDigit(addLastName.getText())) {
     infoLabelList[2].setTextFill(Color.RED);
     infoLabelList[2].setText("*Invalid Last Name");
     textCorrect = false;
     }
     if (addFirstName.getText().isEmpty() || containsDigit(addLastName.getText())) {
     infoLabelList[0].setTextFill(Color.RED);
     infoLabelList[0].setText("*Invalid First Name");
     textCorrect = false;
     }
     if (addAddress.getText().isEmpty()) {
     infoLabelList[3].setTextFill(Color.RED);
     infoLabelList[3].setText("*Address Needed");
     textCorrect = false;
     }

     if (areaCode.getText().isEmpty() || threeDigits.getText().isEmpty()
     || fourDigits.getText().isEmpty() || containsLetter(areaCode)
     || containsLetter(threeDigits) || containsLetter(fourDigits) || isThreeDigits(areaCode) || isThreeDigits(threeDigits) || isFourDigits(fourDigits)) {
     infoLabelList[4].setTextFill(Color.RED);
     infoLabelList[4].setText("*Invalid Home Number");
     textCorrect = false;
     }
     if (containsLetter(cellareaCode) || containsLetter(cellthreeDigits) || containsLetter(cellfourDigits)
     || isThreeDigits(cellareaCode) || isThreeDigits(cellthreeDigits) || isFourDigits(cellfourDigits)) {
     infoLabelList[4].setTextFill(Color.RED);
     infoLabelList[4].setText("*Invalid Cell Number");
     textCorrect = false;
     }

     if (addmoduleNumber.getText().isEmpty() && programRequired) {
     infoLabelList[7].setTextFill(Color.RED);
     infoLabelList[7].setText("*Current Module Number Needed");
     textCorrect = false;
     }

     if (addDeposite.getText().isEmpty()) {
     infoLabelList[11].setTextFill(Color.RED);
     infoLabelList[11].setText("*Deposite Amount Needed");
     textCorrect = false;
     }
     if (addPrice.getText().isEmpty()) {
     infoLabelList[10].setTextFill(Color.RED);
     infoLabelList[10].setText("*Total Price Needed");
     textCorrect = false;
     }
     if (addpracticeNumber.getText().isEmpty() && programRequired) {
     infoLabelList[8].setTextFill(Color.RED);
     infoLabelList[8].setText("*Practice number needed: 0 if starting");
     textCorrect = false;
     }

     if (!programCombo.getSelectionModel().isSelected(0) && !programCombo.getSelectionModel().isSelected(1)) {
     infoLabelList[6].setTextFill(Color.RED);
     infoLabelList[6].setText("*Program needed");
     }

     if (!languageCombo.getSelectionModel().isSelected(0) && !languageCombo.getSelectionModel().isSelected(1)) {
     infoLabelList[9].setTextFill(Color.RED);
     infoLabelList[9].setText("*Language Needed");

     }

     if (textCorrect) {
     try {

     data.add(new Person(
     addLastName.getText(),
     addFirstName.getText(),
     addMiddleName.getText(),
     addAddress.getText(),
     addhomeNumber.getText(),
     addcellNumber.getText(),
     addmoduleNumber.getText(),
     addpracticeNumber.getText(),
     languageCombo.getValue().toString(), programCombo.getValue().toString(), addDeposite.getText(), addPrice.getText()));
     studentList.add(addFirstName.getText() + " " + addMiddleName.getText() + " " + addLastName.getText());

     } catch (NullPointerException error) {

     }
     } else {
     return;
     }
     data.add(new Person("", "", "", "", "", "", "", "", "", "", "", ""));
     addFirstName.clear();
     addLastName.clear();
     addMiddleName.clear();
     addAddress.clear();
     addhomeNumber.clear();
     addcellNumber.clear();
     addmoduleNumber.clear();
     addpracticeNumber.clear();
     languageCombo.setPromptText("Language");
     programCombo.setPromptText("Select Program");
     addDeposite.clear();
     addPrice.clear();

     }
     });

     return tab;
     }*/
    private static void configureBorder(final Region region) {
        region.setStyle("-fx-padding: 15;"
                + "-fx-spacing: 10;");
    }

    public static class Person {

        private final SimpleStringProperty lastName;
        private final SimpleStringProperty firstName;
        private final SimpleStringProperty middleName;
        private final SimpleStringProperty address;
        private final SimpleStringProperty homeNumber;
        private final SimpleStringProperty cellNumber;
        private final SimpleStringProperty moduleNumber;
        private final SimpleStringProperty practiceNumber;
        private final SimpleStringProperty language;
        private final SimpleStringProperty program;
        private final SimpleStringProperty currentPaymentAmount;
        private final SimpleStringProperty finalPaymentcost;
        // private final SimpleStringProperty numberOfPayments;

        private Person(String lastName, String firstName, String middleName, String address, String homeNumber,
                String cellNumber, String moduleNumber, String practiceNumber, String language, String program, String currentPaymentAmount, String finalPaymentcost) {
            this.firstName = new SimpleStringProperty(firstName);
            this.middleName = new SimpleStringProperty(middleName);
            this.address = new SimpleStringProperty(address);
            this.lastName = new SimpleStringProperty(lastName);
            this.homeNumber = new SimpleStringProperty(homeNumber);
            this.cellNumber = new SimpleStringProperty(cellNumber);
            this.moduleNumber = new SimpleStringProperty(moduleNumber);
            this.practiceNumber = new SimpleStringProperty(practiceNumber);
            this.language = new SimpleStringProperty(language);
            this.program = new SimpleStringProperty(program);
            this.currentPaymentAmount = new SimpleStringProperty(currentPaymentAmount);
            this.finalPaymentcost = new SimpleStringProperty(finalPaymentcost);
        }

        /*  private Person(String lastName,String firstName, String middleName, String address,String homeNumber, String cellNumber){  
         this.firstName = new SimpleStringProperty(firstName);
         this.middleName = new SimpleStringProperty(middleName);
         this.address = new SimpleStringProperty(address);
         this.lastName = new SimpleStringProperty(lastName); 
         this.homeNumber = new SimpleStringProperty(homeNumber);
         this.cellNumber = new SimpleStringProperty(cellNumber);
         }*/
        public String getLastName() {
            return lastName.get();
        }

        public void setLastName(String lastName) {
            this.lastName.set(lastName);
        }

        public String getFirstName() {
            return firstName.get();
        }

        public void setFirstName(String firstName) {
            this.firstName.set(firstName);
        }

        public String getMiddleName() {
            return middleName.get();
        }

        public void setMiddleName(String middleName) {
            this.middleName.set(middleName);
        }

        public String getAddress() {
            return address.get();
        }

        public void setAddress(String address) {
            this.address.set(address);
        }

        public String getHomeNumber() {
            return homeNumber.get();
        }

        public void setHomeNumber(String homeNumber) {
            this.homeNumber.set(homeNumber);
        }

        public String getCellNumber() {
            return cellNumber.get();
        }

        public void setCellNumber(String cellNumber) {
            this.cellNumber.set(cellNumber);
        }

        public String getModuleNumber() {
            return moduleNumber.get();
        }

        public void setModuleNumber(String moduleNumber) {
            this.moduleNumber.set(moduleNumber);
        }

        public String getPracticeNumber() {
            return practiceNumber.get();
        }

        public void setPracticeNumber(String practiceNumber) {
            this.practiceNumber.set(practiceNumber);
        }

        public String getLanguage() {
            return language.get();
        }

        public void setLanguage(String language) {
            this.language.set(language);
        }

        public String getProgram() {
            return program.get();
        }

        public void setProgram(String program) {
            this.program.set(program);
        }

        public String getCurrentPaymentAmount() {
            return currentPaymentAmount.get();
        }

        public void setCurrentPaymentAmount(String currentPaymentAmount) {
            this.currentPaymentAmount.set(currentPaymentAmount);
        }

        public String getFinalPaymentcost() {
            return finalPaymentcost.get();
        }

        public void setfinalPaymentcost(String finalPaymentcost) {
            this.finalPaymentcost.set(finalPaymentcost);
        }
    }
}
