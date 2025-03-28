package lt.viko.eif.mjurevicius.ndassignment1xmlserver.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlType;

@Entity
@Table(name = "fooditem")
@XmlType(propOrder = {"id", "name", "price", "PVM"})
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    private String name;
    private Float price;
    private Float PVM;

    public FoodItem() {
    }

    public FoodItem(int id, String name, Float price, Float PVM) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.PVM = PVM;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getPVM() {
        return PVM;
    }

    public void setPVM(Float PVM) {
        this.PVM = PVM;
    }

    @Override
    public String toString() {
        return "FoodItem:" +
                "\n\tid=" + id +
                "\n\tname=" + name +
                "\n\tprice=" + price +
                "€\n";
    }

    public String toStringForClient() {
        float correctedPrice = Math.round(price * (1 + PVM) * 100.0f) /100.0f;
        return name + " - " + correctedPrice + "€\n";
    }

}
