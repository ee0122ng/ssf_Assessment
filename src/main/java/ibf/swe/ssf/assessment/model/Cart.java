package ibf.swe.ssf.assessment.model;

import ibf.swe.ssf.assessment.validation.Stock;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Cart {

    @Stock
    private String item;

    @NotNull
    @Min(value=1, message="You must add at least 1 item")
    private Integer quantity;


    public Cart() {
    }

    public Cart(String item, Integer quantity) {
        this.item = item;
        this.quantity = quantity;
    }


    public String getItem() {
        return this.item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "{" +
            " item='" + getItem() + "'" +
            ", quantity='" + getQuantity() + "'" +
            "}";
    }


    
}
