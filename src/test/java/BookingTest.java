import booking.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class BookingTest {
    @InjectMocks
    private BookingService bookingService;
    @Mock
    private PaymentService paymentServiceMock;
    @Mock
    private RoomService roomServiceMock;
    @Spy
    private BookingDAO bookingDAOMock;
    @Mock
    private MailSender mailSenderMock;

    @Test
    void should_ThrowException_When_NoRoomAvailable() {
        // given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2022, 02, 18),
                LocalDate.of(2020, 02, 22), 2, false);
        when(this.roomServiceMock.findAvailableRoomId(bookingRequest))
                .thenThrow(BusinessException.class);

        // when then
        assertThatThrownBy(() -> {
            bookingService.makeBooking(bookingRequest);
        }).isInstanceOf(BusinessException.class);
    }


    @Test
    void should_MakeBooking_When_InputOK() {
        // given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2022, 02, 18),
                LocalDate.of(2022, 02, 22), 2, true);

        // when
        String bookingId = bookingService.makeBooking(bookingRequest);

        // then
        verify(bookingDAOMock).save(bookingRequest);
        System.out.println("bookingId=" + bookingId);
    }
}
