package php.com.mk.grocerylist.recyclerHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import php.com.mk.grocerylist.R;
import php.com.mk.grocerylist.model.GroceryList;

/**
 * Recycler holder class for a particular grocery list.
 * Each view has getter and setter methods.
 */
public class SubListRecyclerHolder extends RecyclerView.ViewHolder {
    private TextView nameTextView;
    private TextView quantityTextView;
    private Button boughtButton;

    public SubListRecyclerHolder(@NonNull View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.txtItemSubList);
        quantityTextView = itemView.findViewById(R.id.txtQuantity);
        boughtButton = itemView.findViewById(R.id.txtItemSubListBtn);
    }

    public void bind(final GroceryList groceryList) {
        nameTextView.setText(groceryList.getName());
        quantityTextView.setText(String.valueOf(groceryList.getQuantity()));
    }

    public TextView getNameTextView() {
        return nameTextView;
    }

    public void setNameTextView(TextView nameTextView) {
        this.nameTextView = nameTextView;
    }

    public Button getBoughtButton() {
        return boughtButton;
    }

    public void setBoughtButton(Button boughtButton) {
        this.boughtButton = boughtButton;
    }

    public TextView getQuantityTextView() { return quantityTextView; }

    public void setQuantityTextView(TextView quantityTextView) {
        this.quantityTextView = quantityTextView;
    }
}


