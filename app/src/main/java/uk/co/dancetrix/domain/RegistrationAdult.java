package uk.co.dancetrix.domain;

import android.graphics.Bitmap;

import java.util.Date;
import java.util.Map;

public class RegistrationAdult extends RegistrationBase {

    private String emergencyName;
    private String emergencyPhone;

    public RegistrationAdult(final String studentName,
                             final Date dateOfBirth,
                             final String email,
                             final String phone,
                             final String address,
                             final String medical,
                             final String experience,
                             final Bitmap signature,
                             final String emergencyName,
                             final String emergencyPhone) {
        super(studentName, dateOfBirth, email, phone, address, medical, experience, signature);

        this.emergencyName = emergencyName;
        this.emergencyPhone = emergencyPhone;
    }

    public Map<String, Object> getEmailParameters() {
        Map<String, Object> emailParameters = super.getEmailParameters();

        emailParameters.put("emergencyName", emergencyName);
        emailParameters.put("emergencyPhone", emergencyPhone);

        return emailParameters;
    }

}
