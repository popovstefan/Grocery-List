package php.com.mk.grocerylist.recyclerAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import php.com.mk.grocerylist.R;
import php.com.mk.grocerylist.model.GroceryList;
import php.com.mk.grocerylist.persistence.repository.GroceryListRepository;
import php.com.mk.grocerylist.recyclerHolders.SubListRecyclerHolder;

/**
 * Adapter for a particular grocery list.
 * Handles all the observed changes in that list.
 * A direct implementation of the Adapter design pattern,
 * i.e. acts as an adapter between the recycler view holders
 * and the actual data in the repository.
 */
public class SubListRecyclerAdapter extends RecyclerView.Adapter<SubListRecyclerHolder> {
    private List<GroceryList> groceryLists;
    private GroceryListRepository groceryListRepository;

    public SubListRecyclerAdapter(Context context, List<GroceryList> list) {
        this.groceryLists = list;
        this.groceryListRepository = new GroceryListRepository(context);
    }

    /**
     * Method to be executed when the view holder class is created.
     *
     * @param viewGroup in which that view holder class belongs to
     * @param i         is an index in the recycler view's list of items
     * @return a new recycler holder view to be inserted in the recycler view
     */
    @NonNull
    @Override
    public SubListRecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_viewholder_sublist, viewGroup, false);
        return new SubListRecyclerHolder(view);
    }

    /**
     * Connects (adapts) the data in the repository
     * with it's corresponding recycler view holder.
     * Also, sets on click listener for the delete (bought) grocery button.
     *
     * @param subListRecyclerHolder is the recycler holder view object which was bound to this adapter
     * @param i                     is his index in the recycler view's list
     */
    @Override
    public void onBindViewHolder(@NonNull SubListRecyclerHolder subListRecyclerHolder, int i) {
        // Adapting the data to it's recycler holder
        final GroceryList groceryList = groceryLists.get(i);
        final int x = i;
        subListRecyclerHolder.bind(groceryList);
        // OnClick listener
        subListRecyclerHolder.getBoughtButton()
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        groceryLists.remove(x);
                        groceryListRepository.deleteItem(groceryList);
                        notifyDataSetChanged();
                    }
                });
    }

    /**
     * Self explanatory.
     *
     * @return grocery count
     */
    @Override
    public int getItemCount() {
        if (groceryLists == null)
            return 0;
        else
            return groceryLists.size();
    }

    /**
     * Sets the grocery list.
     * Notifies it's observers about the change.
     *
     * @param groceryRepresentation is the new list
     */
    public void updateList(List<GroceryList> groceryRepresentation) {
        this.groceryLists = groceryRepresentation;
        notifyDataSetChanged();
    }

    /**
     * Inserts a new grocery in the repository, and
     * the list in the recycler view.
     * It's observers are notified about this change.
     *
     * @param groceryList is the grocery to be added
     */
    public void addNewItemToTheList(final GroceryList groceryList) {
        groceryListRepository.insertItem(groceryList);
        this.groceryLists = groceryListRepository
                .getGroceriesForListId(groceryList.getListId())
                .getValue();
        notifyDataSetChanged();
    }
}