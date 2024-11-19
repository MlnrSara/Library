package launcher;

import controller.LoginController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import view.LoginView;

import java.sql.Connection;

public class LoginComponentFactory {

    private final RightsRolesRepository rightsRolesRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final LoginController loginController;
    private final LoginView loginView;
    private static Boolean componentsForTest;
    private static Stage primaryStage;

    private static volatile LoginComponentFactory instance;

    public static LoginComponentFactory getInstance(Boolean componentsForTest, Stage primaryStage) {
        if (instance == null) {
            synchronized (LoginComponentFactory.class) {
                if (instance == null) {
                    instance = new LoginComponentFactory(componentsForTest, primaryStage);
                }
            }
        }
        return instance;
    }

    public LoginComponentFactory(Boolean componentsForTest, Stage primaryStage) {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();

        this.primaryStage = primaryStage;
        this.componentsForTest = componentsForTest;

        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
        this.loginView = new LoginView(primaryStage);
        this.loginController = new LoginController(loginView, authenticationService);
    }

    public static Boolean getComponentsForTests() {
        return componentsForTest;
    }

    public static Stage getStage() {
        return primaryStage;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public LoginController getLoginController() {
        return loginController;
    }
}
