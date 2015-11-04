package lconde.vetech;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class rastreado extends AppCompatActivity
{


    Toolbar toolbar;
    TextView Nombre;
    TextView Raza;
    TextView NombreD;
    TextView direccion;
    Button telefono;
    TextView email;
    ImageView Sexo;
    TextView Descripcion;
    ImageView Imagen;
    String tel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rastreado);
        Bundle extras = getIntent().getExtras();

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Imagen = (ImageView) findViewById(R.id.imagen);
        Picasso.with(this).load(extras.getString("Imagen")).into(Imagen);

        Nombre = (TextView) findViewById(R.id.nombre);
        Nombre.setText(extras.getString("Nombre"));

        Raza = (TextView) findViewById(R.id.raza);
        Raza.setText(extras.getString("Raza"));
        //Nacimiento = (TextView) findViewById(R.id.nacimiento);

        Descripcion = (TextView) findViewById(R.id.descripcion);
        Descripcion.setText(extras.getString("Descripcion"));
        Sexo = (ImageView) findViewById(R.id.sexo);
        if(extras.getBoolean("Sexo"))
            Picasso.with(this).load(R.mipmap.male).into(Sexo);
        else
            Picasso.with(this).load(R.mipmap.female).into(Sexo);
        //Descripcion = (TextView) findViewById(R.id.descripcion);

        NombreD = (TextView) findViewById(R.id.nombreD);
        NombreD.setText(extras.getString("NombreD"));

        direccion = (TextView) findViewById(R.id.direccion);
        direccion.setText(extras.getString("Direccion"));

        telefono = (Button) findViewById(R.id.telefono);
        tel="tel:+52"+extras.getString("Telefono");


        //tel:+522221026541
        telefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Se realizará una llamada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(tel));
                startActivity(intent);
            }
        });


        if(Build.VERSION.SDK_INT < 19){
            FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
            statusBar.setVisibility(View.GONE);
            /*ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
            layoutParams.height=0;*/
        }

        if(Build.VERSION.SDK_INT >= 21)
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rastreado, menu);
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
}




