package uk.co.dancetrix.domain;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

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

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(studentName);
        out.writeSerializable(dateOfBirth);
        out.writeString(email);
        out.writeString(phone);
        out.writeString(address);
        out.writeString(medical);
        out.writeString(experience);
        out.writeParcelable(signature, 0);
        out.writeString(emergencyName);
        out.writeString(emergencyPhone);

    }

    public static final Parcelable.Creator<RegistrationAdult> CREATOR
            = new Parcelable.Creator<RegistrationAdult>() {
        public RegistrationAdult createFromParcel(Parcel in) {
            return new RegistrationAdult(in);
        }

        public RegistrationAdult[] newArray(int size) {
            return new RegistrationAdult[size];
        }
    };

    private RegistrationAdult(Parcel in) {
        this(in.readString(),
                (Date)in.readSerializable(),
                in.readString(),
                in.readString(),
                in.readString(),
                in.readString(),
                in.readString(),
                in.readParcelable(RegistrationAdult.class.getClassLoader()),
                in.readString(),
                in.readString());
    }

}
