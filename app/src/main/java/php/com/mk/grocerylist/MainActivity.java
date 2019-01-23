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
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import php.com.mk.grocerylist.model.MainList;
import php.com.mk.grocerylist.persistence.repository.MainListRepository;
import php.com.mk.grocerylist.recyclerAdapters.MainListRecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_LIST_ACTIVITY_CODE = 1000;
    RecyclerView recyclerView;
    MainListRecyclerAdapter mainListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    List<MainList> list = null;
    MainListRepository mainListRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainListRepository = new MainListRepository(this);
        initUI();
    }

    public void initUI() {
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mainListRecyclerAdapter = new MainListRecyclerAdapter(this, list);
        recyclerView.setAdapter(mainListRecyclerAdapter);
        LiveData<List<MainList>> ldItems = mainListRepository.getAll();
        ldItems.observe(this, new Observer<List<MainList>>() {
            @Override
            public void onChanged(@Nullable List<MainList> items) {
                if (items == null || items.size() == 0) {
                    Toast.makeText(getApplicationContext(), "Nemate niedna lista", Toast.LENGTH_SHORT).show();
                } else {
                    mainListRecyclerAdapter.addNewItemToTheList(items);
                }
            }
        });
    }

    public void onNewListClick(View v) {
        Intent intent = new Intent(this, NewListActivity.class);
        startActivityForResult(intent, NEW_LIST_ACTIVITY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_LIST_ACTIVITY_CODE
                && resultCode == Activity.RESULT_OK
                & data != null) {
            MainList result = (MainList) data.getSerializableExtra("newList");
            mainListRepository.insertItem(result);
        }
    }
}
