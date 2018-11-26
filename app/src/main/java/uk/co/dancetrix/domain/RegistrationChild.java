package uk.co.dancetrix.domain;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Map;

import uk.co.dancetrix.util.StringFormatter;

public class RegistrationChild extends RegistrationBase {

    private final String contact;
    private final Date dateJoined;
    private final String hearAbout;
    private final String name;
    private String photoConsent;

    public RegistrationChild(final String studentName,
                             final Date dateOfBirth,
                             final String email,
                             final String phone,
                             final String address,
                             final String medical,
                             final String experience,
                             final Bitmap signature,
                             final String contact,
                             final Date dateJoined,
                             final String hearAbout,
                             final String name,
                             final String photoConsent) {
        super(studentName, dateOfBirth, email, phone, address, medical, experience, signature);

        this.contact = contact;
        this.dateJoined = dateJoined;
        this.hearAbout = hearAbout;
        this.name = name;
        this.photoConsent = photoConsent;
    }

    public void setPhotoConsent(String photoConsent) {
        this.photoConsent = photoConsent;
    }

    public Map<String, Object> getEmailParameters() {
        Map<String, Object> emailParameters = super.getEmailParameters();

        emailParameters.put("contact", contact);
        emailParameters.put("dateJoined", StringFormatter.formatDate(dateJoined));
        emailParameters.put("hearAbout", hearAbout);
        emailParameters.put("name", name);
        emailParameters.put("photoConsent", photoConsent);

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
        out.writeString(contact);
        out.writeSerializable(dateJoined);
        out.writeString(hearAbout);
        out.writeString(name);
        out.writeString(photoConsent);
    }

    public static final Parcelable.Creator<RegistrationChild> CREATOR
            = new Parcelable.Creator<RegistrationChild>() {
        public RegistrationChild createFromParcel(Parcel in) {
            return new RegistrationChild(in);
        }

        public RegistrationChild[] newArray(int size) {
            return new RegistrationChild[size];
        }
    };

    private RegistrationChild(Parcel in) {
        this(in.readString(),
                (Date)in.readSerializable(),
                in.readString(),
                in.readString(),
                in.readString(),
                in.readString(),
                in.readString(),
                in.readParcelable(RegistrationAdult.class.getClassLoader()),
                in.readString(),
                (Date)in.readSerializable(),
                in.readString(),
                in.readString(),
                in.readString());
    }

}
