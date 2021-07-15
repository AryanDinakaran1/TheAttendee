/**
 * This program makes teachers and co-teachers to handle attendance very easily.
 * It is a simple UI app yet powerful and efficient to use and handle data.
 *
 * This uses a MySQL Database.
 * Right now this program is running on localhost but when it goes in production it needs a server to run. (I bought a linux machine)
 * The server needs to be purchased by schools. (I purchased it)
 *
 * In this project i have used JavaFX framework for making a GUI window.
 * Since CSS is not supported on Intellij CE, i am going to push this project to bluej. -- Not sure (May be)
 *
 * TODO:
 *
 * Make a clone of this project and work on editable cells  -- (Done)
 *
 * Implement auto increment to schoolRegister
 *
 * This is version 1 of [theAttendee]
 *
 * Also implement INSERT INTO automation attendance in onEditChanged
 * With this we can get rid of the send button.
 *
 * Add a confirmation box when a student is getting removed from the database.
 * With the confirmation of the user, the program will automatically delete data from schoolReigster and schoolAtt but no data will be lost from attendance list.
 *
 * Put an update statement in goUpdate1()
 * Add an window.setAlwaysOnTop() to attScreen - (done)
 *
 * Implement refresh when sorting.
 * 
 * 
 * Hey Aryan make review tableColumn editable in tableViewAtt;
 *
 * The app is ready
 * 
 * The server also communicates with the app within the same network
 * 
 * Use port forwarding to make the database accessible for outside network
 * 
 * Used NGROK in linux
 * 
 * But the queries have become slow
 * 
 */

//JavaFX Framework Imports
import javafx.application.Application;
import javafx.collections.*;
import javafx.collections.transformation.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

//SQL Imports for JDBC
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

//17 Libs

//Util package
import java.util.Date;
import java.util.function.Predicate;

public class Main extends Application 
{
    //uh uh uh uh uh uh uh uh
    // Database Connection
    Connection con;

    
    // String
    String rights = "an Aryan Dinakaran production theAttendee © 2021";
    //String rights = "Designed by Aryan Dinakaran";
    String backLabel = "Back";

    // Booleans
    Boolean result;
    Boolean confirmation;

    // Instances
    credentials cred = new credentials();
    Date date = new Date();

    // TableView
    TableView<tableViewManager> tableView;
    TableView<finalRecTableViewManager> smartTable;
    TableView<attendance> tableViewAtt;

    // TextFields
    TextField studentIDTF, firstnameTF, lastnameTF, emailTF, phoneNumTF;
    TextField trNameTF;

    // Longie Dongies
    long phoneNumDouble;
    long theStudentIDTF;

    // ComboBox
    ComboBox<String> rollTF;
    ComboBox<String> divTF;
    ComboBox<String> classTF;
    ComboBox<Integer> rating;

    // Text Area
    TextArea txtArea1;
    TextArea txtAreaForAtt;
    TextArea fbTxtArea;

    // Main Window
    Stage window;

    // ObservableLists
    ObservableList<attendance> allGradesStud;
    ObservableList<finalRecTableViewManager> finals;

    // Scenes
    Scene welcomeScene, menuScene, aboutScene, fbScene; // Startup Scenes
    Scene studentsRegisterScene, studentsAttendanceScene; // Working Scenes
    Scene first;
    Scene smartScene; // Smart Scene

    public static void main(String[] args) 
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        window = primaryStage;
        window.setTitle("Welcome to [theAttendee] 👋");

        window.setOnCloseRequest(e -> {

            e.consume();
            closeProgram();

        });

        // Start of welcomeScene

        Label title = new Label("[theAttendee]");
        title.setFont(Font.font(100.0));
        title.setTextFill(Color.rgb(0, 144, 255));
        title.setFont(Font.font("comic sans ms", FontWeight.BLACK, FontPosture.REGULAR, 100.0));

        Label subTitle = new Label("SSRVM Edition");
        subTitle.setFont(Font.font("arial", FontWeight.BLACK, FontPosture.REGULAR, 20.0));
        subTitle.setTextFill(Color.rgb(255, 0, 0));

        VBox titleBox = new VBox();
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().addAll(title, subTitle);

        Button startButton = new Button("Dashboard");
        startButton.setMinHeight(50.0);
        startButton.setMinWidth(500.0);
        startButton.setFont(Font.font(25.0));
        startButton.setStyle("-fx-background-radius: 50");
        startButton.setOnAction(e -> {

            window.setScene(menuScene);
            window.setTitle("Welcome to [theAttendee] 👋");

        });

        Button fbButton = new Button("Feedback");
        fbButton.setMinHeight(50.0);
        fbButton.setMinWidth(500.0);
        fbButton.setFont(Font.font(25.0));
        fbButton.setStyle("-fx-background-radius: 50");
        fbButton.setOnAction(e -> window.setScene(fbScene));

        Button aboutButton = new Button("About");
        aboutButton.setMinHeight(50.0);
        aboutButton.setMinWidth(500.0);
        aboutButton.setFont(Font.font(25.0));
        aboutButton.setStyle("-fx-background-radius: 50");
        aboutButton.setOnAction(e -> window.setScene(aboutScene));

        VBox buttonStack = new VBox();
        buttonStack.getChildren().addAll(startButton, fbButton, aboutButton);
        buttonStack.setAlignment(Pos.CENTER);
        buttonStack.setSpacing(20);

        Label rightsLabel = new Label(rights);

        VBox rightsBox = new VBox();
        rightsBox.getChildren().addAll(rightsLabel);
        rightsBox.setAlignment(Pos.CENTER);

        VBox welcomeStack = new VBox();
        welcomeStack.getChildren().addAll(titleBox, buttonStack);
        welcomeStack.setAlignment(Pos.CENTER);
        welcomeStack.setSpacing(50);

        BorderPane mainWelcomeStack = new BorderPane();
        mainWelcomeStack.setCenter(welcomeStack);
        mainWelcomeStack.setBottom(rightsBox);
        mainWelcomeStack.setPadding(new Insets(20));

        welcomeScene = new Scene(mainWelcomeStack, 1000, 700, Color.RED);

        // End of Scene welcomeScene

        // Start of AboutScene

        Label titleForAbout = new Label("[theAttendee]");
        titleForAbout.setFont(Font.font(100.0));

        VBox titleBoxAbout = new VBox();
        titleBoxAbout.setAlignment(Pos.CENTER);
        titleBoxAbout.getChildren().addAll(titleForAbout);

        Label desc = new Label("Add contents here");
        desc.setFont(Font.font(15));

        VBox descBox = new VBox();
        descBox.setAlignment(Pos.CENTER);
        descBox.getChildren().addAll(desc);

        Button backButtonAbout = new Button("Back");
        backButtonAbout.setOnAction(e -> window.setScene(welcomeScene));
        window.setTitle("Welcome to [theAttendee]");

        BorderPane aboutBP = new BorderPane();
        aboutBP.setPadding(new Insets(20));
        aboutBP.setTop(titleBoxAbout);
        aboutBP.setCenter(descBox);
        aboutBP.setBottom(backButtonAbout);

        aboutScene = new Scene(aboutBP, 1000, 700);

        // End of AboutScene

        // Start of menuScene

        Label dashboardTitle = new Label("Dashboard");
        dashboardTitle.setFont(Font.font(50.0));

        VBox title1Box = new VBox();
        title1Box.getChildren().addAll(dashboardTitle);
        title1Box.setAlignment(Pos.TOP_LEFT);

        Label registrationLabel = new Label("Registration");
        registrationLabel.setFont(Font.font(23));

        Button studentRegistrationButton = new Button("Students Registration");
        studentRegistrationButton.setFont(Font.font(25));
        studentRegistrationButton.setStyle("-fx-background-radius: 50");
        studentRegistrationButton.setOnAction(e -> {
            window.setScene(studentsRegisterScene);
            window.setTitle("Welcome to [theAttendee] 👋");
        });

        VBox buttonRegisterStack = new VBox();
        buttonRegisterStack.getChildren().addAll(studentRegistrationButton);
        buttonRegisterStack.setSpacing(20);

        VBox registrationVBox = new VBox();
        registrationVBox.getChildren().addAll(registrationLabel, buttonRegisterStack);
        registrationVBox.setSpacing(40);
        registrationVBox.setAlignment(Pos.CENTER);
        registrationVBox.setAlignment(Pos.CENTER);

        // Break

        Label attendanceLabel = new Label("Attendance");
        attendanceLabel.setFont(Font.font(23));

