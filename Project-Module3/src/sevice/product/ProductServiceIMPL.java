package sevice.product;

import ra.config.Config;
import ra.model.Products;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceIMPL implements IProductService {
    static Config <List<Products>> config = new Config();
    public static List<Products> productsList;

    static {
        productsList = config.readFile(Config.URL_PRODUCT);
        productsList = (productsList == null) ? new ArrayList<>() : productsList;
    }

    @Override
    public List<Products> findAll() {
        return productsList;
    }

    @Override
    public void save(Products products) {
      if (findById(products.getProductId()) == null) {
          productsList.add(products);
          updateData();
      }else {
          productsList.set(productsList.indexOf(products),products);
          updateData();
      }
    }

    @Override
    public void delete(int id) {
        Products productDelete = findById(id);
        productsList.remove(productDelete);
        updateData();
    }

    @Override
    public Products findById(int id) {
        for (Products products: productsList) {
            if (products.getProductId() == id){
                return products;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (Products products : productsList) {
            if (products.getProductId() > idMax){
                idMax = products.getProductId();
            }
        }
        return (idMax +1);
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_PRODUCT,productsList);
    }

    @Override
    public void update(Products products) {
        Products productEdit = findById(products.getProductId());
        productEdit.setProductName(products.getProductName());
        productEdit.setDescription(products.getDescription());
        productEdit.setUnitPrice(products.getUnitPrice());
        productEdit.setStock(products.getStock());
        productEdit.setCatalogs(products.getCatalogs());
        productEdit.setStatus(products.isStatus());
        updateData();

    }

    @Override
    public List<Products> deleteProducts(int id) {
        return null;
    }

    @Override
    public List<Products> findName(String name) {
        List<Products> foundProducts = new ArrayList<>();
        for (Products products : productsList) {
            if (products.getProductName().toLowerCase().contains(name)){
                foundProducts.add(products);
            }
        }
        return foundProducts;
    }

}
