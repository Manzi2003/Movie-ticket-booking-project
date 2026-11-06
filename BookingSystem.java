public class BookingSystem {
    private static final int MAX_MOVIES = 20;
    private static final int MAX_BOOKINGS = 100;

    private Movie[] movies = new Movie[MAX_MOVIES];
    private int movieCount = 0;

    private Booking[] bookings = new Booking[MAX_BOOKINGS];
    private int bookingCount = 0;

    // ---------- Movies ----------

    public boolean addMovie(Movie movie) {
        if (movieCount >= MAX_MOVIES) {
            return false;  
        }
        movies[movieCount] = movie;
        movieCount++;
        return true;
    }

    public Movie[] getMovies() {
        Movie[] result = new Movie[movieCount];
        for (int i = 0; i < movieCount; i++) {
            result[i] = movies[i];
        }
        return result;
    }

    // ---------- Pricing ----------

    public double calculatePrice(Movie movie, String ticketType) {
        double base = movie.getPrice();
        switch (ticketType) {
            case "VIP":
                return base * 1.5;
            case "Student":
                return base * 0.8;
            case "Regular":
            default:
                return base;
        }
    }

    // ---------- Bookings ----------

    public Booking bookTicket(Movie movie, String seatNumber, String ticketType,
                              String name, String phone) {
        if (bookingCount >= MAX_BOOKINGS) {
            return null;
        }

        Seat seat = movie.findSeat(seatNumber);
        if (seat == null || seat.isBooked()) {
            return null;
        }

        Customer customer = new Customer(name, phone);
        double cost = calculatePrice(movie, ticketType);

        seat.setBooked(true);
        Booking booking = new Booking(customer, movie, seat, ticketType, cost);
        bookings[bookingCount] = booking;
        bookingCount++;
        return booking;
    }

    
    public boolean cancelBooking(int bookingId) {
        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getBookingId() == bookingId) {
                bookings[i].getSeat().setBooked(false);

                for (int j = i; j < bookingCount - 1; j++) {
                    bookings[j] = bookings[j + 1];
                }
                bookings[bookingCount - 1] = null;
                bookingCount--;
                return true;
            }
        }
        return false;
    }

    public Booking[] getBookings() {
        Booking[] result = new Booking[bookingCount];
        for (int i = 0; i < bookingCount; i++) {
            result[i] = bookings[i];
        }
        return result;
    }

    // ---------- Validation helper ----------

    public static boolean isValidPhone(String phone) {
        if (phone.length() < 9 || phone.length() > 13) {
            return false;
        }
        for (int i = 0; i < phone.length(); i++) {
            char c = phone.charAt(i);
            if (!Character.isDigit(c) && !(i == 0 && c == '+')) {
                return false;
            }
        }
        return true;
    }
}
