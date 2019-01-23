package php.com.mk.grocerylist.recyclerAdapters;

import android.arch.lifecycle.LiveData;
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

    @NonNull
    @Override
    public MainListRecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_viewholder, viewGroup, false);
        return new MainListRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainListRecyclerHolder mainListRecyclerHolder, int i) {
        final MainList dataToShow = list.get(i);
        final int id = dataToShow.getId();
        final int x = i;
        mainListRecyclerHolder.bind(dataToShow);
        mainListRecyclerHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ListActivity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });

        mainListRecyclerHolder.getBtnEmail().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GroceryList> data = groceryListRepository.getGroceriesForListId(id).getValue();
                StringBuilder body = new StringBuilder();
                if (data != null) {
                    for (GroceryList groceryList : data)
                        body.append(String.format(Locale.US, "Proizvod: %s\nKolichina: %d\n", groceryList.getName(), groceryList.getQuantity()));
                }
                String email = "";
                String subject = "Namirnici za kupuvanje";
                String chooserTitle = "Email using";
                Uri uri = Uri.parse("mailto:" + email)
                        .buildUpon()
                        .appendQueryParameter("subject", subject)
                        .appendQueryParameter("body", body.toString())
                        .build();
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                context.startActivity(Intent.createChooser(intent, chooserTitle));
            }
        });

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

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void addNewItemToTheList(List<MainList> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
