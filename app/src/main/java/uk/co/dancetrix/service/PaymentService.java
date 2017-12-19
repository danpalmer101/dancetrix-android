package uk.co.dancetrix.service;

import java.util.Date;

public interface PaymentService {

    void notify(Date date,
                Double amount,
                String name,
                String studentName,
                String email,
                String method,
                String reason,
                String additionalInfo,
                Callback<Boolean, Exception> callback);

}
