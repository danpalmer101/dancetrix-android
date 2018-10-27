package uk.co.dancetrix.service.impl;

import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public abstract class FirebaseStorageService {

    protected abstract String getTag();

    public void loadFile(final String fileName,
                         final OnSuccessListener<String> successListener,
                         final OnFailureListener failureListener) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            loadFileAuthenticated(fileName, successListener, failureListener);
        } else {
            Task<AuthResult> authTask = FirebaseAuth.getInstance().signInAnonymously();

            authTask.addOnSuccessListener(result -> loadFileAuthenticated(fileName, successListener, failureListener));
            authTask.addOnFailureListener(failureListener);
        }
    }

    private void loadFileAuthenticated(final String fileName,
                                       final OnSuccessListener<String> successListener,
                                       final OnFailureListener failureListener) {
        final StorageReference classesReference = FirebaseStorage.getInstance().getReference().child(fileName);

        Log.d(getTag(), "    Downloading file from Firebase storage: " + fileName);

        // Download in memory with a maximum allowed size of 1MB (1024 * 1024 bytes)
        final Task<byte []> bytes = classesReference.getBytes(1024 * 1024);

        bytes.addOnFailureListener(e -> {
            Log.w(getTag(), "...failed to download file: " + fileName, e);
            failureListener.onFailure(e);
        });

        bytes.addOnSuccessListener(b -> {
            Log.d(getTag(), "    Downloaded file from Firebase storage:" + fileName);

            try {
                final String content = new String(b, "ISO-8859-1");
                successListener.onSuccess(content);
            } catch (Exception e) {
                Log.w(getTag(), "...failed to read file content as String", e);
                failureListener.onFailure(e);
            }
        });

    }

}
