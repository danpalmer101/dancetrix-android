package uk.co.dancetrix.service.impl;

import android.content.Context;

import java.util.List;
import java.util.Map;

import uk.co.dancetrix.domain.UniformGroup;
import uk.co.dancetrix.domain.UniformItem;
import uk.co.dancetrix.service.Callback;
import uk.co.dancetrix.service.UniformService;
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
        // TODO : Implement
        callback.onError(new UnsupportedOperationException("Not implemented"));
    }

}
