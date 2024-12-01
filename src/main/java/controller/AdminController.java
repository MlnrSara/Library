package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Role;
import model.builder.UserBuilder;
import model.validator.Notification;
import model.validator.ResultFetchException;
import service.user.EmployeeService;
import view.AdminView;
import view.model.UserDTO;

import java.util.ArrayList;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

public class AdminController {

    private final EmployeeService employeeService;

    private final AdminView adminView;

    public AdminController(AdminView adminView, EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.adminView = adminView;

        this.adminView.addAddButtonListener(new AddButtonListener());
        this.adminView.addReportButtonListener(new ReportButtonListener());
    }

    private class AddButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = adminView.getUsername();
            String password = adminView.getPassword();

            if(username.isEmpty() || password.isEmpty()){
                adminView.addDisplayAlertMessage("Add Error", "Problem at password or username fields", "Cannot have empty input fields.");
            } else {
                    UserDTO userDTO = new UserDTO(username);
                    Notification<Boolean> addedEmployee = employeeService.save(username, password);
                    try {
                        addedEmployee.getResult();
                        adminView.addDisplayAlertMessage("Add Successful", "Employee added", "Employee was successfully added to the database.");
                        adminView.addEmployeeToObservableList(userDTO);
                    } catch (ResultFetchException e) {
                        String errors = addedEmployee.getFormattedErrors();
                        adminView.addDisplayAlertMessage("Add Error", "Problem at adding employee", errors);
                    }
            }
        }
    }

    private class ReportButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {

        }
    }
}
