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

/**
 * Activity corresponding to the screen where the user
 * creates a new product for a particular grocery list.
 */
public class NewListItemActivity extends AppCompatActivity {
    private EditText editTextName;
    private TextView textViewAmount;
    private Button buttonIncrease;
    private Button buttonDecrease;
    private Button buttonAdd;
    private int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list_item);
        initUI();

        // Setting on click listeners for the buttons
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

    /**
     * Connecting the elements defined in the layout with the
     * corresponding ones in this activity so that they can
     * be accessed in the program code.
     */
    public void initUI() {
        editTextName = findViewById(R.id.edittext_name);
        textViewAmount = findViewById(R.id.textview_amount);
        buttonIncrease = findViewById(R.id.button_increase);
        buttonDecrease = findViewById(R.id.button_decrease);
        buttonAdd = findViewById(R.id.button_add);
    }

    /**
     * Creates a GroceryList object which is the result
     * of the user's input. Puts that object in an intent
     * and finishes the activity with an OK result.
     */
    public void addItem() {
        // If the item name is empty, do nothing
        if (editTextName.getText().toString().trim().length() == 0 || quantity == 0)
            return;
        // Else, create the GroceryList object
        String name = editTextName.getText().toString();
        GroceryList result = new GroceryList();
        result.setName(name);
        result.setQuantity(quantity);
        // Put it into an intent
        Intent intent = new Intent();
        intent.putExtra("grocery_list", result);
        // Set the result to OK and finish with this activity
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    /**
     * Increase the item's quantity value.
     */
    private void increase() {
        quantity++;
        textViewAmount.setText(String.valueOf(quantity));
    }

    /**
     * Decrease the item's quantity value.
     */
    private void decrease() {
        if (quantity > 0) {
            quantity--;
            textViewAmount.setText(String.valueOf(quantity));
        }
    }
}