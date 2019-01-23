package php.com.mk.grocerylist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import php.com.mk.grocerylist.model.GroceryList;

public class NewListItemActivity extends AppCompatActivity {
    private EditText mEditTextName; //ovde AutoComplete
    private TextView mTextViewAmount;
    Button buttonIncrease;
    Button buttonDecrease;
    Button buttonAdd;
    private int mAmount = 0;

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