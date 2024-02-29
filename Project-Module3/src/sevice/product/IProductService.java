package sevice.product;

import ra.model.Products;
import sevice.IGenericService;

import java.util.List;

public interface IProductService extends IGenericService<Products> {
    List<Products> findName(String name);
}
