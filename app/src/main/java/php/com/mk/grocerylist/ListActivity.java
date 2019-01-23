package php.com.mk.grocerylist;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import php.com.mk.grocerylist.model.GroceryList;
import php.com.mk.grocerylist.persistence.repository.GroceryListRepository;
import php.com.mk.grocerylist.recyclerAdapters.SubListRecyclerAdapter;

public class ListActivity extends AppCompatActivity {
    private static final int NEW_LIST_ITEM_ACTIVITY = 200;
    SubListRecyclerAdapter subListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    int listId;
    List<GroceryList> products = null;
    GroceryListRepository groceryListRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        products = new ArrayList<>();
        groceryListRepository = new GroceryListRepository(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            listId = extras.getInt("id");
        initUI();
    }

    private void initUI() {
        RecyclerView recyclerView = findViewById(R.id.subListRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        subListRecyclerAdapter = new SubListRecyclerAdapter(this, products);
        recyclerView.setAdapter(subListRecyclerAdapter);
        final LiveData<List<GroceryList>> groceryRepresentationLiveData = groceryListRepository.getGroceriesForListId(listId);
        groceryRepresentationLiveData.observe(this, new Observer<List<GroceryList>>() {
            @Override
            public void onChanged(@Nullable List<GroceryList> groceryLists) {
                if (groceryLists == null || groceryLists.size() == 0)
                    Toast.makeText(getApplicationContext(), "Nemate nieden product", Toast.LENGTH_SHORT).show();
                else
                    subListRecyclerAdapter.updateList(groceryLists);
            }
        });
    }

    public void onAddNewListItem(View v) {
        Intent intent = new Intent(this, NewListItemActivity.class);
        startActivityForResult(intent, NEW_LIST_ITEM_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_LIST_ITEM_ACTIVITY
                && resultCode == Activity.RESULT_OK
                && data != null) {
            GroceryList result = (GroceryList) data.getSerializableExtra("grocery_list");
            result.setListId(listId);
            subListRecyclerAdapter.addNewItemToTheList(result);
        }
    }
}
