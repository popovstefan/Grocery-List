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

/**
 * Activity corresponding to the screen where the user
 * creates a new grocery list.
 */
public class NewListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText text;
    String priority;
    final Calendar calendar = Calendar.getInstance();
    final static int REQUEST_LOCATION = 12345;
    EditText edittext;
    String location = "";
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        // UI initializing
        fillSpinner();
        text = findViewById(R.id.newListName);
        edittext = findViewById(R.id.dateUntill);
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        // On click listener for the calendar
        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(NewListActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    /**
     * Formatter method for the calendar time.
     */
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edittext.setText(sdf.format(calendar.getTime()));
    }

    /**
     * Set's up the spinner, i.e. the element where the user picks a priority
     */
    public void fillSpinner() {
        Spinner spinner = findViewById(R.id.priority_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priorities_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    /**
     * Creates a MainList object which is the result
     * of the user's input. Puts that object in an intent
     * and finishes the activity with an OK result.
     * @param v unused
     */
    public void onAddNewListClick(View v) {
        // Create and set the object's properties
        MainList newList = new MainList();
        newList.setListName(text.getText().toString());
        newList.setPriority(priority);
        newList.setListDate(calendar.getTime());
        newList.setLocation(location);
        // Put it into an intent
        Intent intent = new Intent();
        intent.putExtra("newList", newList);
        // Set the result to OK, and finish with this activity
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    /**
     * Creates an explicit intent with which starts
     * the maps activity giving the user an option
     * to select a location for the new grocery list.
     * @param v unused
     */
    public void onSetLocation(View v) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivityForResult(intent, REQUEST_LOCATION);
    }

    /**
     * Sets the location of the new grocery list.
     * Is activated when the maps activity finishes.
     * The location is found in that activity's intent data.
     * @param requestCode is the code with which the maps activity was started with
     * @param resultCode is maps' activity result code
     * @param data is the intent the maps activity gives back after finishing
     */
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

    /**
     * Sets the priority for the new grocery list.
     * @param parent the adapter connecting the layout element with the data in it
     * @param view usused
     * @param position which was selected in the list of priorities
     * @param id unused
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        priority = parent.getItemAtPosition(position).toString();
    }

    /**
     * Defaults the priority if none is selected
     * @param parent unused
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        priority = "Low";
    }
}