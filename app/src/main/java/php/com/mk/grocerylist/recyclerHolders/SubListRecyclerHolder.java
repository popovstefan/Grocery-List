package php.com.mk.grocerylist.recyclerHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class SubListRecyclerHolder extends RecyclerView.ViewHolder {
    private TextView textView;

    public SubListRecyclerHolder(@NonNull View itemView) {
        super(itemView);
        textView = (TextView) itemView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}


