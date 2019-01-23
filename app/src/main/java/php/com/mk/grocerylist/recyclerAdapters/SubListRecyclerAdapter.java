package php.com.mk.grocerylist.recyclerAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import php.com.mk.grocerylist.R;
import php.com.mk.grocerylist.model.GroceryList;
import php.com.mk.grocerylist.model.MainList;
import php.com.mk.grocerylist.persistence.repository.GroceryListRepository;
import php.com.mk.grocerylist.persistence.repository.MainListRepository;
import php.com.mk.grocerylist.recyclerHolders.SubListRecyclerHolder;

public class SubListRecyclerAdapter extends RecyclerView.Adapter<SubListRecyclerHolder> {
    private Context context;
    private List<String> list;
    private List<GroceryList> groceryLists;
    private GroceryListRepository groceryListRepository;

    public SubListRecyclerAdapter(Context context, List<GroceryList> list) {
        this.context = context;
        this.groceryLists = list;
        this.groceryListRepository = new GroceryListRepository(context);
    }

    @NonNull
    @Override
    public SubListRecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_viewholder_sublist, viewGroup, false);
        return new SubListRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubListRecyclerHolder subListRecyclerHolder, int i) {
        String dataToShow = groceryLists.get(i)
                .getName() + "\n" +
                groceryLists.get(i).getQuantity() + "\n";
        subListRecyclerHolder.getTextView()
                .setText(dataToShow);
    }

    @Override
    public int getItemCount() {
        if (groceryLists == null)
            return 0;
        else
            return groceryLists.size();
    }

    public void updateList(List<GroceryList> groceryRepresentation) {
        this.groceryLists = groceryRepresentation;
        notifyDataSetChanged();
    }

    public void addNewItemToTheList(final GroceryList groceryList) {
        groceryListRepository.insertItem(groceryList);
        this.groceryLists = groceryListRepository
                .getGroceriesForListId(groceryList.getListId())
                .getValue();
        notifyDataSetChanged();
    }
}