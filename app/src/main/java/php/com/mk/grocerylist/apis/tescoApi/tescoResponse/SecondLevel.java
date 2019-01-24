package php.com.mk.grocerylist.apis.tescoApi.tescoResponse;

import com.google.gson.annotations.SerializedName;

// ghs
public class SecondLevel {

    @SerializedName(value = "products")
    TescoProducts tescoProducts;

    public TescoProducts getTescoProducts() {
        return tescoProducts;
    }

    public void setTescoProducts(TescoProducts tescoProducts) {
        this.tescoProducts = tescoProducts;
    }
}
