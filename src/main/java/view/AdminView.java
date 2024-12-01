package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.User;
import view.model.UserDTO;

import java.util.List;

public class AdminView {

    private TableView employeesTableView;
    private final ObservableList<UserDTO> employeesObservableList;
    private TextField usernameTextField;
    private TextField passwordTextField;
    private Label usernameLabel;
    private Label passwordLabel;
    private Button addButton;
    private Button reportButton;

    public AdminView(Stage primaryStage, List<UserDTO> users){
        primaryStage.setTitle("Admin dashboard");

        GridPane gridPane = new GridPane();
        initializeGridPage(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);

        employeesObservableList = FXCollections.observableArrayList(users);
        initTableView(gridPane);

        initSaveOptions(gridPane);

        primaryStage.show();
    }

    private void initializeGridPage(GridPane gridPane){
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initTableView(GridPane gridPane){
        employeesTableView = new TableView<UserDTO>();

        employeesTableView.setPlaceholder(new Label ("No employees to display"));
        TableColumn<UserDTO, String> usernameColumn = new TableColumn<UserDTO, String>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        employeesTableView.getColumns().addAll(usernameColumn);
        employeesTableView.setItems(employeesObservableList);
        gridPane.add(employeesTableView, 0, 0, 5, 1);
    }

    private void initSaveOptions(GridPane gridPane){
        usernameLabel = new Label("Username");
        gridPane.add(usernameLabel, 1, 1);
        usernameTextField = new TextField();
        gridPane.add(usernameTextField, 2, 1);

        passwordLabel = new Label("Password");
        gridPane.add(passwordLabel, 3, 1);
        passwordTextField = new TextField();
        gridPane.add(passwordTextField, 4, 1);

        addButton = new Button("Add");
        gridPane.add(addButton, 5, 1);
        reportButton = new Button("Gen. report");
        gridPane.add(reportButton, 6, 1);
    }

    public void addAddButtonListener(EventHandler<ActionEvent> addButtonListener){
        addButton.setOnAction(addButtonListener);
    }

    public void addReportButtonListener(EventHandler<ActionEvent> reportButtonListener){
        reportButton.setOnAction(reportButtonListener);
    }

    public void addDisplayAlertMessage(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public TableView getEmployeesTableView() {
        return employeesTableView;
    }

    public String getUsername() {
        return usernameTextField.getText();
    }

    public String getPassword() {
        return passwordTextField.getText();
    }

    public void addEmployeeToObservableList(UserDTO userDTO){
        this.employeesObservableList.add(userDTO);
    }
}
