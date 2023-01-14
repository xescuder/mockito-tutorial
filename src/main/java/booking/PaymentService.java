package booking;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class PaymentService {
    private final Map<String, Double> payments = new HashMap<>();

    public String pay(BookingRequest bookingRequest, double price) {
        if (price > 200.0 && bookingRequest.getGuestCount() < 3) {
            throw new UnsupportedOperationException("Only small payments are supported.");
        }
        String id = UUID.randomUUID().toString();
        payments.put(id, price);
        return id;
    }
}
