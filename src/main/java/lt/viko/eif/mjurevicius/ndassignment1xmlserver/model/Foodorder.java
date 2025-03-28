package lt.viko.eif.mjurevicius.ndassignment1xmlserver.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lt.viko.eif.mjurevicius.ndassignment1xmlserver.utility.LocalDateTimeAdapter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "foodorder")
@XmlType(propOrder = {"id","tableNumber","date","status","baseSum", "totalSum", "foodItemlist"})
@XmlRootElement
public class Foodorder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    private int tableNumber;
    private LocalDateTime date;
    private String status;
    private float baseSum;
    private float totalSum;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Foodorder_Fooditem",
            joinColumns = @JoinColumn(name = "foodorder_id"),
            inverseJoinColumns = @JoinColumn(name = "fooditem_id")
    )
    private List<FoodItem> foodItemlist;

    public Foodorder() {
    }

    public Foodorder(int id, int tableNumber, float baseSum, float totalSum) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.date = LocalDateTime.now();
        this.status = "Waiting";
        this.baseSum = baseSum;
        this.totalSum = totalSum + baseSum;
    }

    public Foodorder(int tableNumber, List<FoodItem> foodItemlist) {
        this.tableNumber = tableNumber;
        this.foodItemlist = foodItemlist;
        this.date = LocalDateTime.now();
        this.status = "Waiting";
        this.baseSum = getPriceFromList(foodItemlist);
        this.totalSum = getPriceWithPVMFromList(foodItemlist);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public float getBaseSum() {
        return baseSum;
    }

    public void setBaseSum(float baseSum) {
        this.baseSum = baseSum;
    }

    public float getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(float totalSum) {
        this.totalSum = totalSum;
    }

    @XmlElementWrapper(name="fooditems")
    @XmlElement(name="fooditem")
    public List<FoodItem> getFoodItemlist() {
        return foodItemlist;
    }

    public void setFoodItemlist(List<FoodItem> foodItemlist) {
        this.foodItemlist = foodItemlist;
    }

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String tableToString;
        if (tableNumber == -1) tableToString = "Order to go";
        else tableToString = "Table number: " + tableNumber;
        return "Order:" +
                "\n\tid=" + id +
                "\n\tTable=" + tableToString +
                "\n\tDate=" + date.toString() +
                "\n\tFood Items:" + foodItemlist +
                "\n\tBase Sum=" + baseSum +
                "\n\tTotal Sum=" + totalSum +
                "\n\tStatus=" + status +
                "\n";
    }

    public float getPriceFromList (List<FoodItem> foodItemlist) {
        float baseSum = 0;
        for (FoodItem foodItem : foodItemlist) {
            baseSum += foodItem.getPrice();
        }
        return Math.round(baseSum * 100.0f) / 100.0f;
    }

    public float getPriceWithPVMFromList (List<FoodItem> foodItemlist) {
        float baseSum = 0;
        for (FoodItem foodItem : foodItemlist) {
            baseSum += (foodItem.getPrice() * (1 + foodItem.getPVM()));
        }
        return Math.round(baseSum * 100.0f) / 100.0f;
    }

    public String formName()
    {
        String returnMe = "order" + date.toString().replace(':','_') + id + totalSum;
        return returnMe;
    }

}
