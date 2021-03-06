package lconde.vetech;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

public class nfcReader extends AppCompatActivity
{
    Toolbar toolbar;
    NfcAdapter nfcAdapter;
    HttpURLConnection con;
    ArrayList<Perros> perros=null;
    ArrayList<Dueno> dueno=null;
    String nombrePerro="";
    String nombreDueno;
    String raza="";
    Boolean sexo=true;
    String telefono;
    String direccion;
    String email;
    String imagen="";
    String descripcion;
    String recompensa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_reader);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if(Build.VERSION.SDK_INT < 19){
            FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
            statusBar.setVisibility(View.GONE);
        }

        if(Build.VERSION.SDK_INT >= 21)
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void onResume() {
        super.onResume();

        enableForegroundDispatchSystem();
    }

    @Override
    protected void onPause() {
        super.onPause();

        disableForegroundDispatchSystem();
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);

        if (intent.hasExtra(NfcAdapter.EXTRA_TAG))
        {
            //Toast.makeText(this, "NfcIntent!", Toast.LENGTH_SHORT).show();
            Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if(parcelables != null && parcelables.length > 0)
            {
                readTextFromMessage((NdefMessage) parcelables[0]);
            }else{
                Toast.makeText(this, "No NDEF messages found!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void readTextFromMessage(NdefMessage ndefMessage)
    {

        NdefRecord[] ndefRecords = ndefMessage.getRecords();

        if(ndefRecords != null && ndefRecords.length>0){

            NdefRecord ndefRecord = ndefRecords[0];
            String tagContent = getTextFromNdefRecord(ndefRecord);
            Asincrono(tagContent);



        }else
        {
            Toast.makeText(this, "No NDEF records found!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nfc_reader, menu);
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

    private void enableForegroundDispatchSystem() {

        Intent intent = new Intent(this, nfcReader.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        IntentFilter[] intentFilters = new IntentFilter[]{};

        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
    }

    private void disableForegroundDispatchSystem() {
        nfcAdapter.disableForegroundDispatch(this);
    }

    public String getTextFromNdefRecord(NdefRecord ndefRecord)
    {
        String tagContent = null;
        try {
            byte[] payload = ndefRecord.getPayload();
            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
            int languageSize = payload[0] & 0063;
            tagContent = new String(payload, languageSize + 1,
                    payload.length - languageSize - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("getTextFromNdefRecord", e.getMessage(), e);
        }
        return tagContent;
    }

    public void Asincrono(String id)
    {
        String url=getResources().getString(R.string.servidor)+"Api/"+id;
        try{
            ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected())
            {
                new JsonTask(nfcReader.this).execute(new URL(url));

            }
            else {
                Toast.makeText(this, "Error de Conexión", Toast.LENGTH_SHORT).show();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public class JsonTask extends AsyncTask<URL, Void, ArrayList<Perros>>
    {

        Context context;

        JsonTask(Context context)
        {
            this.context = context;
        }
        @Override
        protected ArrayList<Perros> doInBackground(URL... urls)
        {


            try{
                con = (HttpURLConnection)urls[0].openConnection();
                con.setConnectTimeout(15000);
                con.setReadTimeout(10000);

                int statusCode = con.getResponseCode();

                if(statusCode != 200)
                {
                    perros = new ArrayList<>();

                }else
                {
                    InputStream in = new BufferedInputStream(con.getInputStream());
                    JsonDogParser parser = new JsonDogParser();
                    perros = parser.leerFlujoJson(in);
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
            return perros;
        }

        @Override
        protected void onPostExecute(ArrayList<Perros> perros)
        {
            if (perros != null)
            {
                Toast.makeText(getApplicationContext(), "Buscando en Dueños", Toast.LENGTH_SHORT).show();
                String id = getResources().getString(R.string.servidor)+"Duenos/";

                for (Perros i : perros)
                {
                    id += i.getDueno();
                }
                try {
                    new JsonTaskD(context).execute(new URL(id));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }else
            {
                System.out.println("Error");
            }

        }

        public class JsonTaskD extends AsyncTask<URL, Void, ArrayList<Dueno>>
        {
             Context context;

            JsonTaskD (Context context)
            {
                this.context = context;
            }

            @Override
            protected ArrayList<Dueno> doInBackground(URL... urls) {


                try {
                    con = (HttpURLConnection) urls[0].openConnection();
                    con.setConnectTimeout(15000);
                    con.setReadTimeout(10000);

                    int statusCode = con.getResponseCode();

                    if (statusCode != 200) {
                        dueno = new ArrayList<>();

                    } else {
                        InputStream in = new BufferedInputStream(con.getInputStream());
                        JsonDuenoParser parser = new JsonDuenoParser();
                        dueno = parser.leerFlujoJson(in);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
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
            protected void onPostExecute(ArrayList<Dueno> dueno) {
                if (dueno != null)
                {
                    Toast.makeText(getApplicationContext(), "Buscando en Perros...", Toast.LENGTH_SHORT).show();
                    for (Dueno i : dueno)
                    {
                        telefono=i.getTelefono();
                        email=i.getEmail();
                        nombreDueno=i.getNombre();
                        direccion=i.getDireccion();
                    }
                    for (Perros k:perros)
                       {
                            nombrePerro=k.getNombre();
                            imagen=k.getPhoto();
                            raza=k.getRaza();
                            sexo=k.isSexo();
                            descripcion=k.getDescripcion();
                            recompensa=k.getRecompensa();
                        }
                    System.out.println("Array size: "+perros.size());
                    Intent i = new Intent(context, rastreado.class);
                    i.putExtra("Nombre", nombrePerro);
                    i.putExtra("Raza",raza);
                    i.putExtra("Imagen",imagen);
                    i.putExtra("Sexo",sexo);
                    i.putExtra("Descripcion",descripcion);
                    i.putExtra("Recompensa", recompensa);
                    i.putExtra("NombreD",nombreDueno);
                    i.putExtra("Direccion",direccion);
                    i.putExtra("Email",email);
                    i.putExtra("Telefono",telefono);
                    context.startActivity(i);
                } else {
                    System.out.println("Error");
                }
            }
        }
    }



}
