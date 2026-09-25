public class Vehicle {

    private int vehicleId;
    private String vehicleType;
    private String brand;
    private String status;

    public Vehicle(int vehicleId, String vehicleType, String brand, String status) {
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
        this.brand = brand;
        this.status = status;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getBrand() {
        return brand;
    }

    public String getStatus() {
        return status;
    }
}