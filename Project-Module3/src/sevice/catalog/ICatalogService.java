package sevice.catalog;

import ra.model.Catalogs;
import ra.model.Products;
import sevice.IGenericService;

import java.util.List;

public interface ICatalogService extends IGenericService<Catalogs> {
    List<Catalogs> findName(String name);

}
