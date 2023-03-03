package ibf.swe.ssf.assessment.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Customer {

    @NotBlank(message="Name is mandatory")
    @Size(min=2, message="Name must have at least 2 characters")
    private String name;

    @NotBlank(message="Address is mandatory")
    private String address;


    public Customer() {
    }

    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    
}
