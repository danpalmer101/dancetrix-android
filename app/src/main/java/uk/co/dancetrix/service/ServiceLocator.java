package uk.co.dancetrix.service;

import uk.co.dancetrix.service.impl.EmailBookingService;
import uk.co.dancetrix.service.impl.EmailPaymentService;
import uk.co.dancetrix.service.impl.FirebaseClassService;
import uk.co.dancetrix.service.impl.FirebaseUniformService;
import uk.co.dancetrix.service.impl.MailgunEmailService;

public class ServiceLocator {

    public static final ClassService CLASS_SERVICE = new FirebaseClassService();
    public static final UniformService UNIFORM_SERVICE = new FirebaseUniformService();
    public static final PaymentService PAYMENT_SERVICE = new EmailPaymentService();
    public static final BookingService BOOKING_SERVICE = new EmailBookingService();

    public static final EmailService EMAIL_SERVICE = new MailgunEmailService();

}
