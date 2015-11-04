package lconde.vetech;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class perdido extends AppCompatActivity
{
    Toolbar toolbar;
    TextView Nombre;
    TextView Raza;
    TextView NombreD;
    TextView direccion;
    Button telefono;
    ImageView Sexo;
    TextView Descripcion;
    ImageView Imagen;
    String tel;
    HttpURLConnection con;
   ArrayList<Dueno> dueno=null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perdido);

        Bundle extras = getIntent().getExtras();

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if(Build.VERSION.SDK_INT < 19){
            FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
            statusBar.setVisibility(View.GONE);
            /*ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
            layoutParams.height=0;*/
        }

        if(Build.VERSION.SDK_INT >= 21)
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        NombreD = (TextView) findViewById(R.id.nombreD);
        direccion = (TextView) findViewById(R.id.direccion);
        telefono = (Button) findViewById(R.id.telefono);

        Asincrono(extras.getString("IdDueno"));

        telefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Se realizará una llamada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(tel));
                startActivity(intent);
            }
        });
    }


    public void Asincrono(String id)
    {
        String url = getResources().getString(R.string.servidor)+"Duenos/"+id;
        try{
            ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected())
            {
                new JsonTask().execute(new URL(url));

            }
            else {
                Toast.makeText(this, "Error de Conexión", LENGTH_SHORT).show();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_perdido, menu);
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

    public class JsonTask extends AsyncTask<URL, Void, ArrayList<Dueno>>
    {
        JsonTask() {}
        @Override
        protected ArrayList<Dueno> doInBackground(URL... urls)
        {


            try{
                con = (HttpURLConnection)urls[0].openConnection();
                con.setConnectTimeout(15000);
                con.setReadTimeout(10000);

                int statusCode = con.getResponseCode();

                if(statusCode != 200)
                {
                    System.out.println("!200");
                    dueno = new ArrayList<>();
                }else
                {
                    System.out.println("200");
                    InputStream in = new BufferedInputStream(con.getInputStream());
                    JsonDuenoParser parser = new JsonDuenoParser();
                    dueno = parser.leerFlujoJson(in);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                con.disconnect();
            }

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return dueno;
        }

        @Override
        protected void onPostExecute(ArrayList<Dueno> dueno)
        {
            System.out.println("On Post Execute");
            if (dueno != null)
            {
                System.out.println("dueno != null");
                String temp = "";

                for (Dueno i : dueno)
                {
                    NombreD.setText(i.getNombre());
                    direccion.setText(i.getDireccion());
                    temp=i.getTelefono();
                }
                tel="tel:+52"+temp;

            }else
            {
                System.out.println("dueno == null");
            }

        }
    }
}
