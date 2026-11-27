package InterfaceAdapter.ViewModel;

import java.math.BigDecimal;

public class OrderViewModel {
    private boolean success = false;
    private String company = "Error";
    private int quantity = 0;
    private BigDecimal price = BigDecimal.ZERO;
    private BigDecimal cashBalance = BigDecimal.ZERO;
    private String detailText;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(BigDecimal cashBalance) {
        this.cashBalance = cashBalance;
    }

    public String getDetailsText() {
        return detailText;
    }

    public void setDetailsText(String detailText) {
        this.detailText = detailText;
    }
}