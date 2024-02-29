package view.admin;

import ra.config.Validate;
import ra.model.Catalogs;
import sevice.catalog.CatalogServiceIMPL;
import sevice.catalog.ICatalogService;

import static ra.config.Color.*;

public class CatalogManagerment  {
    ICatalogService catalogService = new CatalogServiceIMPL();

    public void menuCatalog() {
        int choice;
        do {
//            System.out.println("\nXin chao: "+ Home.userLogin.getName());
            System.out.println(LIGHT_CYAN + "╔═════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                      "+ORANGE_2+"MENU CATALOG"+LIGHT_CYAN+"                                   ║");
            System.out.println("╠════╦════════════════════════════════════════════════════════════════════════════════╣");
            System.out.println("║  1 ║                            "+WHITE_BOLD_BRIGHT+"Hiển thị danh sách Danh mục"+LIGHT_CYAN+"                         ║");
            System.out.println("╠════╬════════════════════════════════════════════════════════════════════════════════╣");
            System.out.println("║  2 ║                           "+WHITE_BOLD_BRIGHT+"Tạo mới danh mục"+LIGHT_CYAN+"                                     ║");
            System.out.println("╠════╬════════════════════════════════════════════════════════════════════════════════╣");
            System.out.println("║  3 ║                           "+WHITE_BOLD_BRIGHT+"Tìm kiếm danh mục theo tên"+LIGHT_CYAN+"                           ║");
            System.out.println("╠════╬════════════════════════════════════════════════════════════════════════════════╣");
            System.out.println("║  4 ║                          "+WHITE_BOLD_BRIGHT+"Chỉnh sửa thông tin danh mục"+LIGHT_CYAN+"                          ║");
            System.out.println("╠════╬════════════════════════════════════════════════════════════════════════════════╣");
            System.out.println("║  5 ║                          "+WHITE_BOLD_BRIGHT+"Ẩn danh mục theo mã danh mục"+LIGHT_CYAN+"                          ║");
            System.out.println("╠════╬════════════════════════════════════════════════════════════════════════════════╣");
            System.out.println("║  6 ║                          "+WHITE_BOLD_BRIGHT+"Quay lại"+LIGHT_CYAN+"                                              ║");
            System.out.println("╚════╩════════════════════════════════════════════════════════════════════════════════╝"+RESET);


            System.out.print(YELLOW + "Lựa chọn (0/1/2): " + RESET);
            choice = Validate.validateInt();
            switch (choice) {
                case 1:
                    showCatalog();
                    break;
                case 2:
                    addCatalog();
                    break;
                case 3:
                    seachCatalog();
                    break;
                case 4:
                    updateCatalog();
                    break;
                case 5:
                    hideCatalog();
                    break;
                case 6:
                    return;
                default:
                    System.out.println(RED + "lựa chọn không hợp lệ vui lòng nhập lại" + RESET);
                    break;
            }


        } while (true);
    }

    private void hideCatalog() {
        System.out.println("Nhập ID cần ẩn/ mở lại");
        int catalogId = Validate.validateInt();
        Catalogs catalogs = catalogService.findById(catalogId);
        if (catalogs != null) {
            System.out.println("1. Ẩn danh mục");
            System.out.println("2. Mở lại danh mục");
            int choice = Validate.validateInt();
            switch (choice) {
                case 1:
                    if (catalogs.isStatus()) {
                        catalogs.setStatus(false);
                        catalogService.update(catalogs);
                        System.out.println("Danh mục được ẩn thành công");
                    } else {
                        System.out.println("Danh mục đã được ấn trước đó");
                    }
                    break;
                case 2:
                    if (!catalogs.isStatus()) {
                        catalogs.setStatus(true);
                        catalogService.update(catalogs);
                        System.out.println("Dạnh mục đã được mở lại thành công");
                    } else {
                        System.out.println("Danh mục đã được mở lai trước đó");
                    }
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
                    break;
            }
        }else {
            System.out.println("Không tìm thấy id vừa nhập");
        }
    }

    private void updateCatalog() {
        System.out.println("Nhập ID danh mục cần thay đổi");
        int idEdit = Validate.validateInt();
        Catalogs catalogsEdit = catalogService.findById(idEdit);
        if(catalogsEdit != null) {
            System.out.println("1.Sửa tên danh mục");
            System.out.println("2.Sửa  mô tả");
            int choice = Validate.validateInt();
            switch (choice) {
                case 1:
                    System.out.println("Nhập tên mới");
                    catalogsEdit.setCatalogName(Validate.validateString());
                    catalogService.updateData();
                    System.out.println("Sửa tên danh mục thành công");
                    break;
                case 2:
                    System.out.println("Sửa mô tả danh mục");
                    catalogsEdit.setDescription(Validate.validateString());
                    catalogService.updateData();
                    System.out.println("Sửa mô tả danh mục thành công");
                    break;
                default:
                    System.out.println("Không tìm thấy id vừa nhập vào");
            }
        }else {
            System.out.println(RED+"không tim thấy id vừa nhập"+RESET);
        }
    }

    private void seachCatalog() {
        System.out.println("Nhập tên danh mục muốn tìm kiếm");
        String seach = Validate.validateString().toLowerCase();
        int count = 0;
        System.out.println("Danh mục cần tìm: ");
        for (Catalogs catalogs : catalogService.findAll()) {
            if (catalogs.getCatalogName().toLowerCase().contains(seach)) {
                System.out.println(catalogs);
                count++;
            }
        }
        System.out.println("Tìm thấy danh mục theo từ khóa vừa nhập là " + count);
        System.out.println();
    }

    private void showCatalog() {
            System.out.println("Tất cả danh mục");
            for (Catalogs catalogs : catalogService.findAll()) {
                System.out.println(catalogs);
            }
    }

    private void addCatalog() {
        System.out.println("Nhập số lượng danh mục cần thêm");
        int n = Validate.validateInt();
        for (int i = 0; i < n; i++) {
            System.out.println("Sản phẩm thứ " + (i + 1) + " ");
            Catalogs catalogs = new Catalogs();
            catalogs.setCatalogId(catalogService.getNewId());
            System.out.println("Nhập tên danh mục");
            catalogs.setCatalogName(Validate.validateString());
            System.out.println("Nhập mô tả danh mục");
            catalogs.setDescription(Validate.validateString());
            System.out.println("Thêm mới Danh mục thành công");
            catalogService.save(catalogs);
        }
    }
}












