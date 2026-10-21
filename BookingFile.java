
import java.io.FileWriter;
import java.io.IOException;

class BookingFile {

    static boolean saveBooking(String bus, String seat,
            String name, int age, String phone,
            String from, String to, String username) {

        try (FileWriter fw =
                new FileWriter("bookings.txt", true)) {

            fw.write("Username: " + username + "\n");
            fw.write("Bus: " + bus + "\n");
            fw.write("Seat: " + seat + "\n");
            fw.write("Passenger: " + name + "\n");
            fw.write("Age: " + age + "\n");
            fw.write("Phone: " + phone + "\n");
            fw.write("From: " + from + "\n");
            fw.write("To: " + to + "\n");
            fw.write("--------------------------\n");

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
