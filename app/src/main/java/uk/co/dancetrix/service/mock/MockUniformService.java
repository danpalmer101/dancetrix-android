package uk.co.dancetrix.service.mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import uk.co.dancetrix.domain.UniformGroup;
import uk.co.dancetrix.domain.UniformItem;
import uk.co.dancetrix.service.Callback;
import uk.co.dancetrix.service.UniformService;

public class MockUniformService implements UniformService {

    private List<String> childClothesSizes = Arrays.asList("3 - 4 years (Size 0)", "5 - 6 years (Size 1)", "7 - 8 years (Size 1b)", "9 - 10 years (Size 2)", "11 - 13 years (Size 3a)", "Adult Small (Size 3)", "Adult Medium (Size 4)");
    private List<String> childShoeSizes = Arrays.asList("5", "6", "7", "8", "9", "9.5", "10", "10.5");
    private List<String> adultClothesSizes = Arrays.asList("Small", "Medium", "Large", "Extra Large");
    private List<String> adultShoeSizes = Arrays.asList("4", "4.5", "5", "5.5", "6", "6.5", "7", "7.5");
    private List<String> sockSizes = Arrays.asList("6 - 8.5", "9 - 12", "12.5 - 3");

    private List<String> empty = Collections.emptyList();

    @Override
    public List<UniformGroup> getUniformOrderItems() {
        return Arrays.asList(
                new UniformGroup("Order children's clothes", Arrays.asList(
                    new UniformItem("child_turquoise_skirted_leotard", "Turquoise skirted leotard", childClothesSizes),
                    new UniformItem("child_turquoise_leggings", "Turquoise leggings", childClothesSizes),
                    new UniformItem("child_turquoise_skirt", "Turquoise skirt", childClothesSizes),
                    new UniformItem("child_dance_trix_branded_hoodie", "Dance Trix branded hoodie", childClothesSizes),
                    new UniformItem("child_dance_trix_branded_tshirt", "Dance Trix branded t-shirt", childClothesSizes),
                    new UniformItem("child_black_high_neck_leotard",  "Black high neck leotard", childClothesSizes)
                )),
                new UniformGroup("Order children's shoes", Arrays.asList(
                    new UniformItem("child_shoes_tap_white", "White tap shoes", childShoeSizes),
                    new UniformItem("child_shoes_tap_black", "Black tap shoes", childShoeSizes),
                    new UniformItem("child_pink_ballet_shoes", "Pink ballet shoes", childShoeSizes)
                )),
                new UniformGroup("Order adult's clothes", Arrays.asList(
                    new UniformItem("adult_dance_trix_hoodie", "Dance Trix branded hoodie", adultClothesSizes),
                    new UniformItem("adult_dance_trix_tshirt", "Dance Trix branded t-shirt", adultClothesSizes)
                )),
                new UniformGroup("Order adult's shoes", Arrays.asList(
                    new UniformItem("adult_shoes_tap", "Tap shoes", adultShoeSizes),
                    new UniformItem("adult_shoes_ballet", "Ballet shoes", adultShoeSizes)
                )),
                new UniformGroup("Order other items", Arrays.asList(
                    new UniformItem("pink_ballet_socks", "Pink ballet socks", sockSizes),
                    new UniformItem("child_ballet_bag", "Child's ballet bag", empty),
                    new UniformItem("adult_ballet_bag", "Adult's ballet bag", empty),
                    new UniformItem("child_ballet_purse", "Child's ballet purse", empty),
                    new UniformItem("exam_headband", "Exam headband", empty)
                ))
            );
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
                             Callback<Boolean, Exception> callback) {
        // TODO : Delay
        callback.onSuccess(true);
    }

}
