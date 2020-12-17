package entities;

import java.time.LocalDate;

public class Price extends Base {
    private long productID;
    private double price;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;

    public Price() { }

    public Price(long id, long productID, double price, LocalDate startDate, LocalDate endDate, String description) {
        super(id);
        this.productID = productID;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public long getProductID() {
        return productID;
    }
    public void setProductID(long productID) {
        this.productID = productID;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
