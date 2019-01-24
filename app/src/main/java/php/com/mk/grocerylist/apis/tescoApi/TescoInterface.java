package php.com.mk.grocerylist.apis.tescoApi;

import php.com.mk.grocerylist.apis.tescoApi.tescoResponse.TescoResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface TescoInterface {

    @GET("grocery/products/?&offset=0&limit=10")
    @Headers(value = "Ocp-Apim-Subscription-Key: 5570ed63a9f74893906ecaf02cd7f8d5")
    Call<TescoResponse> getProducts(@Query("query") String query);

//    @GET
//    Observable<TescoResponse> getProducts(@Query("query") String query);
}
