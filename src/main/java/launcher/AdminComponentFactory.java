package launcher;

import controller.AdminController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.UserMapper;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.EmployeeService;
import service.user.EmployeeServiceMySQL;
import view.AdminView;
import view.model.UserDTO;

import java.sql.Connection;
import java.util.List;

public class AdminComponentFactory {

    private final AdminView adminView;
    private final AdminController adminController;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final EmployeeService employeeService;
    private static volatile AdminComponentFactory instance;

    public static AdminComponentFactory getInstance(Boolean componentForTest, Stage primaryStage){
        if(instance ==null){
            synchronized (AdminComponentFactory.class){
                if(instance == null){
                    instance = new AdminComponentFactory(componentForTest, primaryStage);
                }
            }
        }
        return instance;
    }

    public AdminComponentFactory(Boolean componentForTest, Stage primaryStage){
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentForTest).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.employeeService = new EmployeeServiceMySQL(userRepository, rightsRolesRepository);

        List<UserDTO> userDTOs = UserMapper.convertUserListToUserDTOList(employeeService.findAllEmployees());
        this.adminView = new AdminView(primaryStage, userDTOs);
        this.adminController = new AdminController(adminView, employeeService);

    }
}
