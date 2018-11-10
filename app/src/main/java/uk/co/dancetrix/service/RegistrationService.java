package uk.co.dancetrix.service;

import android.content.Context;

import uk.co.dancetrix.domain.RegistrationBase;

public interface RegistrationService {

    void register(Context ctx,
                  RegistrationBase registration,
                  Callback<Boolean, Exception> callback);

}
