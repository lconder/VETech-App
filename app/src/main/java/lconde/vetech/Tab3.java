package lconde.vetech;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class Tab3 extends Fragment
{
    SwipeRefreshLayout refreshLayout;
    RecyclerView mRecyclerView;
    HttpURLConnection con;
    ArrayList<Perros> perdidos=null;

    public Tab3(){}

    public static Tab3 newInstance(String param1, String param2) {
        Tab3 fragment = new Tab3();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_3, container, false);

        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefresh);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshLayout.setColorSchemeResources(
                R.color.md_blue_500,
                R.color.md_orange_500,
                R.color.md_black_1000
        );
        Asincrono();
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh()
                    {
                        Asincrono();
                    }
                }

        );
        return v;
    }

    public void Asincrono()
    {
        try{
            ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            //Toast.makeText(getActivity(),"networkInfo: "+networkInfo + " connected:  "+ networkInfo.isConnected(), Toast.LENGTH_LONG).show();
            if(networkInfo != null && networkInfo.isConnected())
            {
                new JsonTaskP().
                        execute(
                                new URL(getResources().getString(R.string.servidor)+"adoptados"));

            }
            else {
                Toast.makeText(getActivity(), "Error de Conexión", Toast.LENGTH_SHORT).show();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public class JsonTaskP extends AsyncTask<URL, Void, ArrayList<Perros>>
    {

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
                    perdidos = new ArrayList<>();

                }else
                {
                    InputStream in = new BufferedInputStream(con.getInputStream());
                    JsonDogParser parser = new JsonDogParser();
                    perdidos = parser.leerFlujoJson(in);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                con.disconnect();
            }

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return perdidos;
        }

        @Override
        protected void onPostExecute(ArrayList<Perros> perdidos)
        {
            if(perdidos != null)
            {
                Activity context=getActivity();
                mRecyclerView.setAdapter(new CardAdapter(perdidos, R.layout.card_view, context,3));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                refreshLayout.setRefreshing(false);
            }else
            {
                Toast.makeText(getActivity(),"Ocurrio un error de Parsing JSON", Toast.LENGTH_SHORT).show();
            }
        }
    }
}