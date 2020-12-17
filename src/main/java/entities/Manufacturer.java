package entities;

public class Manufacturer extends Base {
    private String name;
    private String address;

    public Manufacturer() { }

    public Manufacturer(long id, String name, String address) {
        super(id);
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Manufacturer: "  + name + ", address: " + address + ";";
    }
}
