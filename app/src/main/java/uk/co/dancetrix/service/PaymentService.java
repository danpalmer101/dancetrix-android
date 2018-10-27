package uk.co.dancetrix.service;

import android.content.Context;

import java.util.Date;

public interface PaymentService {

    void notify(Context ctx,
                Date date,
                Double amount,
                String name,
                String studentName,
                String email,
                String method,
                String reason,
                String additionalInfo,
                Callback<Boolean, Exception> callback);

}
