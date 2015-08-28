package lconde.vetech;

import android.os.AsyncTask;
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

import org.json.JSONArray;
import org.json.JSONObject;

public class Bio extends AppCompatActivity
{
    Toolbar toolbar;
    TextView Nombre;
    TextView Raza;
    TextView Nacimiento;
    ImageView Sexo;
    TextView Descripcion;
    ImageView Imagen;
    String idPerro;
    String url1;
    Button adoptado;
    Button perdido;
    Boolean adoption;
    Boolean lost;
    JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio);
        Bundle extras = getIntent().getExtras();

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String Id = extras.getString("Id_Perro");

        Imagen = (ImageView) findViewById(R.id.imagen);
        Picasso.with(this).load(extras.getString("Imagen")).into(Imagen);

        Nombre = (TextView) findViewById(R.id.nombre);
        Nombre.setText(extras.getString("Nombre"));

        Raza = (TextView) findViewById(R.id.raza);
        Raza.setText(extras.getString("Raza"));
        //Nacimiento = (TextView) findViewById(R.id.nacimiento);
        Sexo = (ImageView) findViewById(R.id.sexo);
        if(extras.getBoolean("Sexo"))
            Picasso.with(this).load(R.mipmap.male).into(Sexo);
        else
            Picasso.with(this).load(R.mipmap.female).into(Sexo);
        //Descripcion = (TextView) findViewById(R.id.descripcion);

        adoptado = (Button) findViewById(R.id.adoptado);
        perdido = (Button) findViewById(R.id.perdido);

        adoption = extras.getBoolean("Adoptado");
        lost = extras.getBoolean("Perdido");

        if(extras.getBoolean("Adoptado"))
            adoptado.setBackgroundResource(R.mipmap.adoptado1);
        else
            adoptado.setBackgroundResource(R.mipmap.adoptado0);

        if(extras.getBoolean("Perdido"))
            perdido.setBackgroundResource(R.mipmap.perdido1);
        else
            perdido.setBackgroundResource(R.mipmap.perdido0);




        if(Build.VERSION.SDK_INT < 19){
            FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
            statusBar.setVisibility(View.GONE);
            /*ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
            layoutParams.height=0;*/
        }

        if(Build.VERSION.SDK_INT >= 21)
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void adoptado(View v)
    {
        Toast.makeText(this, "Adoptado", Toast.LENGTH_SHORT).show();
        if(adoption)
        {
            adoptado.setBackgroundResource(R.mipmap.adoptado1);
            adoption =false;
        }
        else
        {
            adoptado.setBackgroundResource(R.mipmap.adoptado0);
            adoption = true;
        }

    }

    public void perdido(View v)
    {
        Toast.makeText(this, "Perdido", Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bio, menu);
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
