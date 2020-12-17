package entities;

public class Partner extends Base {
    private String name;
    private String paymentAccount;
    private String address;
    private String email;

    public Partner() { }

    public Partner(long id, String name, String paymentAccount, String address, String email) {
        super(id);
        this.name = name;
        this.paymentAccount = paymentAccount;
        this.address = address;
        this.email = email;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPaymentAccount() {
        return paymentAccount;
    }
    public void setPaymentAccount(String paymentAccount) {
        this.paymentAccount = paymentAccount;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return name + ", payment account: " + paymentAccount + ", address: " + address + ", e-mail: " + email + ";\n";
    }
}
