package lt.viko.eif.mjurevicius.ndassignment1xmlserver.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Foodorders {
    private List<Foodorder> foodorders = new ArrayList<>();

    public Foodorders() {
    }

    public List<Foodorder> getFoodorders() {
        return foodorders;
    }

    @XmlElementWrapper(name="foodorders")
    @XmlElement(name="foodorder")
    public void setFoodorders(List<Foodorder> foodorders) {
        this.foodorders = foodorders;
    }
}
