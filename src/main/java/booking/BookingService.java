package booking;

import lombok.AllArgsConstructor;

import java.time.temporal.ChronoUnit;

@AllArgsConstructor
public class BookingService {
    private static final double BASE_PRICE_USD = 50.0;

    private final PaymentService paymentService;
    private final RoomService roomService;
    private final BookingDAO bookingDAO;
    private final MailSender mailSender;

    public int getAvailablePlaceCount() {
        return roomService.getAvailableRooms()
                .stream()
                .map(Room::getCapacity)
                .reduce(0, Integer::sum);
    }

    public double calculatePrice(BookingRequest bookingRequest) {
        long nights = ChronoUnit.DAYS.between(bookingRequest.getDateFrom(), bookingRequest.getDateTo());
        return BASE_PRICE_USD * bookingRequest.getGuestCount() * nights;
    }

    public double calculatePriceEuro(BookingRequest bookingRequest) {
        long nights = ChronoUnit.DAYS.between(bookingRequest.getDateFrom(), bookingRequest.getDateTo());
        return CurrencyConverter.toEuro(BASE_PRICE_USD * bookingRequest.getGuestCount() * nights);
    }

    public String makeBooking(BookingRequest bookingRequest) {
        String roomId = roomService.findAvailableRoomId(bookingRequest);
        double price = calculatePrice(bookingRequest);

        if (bookingRequest.isPrepaid()) {
            paymentService.pay(bookingRequest, price);
        }

        bookingRequest.setRoomId(roomId);
        String bookingId = bookingDAO.save(bookingRequest);
        roomService.bookRoom(roomId);
        mailSender.sendBookingConfirmation(bookingId);
        return bookingId;
    }

    public void cancelBooking(String id) {
        BookingRequest request = bookingDAO.get(id);
        roomService.unbookRoom(request.getRoomId());
        bookingDAO.delete(id);
    }
}
