package lt.viko.eif.mjurevicius.ndassignment1xmlserver.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lt.viko.eif.mjurevicius.ndassignment1xmlserver.model.Foodorder;
import lt.viko.eif.mjurevicius.ndassignment1xmlserver.model.Foodorders;

import java.io.File;
import java.util.ArrayList;

public class XMLTransformationService {

    public static void transform(Object object){
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(object, System.out);
        } catch (JAXBException e) {
            System.out.println(e.getLocalizedMessage() + e.getCause());
        }
    }

    public static void transform(Object object, String fileName){
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            if (!fileName.endsWith(".xml")) {
                fileName += ".xml";
            }
            File file = new File(fileName);
            marshaller.marshal(object, file);
        } catch (JAXBException e) {
            System.out.println(e.getLocalizedMessage() + e.getCause());
        }
    }

    public static void transformAppendFoodorders(Foodorder foodorder, String fileName){
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Foodorders.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            if (!fileName.endsWith(".xml")) {
                fileName += ".xml";
            }

            File file = new File(fileName);
            Foodorders foodorders = detransformFOold(fileName);

            boolean fileExists = file.exists();
            if(fileExists){
                int lastid = 0;
                if (!foodorders.getFoodorders().isEmpty()) {
                    for (Foodorder foodordr : foodorders.getFoodorders()) {
                        if (foodordr.getId() > lastid) {
                            lastid = foodordr.getId();
                        }
                    }
                    foodorder.setId(lastid + 1);
                }
                else
                {
                    foodorder.setId(0);
                }
            }

            foodorders.getFoodorders().add(foodorder);
            marshaller.marshal(foodorders, file);
        } catch (JAXBException e) {
            System.out.println(e.getLocalizedMessage() + e.getCause());
        }
    }

    public static Foodorders detransformFOold(String fileName){

        if (!fileName.endsWith(".xml")) {
            fileName += ".xml";
        }
        File file = new File(fileName);

        if (!file.exists()) {
            Foodorders nullorders = new Foodorders();
            nullorders.setFoodorders(new ArrayList<>());
            return nullorders;
        }

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Foodorders.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Foodorders foodorder = (Foodorders) unmarshaller.unmarshal(file);

            if (foodorder.getFoodorders() == null) {
                foodorder.setFoodorders(new ArrayList<>());
            }

            return foodorder;
        } catch (JAXBException e) {
            System.out.println(e.getLocalizedMessage() + e.getCause());
            System.out.println("Unmarshalling failed...");
            Foodorders failorders = new Foodorders();
            failorders.setFoodorders(new ArrayList<>());
            return failorders;
        }
    }

    public static Foodorders detransformFO(String fileName){

        if (!fileName.endsWith(".xml")) {
            fileName += ".xml";
        }
        File file = new File(fileName);

        if (!file.exists()) {
            Foodorders nullorders = new Foodorders();
            nullorders.setFoodorders(new ArrayList<>());
            return nullorders;
        }

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Foodorders.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Foodorders foodorder = (Foodorders) unmarshaller.unmarshal(file);

            if (foodorder.getFoodorders() == null) {
                foodorder.setFoodorders(new ArrayList<>());
            }

            System.out.println("I am TESTING THIS PART!: " + foodorder);

            return foodorder;
        } catch (JAXBException e) {
            System.out.println(e.getLocalizedMessage() + e.getCause());
            System.out.println("Unmarshalling failed...");
            Foodorders failorders = new Foodorders();
            failorders.setFoodorders(new ArrayList<>());
            return failorders;
        }
    }

    public static Foodorder detransformFoodorder(String fileName)
    {
        if (!fileName.endsWith(".xml")) {
            fileName += ".xml";
        }
        File file = new File(fileName);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Foodorder.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (Foodorder) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            System.out.println(e.getLocalizedMessage() + e.getCause());
            System.out.println("Unmarshalling failed...");
            return null;
        }
    }

    public static <T> T detransform(Class<T> tClass, String fileName){

        if (!fileName.endsWith(".xml")) {
            fileName += ".xml";
        }
        File file = new File(fileName);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(tClass);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (T) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            System.out.println(e.getLocalizedMessage() + e.getCause());
            System.out.println("Unmarshalling failed...");
            return null;
        }
    }
}
