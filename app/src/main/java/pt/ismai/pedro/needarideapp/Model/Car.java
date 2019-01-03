package pt.ismai.pedro.needarideapp.Model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Car")
public class Car extends ParseObject {

    private String brand;
    private String model;
    private String seats;
    private String plate;
    private boolean canSmoke;
    private boolean canTakePets;
    private String user_id;

    public Car(){

        super();
    }

    public Car(String brand, String model, String seats, String plate, boolean can_smoke, boolean can_take_pets, String user_id) {

        this.brand = brand;
        this.model = model;
        this.seats = seats;
        this.canSmoke = can_smoke;
        this.plate = plate;
        this.canTakePets = can_take_pets;
        this.user_id = user_id;

    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getSeat() {
        return seats;
    }

    public String getPlate() {
        return plate;
    }

    public boolean isCanSmoke() {
        return canSmoke;
    }

    public boolean isCanTakePets() {
        return canTakePets;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setSeat(String seat) {
        this.seats = seat;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public void setCanSmoke(boolean canSmoke) {
        this.canSmoke = canSmoke;
    }

    public void setCanTakePets(boolean canTakePets) {
        this.canTakePets = canTakePets;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
