package uk.co.dancetrix.service;

import uk.co.dancetrix.BuildConfig;
import uk.co.dancetrix.service.impl.EmailBookingService;
import uk.co.dancetrix.service.impl.EmailPaymentService;
import uk.co.dancetrix.service.impl.EmailRegistrationService;
import uk.co.dancetrix.service.impl.FirebaseClassService;
import uk.co.dancetrix.service.impl.FirebaseUniformService;
import uk.co.dancetrix.service.impl.MailgunEmailService;
import uk.co.dancetrix.service.mock.MockBookingService;
import uk.co.dancetrix.service.mock.MockClassService;
import uk.co.dancetrix.service.mock.MockEmailService;
import uk.co.dancetrix.service.mock.MockPaymentService;
import uk.co.dancetrix.service.mock.MockRegistrationService;
import uk.co.dancetrix.service.mock.MockUniformService;

public class ServiceLocator {

    public static final ClassService CLASS_SERVICE =
            BuildConfig.MOCK_SERVICES ? new MockClassService() : new FirebaseClassService();
    public static final UniformService UNIFORM_SERVICE =
            BuildConfig.MOCK_SERVICES ? new MockUniformService() : new FirebaseUniformService();
    public static final PaymentService PAYMENT_SERVICE =
            BuildConfig.MOCK_SERVICES ? new MockPaymentService() : new EmailPaymentService();
    public static final BookingService BOOKING_SERVICE =
            BuildConfig.MOCK_SERVICES ? new MockBookingService() : new EmailBookingService();
    public static final RegistrationService REGISTRATION_SERVICE =
            BuildConfig.MOCK_SERVICES ? new MockRegistrationService() : new EmailRegistrationService();

    public static final EmailService EMAIL_SERVICE =
            BuildConfig.MOCK_EMAIL ? new MockEmailService() : new MailgunEmailService();

}
