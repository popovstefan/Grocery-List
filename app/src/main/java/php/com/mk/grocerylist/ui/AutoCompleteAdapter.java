package php.com.mk.grocerylist.ui;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import php.com.mk.grocerylist.apis.inrApi.InrFoodApiClient;
import php.com.mk.grocerylist.apis.inrApi.InrFoodInterface;
import php.com.mk.grocerylist.apis.inrApi.InrFoodResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
    private List<String> mData;

    public AutoCompleteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        mData = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int index) {
        return mData.get(index);
    }

    @Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint != null) {
                    //Call to the web API, parses the data and returns an ArrayList<String>
                    try {
                        loadProductsFromApi(constraint.toString());
                    }
                    catch(Exception e) {
                        Log.e("myException", e.getMessage());
                    }
                    // Now assign the values and count to the FilterResults object
                    filterResults.values = mData;
                    filterResults.count = mData.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence contraint, FilterResults results) {
                if(results != null && results.count > 0) {
                    mData= (ArrayList<String>)results.values;
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return myFilter;
    }

    // call to Retrofit ASYNCHRONIOUSLY --- not working in Async way
    /*public void loadProductsFromApi(String title) {
        Log.i("TAG", "loadProductsFromApi: in the beggining of the method");
        InrFoodInterface service = InrFoodApiClient.getRetrofit().create(InrFoodInterface.class);
        Call<List<InrFoodResponse>> call = service.getProducts(title);
                call.enqueue(new Callback<List<InrFoodResponse>>() {
            @Override
            public void onResponse(Call<List<InrFoodResponse>> call, Response <List<InrFoodResponse>> response) {

                Log.i("TAG", "onResponse: response not successful");
                if (response.isSuccessful()) {

                    Log.i("TAG", "onResponse: in the beggining in onResponse");
                    List<InrFoodResponse> products = response.body();
                    if (products == null) {
                        Toast.makeText(getContext(), "No search results.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    for (InrFoodResponse i : response.body()
                    ) {
                        mData.add(i.getName() + " - " + i.getCategory());
                    }
                    Log.i("TAG", "onResponse: in the end in on Response");
                }
            }
            @Override
            public void onFailure(Call<List<InrFoodResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Failure:(", Toast.LENGTH_LONG).show();
                Log.i("TAG", "onFailure: " + t.getMessage());

            }
        });
    }*/

    //call to retrofit synchroniously
    private void loadProductsFromApi(String product) throws IOException {
        InrFoodInterface service = InrFoodApiClient.getRetrofit().create(InrFoodInterface.class);
        Call<List<InrFoodResponse>> call = service.getProducts(product);
        List<InrFoodResponse> products=call.execute().body();
        if (products == null) {
            Toast.makeText(getContext(), "No search results.", Toast.LENGTH_LONG).show();
            return;
        }
        for (InrFoodResponse i : products
        ) {
            mData.add(i.getName() + " - " + i.getCategory());
        }
    }


}
