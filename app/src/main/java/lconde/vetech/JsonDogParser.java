package lconde.vetech;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class JsonDogParser
{
    public ArrayList<Perros> leerFlujoJson(InputStream in) throws IOException
    {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));

        try{
            return leerArrayDogs(reader);
        }finally{
            reader.close();
        }
    }


    public ArrayList<Perros> leerArrayDogs(JsonReader reader) throws IOException
    {
        ArrayList<Perros> perros = new ArrayList<>();

        reader.beginArray();

        while(reader.hasNext())
        {
            perros.add(leerPerro(reader));
        }
        reader.endArray();
        return perros;
    }

    public Perros leerPerro(JsonReader reader) throws  IOException
    {
        String nombre=null;
        String raza=null;
        String nacimiento=null;
        boolean sexo=false;
        String alimentacion=null;
        String imagen=null;
        String idPerro=null;
        String descripcion=null;
        boolean adoptado=false;
        boolean perdido = false;

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
                case "imagen":
                    imagen= reader.nextString();
                    System.out.println("Imagen: "+imagen+"\n");
                    break;
                case "id_perro":
                    idPerro = reader.nextString();
                    break;
                case "raza":
                    raza= reader.nextString();
                    break;
                case "alimentacion":
                    alimentacion= reader.nextString();
                    break;

                case "descripcion":
                    descripcion= reader.nextString();
                    break;
                case "nacimiento":
                    nacimiento= reader.nextString();
                    break;
                case "sexo":
                    sexo = reader.nextBoolean();
                    break;
                case "perdido":
                    perdido = reader.nextBoolean();
                    break;
                case "adoptado":
                    adoptado = reader.nextBoolean();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Perros(nombre, imagen,  raza,  nacimiento,  sexo,  alimentacion,  idPerro,  descripcion, adoptado, perdido);
    }
}


