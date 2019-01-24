package php.com.mk.grocerylist.apis.tescoApi.tescoResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TescoProducts {
    @SerializedName(value = "results")
    List<TescoResult> results;

    public List<TescoResult> getResults() {
        return results;
    }

    public void setResults(List<TescoResult> results) {
        this.results = results;
    }
}
