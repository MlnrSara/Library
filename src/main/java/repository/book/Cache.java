package repository.book;

import java.util.List;

public class Cache<T> {
    public List<T> storage;
    public List<T> load(){
        return storage;
    }

    public void save(List<T> storage){
        this.storage = storage;
    }

    public boolean hasResults(){
        return storage != null;
    }

    public void invalidateCache(){
        storage = null;
    }
}
