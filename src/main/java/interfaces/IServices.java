package interfaces;

import java.util.List;

public interface IServices <T>{
    void Ajouter(T t);
    void updateEntity(T t);
    void deleteEntity(T t);
    List<T> getAllData();
}
