import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
public class StudentManagement {
    
    static Scanner sc=new Scanner(System.in);

    //ADD STUDENT
    public static void addStudent(){
        try{
            
            Connection con=DbConnection.getConnection();

            System.out.print("Enter Name:");
            String name=sc.nextLine();

            System.out.print("Enter Register Number:");
            String regnum=sc.nextLine();

            System.out.print("Enter Department:");
            String dept=sc.nextLine();

            System.out.print("Enter Address:");
            String address=sc.nextLine();

            System.out.print("Enter Mark:");
            double mark=sc.nextDouble();
            sc.nextLine();

            String sql="INSERT INTO student"+
            "(name,regnum,dept,address,mark)"+
            "VALUES(?,?,?,?,?)";

            PreparedStatement ps=con.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,regnum);
            ps.setString(3,dept);
            ps.setString(4,address);
            ps.setDouble(5,mark);

            ps.executeUpdate();
            System.out.println("StudentAdded Successfully!");

            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void viewstudents(){
        try{
            Connection con=DbConnection.getConnection();
            String sql="SELECT*FROM student";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            System.out.println("STUDENT DETAILS");

            while(rs.next()){

                System.out.println(
                    rs.getInt("id")+"|"+
                    rs.getString("name")+ "|"+
                    rs.getString("regnum")+"|"+
                    rs.getString("dept")+"|"+
                    rs.getString("address")+"|"+
                    rs.getDouble("mark"));
            }
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void searchStudent(){
        try{

            Connection con=DbConnection.getConnection();

            System.out.print("Enter Register Number:");
            String regnum=sc.nextLine();

            String sql="SELECT*FROM student WHERE regnum=?";

            PreparedStatement ps=con.prepareStatement(sql);

            ps.setString(1,regnum);
            ResultSet rs=ps.executeQuery();

            if(rs.next()){

                System.out.println("\n Student found!");

                System.out.println("ID        :"+rs.getInt("id"));

                System.out.println("Name       :"+ rs.getString("name"));

                System.out.println("Reg No    :"+rs.getString("regnum"));

                System.out.println("Dept    :"+rs.getString("dept"));

                System.out.println("Address    :"+rs.getString("address"));

                System.out.println("Mark    :"+rs.getDouble("mark"));
            
            }else{
                System.out.println("Student Not Found");
            }
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void updateStudent(){

        try{

            Connection con=DbConnection.getConnection();

            System.out.print("Enter Register Number:");
            String regnum=sc.nextLine();

            System.out.print("Enter New Mark;");
            double mark=sc.nextDouble();
            sc.nextLine();

            String sql="UPDATE student SET mark= ? WHERE regnum =";

            PreparedStatement ps=con.prepareStatement(sql);

            ps.setDouble(1,mark);
            ps.setString(2,regnum);

            int rows=ps.executeUpdate();

            if(rows>0){
                System.out.println("student Updated Successfully!");
            }else{
                System.out.println("Student Not Found!");
            }

            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void deleteStudent(){

        try{
            Connection con= DbConnection.getConnection();

            System.out.print("Enter register Number:");
            String regnum=sc.nextLine();

            String sql="DELETE FROM student WHERE regnum=?";

            PreparedStatement ps=con.prepareStatement(sql);
        
        ps.setString(1,regnum);
        
        int rows=ps.executeUpdate();

        if(rows>0){
            System.out.println("Student deleted Successfully!");
        }else{
            System.out.println("Student Not found!");
        }
        con.close();
    }catch(Exception e){
        e.printStackTrace();
    }
    }

    public static void main(String[] args){
         
        while(true){

            System.out.println("STUDENT MANAGEMENT SYSTEM");

            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. update Student Mark");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");

            System.out.print("Enter Your Choice:");

            int choice=sc.nextInt();
            sc.nextLine();

            switch(choice){

                case 1:
                    addStudent();
                    break;

                case 2:
                    viewstudents();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    updateStudent();
                    break;

                case 5:
                    deleteStudent();
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
    
}
