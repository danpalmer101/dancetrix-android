package uk.co.dancetrix.service.impl;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.dancetrix.domain.DateInterval;
import uk.co.dancetrix.domain.UniformGroup;
import uk.co.dancetrix.domain.UniformItem;
import uk.co.dancetrix.service.Callback;
import uk.co.dancetrix.service.ServiceLocator;
import uk.co.dancetrix.service.UniformService;
import uk.co.dancetrix.util.Configuration;
import uk.co.dancetrix.util.StringFormatter;
import uk.co.dancetrix.util.UniformParser;

public class FirebaseUniformService extends FirebaseStorageService implements UniformService {

    @Override
    protected String getTag() {
        return null;
    }

    @Override
    public void getUniformOrderItems(final Context ctx,
                                     final Callback<List<UniformGroup>, Exception> callback) {
        loadFile("uniforms.csv",
                csvString -> {
                    final List<UniformGroup> uniformGroups = UniformParser.parse(csvString);
                    callback.onSuccess(uniformGroups);
                },
                callback::onError);
    }

    @Override
    public void orderUniform(final Context ctx,
                             final String name,
                             final String studentName,
                             final String email,
                             final String packageName,
                             final boolean paymentMade,
                             final String paymentMethod,
                             final String additionalInfo,
                             final Map<UniformItem, String> orderItems,
                             final Callback<Boolean, Exception> callback) {
        Log.d("Uniform", "Sending uniform order email");

        ServiceLocator.EMAIL_SERVICE.sendEmail(ctx,
                "uniform_order",
                Configuration.fromBookingEmailAddress(),
                Configuration.toEmailAddress(),
                Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put("name", name);
                        put("studentName", studentName);
                        put("email", email);
                        put("package", packageName);
                        put("paymentMade", paymentMade);
                        put("paymentMethod", paymentMethod);
                        put("orderItems", orderItems);
                        put("additionalInfo", additionalInfo);
                    }
                }),
                new Callback<Boolean, Exception>() {
                    @Override
                    public void onSuccess(Boolean response) {
                        Log.d("Uniform", "Uniform order sent");
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(Exception exception) {
                        Log.w("Uniform", "Uniform order not sent", exception);
                        callback.onError(exception);
                    }
                });
    }

}
