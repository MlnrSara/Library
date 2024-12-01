package service.user;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validator.Notification;
import model.validator.UserValidator;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static database.Constants.Roles.EMPLOYEE;

public class EmployeeServiceMySQL implements EmployeeService{

    private UserRepository userRepository;
    private RightsRolesRepository rightsRolesRepository;

    public EmployeeServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository){
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Notification<Boolean> save(String username, String password) {
        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);

        User user = new UserBuilder().setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(employeeRole))
                .build();
        UserValidator userValidator = new UserValidator(user);

        boolean userValid = userValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        if(!userValid){
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            user.setPassword(hashPassword(password));
            userRegisterNotification.setResult(userRepository.save(user).getResult());
        }
        return userRegisterNotification;
    }

    @Override
    public List<User> findAll() {
        Notification<List<User>> notification = userRepository.findAll();
        return notification.getResult();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAllEmployees(){
        return userRepository.findAllEmployees();
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


}
