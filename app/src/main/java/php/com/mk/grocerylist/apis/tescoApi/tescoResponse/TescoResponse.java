package php.com.mk.grocerylist.apis.tescoApi.tescoResponse;

import com.google.gson.annotations.SerializedName;

public class TescoResponse {
    @SerializedName(value = "uk")
    FirstLevel firstLevel;

    public TescoResponse(FirstLevel firstLevel) {
        this.firstLevel = firstLevel;
    }

    public FirstLevel getFirstLevel() {
        return firstLevel;
    }

    public void setFirstLevel(FirstLevel firstLevel) {
        this.firstLevel = firstLevel;
    }
}
