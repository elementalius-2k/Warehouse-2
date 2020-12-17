package services;

public class DBLoader {
    private static boolean isLoad = false;

    public static void load() {
        if (!isLoad) {
            ManufacturerService.getInstance().createTable();
            GroupService.getInstance().createTable();
            PartnerService.getInstance().createTable();
            InvoiceService.getInstance().createTable();
            ProductService.getInstance().createTable();
            PriceService.getInstance().createTable();
            ItemService.getInstance().createTable();
            isLoad = true;
        }
    }
}
