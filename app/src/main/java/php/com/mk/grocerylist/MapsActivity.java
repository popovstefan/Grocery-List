package php.com.mk.grocerylist;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SupportPlaceAutocompleteFragment supportPlaceAutocompleteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        requestFineLocationPermission();

        // Google Place auto complete fragment
        supportPlaceAutocompleteFragment = (SupportPlaceAutocompleteFragment) getSupportFragmentManager()
                .findFragmentById(R.id.place_autocomplete);
        // On click listener
        supportPlaceAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            /**
             * Creates an intent and populates it with the
             * location of the newly created grocery list.
             * Then the activity finishes with an OK result.
             * @param place which was selected
             */
            @Override
            public void onPlaceSelected(Place place) {
                Intent intent = new Intent();
                intent.putExtra("location", place.getName().toString());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }

            @Override
            public void onError(Status status) {
            }
        });
    }

    /**
     * Requests a fine location permission if one is not already granted
     */
    private void requestFineLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, permissions, 1);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
