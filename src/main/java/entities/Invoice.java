package entities;

import java.time.LocalDate;

public class Invoice extends Base {
    private long partnerID;
    private LocalDate date;
    private boolean release;

    public Invoice() { }

    public Invoice(long id, LocalDate date, long partnerID, boolean release) {
        super(id);
        this.date = date;
        this.partnerID = partnerID;
        this.release = release;
    }

    public long getPartnerID() {
        return partnerID;
    }
    public void setPartnerID(long partnerID) {
        this.partnerID = partnerID;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isRelease() {
        return release;
    }
    public void setRelease(boolean release) {
        this.release = release;
    }

    @Override
    public String toString() {
        String res = "Invoice " + id + "; Date: " + date + ";\n";
        if (isRelease())
            res += "Buyer: ";
        else
            res += "Seller: ";
        return res;
    }
}
