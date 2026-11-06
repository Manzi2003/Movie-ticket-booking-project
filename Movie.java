/**
 * Represents a movie showing. Each movie has its own array of seats.
 */
public class Movie {
    private String title;
    private String genre;
    private int duration;      // in minutes
    private String rating;
    private double price;      // base (Regular) ticket price
    private String showtime;
    private Seat[] seats;

    public Movie(String title, String genre, int duration, String rating, double price, String showtime) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.rating = rating;
        this.price = price;
        this.showtime = showtime;
        createSeats();
    }

    // Creates 3 rows (A, B, C) with 5 seats each = 15 seats
    private void createSeats() {
        String rows = "ABC";
        seats = new Seat[15];
        int index = 0;
        for (int r = 0; r < rows.length(); r++) {
            for (int n = 1; n <= 5; n++) {
                seats[index] = new Seat("" + rows.charAt(r) + n);
                index++;
            }
        }
    }

    // Finds a seat by its number (e.g. "A3"). Returns null if not found.
    public Seat findSeat(String seatNumber) {
        int i = 0;
        while (i < seats.length) {
            if (seats[i].getSeatNumber().equalsIgnoreCase(seatNumber)) {
                return seats[i];
            }
            i++;
        }
        return null;
    }

    public int countAvailableSeats() {
        int count = 0;
        for (int i = 0; i < seats.length; i++) {
            if (!seats[i].isBooked()) {
                count++;
            }
        }
        return count;
    }

    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getShowtime() { return showtime; }
    public void setShowtime(String showtime) { this.showtime = showtime; }

    public Seat[] getSeats() { return seats; }

    @Override
    public String toString() {
        return title + " - " + rating + " (" + showtime + ")";
    }
}
