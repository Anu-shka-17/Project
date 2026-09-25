import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class VehicleRentalSystem {

    static Scanner sc = new Scanner(System.in);

    // ADD VEHICLE
    public static void addVehicle() {

        try {

            Connection con = DbConnection.getConnection();

            System.out.print("Enter Vehicle ID: ");
            int vehicleId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Vehicle Type: ");
            String vehicleType = sc.nextLine();

            System.out.print("Enter Brand: ");
            String brand = sc.nextLine();

            String sql = "INSERT INTO vehicle " +
                         "(vehicle_id, vehicle_type, brand, status) " +
                         "VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, vehicleId);
            ps.setString(2, vehicleType);
            ps.setString(3, brand);
            ps.setString(4, "Available");

            ps.executeUpdate();

            System.out.println("Vehicle Added Successfully!");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // VIEW VEHICLES
    public static void viewVehicles() {

        try {

            Connection con = DbConnection.getConnection();

            String sql = "SELECT * FROM vehicle";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println("\nVEHICLE DETAILS");

            while (rs.next()) {

                System.out.println(
                    rs.getInt("vehicle_id") + " | " +
                    rs.getString("vehicle_type") + " | " +
                    rs.getString("brand") + " | " +
                    rs.getString("status")
                );
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // SEARCH VEHICLE
    public static void searchVehicle() {

        try {

            Connection con = DbConnection.getConnection();

            System.out.print("Enter Vehicle ID: ");
            int vehicleId = sc.nextInt();
            sc.nextLine();

            String sql = "SELECT * FROM vehicle WHERE vehicle_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, vehicleId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("\nVehicle Found!");

                System.out.println(
                    "Vehicle ID : " +
                    rs.getInt("vehicle_id")
                );

                System.out.println(
                    "Type       : " +
                    rs.getString("vehicle_type")
                );

                System.out.println(
                    "Brand      : " +
                    rs.getString("brand")
                );

                System.out.println(
                    "Status     : " +
                    rs.getString("status")
                );

            } else {

                System.out.println("Vehicle Not Found!");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // RENT VEHICLE
    public static void rentVehicle() {

        try {

            Connection con = DbConnection.getConnection();

            System.out.print("Enter Customer ID: ");
            int customerId = sc.nextInt();

            System.out.print("Enter Vehicle ID: ");
            int vehicleId = sc.nextInt();
            sc.nextLine();

            // Check vehicle status

            String checkSql =
                "SELECT status FROM vehicle WHERE vehicle_id=?";

            PreparedStatement checkPs =
                con.prepareStatement(checkSql);

            checkPs.setInt(1, vehicleId);

            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {

                String status = rs.getString("status");

                if (status.equals("Available")) {

                    // Add rental

                    String sql =
                        "INSERT INTO rental " +
                        "(customer_id, vehicle_id, rent_date, status) " +
                        "VALUES (?, ?, CURDATE(), ?)";

                    PreparedStatement ps =
                        con.prepareStatement(sql);

                    ps.setInt(1, customerId);
                    ps.setInt(2, vehicleId);
                    ps.setString(3, "Rented");

                    ps.executeUpdate();

                    // Change vehicle status

                    String updateSql =
                        "UPDATE vehicle SET status=? " +
                        "WHERE vehicle_id=?";

                    PreparedStatement updatePs =
                        con.prepareStatement(updateSql);

                    updatePs.setString(1, "Rented");
                    updatePs.setInt(2, vehicleId);

                    updatePs.executeUpdate();

                    System.out.println(
                        "Vehicle Rented Successfully!"
                    );

                } else {

                    System.out.println(
                        "Vehicle is already Rented!"
                    );
                }

            } else {

                System.out.println("Vehicle Not Found!");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // RETURN VEHICLE
    public static void returnVehicle() {

        try {

            Connection con = DbConnection.getConnection();

            System.out.print("Enter Vehicle ID: ");
            int vehicleId = sc.nextInt();
            sc.nextLine();

            String sql =
                "UPDATE rental SET return_date=CURDATE(), " +
                "status=? WHERE vehicle_id=? AND status=?";

            PreparedStatement ps =
                con.prepareStatement(sql);

            ps.setString(1, "Returned");
            ps.setInt(2, vehicleId);
            ps.setString(3, "Rented");

            int rows = ps.executeUpdate();

            if (rows > 0) {

                String updateSql =
                    "UPDATE vehicle SET status=? " +
                    "WHERE vehicle_id=?";

                PreparedStatement updatePs =
                    con.prepareStatement(updateSql);

                updatePs.setString(1, "Available");
                updatePs.setInt(2, vehicleId);

                updatePs.executeUpdate();

                System.out.println(
                    "Vehicle Returned Successfully!"
                );

            } else {

                System.out.println(
                    "Vehicle is not currently rented!"
                );
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // VIEW RENTAL HISTORY
    public static void rentalHistory() {

        try {

            Connection con = DbConnection.getConnection();

            String sql =
                "SELECT * FROM rental";

            PreparedStatement ps =
                con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println("\nRENTAL HISTORY");

            while (rs.next()) {

                System.out.println(
                    rs.getInt("rental_id") + " | " +
                    rs.getInt("customer_id") + " | " +
                    rs.getInt("vehicle_id") + " | " +
                    rs.getDate("rent_date") + " | " +
                    rs.getDate("return_date") + " | " +
                    rs.getString("status")
                );
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {

        while (true) {

            System.out.println(
                "\n===== VEHICLE RENTAL SYSTEM ====="
            );

            System.out.println("1. Add Vehicle");
            System.out.println("2. View Vehicles");
            System.out.println("3. Search Vehicle");
            System.out.println("4. Rent Vehicle");
            System.out.println("5. Return Vehicle");
            System.out.println("6. View Rental History");
            System.out.println("7. Exit");

            System.out.print("Enter Your Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    addVehicle();
                    break;

                case 2:
                    viewVehicles();
                    break;

                case 3:
                    searchVehicle();
                    break;

                case 4:
                    rentVehicle();
                    break;

                case 5:
                    returnVehicle();
                    break;

                case 6:
                    rentalHistory();
                    break;

                case 7:
                    System.out.println("Thank You!");
                    return;

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}