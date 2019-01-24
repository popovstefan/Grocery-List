package php.com.mk.grocerylist.recyclerHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import php.com.mk.grocerylist.R;


public class SubListRecyclerHolder extends RecyclerView.ViewHolder {
    private TextView textView;
    private Button boughtButton;

    public SubListRecyclerHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.txtItemSubList);
        boughtButton = itemView.findViewById(R.id.txtItemSubListBtn);
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public Button getBoughtButton() {
        return boughtButton;
    }

    public void setBoughtButton(Button boughtButton) {
        this.boughtButton = boughtButton;
    }
}


