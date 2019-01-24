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
import php.com.mk.grocerylist.apis.tescoApi.TescoApiClient;
import php.com.mk.grocerylist.apis.tescoApi.TescoInterface;
import php.com.mk.grocerylist.apis.tescoApi.tescoResponse.FirstLevel;
import php.com.mk.grocerylist.apis.tescoApi.tescoResponse.TescoResponse;
import php.com.mk.grocerylist.apis.tescoApi.tescoResponse.TescoResult;
import retrofit2.Call;

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
                        mData.clear();
                        loadProductsFromApi(constraint.toString());
                        loadProductsFromTescoApi(constraint.toString());
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

    //call to retrofit synchroniously
    private void loadProductsFromApi(String product) throws IOException {
        InrFoodInterface service = InrFoodApiClient.getRetrofit().create(InrFoodInterface.class);
        Call<List<InrFoodResponse>> call = service.getProducts(product);
        List<InrFoodResponse> products=call.execute().body();
        if (products == null) {
            Toast.makeText(getContext(), "No search results.", Toast.LENGTH_LONG).show();
            //return;
        }
        for (InrFoodResponse i : products
        ) {
            mData.add(i.getName() + " - " + i.getCategory());
        }
    }

    private void loadProductsFromTescoApi(String product) throws IOException {
        TescoInterface service = TescoApiClient.getRetrofit().create(TescoInterface.class);
        Call<TescoResponse> call = service.getProducts(product);
        TescoResponse response=call.execute().body();
        if (response == null) {
            Toast.makeText(getContext(), "No search results.", Toast.LENGTH_LONG).show();
            //return;
        }
        FirstLevel firstLevel = response.getFirstLevel();
        List<TescoResult> list= firstLevel.getSecondLevel().getTescoProducts().getResults();

        for (TescoResult i : list
        ) {
            mData.add(i.getName() + " - " + i.getDepartment());
        }
    }



}
