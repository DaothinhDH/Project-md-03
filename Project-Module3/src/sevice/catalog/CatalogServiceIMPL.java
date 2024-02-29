package sevice.catalog;

import ra.config.Config;
import ra.model.Catalogs;
import ra.model.Products;

import java.util.ArrayList;
import java.util.List;

public class CatalogServiceIMPL implements ICatalogService {
    static Config<List<Catalogs>> config = new Config();
    public static List<Catalogs> catalogsList;

    static {
        catalogsList = config.readFile(Config.URL_CATALOG);
        catalogsList = (catalogsList == null) ? new ArrayList<>() : catalogsList;
    }

    @Override
    public List<Catalogs> findAll() {
        return catalogsList;
    }

    @Override
    public void save(Catalogs catalogs) {
        if (findById(catalogs.getCatalogId()) == null) {
            catalogsList.add(catalogs);
            updateData();
        }else {
            catalogsList.set(catalogsList.indexOf(catalogs),catalogs);
            updateData();
        }

    }

    @Override
    public void delete(int id) {
        Catalogs catalogsDelete = findById(id);
        catalogsList.remove(catalogsDelete);
        updateData();
    }

    @Override
    public Catalogs findById(int id) {
        for (Catalogs catalogs : catalogsList) {
            if (catalogs.getCatalogId() == id) {
                return catalogs;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (Catalogs catalogs : catalogsList) {
            if (catalogs.getCatalogId() > idMax) {
                idMax = catalogs.getCatalogId();
            }
        }
        return (idMax + 1);
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_CATALOG, catalogsList);
    }

    @Override
    public void update(Catalogs catalogs) {
        Catalogs catalogsEdit = findById(catalogs.getCatalogId());
        catalogsEdit.setCatalogName(catalogs.getCatalogName());
        catalogsEdit.setDescription(catalogs.getDescription());
        catalogsEdit.setStatus(catalogs.isStatus());
        updateData();
    }

    @Override
    public List<Products> deleteProducts(int id) {
        return null;
    }

    @Override
    public List<Catalogs> findName(String name) {
        List <Catalogs> foundCatalogs = new ArrayList<>();
        for (Catalogs catalog : catalogsList){
           if (catalog.getCatalogName().toLowerCase().contains(name)){
               foundCatalogs.add(catalog);
           }
        }
        return catalogsList;
    }
}
