package php.com.mk.grocerylist.apis.inrApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface InrFoodInterface {

    @GET("v1/ingredient/list")
    @Headers("apikey:YZYrhCV6Dd7iVnLrnjnPvLEuG6oJvsAi")
    Call<List<InrFoodResponse>> getProducts(@Query("name") String name);

//    @GET("/list")
//    Observable<List<InrFoodResponse>> getPublicPhotos(@Query("name") String name);

}
