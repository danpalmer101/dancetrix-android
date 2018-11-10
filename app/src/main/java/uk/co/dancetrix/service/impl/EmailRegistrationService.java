package uk.co.dancetrix.service.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.UUID;

import uk.co.dancetrix.domain.RegistrationAdult;
import uk.co.dancetrix.domain.RegistrationBase;
import uk.co.dancetrix.service.Callback;
import uk.co.dancetrix.service.RegistrationService;
import uk.co.dancetrix.service.ServiceLocator;
import uk.co.dancetrix.util.Configuration;

public class EmailRegistrationService implements RegistrationService {

    @Override
    public void register(final Context ctx,
                         final RegistrationBase registration,
                         final Callback<Boolean, Exception> callback) {
        Log.d("Registration", "Sending registration email");

        uploadImage(
            registration.getSignature(),
            new Callback<Uri, Exception>() {
                @Override
                public void onSuccess(Uri signatureUrl) {
                    Map<String, Object> emailParameters = registration.getEmailParameters();
                    if (signatureUrl != null) {
                        emailParameters.put("signatureUrl", signatureUrl.toString());
                    }

                    ServiceLocator.EMAIL_SERVICE.sendEmail(
                            ctx,
                            registration instanceof RegistrationAdult ? "registration_adult": "registration_child",
                            Configuration.fromRegistrationEmailAddress(),
                            Configuration.toEmailAddress(),
                            emailParameters,
                            new Callback<Boolean, Exception>() {
                                @Override
                                public void onSuccess(Boolean response) {
                                    Log.d("Registration", "Registration email sent");
                                    callback.onSuccess(response);
                                }

                                @Override
                                public void onError(Exception exception) {
                                    Log.w("Registration", "Registration email not sent", exception);
                                    callback.onError(exception);
                                }
                            });
                }

                @Override
                public void onError(Exception exception) {
                    Log.w("Registration", "Registration email not sent", exception);
                    callback.onError(exception);
                }
            });
    }

    private void uploadImage(final Bitmap image,
                             final Callback<Uri, Exception> callback) {
        if (image == null) {
            // No signature, success but no URL
            callback.onSuccess(null);
            return;
        }

        // Get the data from a Bitmap as bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        StorageReference imageReference =
                FirebaseStorage.getInstance().getReference()
                        .child("signatures/" + UUID.randomUUID().toString() + ".png");

        // Upload the image

        imageReference.putBytes(data)
                .addOnFailureListener(callback::onError)
                .addOnSuccessListener(taskSnapshot -> {
                    Log.d("Registration", "Uploaded signature");

                    // Get the download URL

                    imageReference.getDownloadUrl()
                            .addOnFailureListener(callback::onError)
                            .addOnSuccessListener(callback::onSuccess);
        });
    }

}
