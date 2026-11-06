import javax.swing.*;
import java.awt.*;


public class MainFrame extends JFrame {
    private BookingSystem system;

    // Movies tab
    private JTextArea moviesArea;

    // Book tab
    private JComboBox<Movie> movieBox;
    private JComboBox<String> seatBox;
    private JComboBox<String> typeBox;
    private JTextField nameField;
    private JTextField phoneField;

    // Bookings tab
    private JTextArea bookingsArea;
    private JTextField cancelIdField;

    public MainFrame(BookingSystem system) {
        this.system = system;

        setTitle("Movie Ticket Booking System");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Movies", buildMoviesPanel());
        tabs.addTab("Book Ticket", buildBookPanel());
        tabs.addTab("Bookings", buildBookingsPanel());
        add(tabs);

        refreshMovies();
        refreshSeats();
        refreshBookings();
    }

    // ---------- Movies tab ----------
    private JPanel buildMoviesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        moviesArea = new JTextArea();
        moviesArea.setEditable(false);
        panel.add(new JScrollPane(moviesArea), BorderLayout.CENTER);
        return panel;
    }

    private void refreshMovies() {
        Movie[] movies = system.getMovies();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < movies.length; i++) {
            Movie m = movies[i];
            sb.append(m.getTitle()).append("\n")
              .append("   Genre: ").append(m.getGenre())
              .append(" | ").append(m.getDuration()).append(" min")
              .append(" | Rating: ").append(m.getRating())
              .append(" | Showtime: ").append(m.getShowtime())
              .append(" | Price: ").append(m.getPrice())
              .append(" | Seats left: ").append(m.countAvailableSeats())
              .append("\n\n");
        }
        moviesArea.setText(sb.toString());
    }

    // ---------- Book tab ----------
    private JPanel buildBookPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        movieBox = new JComboBox<Movie>(system.getMovies());
        seatBox = new JComboBox<String>();
        typeBox = new JComboBox<String>(new String[]{"Regular", "VIP", "Student"});
        nameField = new JTextField();
        phoneField = new JTextField();
        JButton bookButton = new JButton("Book Ticket");

        // when the movie changes, update the list of free seats
        movieBox.addActionListener(e -> refreshSeats());
        bookButton.addActionListener(e -> handleBooking());

        panel.add(new JLabel("Movie:"));        panel.add(movieBox);
        panel.add(new JLabel("Seat:"));         panel.add(seatBox);
        panel.add(new JLabel("Ticket type:"));  panel.add(typeBox);
        panel.add(new JLabel("Customer name:")); panel.add(nameField);
        panel.add(new JLabel("Phone number:")); panel.add(phoneField);
        panel.add(new JLabel(""));              panel.add(bookButton);
        return panel;
    }

    // Fill the seat dropdown with only the seats that are still free
    private void refreshSeats() {
        seatBox.removeAllItems();
        Movie selected = (Movie) movieBox.getSelectedItem();
        if (selected == null) {
            return;
        }
        Seat[] seats = selected.getSeats();
        for (int i = 0; i < seats.length; i++) {
            if (!seats[i].isBooked()) {
                seatBox.addItem(seats[i].getSeatNumber());
            }
        }
    }

    private void handleBooking() {
        Movie movie = (Movie) movieBox.getSelectedItem();
        String seat = (String) seatBox.getSelectedItem();
        String type = (String) typeBox.getSelectedItem();
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();

        if (movie == null || seat == null) {
            JOptionPane.showMessageDialog(this, "No seat available for this movie.");
            return;
        }
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the customer's name.");
            return;
        }
        if (!BookingSystem.isValidPhone(phone)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid phone number (digits only).");
            return;
        }

        Booking booking = system.bookTicket(movie, seat, type, name, phone);
        if (booking == null) {
            JOptionPane.showMessageDialog(this, "Booking failed. The seat may already be taken.");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Booked!\n" + booking.toString());
            nameField.setText("");
            phoneField.setText("");
        }
        refreshSeats();
        refreshMovies();
        refreshBookings();
    }

    // ---------- Bookings tab ----------
    private JPanel buildBookingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        bookingsArea = new JTextArea();
        bookingsArea.setEditable(false);
        panel.add(new JScrollPane(bookingsArea), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        cancelIdField = new JTextField(5);
        JButton cancelButton = new JButton("Cancel Booking");
        cancelButton.addActionListener(e -> handleCancel());
        bottom.add(new JLabel("Booking ID:"));
        bottom.add(cancelIdField);
        bottom.add(cancelButton);
        panel.add(bottom, BorderLayout.SOUTH);
        return panel;
    }

    private void refreshBookings() {
        Booking[] bookings = system.getBookings();
        if (bookings.length == 0) {
            bookingsArea.setText("No bookings yet.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bookings.length; i++) {
            sb.append(bookings[i].toString()).append("\n");
        }
        bookingsArea.setText(sb.toString());
    }

    private void handleCancel() {
        int id;
        try {
            id = Integer.parseInt(cancelIdField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid booking ID (a number).");
            return;
        }

        if (system.cancelBooking(id)) {
            JOptionPane.showMessageDialog(this, "Booking #" + id + " cancelled.");
            cancelIdField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "No booking found with ID " + id + ".");
        }
        refreshSeats();
        refreshMovies();
        refreshBookings();
    }
}
