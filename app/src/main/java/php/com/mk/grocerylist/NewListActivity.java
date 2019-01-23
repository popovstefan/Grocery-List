package php.com.mk.grocerylist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import php.com.mk.grocerylist.model.MainList;

public class NewListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText text;
    String selectedPriority;
    final Calendar myCalendar = Calendar.getInstance();
    final static int REQUEST_LOCATION = 12345;
    EditText edittext;
    String location = "";
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);
        //mainListRepository=new MainListRepository(this);
        fillSpinner();
        text = findViewById(R.id.newListName);
        edittext = findViewById(R.id.dateUntill);

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(NewListActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edittext.setText(sdf.format(myCalendar.getTime()));
    }

    public void fillSpinner() {
        Spinner spinner = findViewById(R.id.priority_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priorities_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void onAddNewListClick(View v) {
        MainList mainList = new MainList();
        mainList.setListName(text.getText().toString());
        mainList.setPriority(selectedPriority);
        mainList.setListDate(myCalendar.getTime());
        mainList.setLocation(location);
        //insert to DB
        Intent result = new Intent();
        //result.putExtra("name",text.getText().toString());
        //result.putExtra("priority",selectedPriority);
        result.putExtra("newList", mainList);
        setResult(Activity.RESULT_OK, result);
        finish();
    }

    public void onSetLocation(View v) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivityForResult(intent, REQUEST_LOCATION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_LOCATION
                && resultCode == Activity.RESULT_OK
                && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null)
                location = extras.getString("location");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedPriority = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedPriority = "Low";
    }
}