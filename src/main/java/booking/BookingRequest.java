package booking;
import lombok.*;
import java.time.LocalDate;

@Data
public class BookingRequest {
    @NonNull
    private final String userId;
    @NonNull
    private final LocalDate dateFrom;
    @NonNull
    private final LocalDate dateTo;
    @NonNull
    private final int guestCount;
    @NonNull
    private final boolean prepaid;
    @Getter @Setter private String roomId;
}
