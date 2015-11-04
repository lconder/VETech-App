package lconde.vetech;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class JsonDuenoParser
{
    public ArrayList<Dueno> leerFlujoJson(InputStream in) throws IOException
    {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));

        try{
            return leerArrayDuenos(reader);
        }finally{
            reader.close();
        }
    }

    public ArrayList<Dueno> leerArrayDuenos(JsonReader reader) throws IOException
    {
        ArrayList<Dueno> duenos = new ArrayList<>();

        reader.beginArray();

        while(reader.hasNext())
        {
            duenos.add(leerDueno(reader));
        }
        reader.endArray();
        return duenos;
    }

    public Dueno leerDueno(JsonReader reader) throws  IOException
    {
        String nombre=null;
        String direccion=null;
        String telefono=null;
        String email=null;
        String idDueno=null;

        reader.beginObject();
        System.out.println("LEYENDO:");
        while(reader.hasNext())
        {
            String name=reader.nextName();
            switch(name){
                case "nombre":
                    nombre= reader.nextString();
                    System.out.println("Nombre: "+nombre + "\n");
                    break;
                case "direccion":
                    direccion= reader.nextString();
                    break;
                case "id_dueno":
                    idDueno = reader.nextString();
                    break;
                case "telefono":
                    telefono= reader.nextString();
                    break;
                case "email":
                    email= reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return  new Dueno(idDueno, nombre, direccion, telefono, email);
    }
}
