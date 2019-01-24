package php.com.mk.grocerylist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import php.com.mk.grocerylist.model.GroceryList;
import php.com.mk.grocerylist.ui.AutoCompleteAdapter;
import php.com.mk.grocerylist.ui.DelayAutoCompleteTextView;

public class NewListItemActivity extends AppCompatActivity {
    private DelayAutoCompleteTextView mEditTextName;
    private TextView mTextViewAmount;
    Button buttonIncrease;
    Button buttonDecrease;
    Button buttonAdd;
    private int mAmount = 0;
    AutoCompleteAdapter autoCompleteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list_item);
        initUI();
        buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increase();
            }
        });
        buttonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrease();
            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
    }

    public void addItem() {
        if (mEditTextName.getText().toString().trim().length() == 0 || mAmount == 0)
            return;
        String name = mEditTextName.getText().toString();
        Intent intent = new Intent();
        GroceryList result = new GroceryList();
        result.setName(name);
        result.setQuantity(mAmount);
        intent.putExtra("grocery_list", result);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void initUI() {

        mEditTextName = findViewById(R.id.edittext_name);
        mEditTextName.setThreshold(4);
        autoCompleteAdapter =
                new AutoCompleteAdapter(this, android.R.layout.simple_list_item_1);
        mEditTextName.setAdapter(autoCompleteAdapter);
        mEditTextName.setLoadingIndicator(
                (android.widget.ProgressBar) findViewById(R.id.pb_loading_indicator));
        mEditTextName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String product = (String) adapterView.getItemAtPosition(position);
                mEditTextName.setText(product);
            }
        });

        mTextViewAmount = findViewById(R.id.textview_amount);
        buttonIncrease = findViewById(R.id.button_increase);
        buttonDecrease = findViewById(R.id.button_decrease);
        buttonAdd = findViewById(R.id.button_add);
    }

    private void increase() {
        mAmount++;
        mTextViewAmount.setText(String.valueOf(mAmount));
    }

    private void decrease() {
        if (mAmount > 0) {
            mAmount--;
            mTextViewAmount.setText(String.valueOf(mAmount));
        }
    }
}