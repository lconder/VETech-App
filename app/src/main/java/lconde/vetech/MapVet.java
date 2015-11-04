package lconde.vetech;

import android.content.Context;
import android.location.LocationManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapVet extends AppCompatActivity {

    Toolbar toolbar;
    GoogleMap map;
    LocationManager mlocManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_vet);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if(Build.VERSION.SDK_INT < 19){
            FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
            statusBar.setVisibility(View.GONE);
        }

        if(Build.VERSION.SDK_INT >= 21)
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SupportMapFragment spmp = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        map=spmp.getMap();

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setMyLocationEnabled(true);

        mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

       // LocationListener mlocListener = new MyLocationListener();
        //mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);

        map.addMarker(new MarkerOptions()
                .position(new LatLng(18.997264, -98.203131))
                .title("Hospital Veterinario para pequeñas especies").icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker)));

        map.addMarker(new MarkerOptions()
                .position(new LatLng(18.996315, -98.210946))
                .title("Hospital Veterinario").icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker)));

        map.addMarker(new MarkerOptions()
                .position(new LatLng(19.022518, -98.219207))
                .title("Clínica Veterinari Animal House").icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*public class MyLocationListener implements LocationListener
    {

        @Override
        public void onLocationChanged(Location loc)
        {

            LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
            map.animateCamera(cameraUpdate);
            mlocManager.removeUpdates(this);

        }

        @Override
        public void onProviderDisabled(String provider)
        {
            Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
        }

        @Override
        public void onProviderEnabled(String provider)
        {
            Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }
    }*/

}