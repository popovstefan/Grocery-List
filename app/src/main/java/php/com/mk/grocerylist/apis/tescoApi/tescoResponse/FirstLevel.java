package php.com.mk.grocerylist.apis.tescoApi.tescoResponse;

import com.google.gson.annotations.SerializedName;

// uk
public class FirstLevel {
    @SerializedName(value = "ghs")
    SecondLevel secondLevel;

    public SecondLevel getSecondLevel() {
        return secondLevel;
    }

    public void setSecondLevel(SecondLevel secondLevel) {
        this.secondLevel = secondLevel;
    }
}
