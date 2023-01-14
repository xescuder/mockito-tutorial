package booking;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BookingDAO {
    private final Map<String, BookingRequest> bookings = new HashMap<>();

    public String save(BookingRequest bookingRequest) {
        String id = UUID.randomUUID().toString();
        bookings.put(id, bookingRequest);
        return id;
    }

    public BookingRequest get(String id) {
		return bookings.get(id);
    }

    public void delete(String bookingId) {
		bookings.remove(bookingId);
    }
}
