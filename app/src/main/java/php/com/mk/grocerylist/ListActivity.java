package php.com.mk.grocerylist;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import php.com.mk.grocerylist.model.GroceryList;
import php.com.mk.grocerylist.model.MainList;
import php.com.mk.grocerylist.persistence.repository.GroceryListRepository;
import php.com.mk.grocerylist.recyclerAdapters.SubListRecyclerAdapter;

public class ListActivity extends AppCompatActivity {
    private static final int NEW_LIST_ITEM_ACTIVITY = 200;
    SubListRecyclerAdapter subListRecyclerAdapter;
    int listId;
    List<GroceryList> products = null;
    GroceryListRepository groceryListRepository;
    MainList selectedList;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        products = new ArrayList<>();
        groceryListRepository = new GroceryListRepository(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedList = (MainList) extras.getSerializable("selectedList");
            listId = selectedList.getId();
        }
        setTitle(selectedList.getListName());
        initUI();
    }

    /**
     * Connecting the elements defined in the layout with the
     * corresponding ones in this activity so that they can
     * be accessed in the program code.
     */
    private void initUI() {
        // Setup the repository and current data for the recycler view
        ArrayList<GroceryList> products = new ArrayList<>();
        GroceryListRepository groceryListRepository = new GroceryListRepository(this);
        // Setup the recycler view
        RecyclerView recyclerView = findViewById(R.id.subListRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        subListRecyclerAdapter = new SubListRecyclerAdapter(this, products);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(subListRecyclerAdapter);

        final LiveData<List<GroceryList>> groceryRepresentationLiveData = groceryListRepository.getGroceriesForListId(listId);
        groceryRepresentationLiveData.observe(this, new Observer<List<GroceryList>>() {
            /**
             * Method which is to be activated then the observed list of groceries changes.
             * A direct implementation of the observer design pattern.
             * @param groceryLists which are to be updated, if they are not empty or null
             */
            @Override
            public void onChanged(@Nullable List<GroceryList> groceryLists) {
                if (groceryLists == null || groceryLists.size() == 0)
                    Toast.makeText(getApplicationContext(), "Nothing to buy here", Toast.LENGTH_SHORT).show();
                else
                    subListRecyclerAdapter.updateList(groceryLists);
            }
        });

        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            /**
             * Creates an explicit intent with which starts
             * an activity for creating a new grocery.
             *
             * @param view unused
             */

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewListItemActivity.class);
                startActivityForResult(intent, NEW_LIST_ITEM_ACTIVITY);
            }
        });
    }


    /**
     * Adds the newly created grocery to it's list of groceries.
     * Method is activated after the new grocery activity finishes.
     * The newly created grocery is found in that activity's intent data.
     *
     * @param requestCode is the code with which the new grocery activity was started with
     * @param resultCode  is the code the new grocery activity returns as result
     * @param data        is the intent the new grocery activity gives back after finishing
     */
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
