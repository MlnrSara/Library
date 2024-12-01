package mapper;

import model.User;
import model.builder.UserBuilder;
import view.model.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO convertUserToUserDTO(User user){
        return new UserDTO(user.getUsername());
    }

    public static User convertUserDTOToUser(UserDTO userDTO){
        return new UserBuilder().setUsername(userDTO.getUsername()).build();
    }

    public static List<UserDTO> convertUserListToUserDTOList(List<User> users){
        return users.parallelStream().map(UserMapper::convertUserToUserDTO).collect(Collectors.toList());
    }

}
