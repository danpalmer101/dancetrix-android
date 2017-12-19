package uk.co.dancetrix.service.impl;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.samskivert.mustache.Mustache;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import uk.co.dancetrix.service.EmailService;
import uk.co.dancetrix.util.Configuration;
import uk.co.dancetrix.util.FileReader;

public class MailgunEmailService implements EmailService {

    private static final String SUBJECT_TEMPLATE = "%s_subject";
    private static final String BODY_PLAIN_TEMPLATE = "%s_body_plain";
    private static final String BODY_HTML_TEMPLATE = "%s_body_html";

    @Override
    public void sendEmail(Context ctx, String templateName, String from, String to,
                          Map<String, Object> templateParameters,
                          uk.co.dancetrix.service.Callback<Boolean, Exception> callback) {
        try {
            send(from,
                    to,
                    buildTemplate(ctx, String.format(SUBJECT_TEMPLATE, templateName), templateParameters),
                    buildTemplate(ctx, String.format(BODY_PLAIN_TEMPLATE, templateName), templateParameters),
                    buildTemplate(ctx, String.format(BODY_HTML_TEMPLATE, templateName), templateParameters),
                    callback);
        } catch (Exception e) {
            Log.e("Email", "Error sending email", e);

            callback.onError(e);
        }
    }

    private String buildTemplate(Context ctx, String templateName, Map<String, Object> templateParameters)
            throws Exception {
        int rawId = ctx.getResources().getIdentifier(
                templateName,
                "raw",
                ctx.getPackageName());

        String template = FileReader.readFile(ctx, rawId);
        return Mustache.compiler().compile(template).execute(templateParameters);
    }

    protected void send(String from, String to, String subject, String plainText, String htmlText,
                        final uk.co.dancetrix.service.Callback<Boolean, Exception> callback) {
        try {
            RequestBody body = new FormBody.Builder()
                .add("from", from)
                .add("to", to)
                .add("subject", subject)
                .add("text", plainText)
                .add("html", htmlText).build();

            Request request = new Request.Builder()
                .url("https://api.mailgun.net/v3/" + Configuration.mailgunDomain() + "/messages")
                .post(body)
                .addHeader("Authorization",
                           "Basic " + Base64.encodeToString(
                                   ("api:" + Configuration.mailgunApiKey()).getBytes("UTF-8"), Base64.NO_WRAP))
                .build();

            new OkHttpClient().newCall(request).enqueue(
                    new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("Email", "Error sending email", e);

                            callback.onError(e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.d("Email", "Email response: " + response.toString() + ", Response body: " + response.body().string());

                            callback.onSuccess(true);
                        }
                    }
            );
        } catch (Exception e) {
            Log.e("Email", "Error sending email", e);

            callback.onError(e);
        }
    }

}
