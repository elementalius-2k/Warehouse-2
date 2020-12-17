package entities;

public class Product extends Base {
    private String name;
    private int quantity;
    private long groupID;
    private long manufacturerID;

    public Product() { }

    public Product(long id, String name, int quantity, long groupID, long manufacturerID) {
        super(id);
        this.name = name;
        this.quantity = quantity;
        this.groupID = groupID;
        this.manufacturerID = manufacturerID;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getGroupID() {
        return groupID;
    }
    public void setGroupID(long groupID) {
        this.groupID = groupID;
    }

    public long getManufacturerID() {
        return manufacturerID;
    }
    public void setManufacturerID(long manufacturerID) {
        this.manufacturerID = manufacturerID;
    }

    @Override
    public String toString() {
        return "Product: " + name + ", quantity: " + quantity + ";\n";
    }
}
