import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
class Db{
	java.sql.Statement st = null;
ResultSet rs = null;
			Connection con;
			Db(){
				try{
						con=DriverManager.getConnection("jdbc:sqlite:busreservation.db");
						con.setAutoCommit(true);
						System.out.println("Datbase created");
			
				}catch(Exception e){
						System.out.println(e);
}

}
void createTable(){
			try{
					String sql="CREATE TABLE IF NOT EXISTS 	USERS("+"ID integer,"+"username TEXT,"+"password TEXT)";
					con.createStatement().execute(sql);
						System.out.println("users table created ");
			}catch(Exception e){
					System.out.println(e);
			}
}
void createbusTable(){
    try{
        String sql = "CREATE TABLE IF NOT EXISTS BUS(" +
                     "ID INTEGER," +
                     "busname TEXT," +
                     "FROM_PLACE TEXT," +
                     "TO_PLACE TEXT," +
                     "TIME TEXT," +
                     "TOTALSEATS INTEGER," +
                     "AVAILABLE_SEATS INTEGER)";

        con.createStatement().execute(sql);
        System.out.println("bus table created");
    }
    catch(Exception e){
        System.out.println(e);
    }
}
void addbus(String busname, String from, String to, String time,
            int totalseats, int availableseats){

    try{
        String sql = "INSERT INTO BUS " +
                     "(busname,FROM_PLACE,TO_PLACE,TIME,TOTALSEATS,AVAILABLE_SEATS)" +
                     " VALUES('" + busname + "','" + from + "','" + to + "','" +
                     time + "'," + totalseats + "," + availableseats + ")";

        con.createStatement().executeUpdate(sql);

        System.out.println("bus added");
    }
    catch(Exception e){
        System.out.println(e);
    }
}
String searchbus(String from, String to){

    String result = "";
java.sql.Statement st = null;
ResultSet rs = null;
    try{
       String sql = "SELECT * FROM BUS WHERE " +
             "LOWER(FROM_PLACE)=LOWER('" + from + "') " +
             "AND LOWER(TO_PLACE)=LOWER('" + to + "')";

        st = con.createStatement();
rs = st.executeQuery(sql);
        while(rs.next()){
            result = result +
                     "Bus: " + rs.getString("busname") +
                     "\nTime: " + rs.getString("TIME") +
                     "\nAvailable seats: " +
                     rs.getInt("AVAILABLE_SEATS") +
                     "\n\n";
        }
    }
    catch(Exception e){
        System.out.println(e);
    }finally{
		try{
				if(rs!=null)rs.close();
				if(st!=null)st.close();
		}catch(Exception e){
			System.out.println(e);
		}
	}
		

    return result;
}
void adduser(String username,String password){
			try{
					String sql="INSERT INTO users (username ,password )VALUES('"+username+"','"+password+"')";
					con.createStatement().executeUpdate(sql);
						System.out.println("users added ");
			}catch(Exception e){
					System.out.println(e);
			}
}

boolean checkUser(String username,String password){
			try{
					String sql="SELECT * FROM users WHERE username='" +username+"'AND password ='"+password+"'";
					return con.createStatement().executeQuery(sql).next();
						
			}catch(Exception e){
					System.out.println(e);
					return false;
					
			}
}
void viewusers(){
		try{
				String sql ="SELECT * FROM users";
				ResultSet rs=con.createStatement().executeQuery(sql);
					while(rs.next()){
						System.out.println(
								rs.getInt("ID")+" "+
								rs.getString("username")+""+
								rs.getString("password")
								);
					}
		}catch(Exception e){
					System.out.println(e);
				
					
			}
}
void createbookingtable(){
				try{
					String sql="CREATE TABLE IF NOT EXISTS booking("+"bookingid INTEGER, "+"bus_name TEXT ,"+"seat_no TEXT,"+"passenger_name TEXT ,"+"age INTEGER,"+"phone TEXT ,"+"source TEXT ,"+"destination TEXT,"+"username TEXT)";
					con.createStatement().execute(sql);
					
					System.out.println("booking table created ");
				}
				catch (Exception e){
						System.out.println(e);
				}
}

void addUsernameColumn() {
    try {
        String sql = "ALTER TABLE booking ADD COLUMN username TEXT";
        con.createStatement().executeUpdate(sql);
        System.out.println("username column added");
    } catch (Exception e) {
        if (e.getMessage().contains("duplicate column name")) {
            System.out.println("username column already exists");
        } else {
            e.printStackTrace();
        }
    }
}

	

void saveBooking(String bus, String seat, String name, int age,
                 String phone, String from, String to, String username) {
    try {
        String sql = "INSERT INTO booking " +
                     "(bus_name, seat_no, passenger_name, age, phone, source, destination, username) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        java.sql.PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, bus);
        ps.setString(2, seat);
        ps.setString(3, name);
        ps.setInt(4, age);
        ps.setString(5, phone);
        ps.setString(6, from);
        ps.setString(7, to);
        ps.setString(8, username);
ps.executeUpdate();
ps.close();


System.out.println("Booking saved for: " + username);
    } catch (Exception e) {
		System.out.println("Booking error:");
        e.printStackTrace();
    }
}















}
public class Database{	
public static void main(String[] args){
			Db db=new Db();
			db.createTable();
			
		db.createbookingtable();
			
			db.viewusers();
		db.addUsernameColumn();
    
			db.createbusTable();
		db.addbus("KSRTC", "kochi", "thrissur", "8am", 40, 40);
db.addbus("Express Bus", "kochi", "thrissur", "9am", 35, 35);
db.addbus("Super Fast", "kochi", "thrissur", "10am", 40, 40);
db.addbus("Sleeper Bus", "kochi", "thrissur", "9pm", 30, 30);
db.addbus("Swift", "kochi", "thrissur", "11pm", 25, 25);

// Kochi to Kozhikode
db.addbus("KSRTC", "kochi", "kozhikode", "7am", 40, 40);
db.addbus("Super Fast", "kochi", "kozhikode", "8am", 40, 40);
db.addbus("Sleeper Bus", "kochi", "kozhikode", "9pm", 30, 30);
db.addbus("Swift", "kochi", "kozhikode", "10pm", 25, 25);

// Kochi to Trivandrum
db.addbus("KSRTC", "kochi", "trivandrum", "6am", 40, 40);
db.addbus("Express Bus", "kochi", "trivandrum", "7am", 35, 35);
db.addbus("Sleeper Bus", "kochi", "trivandrum", "8pm", 30, 30);
db.addbus("Swift", "kochi", "trivandrum", "9pm", 25, 25);

// Thrissur to Palakkad
db.addbus("KSRTC", "thrissur", "palakkad", "8am", 40, 40);
db.addbus("Super Fast", "thrissur", "palakkad", "9am", 40, 40);
db.addbus("Swift", "thrissur", "palakkad", "10am", 25, 25);

// Kozhikode to Kannur
db.addbus("KSRTC", "kozhikode", "kannur", "7am", 40, 40);
db.addbus("Express Bus", "kozhikode", "kannur", "8am", 35, 35);
db.addbus("Swift", "kozhikode", "kannur", "9am", 25, 25);

// Trivandrum to Kollam
db.addbus("KSRTC", "trivandrum", "kollam", "6am", 40, 40);
db.addbus("Super Fast", "trivandrum", "kollam", "7am", 40, 40);
db.addbus("Swift", "trivandrum", "kollam", "8am", 25, 25);

// Kochi to Kottayam
db.addbus("KSRTC", "kochi", "kottayam", "8am", 40, 40);
db.addbus("Express Bus", "kochi", "kottayam", "9am", 35, 35);
db.addbus("Swift", "kochi", "kottayam", "10am", 25, 25);

// Kannur to Wayanad
db.addbus("KSRTC", "kannur", "wayanad", "7am", 40, 40);
db.addbus("Super Fast", "kannur", "wayanad", "8am", 40, 40);
db.addbus("Sleeper Bus", "kannur", "wayanad", "9pm", 30, 30);
// Kochi to Kozhikode
db.addbus("KSRTC", "kochi", "kozhikode", "7am", 40, 40);
db.addbus("Super Fast", "kochi", "kozhikode", "8am", 40, 40);
db.addbus("Normal Bus", "kochi", "kozhikode", "9am", 40, 40);
db.addbus("Express Bus", "kochi", "kozhikode", "10am", 40, 40);
db.addbus("Private Bus", "kochi", "kozhikode", "11am", 40, 40);
db.addbus("Sleeper Bus", "kochi", "kozhikode", "9pm", 30, 30);
db.addbus("Swift", "kochi", "kozhikode", "10pm", 25, 25);

// Kochi to Trivandrum
db.addbus("KSRTC", "kochi", "trivandrum", "6am", 40, 40);
db.addbus("Normal Bus", "kochi", "trivandrum", "7am", 40, 40);
db.addbus("Express Bus", "kochi", "trivandrum", "8am", 40, 40);
db.addbus("Private Bus", "kochi", "trivandrum", "9am", 40, 40);
db.addbus("Super Fast", "kochi", "trivandrum", "10am", 40, 40);
db.addbus("Sleeper Bus", "kochi", "trivandrum", "9pm", 30, 30);
db.addbus("Swift", "kochi", "trivandrum", "10pm", 25, 25);


}
}
