package uk.co.dancetrix.service.mock;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.Map;

import uk.co.dancetrix.R;
import uk.co.dancetrix.domain.UniformGroup;
import uk.co.dancetrix.domain.UniformItem;
import uk.co.dancetrix.service.Callback;
import uk.co.dancetrix.service.UniformService;
import uk.co.dancetrix.util.FileReader;
import uk.co.dancetrix.util.UniformParser;

public class MockUniformService implements UniformService {

    @Override
    public void getUniformOrderItems(final Context ctx,
                                     final Callback<List<UniformGroup>, Exception> callback) {

        AsyncTask.execute(() -> {
            try {
                String csv = FileReader.readFile(ctx, R.raw.uniforms);

                callback.onSuccess(UniformParser.parse(csv));
            } catch (Exception e) {
                Log.e("Uniform", "Error reading uniforms", e);

                callback.onError(e);
            }
        });
    }

    @Override
    public void orderUniform(String name,
                             String studentName,
                             String email,
                             String packageName,
                             boolean paymentMade,
                             String paymentMethod,
                             String additionalInfo,
                             Map<UniformItem, String> orderItems,
                             final Callback<Boolean, Exception> callback) {
        AsyncTask.execute(() -> callback.onSuccess(true));
    }

}
