package ra.model;

import java.io.Serializable;

public class Catalogs implements Serializable {
    private static final long serialVersionUID = 1L;
    private int catalogId;
    private String catalogName;
    private String description;
    private boolean status = true;


    public Catalogs() {
    }

    public Catalogs(int catalogId,String catalogName, String description, boolean status) {
        this.catalogId = catalogId;
        this.catalogName = catalogName;
        this.description = description;
        this.status = status;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Danh mục: " +
                "Mã danh mục: " + catalogId +
                ", Tên danh mục: '" + catalogName + '\'' +
                ", Mô tả: '" + description + '\'' +
                ", Trạng thái: " + (status ? "Mở" : "Đóng");
    }
}
