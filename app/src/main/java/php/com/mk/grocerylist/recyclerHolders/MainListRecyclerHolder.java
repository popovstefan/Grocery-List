package php.com.mk.grocerylist.recyclerHolders;

import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import php.com.mk.grocerylist.R;
import php.com.mk.grocerylist.model.MainList;


public class MainListRecyclerHolder extends RecyclerView.ViewHolder {
    private TextView txtName;
    private TextView txtDate;
    private TextView txtPriority;
    private TextView txtLocation;
    private Button btnDone;
    private Button btnEmail;

    public MainListRecyclerHolder(@NonNull View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.txtName);
        txtDate = itemView.findViewById(R.id.txtDate);
        txtPriority = itemView.findViewById(R.id.txtPriority);
        txtLocation = itemView.findViewById(R.id.txtLocation);
        btnDone = itemView.findViewById(R.id.btnDone);
        btnEmail = itemView.findViewById(R.id.btnEmail);
    }

    public void bind(MainList mainList) {
        txtName.setText(mainList.getListName());
        txtDate.setText(mainList.getListDate().toString());
        txtPriority.setText(mainList.getPriority());
        txtLocation.setText(mainList.getLocation());
    }

    public Button getBtnDone() {
        return btnDone;
    }

    public TextView getTxtName() {
        return txtName;
    }

    public void setTxtName(TextView txtName) {
        this.txtName = txtName;
    }

    public TextView getTxtDate() {
        return txtDate;
    }

    public void setTxtDate(TextView txtDate) {
        this.txtDate = txtDate;
    }

    public TextView getTxtPriority() {
        return txtPriority;
    }

    public void setTxtPriority(TextView txtPriority) {
        this.txtPriority = txtPriority;
    }

    public Button getBtnEmail() {
        return btnEmail;
    }
}