        Button studentAttendanceButton = new Button("Students Attendance");
        studentAttendanceButton.setFont(Font.font(25));
        studentAttendanceButton.setStyle("-fx-background-radius: 50");
        studentAttendanceButton.setOnAction(e -> {
            window.setScene(studentsAttendanceScene);
            window.setTitle("Welcome to [theAttendee] 👋");
        });

        VBox buttonAttendanceStack = new VBox();
        buttonAttendanceStack.getChildren().addAll(studentAttendanceButton);
        buttonAttendanceStack.setSpacing(20);
        buttonAttendanceStack.setAlignment(Pos.CENTER);

        VBox attendanceVBox = new VBox();
        attendanceVBox.getChildren().addAll(attendanceLabel, buttonAttendanceStack);
        attendanceVBox.setSpacing(40);
        attendanceVBox.setAlignment(Pos.CENTER);

        HBox optionStack = new HBox();
        optionStack.getChildren().addAll(registrationVBox, attendanceVBox);
        optionStack.setAlignment(Pos.CENTER);
        optionStack.setSpacing(200);

        Button smartSearchButton = new Button("Smart Search");
        smartSearchButton.setMinHeight(50);
        smartSearchButton.setMaxWidth(715);
        smartSearchButton.setFont(Font.font(25));
        smartSearchButton.setStyle("-fx-background-radius: 50");
        smartSearchButton.setOnAction(e -> window.setScene(smartScene));

        Button backButton = new Button(backLabel);
        backButton.setStyle("-fx-background-radius: 50");
        backButton.setOnAction(e -> {

            window.setScene(welcomeScene);
            window.setTitle("Welcome to [theAttendee] 👋");

        });

        backButton.setMinHeight(50);
        backButton.setMaxWidth(715);
        backButton.setFont(Font.font(25));

        VBox backButtonStack = new VBox();
        backButtonStack.getChildren().addAll(smartSearchButton, backButton);
        backButtonStack.setSpacing(10);
        backButtonStack.setAlignment(Pos.CENTER);
        backButtonStack.setMaxWidth(optionStack.getMaxWidth());

        VBox fullMenuView = new VBox();
        fullMenuView.getChildren().addAll(optionStack, backButtonStack);
        fullMenuView.setAlignment(Pos.CENTER);
        fullMenuView.setSpacing(30);

        Label rightsLabel1 = new Label(rights);

        VBox rightsBox1 = new VBox();
        rightsBox1.getChildren().addAll(rightsLabel1);
        rightsBox1.setAlignment(Pos.CENTER);

        BorderPane menuBorder = new BorderPane();
        menuBorder.setTop(title1Box);
        menuBorder.setCenter(fullMenuView);
        menuBorder.setBottom(rightsBox1);
        menuBorder.setPadding(new Insets(20));

        menuScene = new Scene(menuBorder, 1000, 700);

        // End of menuScene

        // Start of studentsRegisterScene

        studentIDTF = new TextField();
        studentIDTF.setPromptText("Enter Students ID");

        VBox studentsIDStack = new VBox();
        studentIDTF.setAlignment(Pos.CENTER);
        studentsIDStack.getChildren().addAll(studentIDTF);

        firstnameTF = new TextField();
        firstnameTF.setPromptText("Enter Firstname");

        lastnameTF = new TextField();
        lastnameTF.setPromptText("Enter Lastname");

        HBox nameStack = new HBox();
        nameStack.getChildren().addAll(firstnameTF, lastnameTF);
        nameStack.setAlignment(Pos.CENTER);

        emailTF = new TextField();
        emailTF.setPromptText("Enter Email");

        phoneNumTF = new TextField();
        phoneNumTF.setPromptText("Enter Phone Number");

        HBox studCredentialBox = new HBox();
        studCredentialBox.getChildren().addAll(emailTF, phoneNumTF);
        studCredentialBox.setAlignment(Pos.CENTER);

        rollTF = new ComboBox<>();
        rollTF.setPromptText("Enter Roll Number");
        /*
        rollTF.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
                "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33",
                "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50");
                */
        for(int i=1;i<=50;i++)
        {
            rollTF.getItems().addAll(Integer.toString(i));
        }
        rollTF.setMaxWidth(nameStack.getMaxWidth());

        VBox rollStack = new VBox();
        rollStack.getChildren().addAll(rollTF);
        rollStack.setAlignment(Pos.CENTER);
        rollStack.setMaxWidth(nameStack.getMaxWidth());

        classTF = new ComboBox<>();
        classTF.setPromptText("Enter Class");
        classTF.getItems().addAll("I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X");

        divTF = new ComboBox<>();
        divTF.setPromptText("Enter Division");
        divTF.getItems().addAll("A", "B", "C", "D");

