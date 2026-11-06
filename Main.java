/*
 CSC 1102 Practical Exam Project: Movie Ticket Booking System
 Group leader: Okol Moses
 Group members: Ageno Abigail Nouelinah, Kayaga Gabriella Lyton, Omara Henry Muge, Manzi Daudi Dembe
 */
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        BookingSystem system = new BookingSystem();

        system.addMovie(new Movie("Clayface", "Horror, Sci-fi, Thriller", 108, "R-16", 25000, "13:15"));
        system.addMovie(new Movie("Clayface", "Horror, Sci-fi, Thriller", 108,"R-16", 25000, "19:15"));
        system.addMovie(new Movie("Forgotten Island", "Action, Adventure, Animation, Family", 106,"PG", 15000, "11:00"));
        system.addMovie(new Movie("Forgotten Island", "Action, Adventure, Animation, Family", 106,"PG", 15000, "13:00"));
        system.addMovie(new Movie("Other Mommy", "Horror, Thriller", 93,"R-16", 18000, "19:45"));
        system.addMovie(new Movie("Other Mommy", "Horror, Thriller", 93,"R-16", 18000, "22:00"));
        system.addMovie(new Movie("Street-fighter", "Action", 119,"R-16", 22000, "12:30"));
        system.addMovie(new Movie("Street-fighter", "Action", 119,"R-16", 22000, "15:15"));

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(system);
            frame.setVisible(true);
        });
    }
}
