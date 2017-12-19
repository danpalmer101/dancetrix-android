package uk.co.dancetrix.service;

import java.util.List;
import java.util.Map;

import uk.co.dancetrix.domain.UniformGroup;
import uk.co.dancetrix.domain.UniformItem;

public interface UniformService {

    List<UniformGroup> getUniformOrderItems();

    void orderUniform(String name,
                      String studentName,
                      String email,
                      String packageName,
                      boolean paymentMade,
                      String paymentMethod,
                      String additionalInfo,
                      Map<UniformItem, String> orderItems,
                      Callback<Boolean, Exception> callback);

}
