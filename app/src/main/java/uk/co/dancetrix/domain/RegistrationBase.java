package uk.co.dancetrix.domain;

import android.graphics.Bitmap;
import android.os.Parcelable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import uk.co.dancetrix.util.StringFormatter;

public abstract class RegistrationBase implements Parcelable {

    final String studentName;
    final Date dateOfBirth;
    final String email;
    final String phone;
    final String address;
    final String medical;
    final String experience;
    Bitmap signature;

    RegistrationBase(final String studentName,
                     final Date dateOfBirth,
                     final String email,
                     final String phone,
                     final String address,
                     final String medical,
                     final String experience,
                     final Bitmap signature) {
        this.studentName = studentName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.medical = medical;
        this.experience = experience;
        this.signature = signature;
    }

    public void setSignature(Bitmap signature) {
        this.signature = signature;
    }

    public Bitmap getSignature() {
        return signature;
    }

    public Map<String, Object> getEmailParameters() {
        Map<String, Object> emailParameters = new HashMap<>();
        emailParameters.put("studentName", studentName);
        emailParameters.put("dateOfBirth", StringFormatter.formatDate(dateOfBirth));
        emailParameters.put("email", email);
        emailParameters.put("phone", phone);
        emailParameters.put("address", address);
        emailParameters.put("experience", experience);
        emailParameters.put("medical", medical);

        return emailParameters;
    }

    @Override
    public String toString() {
        return "data = " + getEmailParameters().toString() + ", signature = " + (signature != null ? "Set" : "Not set");
    }

}
