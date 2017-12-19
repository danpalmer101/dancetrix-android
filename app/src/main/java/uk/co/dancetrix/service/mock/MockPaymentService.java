package uk.co.dancetrix.service.mock;

import java.util.Date;

import uk.co.dancetrix.service.Callback;
import uk.co.dancetrix.service.PaymentService;

public class MockPaymentService implements PaymentService {

    @Override
    public void notify(Date date,
                       Double amount,
                       String name,
                       String studentName,
                       String email,
                       String method,
                       String reason,
                       String additionalInfo,
                       Callback<Boolean, Exception> callback) {
        // TODO : Delay
        callback.onSuccess(true);
    }

}
