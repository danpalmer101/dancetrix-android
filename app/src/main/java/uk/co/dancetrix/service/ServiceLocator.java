package uk.co.dancetrix.service;

import uk.co.dancetrix.service.impl.FirebaseClassService;
import uk.co.dancetrix.service.impl.FirebaseUniformService;
import uk.co.dancetrix.service.impl.MailgunEmailService;
import uk.co.dancetrix.service.mock.MockBookingService;
import uk.co.dancetrix.service.mock.MockClassService;
import uk.co.dancetrix.service.mock.MockPaymentService;
import uk.co.dancetrix.service.mock.MockUniformService;

public class ServiceLocator {

    // TODO - Implement Firebase implementations

    public static final ClassService CLASS_SERVICE = new FirebaseClassService();
    public static final UniformService UNIFORM_SERVICE = new FirebaseUniformService();
    public static final PaymentService PAYMENT_SERVICE = new MockPaymentService();
    public static final BookingService BOOKING_SERVICE = new MockBookingService();

    public static final EmailService EMAIL_SERVICE = new MailgunEmailService();

}
