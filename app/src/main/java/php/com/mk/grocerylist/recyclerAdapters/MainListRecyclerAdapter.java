package php.com.mk.grocerylist.recyclerAdapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Locale;

import php.com.mk.grocerylist.ListActivity;
import php.com.mk.grocerylist.R;
import php.com.mk.grocerylist.model.GroceryList;
import php.com.mk.grocerylist.model.MainList;
import php.com.mk.grocerylist.persistence.repository.GroceryListRepository;
import php.com.mk.grocerylist.persistence.repository.MainListRepository;
import php.com.mk.grocerylist.recyclerHolders.MainListRecyclerHolder;

/**
 * Adapter for the list of grocery lists.
 * Handles all the observed changes in that list.
 * A direct implementation of the Adapter design pattern,
 * i.e. acts as an adapter between the recycler view holders
 * and the actual data in the repository.
 */
public class MainListRecyclerAdapter extends RecyclerView.Adapter<MainListRecyclerHolder> {
    private Context context;
    private List<MainList> list;
    private MainListRepository mainListRepository;
    private GroceryListRepository groceryListRepository;

    public MainListRecyclerAdapter(Context context, List<MainList> list) {
        this.context = context;
        this.list = list;
        mainListRepository = new MainListRepository(context);
        groceryListRepository = new GroceryListRepository(context);
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
    public MainListRecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_viewholder, viewGroup, false);
        return new MainListRecyclerHolder(view);
    }

    /**
     * Connects (adapts) the data in the repository
     * with it's corresponding recycler view holder
     * Also, sets on click listener for the delete (done)
     * grocery list button and the email button.
     *
     * @param mainListRecyclerHolder is the recycler holder view object which was bound to this adapter
     * @param i                      is his index in the recycler view's list
     */
    @Override
    public void onBindViewHolder(@NonNull final MainListRecyclerHolder mainListRecyclerHolder, int i) {
        final MainList dataToShow = list.get(i);
        final int id = dataToShow.getId();
        final StringBuilder text = new StringBuilder();
        // TODO: This data is always null. Make it not
        final List<GroceryList> data = groceryListRepository.getGroceriesForListId(id)
                .getValue();
        if (data != null) {
            for (GroceryList groceryList : data)
                text.append(String.format(Locale.US, "Product: %s\nQuantity: %d\n", groceryList.getName(), groceryList.getQuantity()));
        }
        text.append(String.format("\n\nAt %s", dataToShow.getLocation()));
        final int x = i;
        mainListRecyclerHolder.bind(dataToShow);
        mainListRecyclerHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ListActivity.class);
                intent.putExtra("selectedList", dataToShow);
                //intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });

        /*
          On click listener which creates an implicit intent
          for sending e-mail with the following content:
          (1) receiver email (always empty, so the user can enter it in the email app)
          (2) subject (always "Groceries list"
          (3) body text containing all groceries and the corresponding quantities that are to be bought
          This method starts a dialog with the user in which
          an email app is chosen to deal with the implicit intent.
         */
        mainListRecyclerHolder.getBtnEmail().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = "";
                final String subject = "Groceries list";
                final String chooserTitle = "Email using";
                final Uri uri = Uri.parse("mailto:" + email)
                        .buildUpon()
                        .appendQueryParameter("subject", subject)
                        .build();
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra(Intent.EXTRA_TEXT, text.toString());
                context.startActivity(Intent.createChooser(intent, chooserTitle));
            }
        });

        // On click listener for deleting a list
        mainListRecyclerHolder.getBtnDone().setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        list.remove(x);
                        mainListRepository.deleteItem(dataToShow);
                        notifyDataSetChanged();
                    }
                });
    }

    /**
     * Self explanatory.
     *
     * @return the list's size
     */
    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    /**
     * Sets a new list.
     * Notifies it's observers about the change.
     *
     * @param list is the new list
     */
    public void updateMainList(List<MainList> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
