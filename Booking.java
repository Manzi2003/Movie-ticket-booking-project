/**
 * Links a customer, a movie and a seat together.
 */
public class Booking {
    private static int nextId = 1;   // shared counter so every booking gets a unique ID

    private int bookingId;
    private Customer customer;
    private Movie movie;
    private Seat seat;
    private String ticketType;
    private double totalCost;

    public Booking(Customer customer, Movie movie, Seat seat, String ticketType, double totalCost) {
        this.bookingId = nextId;
        nextId++;
        this.customer = customer;
        this.movie = movie;
        this.seat = seat;
        this.ticketType = ticketType;
        this.totalCost = totalCost;
    }

    public int getBookingId() { return bookingId; }
    public Customer getCustomer() { return customer; }
    public Movie getMovie() { return movie; }
    public Seat getSeat() { return seat; }
    public String getTicketType() { return ticketType; }
    public double getTotalCost() { return totalCost; }

    @Override
    public String toString() {
        return "#" + bookingId + " | " + customer.getName()
                + " | " + movie.getTitle() + " " + movie.getShowtime()
                + " | Seat " + seat.getSeatNumber()
                + " | " + ticketType
                + " | " + totalCost;
    }
}
