package lconde.vetech;


import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bio extends AppCompatActivity
{
    Toolbar toolbar;
    TextView Nombre;
    TextView Raza;
    ImageView Sexo;
    ImageView Imagen;
    Button adoptado;
    Button perdido;
    Boolean adoption;
    Boolean lost;
    RecyclerView mRecyclerView;
    ArrayList<Consulta> consultas;



    String url;
    String url1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio);
        Bundle extras = getIntent().getExtras();



        consultas=extras.getParcelableArrayList("consultas");
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String Id = extras.getString("Id_Perro");
        url=getResources().getString(R.string.servidor)+"adoptados/"+Id;

        url1=getResources().getString(R.string.servidor)+"perdidos/"+Id;

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


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new AdapterConsultas(consultas, R.layout.item_list));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

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


        Map<String, String> params = new HashMap<String, String>();
        if (adoption)
            params.put("adoptado", "false");
        else
            params.put("adoptado", "true");

        params.put("id", "");

        JsonArrayPostRequest jsonArrRequest = new JsonArrayPostRequest(url,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        try {
                            for (int i = 0; i < response.length(); i++)
                            {
                                JSONArray location = (JSONArray) response.get(i);
                                Toast.makeText(getApplicationContext(), "OK: "+location.getString(1), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                //Log.d(TAG, "Error: " + error.getMessage());
               // Toast.makeText(getApplicationContext(),"Error: "+ error.getMessage(), Toast.LENGTH_SHORT).show();
                if(adoption){
                    Toast.makeText(getApplicationContext(),"Quitando de Adopción", Toast.LENGTH_SHORT).show();
                    adoption = false;
                    adoptado.setBackgroundResource(R.mipmap.adoptado0);
                }else{
                    Toast.makeText(getApplicationContext(),"Enviando a Adopción", Toast.LENGTH_SHORT).show();
                    adoption = true;
                    adoptado.setBackgroundResource(R.mipmap.adoptado1);
                }


            }
        }, params);
        // Adding request to request queue
        MySingleton.getInstance(this).addToRequestQueue(jsonArrRequest);

    }

    public void perdido(View v)
    {
        Map<String, String> param = new HashMap<String, String>();
        if (lost)
            param.put("perdido", "false");
        else
            param.put("perdido", "true");

        param.put("id", "");

        JsonArrayPostRequest jsonArrRequest = new JsonArrayPostRequest(url1,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        try {
                            for (int i = 0; i < response.length(); i++)
                            {
                                JSONArray location = (JSONArray) response.get(i);
                                Toast.makeText(getApplicationContext(), "OK: "+location.getString(1), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                //Log.d(TAG, "Error: " + error.getMessage());
                // Toast.makeText(getApplicationContext(),"Error: "+ error.getMessage(), Toast.LENGTH_SHORT).show();
                if(lost){
                    Toast.makeText(getApplicationContext(),"Quitando de Perdidos", Toast.LENGTH_SHORT).show();
                    lost = false;
                    perdido.setBackgroundResource(R.mipmap.perdido0);
                }else{
                    Toast.makeText(getApplicationContext(),"Enviando a Perdidos", Toast.LENGTH_SHORT).show();
                    lost = true;
                    perdido.setBackgroundResource(R.mipmap.perdido1);
                }


            }
        }, param);
        // Adding request to request queue
        MySingleton.getInstance(this).addToRequestQueue(jsonArrRequest);
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
