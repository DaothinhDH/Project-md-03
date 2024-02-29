package sevice;

import ra.model.Products;

import java.util.List;

public interface IGenericService<T> {
    List<T> findAll();
    void save (T t); // add + update
    void delete(int id);
    T findById(int id);
    int getNewId();
    void updateData();

    void update(T t);
    List<Products> deleteProducts(int id);
}