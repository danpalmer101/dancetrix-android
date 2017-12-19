package uk.co.dancetrix.service;

import uk.co.dancetrix.service.mock.MockClassService;
import uk.co.dancetrix.service.mock.MockPaymentService;
import uk.co.dancetrix.service.mock.MockUniformService;

public class ServiceLocator {

    public static final ClassService CLASS_SERVICE = new MockClassService();
    public static final UniformService UNIFORM_SERVICE = new MockUniformService();
    public static final PaymentService PAYMENT_SERVICE = new MockPaymentService();

}
