package auto.carrent;

import java.sql.Date;

public class CarData {

    private Integer carID;
    private String brand;
    private String model;
    private Double price;
    private String status;
    private Date date;

    private String image;

    public CarData(Integer carID, String brand, String model, Double price, String status, String image, Date date){
        this.carID = carID;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.status = status;
        this.image = image;
        this.date = date;
    }

    public Integer getCarID(){
        return carID;
    }

    public String getBrand(){
        return brand;
    }

    public String getModel(){
        return model;
    }

    public Double getPrice(){
        return price;
    }

    public String getStatus(){
        return status;
    }

    public Date getDate(){
        return date;
    }

    public String getImage(){
        return image;
    }



}
