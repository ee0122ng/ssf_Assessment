package ibf.swe.ssf.assessment.model;

public class Invoice {

    private String id;

    private String name;

    private String address;

    private Float total;

    public Invoice() {
    }

    public Invoice(String id, String name, String address, Float total) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.total = total;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Float getTotal() {
        return this.total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    
}
