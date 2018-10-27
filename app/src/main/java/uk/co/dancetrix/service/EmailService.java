package uk.co.dancetrix.service;

import android.content.Context;

import java.util.Date;
import java.util.Map;

public interface EmailService {

    void sendEmail(Context ctx,
                   String templateName,
                   String from,
                   String to,
                   Map<String, Object> templateParameters,
                   Callback<Boolean, Exception> callback);

}
