package uk.co.dancetrix.service;

import android.content.Context;

import java.util.List;
import java.util.Map;

import uk.co.dancetrix.domain.ClassMenu;
import uk.co.dancetrix.domain.UniformGroup;
import uk.co.dancetrix.domain.UniformItem;

public interface UniformService {

    void getUniformOrderItems(Context ctx,
                              Callback<List<UniformGroup>, Exception> callback);

    void orderUniform(Context ctx,
                      String name,
                      String studentName,
                      String email,
                      String packageName,
                      boolean paymentMade,
                      String paymentMethod,
                      String additionalInfo,
                      Map<UniformItem, String> orderItems,
                      Callback<Boolean, Exception> callback);

}
