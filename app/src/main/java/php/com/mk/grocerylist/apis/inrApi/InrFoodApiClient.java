package php.com.mk.grocerylist.apis.inrApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InrFoodApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            ///ova e za obicen retrofit bez rxjava da funkcionira
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://inrfood.api.run/v1/ingredient/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            /*Retrofit.Builder()
                    .baseUrl(BuildConfig.END_POINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();*/

        }
        return retrofit;
    }
}
