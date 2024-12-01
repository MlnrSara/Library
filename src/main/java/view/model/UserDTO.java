package view.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserDTO {
    private StringProperty username;

    public UserDTO(){
        setUsername("");
    }

    public UserDTO(String username){
        setUsername(username);
    }

    public void setUsername(String username){
        usernameProperty().set(username);
    }

    public String getUsername(){
        return usernameProperty().get();
    }

    public StringProperty usernameProperty(){
        if(username == null){
            username = new SimpleStringProperty(this, "username");
        }
        return username;
    }
}