        HBox classDivStack = new HBox();
        classDivStack.getChildren().addAll(classTF, divTF);
        classDivStack.setAlignment(Pos.CENTER);

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {

            insertData();
            tableView.setItems(getDataElements());

        });

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteButtonAction());

        Button backToMenuReg = new Button(backLabel);
        backToMenuReg.setMinWidth(500);
        backToMenuReg.setMinHeight(40);
        backToMenuReg.setAlignment(Pos.CENTER);
        backToMenuReg.setOnAction(e -> window.setScene(menuScene));

        Button refreshButtonforReg = new Button("Refresh");
        refreshButtonforReg.setMinWidth(500);
        refreshButtonforReg.setMinHeight(40);
        refreshButtonforReg.setAlignment(Pos.CENTER);
        refreshButtonforReg.setOnAction(e -> getDataElements());

        HBox backToMenuForRegister = new HBox();
        backToMenuForRegister.setPadding(new Insets(20));
        backToMenuForRegister.setSpacing(20);
        backToMenuForRegister.setAlignment(Pos.CENTER);
        backToMenuForRegister.getChildren().addAll(backToMenuReg,refreshButtonforReg);

        HBox submitButtonStack = new HBox();
        submitButtonStack.getChildren().addAll(submitButton, deleteButton);
        submitButtonStack.setAlignment(Pos.CENTER);

        HBox entriesStack = new HBox();
        entriesStack.setSpacing(5);
        entriesStack.getChildren().addAll(studentsIDStack, nameStack, studCredentialBox, rollStack, classDivStack,submitButtonStack);
        entriesStack.setAlignment(Pos.CENTER);

        TableColumn<tableViewManager, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<tableViewManager, String> fnameCol = new TableColumn<>("Firstname");
        fnameCol.setCellValueFactory(new PropertyValueFactory<>("fname"));
        fnameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        fnameCol.setOnEditCommit(this::onEditChangedFnameCol);

        TableColumn<tableViewManager, String> lnameCol = new TableColumn<>("Lastname");
        lnameCol.setCellValueFactory(new PropertyValueFactory<>("lname"));
        lnameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lnameCol.setOnEditCommit(this::onEditChangedLnameCol);

        TableColumn<tableViewManager, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setOnEditCommit(this::onEditChangedEmailCol);

        TableColumn<tableViewManager, String> phoneNumCol = new TableColumn<>("Phone Number");
        phoneNumCol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        phoneNumCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumCol.setOnEditCommit(this::onEditChangedPhoneCol);

        TableColumn<tableViewManager, String> rollCol = new TableColumn<>("Roll Number");
        rollCol.setCellValueFactory(new PropertyValueFactory<>("roll"));
        rollCol.setCellFactory(TextFieldTableCell.forTableColumn());
        rollCol.setOnEditCommit(this::onEditChangedRollCol);

        TableColumn<tableViewManager, String> theClassCol = new TableColumn<>("Class");
        theClassCol.setCellValueFactory(new PropertyValueFactory<>("theClass"));
        theClassCol.setCellFactory(TextFieldTableCell.forTableColumn());
        theClassCol.setOnEditCommit(this::onEditChangedClassCol);

        TableColumn<tableViewManager, String> divCol = new TableColumn<>("Division");
        divCol.setCellValueFactory(new PropertyValueFactory<>("div"));
        divCol.setCellFactory(TextFieldTableCell.forTableColumn());
        divCol.setOnEditCommit(this::onEditChangedDivCol);

        TableColumn<tableViewManager, String> dateCol = new TableColumn<>("Date of Registration");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        tableView = new TableView<>();
        tableView.setItems(getDataElements());
        tableView.getColumns().addAll(idCol, fnameCol, lnameCol, emailCol, phoneNumCol, rollCol, theClassCol, divCol,dateCol);
        tableView.setEditable(true);

        VBox tableViewStack = new VBox();
        tableViewStack.getChildren().addAll(tableView);

        txtArea1 = new TextArea();
        txtArea1.setEditable(false);
        txtArea1.setText("Hi! This is the Console Area. Cool Right?");

        VBox textBox = new VBox();
        textBox.getChildren().addAll(txtArea1);

        VBox tableViewAndEntries = new VBox();
        tableViewAndEntries.getChildren().addAll(entriesStack, backToMenuForRegister, tableViewStack);

        VBox withConsole = new VBox();
        withConsole.getChildren().addAll(tableViewAndEntries, textBox);
        withConsole.setSpacing(10);

        Label rightsLabel2 = new Label(rights);

        VBox rightsBox2 = new VBox();
        rightsBox2.getChildren().addAll(rightsLabel2);
        rightsBox2.setAlignment(Pos.CENTER);

        BorderPane bpMenu = new BorderPane();
        bpMenu.setCenter(withConsole);
        bpMenu.setBottom(rightsBox2);
        bpMenu.setPadding(new Insets(20));

        studentsRegisterScene = new Scene(bpMenu, 1400, 800);

        // End of studentsRegistration

        // Start of studentsAttendanceScene

        Label goodTitle = new Label("[theAttendee]");
        goodTitle.setFont(Font.font(100.0));

        ComboBox<String> selectGrade = new ComboBox<>();
        selectGrade.getItems().addAll("Grade 1", "Grade 2", "Grade 3", "Grade 4", "Grade 5", "Grade 6", "Grade 7",
                "Grade 8", "Grade 9", "Grade 10");
        selectGrade.setPromptText("Which Grade would you like to find?");
        selectGrade.setMaxWidth(500);
        selectGrade.setMinHeight(50);

        Button goToAttButton = new Button("Let's Go");
        goToAttButton.setMaxWidth(500);
        goToAttButton.setMinHeight(50);
        goToAttButton.setOnAction(e -> {

            try {
                switch (selectGrade.getValue()) {
                    case "Grade 1":
                        tableViewAtt.refresh();
                        window.setScene(first);
                        txtAreaForAtt.setText("Console\n>> This is for Class I");
                        tableViewAtt.setItems(firstElements());
                        break;
                    case "Grade 2":
                        tableViewAtt.refresh();
                        window.setScene(first);
                        txtAreaForAtt.setText("Console\n>> This is for Class II");
                        tableViewAtt.setItems(secondElements());
                        break;
                    case "Grade 3":
                        tableViewAtt.refresh();
                        window.setScene(first);
                        txtAreaForAtt.setText("Console\n>> This is for Class III");
                        tableViewAtt.setItems(thirdElements());
                        break;
                    case "Grade 4":
                        tableViewAtt.refresh();
                        window.setScene(first);
                        txtAreaForAtt.setText("Console\n>> This is for Class IV");
                        tableViewAtt.setItems(fourthElements());
                        break;
                    case "Grade 5":
                        tableViewAtt.refresh();
                        window.setScene(first);
                        txtAreaForAtt.setText("Console\n>> This is for Class V");
                        tableViewAtt.setItems(fifthElements());
                        break;
                    case "Grade 6":
                        tableViewAtt.refresh();
                        window.setScene(first);
                        txtAreaForAtt.setText("Console\n>> This is for Class VI");
                        tableViewAtt.setItems(sixthElements());
                        break;
                    case "Grade 7":
                        tableViewAtt.refresh();
                        window.setScene(first);
                        txtAreaForAtt.setText("Console\n>> This is for Class VII");
                        tableViewAtt.setItems(seventhElements());
                        break;
                    case "Grade 8":
                        tableViewAtt.refresh();
                        window.setScene(first);
                        txtAreaForAtt.setText("Console\n>> This is for Class VIII");
                        tableViewAtt.setItems(eighthElements());
                        break;
                    case "Grade 9":
                        tableViewAtt.refresh();
                        window.setScene(first);
                        txtAreaForAtt.setText("Console\n>> This is for Class IX");
                        tableViewAtt.setItems(ninthElements());
                        break;
                    case "Grade 10":
                        tableViewAtt.refresh();
                        window.setScene(first);
                        txtAreaForAtt.setText("Console\n>> This is for Class X");
                        tableViewAtt.setItems(tenthElements());
                        break;

                    default:
                        System.out.println("Error");
                }
            } catch (Exception e1) {
                System.out.println("" + e1);
            }

        });

        Button goToAttBackButton = new Button(backLabel);
        goToAttBackButton.setMaxWidth(500);
        goToAttBackButton.setMinHeight(50);
        goToAttBackButton.setOnAction(e -> window.setScene(menuScene));

        VBox selectGradeStack = new VBox();
        selectGradeStack.setAlignment(Pos.CENTER);
        selectGradeStack.getChildren().addAll(selectGrade);

        VBox attButtonStack = new VBox();
        attButtonStack.setSpacing(10);
        attButtonStack.setAlignment(Pos.CENTER);
        attButtonStack.getChildren().addAll(goToAttButton, goToAttBackButton);

        VBox chooseVBox = new VBox();
        chooseVBox.setAlignment(Pos.CENTER);
        chooseVBox.setSpacing(10);
        chooseVBox.getChildren().addAll(goodTitle, selectGradeStack, attButtonStack);

        Label rightsLabel3 = new Label(rights);

        VBox rightsLabel3Stack = new VBox();
        rightsLabel3Stack.setAlignment(Pos.CENTER);
        rightsLabel3Stack.getChildren().addAll(rightsLabel3);

        BorderPane bpForGradeSearch = new BorderPane();
        bpForGradeSearch.setPadding(new Insets(20));
        bpForGradeSearch.setCenter(chooseVBox);
        bpForGradeSearch.setBottom(rightsLabel3Stack);

        studentsAttendanceScene = new Scene(bpForGradeSearch, 1000, 700);

        // End of studentsAttendanceScene

        // Start of firstScene

        TableColumn<attendance, String> tableIDForFirst = new TableColumn<>("ID");
        tableIDForFirst.setCellValueFactory(new PropertyValueFactory<>("idAtt"));

        TableColumn<attendance, String> tableFnameForFirst = new TableColumn<>("Firstname");
        tableFnameForFirst.setCellValueFactory(new PropertyValueFactory<>("fnameAtt"));

        TableColumn<attendance, String> tableLnameForFirst = new TableColumn<>("Lastname");
        tableLnameForFirst.setCellValueFactory(new PropertyValueFactory<>("lnameAtt"));

        TableColumn<attendance, String> tableRollForFirst = new TableColumn<>("Roll Number");
        tableRollForFirst.setCellValueFactory(new PropertyValueFactory<>("rollAtt"));

        TableColumn<attendance, String> tableTheClassForFirst = new TableColumn<>("Class");
        tableTheClassForFirst.setCellValueFactory(new PropertyValueFactory<>("theClassAtt"));

        TableColumn<attendance, String> tableDivForFirst = new TableColumn<>("Division");
        tableDivForFirst.setCellValueFactory(new PropertyValueFactory<>("divAtt"));

        TableColumn<attendance, String> tableSubjectForFirst = new TableColumn<>("Subject");
        tableSubjectForFirst.setCellValueFactory(new PropertyValueFactory<>("subject"));
        tableSubjectForFirst.setCellFactory(TextFieldTableCell.forTableColumn());
        tableSubjectForFirst.setOnEditCommit(this::onEditChangedForSubject1);

        TableColumn<attendance, String> tableAttendanceForFirst = new TableColumn<>("Attendance");
        tableAttendanceForFirst.setCellValueFactory(new PropertyValueFactory<>("attendance"));
        tableAttendanceForFirst.setCellFactory(TextFieldTableCell.forTableColumn());
        tableAttendanceForFirst.setOnEditCommit(this::onEditChangedForAttendance1);

        TableColumn<attendance, String> tableReviewForFirst = new TableColumn<>("Review");
        tableReviewForFirst.setCellValueFactory(new PropertyValueFactory<>("review"));
        tableReviewForFirst.setCellFactory(TextFieldTableCell.forTableColumn());
        tableReviewForFirst.setOnEditCommit(this::onEditChangedForReview1);

        tableViewAtt = new TableView<>();
        tableViewAtt.getColumns().addAll(tableIDForFirst, tableFnameForFirst, tableLnameForFirst, tableRollForFirst, tableTheClassForFirst, tableDivForFirst, tableSubjectForFirst, tableAttendanceForFirst, tableReviewForFirst);
        tableViewAtt.setEditable(true);

        txtAreaForAtt = new TextArea();
        txtAreaForAtt.setMinHeight(80.0);
        txtAreaForAtt.setEditable(false);

        Button backToAttDashboard1 = new Button(backLabel);
        backToAttDashboard1.setMinHeight(40);
        backToAttDashboard1.setMinWidth(500);
        backToAttDashboard1.setOnAction(e -> window.setScene(studentsAttendanceScene));

        Button updateButton1 = new Button("Send / Save Data");
        updateButton1.setMinHeight(40);
        updateButton1.setMinWidth(460);
        updateButton1.setOnAction(e -> goUpdate1());

        HBox backAndSubmitButtonStack1 = new HBox();
        backAndSubmitButtonStack1.setPadding(new Insets(10,0,0,0));
        //Set Padding
    
        backAndSubmitButtonStack1.setAlignment(Pos.CENTER);
        backAndSubmitButtonStack1.getChildren().addAll(backToAttDashboard1, updateButton1);

        VBox fullFirstStack = new VBox();
        fullFirstStack.setAlignment(Pos.CENTER);
        fullFirstStack.getChildren().addAll(tableViewAtt, txtAreaForAtt, backAndSubmitButtonStack1);

        Label rightsLabelForFirst = new Label(rights);

        VBox rightsStackForFirst = new VBox();
        rightsStackForFirst.setAlignment(Pos.CENTER);
        rightsStackForFirst.getChildren().addAll(rightsLabelForFirst);

        BorderPane forFirstScene = new BorderPane();
        forFirstScene.setPadding(new Insets(20));
        forFirstScene.setCenter(fullFirstStack);
        forFirstScene.setBottom(rightsStackForFirst);

        first = new Scene(forFirstScene, 1000, 700);

        // End of firstScene

        // Start of smartScene

        TextField fnameSortTF = new TextField();
        fnameSortTF.setPromptText("Sort Firstname");

        TextField lnameSortTF = new TextField();
        lnameSortTF.setPromptText("Sort Lastname");

        TextField classSortTF = new TextField();
        classSortTF.setPromptText("Sort Class");

        TextField subjectSortTF = new TextField();
        subjectSortTF.setPromptText("Sort Subject");

        TextField attendanceSortTF = new TextField();
        attendanceSortTF.setPromptText("Sort Attendance");

        TextField dateSortTF = new TextField();
        dateSortTF.setPromptText("Sort Date");

        HBox sortFields = new HBox();
        sortFields.getChildren().addAll(fnameSortTF, lnameSortTF, classSortTF, subjectSortTF, attendanceSortTF, dateSortTF);

        TableColumn<finalRecTableViewManager, Integer> frIndex = new TableColumn<>("Index");
        frIndex.setCellValueFactory(new PropertyValueFactory<>("frIndex"));

        TableColumn<finalRecTableViewManager, String> frID = new TableColumn<>("Student ID");
        frID.setCellValueFactory(new PropertyValueFactory<>("frID"));

        TableColumn<finalRecTableViewManager, String> frFname = new TableColumn<>("Firstname");
        frFname.setCellValueFactory(new PropertyValueFactory<>("frFname"));
        // frFname.setCellFactory(TextFieldTableCell.forTableColumn());
        // frFname.setOnEditCommit(this::onEditChangedFnameColSmart);

        TableColumn<finalRecTableViewManager, String> frLname = new TableColumn<>("Lastname");
        frLname.setCellValueFactory(new PropertyValueFactory<>("frLname"));
        // frLname.setCellFactory(TextFieldTableCell.forTableColumn());
        // frLname.setOnEditCommit(this::onEditChangedLnameColSmart);

        TableColumn<finalRecTableViewManager, String> frRoll = new TableColumn<>("Roll Number");
        frRoll.setCellValueFactory(new PropertyValueFactory<>("frRoll"));
        // frRoll.setCellFactory(TextFieldTableCell.forTableColumn());
        // frRoll.setOnEditCommit(this::onEditChangedRollColSmart);

        TableColumn<finalRecTableViewManager, String> frClass = new TableColumn<>("Class");
        frClass.setCellValueFactory(new PropertyValueFactory<>("frClass"));
        // frClass.setCellFactory(TextFieldTableCell.forTableColumn());
        // frClass.setOnEditCommit(this::onEditChangedClassColSmart);

        TableColumn<finalRecTableViewManager, String> frDiv = new TableColumn<>("Division");
        frDiv.setCellValueFactory(new PropertyValueFactory<>("frDiv"));
        // frDiv.setCellFactory(TextFieldTableCell.forTableColumn());
        // frDiv.setOnEditCommit(this::onEditChangedDivColSmart);

        TableColumn<finalRecTableViewManager, String> frSubject = new TableColumn<>("Subject");
        frSubject.setCellValueFactory(new PropertyValueFactory<>("frSubject"));
        frSubject.setCellFactory(TextFieldTableCell.forTableColumn());
        frSubject.setOnEditCommit(this::onEditChangedSubjectColSmart);

        TableColumn<finalRecTableViewManager, String> frAttendance = new TableColumn<>("Attendance");
        frAttendance.setCellValueFactory(new PropertyValueFactory<>("frAttendance"));
        frAttendance.setCellFactory(TextFieldTableCell.forTableColumn());
        frAttendance.setOnEditCommit(this::onEditChangedAttendanceColSmart);

        TableColumn<finalRecTableViewManager, String> frDate = new TableColumn<>("Date of Attendance");
        frDate.setCellValueFactory(new PropertyValueFactory<>("frDate"));
        frDate.setCellFactory(TextFieldTableCell.forTableColumn());
        frDate.setOnEditCommit(this::onEditChangedDateColSmart);

        TableColumn<finalRecTableViewManager, String> frReview = new TableColumn<>("Review");
        frReview.setCellValueFactory(new PropertyValueFactory<>("frReview"));
        frReview.setCellFactory(TextFieldTableCell.forTableColumn());
        frReview.setOnEditCommit(this::onEditChangedReviewColSmart);

        smartTable = new TableView<>();
        smartTable.setEditable(true);
        smartTable.getColumns().addAll(frIndex, frID, frFname, frLname, frRoll, frClass, frDiv, frSubject, frAttendance,frDate,frReview);
        smartTable.setItems(finalRec());

        
        Button refreshButton = new Button("Refresh");
        refreshButton.setAlignment(Pos.CENTER);
        refreshButton.setMinHeight(30);
        refreshButton.setMinWidth(200);
        refreshButton.setOnAction(e -> finalRec());
        
        Button deleteFromRec = new Button("Delete");
        deleteFromRec.setAlignment(Pos.CENTER);
        deleteFromRec.setMinHeight(30);
        deleteFromRec.setMinWidth(200);
        deleteFromRec.setOnAction(e -> deleteFromRecAction());
        
        
        Button smartBackButton = new Button("Back");
        smartBackButton.setAlignment(Pos.CENTER);
        smartBackButton.setMinHeight(30);
        smartBackButton.setMinWidth(420);
        smartBackButton.setOnAction(e -> window.setScene(menuScene));
        
        // LEFT HERE

        HBox safeButtonStack1 = new HBox();
        safeButtonStack1.setAlignment(Pos.CENTER);
        safeButtonStack1.setPadding(new Insets(20,0,0,0));
        safeButtonStack1.setSpacing(20);
        safeButtonStack1.getChildren().addAll(deleteFromRec, refreshButton);

        HBox safeButtonStack2 = new HBox();
        safeButtonStack2.setAlignment(Pos.CENTER);
        safeButtonStack2.setPadding(new Insets(20,0,0,0));
        safeButtonStack2.getChildren().addAll(smartBackButton);

        VBox mainSmartScene = new VBox();
        mainSmartScene.getChildren().addAll(sortFields, smartTable, safeButtonStack1,safeButtonStack2);

        Label rightsLabelForSmartSearch = new Label(rights);

        VBox rightsForSmart = new VBox();
        rightsForSmart.setAlignment(Pos.CENTER);
        rightsForSmart.getChildren().addAll(rightsLabelForSmartSearch);

        BorderPane smartBorderPane = new BorderPane();
        smartBorderPane.setPadding(new Insets(20));
        smartBorderPane.setCenter(mainSmartScene);

        smartBorderPane.setBottom(rightsForSmart);

        smartScene = new Scene(smartBorderPane, 1000, 700);

        // End of smartScene

        // Start of fbScene

        trNameTF = new TextField();
        trNameTF.setPromptText("Enter Your Name");
        trNameTF.setMinWidth(883);

        rating = new ComboBox<>();
        rating.setPromptText("Rating");
        rating.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        HBox tfCombo = new HBox();
        tfCombo.getChildren().addAll(trNameTF,rating);

        fbTxtArea = new TextArea();
        fbTxtArea.setPromptText("What's on your mind about this app?");

        VBox txtFB = new VBox();
        txtFB.getChildren().addAll(tfCombo, fbTxtArea);

        Button sendFB = new Button("Send");
        sendFB.setOnAction(e -> sendFeedback());

        Button fbBackButton = new Button("back");
        fbBackButton.setOnAction(e -> window.setScene(welcomeScene));
        fbBackButton.setFocusTraversable(false);

        HBox fbHBox = new HBox();
        fbHBox.setPadding(new Insets(20,0,0,0));
        fbHBox.setSpacing(10);
        fbHBox.getChildren().addAll(fbBackButton,sendFB);

        VBox fbFull = new VBox();
        fbFull.getChildren().addAll(txtFB, fbHBox);

        Label fbRightsLabel = new Label(rights);

        VBox fbRightsStack = new VBox();
        fbRightsStack.getChildren().addAll(fbRightsLabel);
        fbRightsStack.setAlignment(Pos.CENTER);

        BorderPane fbBP = new BorderPane();
        fbBP.setPadding(new Insets(20));
        fbBP.setTop(fbFull);
        fbBP.setBottom(fbRightsStack);

        fbScene = new Scene(fbBP, 1000, 700);

        // Hey Aryan you left here connect this scene to mysql fb table

        // End of fbScene

        // Start of Filtering Data

        FilteredList<finalRecTableViewManager> filteredData1 = new FilteredList<>(finals, e -> true);
        fnameSortTF.setOnKeyReleased(e -> {

            fnameSortTF.textProperty().addListener((observableValue, oldValue, newValue) -> {

                filteredData1.setPredicate((Predicate<? super finalRecTableViewManager>) finalRecTableViewManager -> {

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (finalRecTableViewManager.getFrFname().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }

                    return false;

                });

            });

            SortedList<finalRecTableViewManager> sortedData1 = new SortedList<>(filteredData1);
            sortedData1.comparatorProperty().bind(smartTable.comparatorProperty());
            smartTable.setItems(sortedData1);

        });

        FilteredList<finalRecTableViewManager> filteredData2 = new FilteredList<>(finals, e -> true);
        lnameSortTF.setOnKeyReleased(e -> {

            lnameSortTF.textProperty().addListener((observableValue, oldValue, newValue) -> {

                filteredData2.setPredicate((Predicate<? super finalRecTableViewManager>) finalRecTableViewManager -> {

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (finalRecTableViewManager.getFrLname().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }

                    return false;

                });

            });

            SortedList<finalRecTableViewManager> sortedData2 = new SortedList<>(filteredData2);
            sortedData2.comparatorProperty().bind(smartTable.comparatorProperty());
            smartTable.setItems(sortedData2);

        });

        FilteredList<finalRecTableViewManager> filteredData3 = new FilteredList<>(finals, e -> true);
        classSortTF.setOnKeyReleased(e -> {

            classSortTF.textProperty().addListener((observableValue, oldValue, newValue) -> {

                filteredData3.setPredicate((Predicate<? super finalRecTableViewManager>) finalRecTableViewManager -> {

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (finalRecTableViewManager.getFrClass().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }

                    return false;
                
                });

            });

            SortedList<finalRecTableViewManager> sortedData3 = new SortedList<>(filteredData3);
            sortedData3.comparatorProperty().bind(smartTable.comparatorProperty());
            smartTable.setItems(sortedData3);

        });

        FilteredList<finalRecTableViewManager> filteredData4 = new FilteredList<>(finals, e -> true);
        subjectSortTF.setOnKeyReleased(e -> {

            subjectSortTF.textProperty().addListener((observableValue, oldValue, newValue) -> {

                filteredData4.setPredicate((Predicate<? super finalRecTableViewManager>) finalRecTableViewManager -> {

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (finalRecTableViewManager.getFrSubject().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }

                    return false;

                });

            });

            SortedList<finalRecTableViewManager> sortedData4 = new SortedList<>(filteredData4);
            sortedData4.comparatorProperty().bind(smartTable.comparatorProperty());
            smartTable.setItems(sortedData4);

        });

        FilteredList<finalRecTableViewManager> filteredData5 = new FilteredList<>(finals, e -> true);
        attendanceSortTF.setOnKeyReleased(e -> {

            attendanceSortTF.textProperty().addListener((observableValue, oldValue, newValue) -> {

                filteredData5.setPredicate((Predicate<? super finalRecTableViewManager>) finalRecTableViewManager -> {

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (finalRecTableViewManager.getFrAttendance().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }

                    return false;

                });

            });

            SortedList<finalRecTableViewManager> sortedData5 = new SortedList<>(filteredData5);
            sortedData5.comparatorProperty().bind(smartTable.comparatorProperty());
            smartTable.setItems(sortedData5);

        });

        FilteredList<finalRecTableViewManager> filteredData6 = new FilteredList<>(finals, e -> true);
        dateSortTF.setOnKeyReleased(e -> {

            dateSortTF.textProperty().addListener((observableValue, oldValue, newValue) -> {

                filteredData6.setPredicate((Predicate<? super finalRecTableViewManager>) finalRecTableViewManager -> {

                    if (newValue == null || newValue.isEmpty()) 
                    {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (finalRecTableViewManager.getFrDate().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }

                    return false;

                });
            });
            SortedList<finalRecTableViewManager> sortedData6 = new SortedList<>(filteredData6);
            sortedData6.comparatorProperty().bind(smartTable.comparatorProperty());
            smartTable.setItems(sortedData6);

        });

        // Add filtering for subject and attendance

        // End of Filtering Data

        //CSS File Connection
        welcomeScene.getStylesheets().add(getClass().getResource("changes.css").toString());
        menuScene.getStylesheets().add(getClass().getResource("changes.css").toString());
        aboutScene.getStylesheets().add(getClass().getResource("changes.css").toString());
        fbScene.getStylesheets().add(getClass().getResource("changes.css").toString());
        studentsRegisterScene.getStylesheets().add(getClass().getResource("changes.css").toString());
        studentsAttendanceScene.getStylesheets().add(getClass().getResource("changes.css").toString());
        smartScene.getStylesheets().add(getClass().getResource("changes.css").toString());
        first.getStylesheets().add(getClass().getResource("changes.css").toString());

        //Setting up multiple selections
        tableViewAtt.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Window setup
        window.setScene(welcomeScene);
        window.setResizable(false);
        //window.setAlwaysOnTop(true);
        window.show();

    }

    //For Smart Table

    public void onEditChangedFnameColSmart(TableColumn.CellEditEvent<finalRecTableViewManager, String> fnameColSmartStringCellEditEvent) {
        finalRecTableViewManager sm = smartTable.getSelectionModel().getSelectedItem();

        sm.setFrFname(fnameColSmartStringCellEditEvent.getNewValue());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            st.execute("UPDATE fullRecords SET fname = '" + fnameColSmartStringCellEditEvent.getNewValue() + "' WHERE id = " + sm.getFrIndex() + "");
            
            smartTable.setItems(finalRec());
            st.close();
            con.close();

        } catch (Exception e) {
            txtArea1.setText("" + e);
        }

    }

    public void onEditChangedLnameColSmart(TableColumn.CellEditEvent<finalRecTableViewManager, String> lnameColSmartStringCellEditEvent) {
        finalRecTableViewManager sm = smartTable.getSelectionModel().getSelectedItem();


        sm.setFrLname(lnameColSmartStringCellEditEvent.getNewValue());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            st.execute("UPDATE fullRecords SET lname = '" + lnameColSmartStringCellEditEvent.getNewValue() + "' WHERE id = " + sm.getFrIndex() + "");
            smartTable.setItems(finalRec());
            st.close();
            con.close();
        } catch (Exception e) {
            txtArea1.setText("" + e);
        }

    }

    public void onEditChangedRollColSmart(TableColumn.CellEditEvent<finalRecTableViewManager, String> rollColSmartStringCellEditEvent) {
        finalRecTableViewManager sm = smartTable.getSelectionModel().getSelectedItem();


        sm.setFrRoll(rollColSmartStringCellEditEvent.getNewValue());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            st.execute("UPDATE fullRecords SET roll = '" + rollColSmartStringCellEditEvent.getNewValue() + "' WHERE id = " + sm.getFrIndex() + "");

            smartTable.setItems(finalRec());
            st.close();
            con.close();
        } catch (Exception e) {
            txtArea1.setText("" + e);
        }

    }

    public void onEditChangedClassColSmart(TableColumn.CellEditEvent<finalRecTableViewManager, String> classColSmartStringCellEditEvent) {
        finalRecTableViewManager sm = smartTable.getSelectionModel().getSelectedItem();


        sm.setFrClass(classColSmartStringCellEditEvent.getNewValue());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            st.execute("UPDATE fullRecords SET class = '" + classColSmartStringCellEditEvent.getNewValue() + "' WHERE id = " + sm.getFrIndex() + "");

            smartTable.setItems(finalRec());
            st.close();
            con.close();

        } catch (Exception e) {
            txtArea1.setText("" + e);
        }

    }

    public void onEditChangedDivColSmart(TableColumn.CellEditEvent<finalRecTableViewManager, String> divColSmartStringCellEditEvent) {
        finalRecTableViewManager sm = smartTable.getSelectionModel().getSelectedItem();


        sm.setFrDiv(divColSmartStringCellEditEvent.getNewValue());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            st.execute("UPDATE fullRecords SET div = '" + divColSmartStringCellEditEvent.getNewValue() + "' WHERE id = " + sm.getFrIndex() + "");
            smartTable.setItems(finalRec());
            st.close();
            con.close();
        } catch (Exception e) {
            txtArea1.setText("" + e);
        }

    }

    public void onEditChangedSubjectColSmart(TableColumn.CellEditEvent<finalRecTableViewManager, String> subjectColSmartStringCellEditEvent) {
        finalRecTableViewManager sm = smartTable.getSelectionModel().getSelectedItem();


        sm.setFrSubject(subjectColSmartStringCellEditEvent.getNewValue());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            st.execute("UPDATE fullRecords SET subject = '" + subjectColSmartStringCellEditEvent.getNewValue() + "' WHERE id = " + sm.getFrIndex() + "");
            smartTable.setItems(finalRec());
            st.close();
            con.close();
        } catch (Exception e) {
            txtArea1.setText("" + e);
        }

    }

    public void onEditChangedAttendanceColSmart(TableColumn.CellEditEvent<finalRecTableViewManager, String> attendanceColSmartStringCellEditEvent) {
        
        finalRecTableViewManager sm = smartTable.getSelectionModel().getSelectedItem();
        
        sm.setFrAttendance(attendanceColSmartStringCellEditEvent.getNewValue());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            st.execute("UPDATE fullRecords SET attendance = '" + attendanceColSmartStringCellEditEvent.getNewValue() + "' WHERE id = " + sm.getFrIndex() + "");
            smartTable.setItems(finalRec());
            st.close();
            con.close();
        } catch (Exception e) {
            txtArea1.setText("" + e);
        }

    }

    public void onEditChangedDateColSmart(TableColumn.CellEditEvent<finalRecTableViewManager, String> dateColSmartStringCellEditEvent) {
        
        finalRecTableViewManager sm = smartTable.getSelectionModel().getSelectedItem();
        
        sm.setFrDate(dateColSmartStringCellEditEvent.getNewValue());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            st.execute("UPDATE fullRecords SET date = '" + dateColSmartStringCellEditEvent.getNewValue() + "' WHERE id = " + sm.getFrIndex() + "");
            smartTable.setItems(finalRec());
            st.close();
            con.close();
        } catch (Exception e) {
            txtArea1.setText("" + e);
        }

    }

    public void onEditChangedReviewColSmart(TableColumn.CellEditEvent<finalRecTableViewManager, String> reviewColSmartStringCellEditEvent) {
        
        finalRecTableViewManager sm = smartTable.getSelectionModel().getSelectedItem();
        
        sm.setFrReview(reviewColSmartStringCellEditEvent.getNewValue());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            st.execute("UPDATE fullRecords SET review = '" + reviewColSmartStringCellEditEvent.getNewValue() + "' WHERE id = " + sm.getFrIndex() + "");
            smartTable.setItems(finalRec());
            st.close();
            con.close();
        } catch (Exception e) {
            txtArea1.setText("" + e);
        }

    }




    //For schoolRegister

    public void onEditChangedFnameCol(TableColumn.CellEditEvent<tableViewManager, String> fnameColStringCellEditEvent) {
        tableViewManager atm1 = tableView.getSelectionModel().getSelectedItem();


        atm1.setFname(fnameColStringCellEditEvent.getNewValue());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Add Class.forName("com.mysql.cj.jdbc.Driver"); to all connections
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            st.execute("UPDATE schoolRegister SET fname = '" + fnameColStringCellEditEvent.getNewValue() + "' WHERE id = " + atm1.getId() + "");
            st.execute("UPDATE schoolAtt SET fname = '" + fnameColStringCellEditEvent.getNewValue() + "' WHERE id = " + atm1.getId() + "");
            st.execute("UPDATE fullRecords SET fname = '" + fnameColStringCellEditEvent.getNewValue() + "' WHERE studID = " + atm1.getId() + "");

            tableView.setItems(getDataElements());
            st.close();
            con.close();
        } catch (Exception e) {
            txtArea1.setText("" + e);
        }

    }

    public void onEditChangedLnameCol(TableColumn.CellEditEvent<tableViewManager, String> lnameColStringCellEditEvent) {
        tableViewManager atm = tableView.getSelectionModel().getSelectedItem();

        atm.setLname(lnameColStringCellEditEvent.getNewValue());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            st.execute("UPDATE schoolRegister SET lname = '" + lnameColStringCellEditEvent.getNewValue() + "' WHERE id = " + atm.getId() + "");
            st.execute("UPDATE schoolAtt SET lname = '" + lnameColStringCellEditEvent.getNewValue() + "' WHERE id = " + atm.getId() + "");
            st.execute("UPDATE fullRecords SET lname = '" + lnameColStringCellEditEvent.getNewValue() + "' WHERE studID = " + atm.getId() + "");

            tableView.setItems(getDataElements());
            st.close();
            con.close();
        } catch (Exception e) {
            txtArea1.setText("" + e);
        }

    }

    public void onEditChangedRollCol(TableColumn.CellEditEvent<tableViewManager, String> rollColStringCellEditEvent) {
        tableViewManager atm = tableView.getSelectionModel().getSelectedItem();

        atm.setRoll(rollColStringCellEditEvent.getNewValue());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            st.execute("UPDATE schoolRegister SET roll = '" + rollColStringCellEditEvent.getNewValue() + "' WHERE id = " + atm.getId() + "");
            st.execute("UPDATE schoolAtt SET roll = '" + rollColStringCellEditEvent.getNewValue() + "' WHERE id = " + atm.getId() + "");
            st.execute("UPDATE fullRecords SET roll = '" + rollColStringCellEditEvent.getNewValue() + "' WHERE studID = " + atm.getId() + "");

            tableView.setItems(getDataElements());
            st.close();
            con.close();
        } catch (Exception e) {
            txtArea1.setText("" + e);
        }

    }

    public void onEditChangedClassCol(TableColumn.CellEditEvent<tableViewManager, String> classColStringCellEditEvent) {
        tableViewManager atm1 = tableView.getSelectionModel().getSelectedItem();

        atm1.setTheClass(classColStringCellEditEvent.getNewValue());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            st.execute("UPDATE schoolRegister SET class = '" + classColStringCellEditEvent.getNewValue() + "' WHERE id = " + atm1.getId() + "");
            st.execute("UPDATE schoolAtt SET class = '" + classColStringCellEditEvent.getNewValue() + "' WHERE id = " + atm1.getId() + "");
            //st.execute("UPDATE fullRecords SET class = '" + classColStringCellEditEvent.getNewValue() + "' WHERE studID = " + atm1.getId() + "");

            tableView.setItems(getDataElements());
            st.close();
            con.close();
        } catch (Exception e) {
            txtArea1.setText("" + e);
        }

    }

    public void onEditChangedDivCol(TableColumn.CellEditEvent<tableViewManager, String> divColStringCellEditEvent) {
        tableViewManager atm = tableView.getSelectionModel().getSelectedItem();

        atm.setDiv(divColStringCellEditEvent.getNewValue());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            st.execute("UPDATE schoolRegister SET division = '" + divColStringCellEditEvent.getNewValue() + "' WHERE id = " + atm.getId() + "");
            st.execute("UPDATE schoolAtt SET division = '" + divColStringCellEditEvent.getNewValue() + "' WHERE id = " + atm.getId() + "");
            //st.execute("UPDATE fullRecords SET div = '" + divColStringCellEditEvent.getNewValue() + "' WHERE studID = " + atm.getId() + "");

            tableView.setItems(getDataElements());
            st.close();
            con.close();
        } catch (Exception e) {
            txtArea1.setText("" + e);
        }

    }

    public void onEditChangedEmailCol(TableColumn.CellEditEvent<tableViewManager, String> emailColStringCellEditEvent) {
        tableViewManager atm = tableView.getSelectionModel().getSelectedItem();

        atm.setEmail(emailColStringCellEditEvent.getNewValue());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            st.execute("UPDATE schoolRegister SET email = '" + emailColStringCellEditEvent.getNewValue() + "' WHERE id = " + atm.getId() + "");
            st.execute("UPDATE schoolAtt SET email = '" + emailColStringCellEditEvent.getNewValue() + "' WHERE id = " + atm.getId() + "");
            st.close();
            con.close();
            tableView.setItems(getDataElements());
        } catch (Exception e) {
            txtArea1.setText("" + e);
        }

    }

    public void onEditChangedPhoneCol(TableColumn.CellEditEvent<tableViewManager, String> phoneColStringCellEditEvent) {
        tableViewManager atm = tableView.getSelectionModel().getSelectedItem();

        atm.setPhoneNum(phoneColStringCellEditEvent.getNewValue());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            st.execute("UPDATE schoolRegister SET phoneNum = '" + phoneColStringCellEditEvent.getNewValue() + "' WHERE id = " + atm.getId() + "");
            st.execute("UPDATE schoolAtt SET phoneNum = '" + phoneColStringCellEditEvent.getNewValue() + "' WHERE id = " + atm.getId() + "");
            st.close();
            con.close();
            tableView.setItems(getDataElements());
        } catch (Exception e) {
            txtArea1.setText("" + e);
        }

    }


    //Break from Scene
    //All functions starts here


    public ObservableList<finalRecTableViewManager> finalRec() 
    {

        finals = FXCollections.observableArrayList();

        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM fullRecords");
            while (rs.next()) 
            {
                finals.add(new finalRecTableViewManager(rs.getInt("id"), rs.getString("studID"), rs.getString("fname"), rs.getString("lname"), rs.getString("roll"), rs.getString("class"), rs.getString("div"), rs.getString("subject"), rs.getString("attendance"), rs.getString("date"), rs.getString("review")));
            }
            smartTable.setItems(finals);
            st.close();
            con.close();
        } 
        catch (Exception e) {
            System.out.println("" + e);
        }

        return finals;
    }


    public ObservableList<attendance> firstElements() 
    {
        allGradesStud = FXCollections.observableArrayList();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM schoolAtt WHERE class = 'I'");

            while (rs.next())
            {
                allGradesStud.add(new attendance(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("roll"), rs.getString("class"), rs.getString("div"), rs.getString("subject"), rs.getString("attendance"), rs.getString("review")));
            }
            tableViewAtt.setItems(allGradesStud);
            con.close();

        } catch (Exception e) {
            txtAreaForAtt.setText("" + e);
        }

        return allGradesStud;

    }

    public ObservableList<attendance> secondElements() {

        allGradesStud = FXCollections.observableArrayList();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM schoolAtt WHERE class = 'II'");

            while (rs.next()) {
                allGradesStud.add(new attendance(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("roll"), rs.getString("class"), rs.getString("div"), rs.getString("subject"), rs.getString("attendance"), rs.getString("review")));
            }
            tableViewAtt.setItems(allGradesStud);
            con.close();

        } catch (Exception e) {
            txtAreaForAtt.setText("" + e);
        }

        return allGradesStud;
    }

    public ObservableList<attendance> thirdElements() {

        allGradesStud = FXCollections.observableArrayList();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM schoolAtt WHERE class = 'III'");

            while (rs.next()) {
                allGradesStud.add(new attendance(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("roll"), rs.getString("class"), rs.getString("div"), rs.getString("subject"), rs.getString("attendance"), rs.getString("review")));
            }
            tableViewAtt.setItems(allGradesStud);
            con.close();

        } catch (Exception e) {
            txtAreaForAtt.setText("" + e);
        }

        return allGradesStud;

    }

    public ObservableList<attendance> fourthElements() {

        allGradesStud = FXCollections.observableArrayList();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM schoolAtt WHERE class = 'IV'");

            while (rs.next()) 
            {
                allGradesStud.add(new attendance(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("roll"), rs.getString("class"), rs.getString("div"), rs.getString("subject"), rs.getString("attendance"), rs.getString("review")));
            }
            tableViewAtt.setItems(allGradesStud);
            con.close();

        } catch (Exception e) {
            txtAreaForAtt.setText("" + e);
        }

        return allGradesStud;
    }

    public ObservableList<attendance> fifthElements() {

        allGradesStud = FXCollections.observableArrayList();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM schoolAtt WHERE class = 'V'");

            while (rs.next()) {
                allGradesStud.add(new attendance(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("roll"), rs.getString("class"), rs.getString("div"), rs.getString("subject"), rs.getString("attendance"), rs.getString("review")));
            }
            tableViewAtt.setItems(allGradesStud);
            con.close();
            
        } catch (Exception e) {
            txtAreaForAtt.setText("" + e);
        }

        return allGradesStud;
    }

    public ObservableList<attendance> sixthElements() {

        allGradesStud = FXCollections.observableArrayList();

        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM schoolAtt WHERE class = 'VI'");

            while (rs.next()) {
                allGradesStud.add(new attendance(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("roll"), rs.getString("class"), rs.getString("div"), rs.getString("subject"), rs.getString("attendance"), rs.getString("review")));
            }
            tableViewAtt.setItems(allGradesStud);
            con.close();

        } catch (Exception e) {
            txtAreaForAtt.setText("" + e);
        }

        return allGradesStud;
    }

    public ObservableList<attendance> seventhElements() {

        allGradesStud = FXCollections.observableArrayList();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM schoolAtt WHERE class = 'VII'");

            while (rs.next()) 
            {
                allGradesStud.add(new attendance(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("roll"), rs.getString("class"), rs.getString("div"), rs.getString("subject"), rs.getString("attendance"), rs.getString("review")));
            }
            tableViewAtt.setItems(allGradesStud);
            con.close();

        } catch (Exception e) {
            txtAreaForAtt.setText("" + e);
        }

        return allGradesStud;
    }


    public ObservableList<attendance> eighthElements() {

        allGradesStud = FXCollections.observableArrayList();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM schoolAtt WHERE class = 'VIII'");

            while (rs.next()) 
            {
                allGradesStud.add(new attendance(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("roll"), rs.getString("class"), rs.getString("div"), rs.getString("subject"), rs.getString("attendance"), rs.getString("review")));
            }
            tableViewAtt.setItems(allGradesStud);
            con.close();

        } catch (Exception e) {
            txtAreaForAtt.setText("" + e);
        }

        return allGradesStud;
    }

    public ObservableList<attendance> ninthElements() {

        allGradesStud = FXCollections.observableArrayList();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM schoolAtt WHERE class = 'IX'");

            while (rs.next()) 
            {
                allGradesStud.add(new attendance(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("roll"), rs.getString("class"), rs.getString("div"), rs.getString("subject"), rs.getString("attendance"), rs.getString("review")));
            }
            tableViewAtt.setItems(allGradesStud);
            con.close();

        } catch (Exception e) {
            txtAreaForAtt.setText("" + e);
        }

        return allGradesStud;
    }

    public ObservableList<attendance> tenthElements() {

        allGradesStud = FXCollections.observableArrayList();

        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM schoolAtt WHERE class = 'X'");

            while (rs.next())
            {
                allGradesStud.add(new attendance(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("roll"), rs.getString("class"), rs.getString("div"), rs.getString("subject"), rs.getString("attendance"), rs.getString("review")));
            }
            tableViewAtt.setItems(allGradesStud);
            con.close();

        } catch (Exception e) {
            txtAreaForAtt.setText("" + e);
        }

        return allGradesStud;
    }


    public ObservableList<tableViewManager> getDataElements()
    {
        ObservableList<tableViewManager> stud = FXCollections.observableArrayList();

        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM schoolRegister");

            while (rs.next())
            {
                stud.add(new tableViewManager(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("roll"), rs.getString("class"), rs.getString("division"), rs.getString("email"), rs.getString("phoneNum"), rs.getString("dateAdded")));
            }
            tableView.setItems(stud);
            tableView.refresh();

            st.close();
            rs.close();
            con.close();

        } catch (Exception e) {
            txtArea1.setText("" + e);
        }

        return stud;
    }

    //INSERT INTO schoolRegister AND schoolAtt
    public void insertData()
    {
            if ((studentIDTF.getText().isEmpty()) || (firstnameTF.getText().isEmpty()) || (lastnameTF.getText().isEmpty()) || (emailTF.getText().isEmpty()) || (phoneNumTF.getText().isEmpty()) || (rollTF.getValue().isEmpty()) || (classTF.getValue().isEmpty()) || (divTF.getValue().isEmpty())) {
                txtArea1.setText("Please fill all the fields");
            } else {
                if (emailTF.getText().contains("@") && emailTF.getText().contains(".com")) {
                    if (phoneNumTF.getLength() == 10) 
                    {
                        if(studentIDTF.getText() == "0")
                        {
                            txtArea1.setText("Student ID cannot be 0");
                        }
                        else
                        {
                            try {

                                phoneNumDouble = (Long.parseLong(phoneNumTF.getText()));
                                theStudentIDTF = (Long.parseLong(studentIDTF.getText()));

                                Class.forName("com.mysql.cj.jdbc.Driver");
                                con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
                                Statement st = con.createStatement();
                                st.execute("INSERT INTO schoolRegister VALUES ('" + theStudentIDTF + "','" + firstnameTF.getText() + "','" + lastnameTF.getText() + "','" + rollTF.getValue() + "','" + classTF.getValue() + "','" + divTF.getValue() + "','" + emailTF.getText() + "','" + phoneNumDouble + "','" + (date.toString()) + "')");
                                st.execute("INSERT INTO schoolAtt VALUES ('" + theStudentIDTF + "','" + firstnameTF.getText() + "','" + lastnameTF.getText() + "','" + rollTF.getValue() + "','" + classTF.getValue() + "','" + divTF.getValue() + "','all','p','')");
                                ResultSet rs = st.executeQuery("SELECT * FROM schoolAtt");

                                //Hey Aryan
                                //You need to test this app cuz
                                //Class.forName(""); is added

                                while (rs.next()) 
                                {
                                    tableView.setItems(getDataElements());

                                    tableViewAtt.setItems(firstElements());
                                    tableViewAtt.setItems(secondElements());
                                    tableViewAtt.setItems(thirdElements());
                                    tableViewAtt.setItems(fourthElements());
                                    tableViewAtt.setItems(fifthElements());
                                    tableViewAtt.setItems(sixthElements());
                                    tableViewAtt.setItems(seventhElements());
                                    tableViewAtt.setItems(eighthElements());
                                    tableViewAtt.setItems(ninthElements());
                                    tableViewAtt.setItems(tenthElements());

                                    tableView.refresh();
                                    tableViewAtt.refresh();
                                }


                                //st.close();
                                //rs.close();
                                con.close();

                                clearSome();

                                txtArea1.setText("Success!");

                            } catch (Exception e) {
                                txtArea1.setText("" + e);
                            }
                        }

                    } else {
                        txtArea1.setText("Enter a valid phone number");
                    }
            } else {
                txtArea1.setText("Enter a valid email address");
            }
        }
    }

    //For tableViewAtt

    public void onEditChangedForAttendance1(TableColumn.CellEditEvent<attendance, String> attendanceStringCellEditEvent) 
    {
        attendance atm1 = tableViewAtt.getSelectionModel().getSelectedItem();
        atm1.setAttendance(attendanceStringCellEditEvent.getNewValue());
    }

    public void onEditChangedForSubject1(TableColumn.CellEditEvent<attendance, String> subjectStringCellEditEvent) 
    {    
        attendance atm = tableViewAtt.getSelectionModel().getSelectedItem();
        atm.setSubject(subjectStringCellEditEvent.getNewValue());
    }

    public void onEditChangedForReview1(TableColumn.CellEditEvent<attendance, String> reviewStringCellEditEvent) 
    {    
        attendance atm = tableViewAtt.getSelectionModel().getSelectedItem();
        atm.setReview(reviewStringCellEditEvent.getNewValue());
    }

    //Updates

    public void goUpdate1() 
    {
        attendance tbOBJ = tableViewAtt.getSelectionModel().getSelectedItem();

        try
        {
            con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
            Statement st = con.createStatement();

            st.execute("INSERT INTO fullRecords VALUES (default,'" + tbOBJ.getIdAtt() + "','" + tbOBJ.getFnameAtt() + "','" + tbOBJ.getLnameAtt() + "','" + tbOBJ.getRollAtt() + "','" + tbOBJ.getTheClassAtt() + "','" + tbOBJ.getDivAtt() + "','" + tbOBJ.getSubject() + "','" + tbOBJ.getAttendance() + "','" + date.toString() + "','"+tbOBJ.getReview()+"')");

            txtAreaForAtt.setText("Success");
            con.close();

            //st.close();

        } catch (Exception e) {
            txtAreaForAtt.setText("" + e);
        }
    }

    //"INSERT INTO fullRecords VALUES (default,'" + tbOBJ.getIdAtt() + "','" + tbOBJ.getFnameAtt() + "','" + tbOBJ.getLnameAtt() + "','" + tbOBJ.getRollAtt() + "','" + tbOBJ.getTheClassAtt() + "','" + tbOBJ.getDivAtt() + "','" + tbOBJ.getSubject() + "','" + tbOBJ.getAttendance() + "','" + date.toString() + "')"


    public void deleteButtonAction()    //DELETE FIXED ✅
    {
        tableViewManager tbv = tableView.getSelectionModel().getSelectedItem();

        result = AlertBox.display("Are you sure you want to delete student");

        if (result)
        {
            try 
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
                Statement st = con.createStatement();

                st.execute("DELETE FROM schoolRegister WHERE id = " + tbv.getId() + "");
                st.execute("DELETE FROM schoolAtt WHERE id = " + tbv.getId() + "");
                st.execute("DELETE FROM fullRecords WHERE studID = " + tbv.getId() + "");

                tableView.setItems(getDataElements());

                tableViewAtt.setItems(firstElements());
                tableViewAtt.setItems(secondElements());
                tableViewAtt.setItems(thirdElements());
                tableViewAtt.setItems(fourthElements());
                tableViewAtt.setItems(fifthElements());
                tableViewAtt.setItems(sixthElements());
                tableViewAtt.setItems(seventhElements());
                tableViewAtt.setItems(eighthElements());
                tableViewAtt.setItems(ninthElements());
                tableViewAtt.setItems(tenthElements());

                smartTable.setItems(finalRec());

                st.close();

                tableView.refresh();
                tableViewAtt.refresh();

                tableView.setItems(getDataElements());

                txtArea1.setText("Student Deleted🔴");
                con.close();

            } catch (Exception e) {
                txtArea1.setText("" + e);
            }
        }
    }

    public void deleteFromRecAction() 
    {
        finalRecTableViewManager fRec = smartTable.getSelectionModel().getSelectedItem();        
        result = AlertBox.display("Are you sure you want to delete student's records");

        if(result)
        {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
                Statement st = con.createStatement();
                st.execute("DELETE FROM fullRecords WHERE id = " + fRec.getFrIndex() + "");

                smartTable.setItems(finalRec());
                con.close();

            } catch (Exception e) {
                System.out.println("" + e);
            }
        }
    }

    //Show ErrorBox
    public void oopsie()
    {
        result = AlertBox.display("Something went wrong...");

        if(result)
            window.close();

    }

    //Show Alert Box
    public void closeProgram()
    {
        result = AlertBox.display("Are you sure you want to exit?");

        if (result)
            window.close();
    }

    //Make an error function here

    public void sendFeedback() {
        try {
            if ((trNameTF.getText().isEmpty()) || (fbTxtArea.getText().isEmpty())) {
                System.out.println("Please fill ll the fields");
            } else {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(cred.url, cred.user, cred.pass);
                Statement st = con.createStatement();
                st.execute("INSERT INTO fb VALUES (default,'" + trNameTF.getText() + "','" + fbTxtArea.getText() + "','" + rating.getValue() + "','" + date.toString() + "')");
                st.close();

                clearSome();
                con.close();
            }

        } catch (Exception e) {
            System.out.println("" + e);
        }
    }

    public void clearSome() 
    {
        studentIDTF.clear();
        firstnameTF.clear();
        lastnameTF.clear();
        emailTF.clear();
        phoneNumTF.clear();
        fbTxtArea.clear();
        trNameTF.clear();
    }
}

