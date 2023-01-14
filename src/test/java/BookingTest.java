import booking.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;

public class BookingTest {
    @InjectMocks
    private BookingService bookingService;
    @Mock
    private PaymentService paymentServiceMock;
    @Mock
    private RoomService roomServiceMock;
    @Mock
    private BookingDAO bookingDAOMock;
    @Mock
    private MailSender mailSenderMock;

    @Test
    void should_CalculateCorrectPrice_When_CorrectInput() {
        // given
        PaymentService paymentService = new PaymentService();
        RoomService roomService = new RoomService();
        BookingDAO bookingDAO = new BookingDAO();
        MailSender mailSender = new MailSender();

        bookingService = new BookingService(paymentService, roomService, bookingDAO, mailSender);
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2022, 02, 18),
                LocalDate.of(2022, 02, 22), 2, false);
        double expected = 4 * 2 * 50.0;

        // when
        double actual = bookingService.calculatePrice(bookingRequest);

        // then
        assertThat(expected).isEqualTo(actual);
    }
}
