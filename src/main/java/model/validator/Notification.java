package model.validator;

import java.util.ArrayList;
import java.util.List;

public class Notification<T> {

    private T result;
    private final List<String> errors;

    public Notification(){
        this.errors = new ArrayList<>();
    }

    public void addError(String error){
        this.errors.add(error);
    }

    public boolean hasError(){
        return !this.errors.isEmpty();
    }

    public T getResult(){
        if(hasError()){
            throw new ResultFetchException(errors);
        }
        return result;
    }

    public void setResult(T result){
        this.result = result;
    }

    public String getFormattedErrors(){
        return String.join("\n", errors);
    }
}
