package php.com.mk.grocerylist.apis.tescoApi.tescoResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TescoProducts {
    @SerializedName(value = "results")
    List<TescoResult> results;
}
