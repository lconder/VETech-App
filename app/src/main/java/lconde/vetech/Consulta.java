package lconde.vetech;


import android.os.Parcel;
import android.os.Parcelable;

public class Consulta implements Parcelable
{
    String Fecha;
    String sintomas;
    String diagnostico;
    String receta;

    public Consulta(String fecha, String sintomas, String diagnostico, String receta) {
        Fecha = fecha;
        this.sintomas = sintomas;
        this.diagnostico = diagnostico;
        this.receta = receta;
    }


    private Consulta(Parcel in) {
        Fecha = in.readString();
        sintomas = in.readString();
        diagnostico = in.readString();
        receta = in.readString();
    }

    public static final Creator<Consulta> CREATOR = new Creator<Consulta>() {
        @Override
        public Consulta createFromParcel(Parcel in) {
            return new Consulta(in);
        }

        @Override
        public Consulta[] newArray(int size) {
            return new Consulta[size];
        }
    };

    @Override
    public int describeContents()
    {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(Fecha);
        dest.writeString(sintomas);
        dest.writeString(diagnostico);
        dest.writeString(receta);
    }

    public void setFecha(String fecha)
    {
        Fecha = fecha;
    }

    public void setSintomas(String sintomas)
    {
        this.sintomas = sintomas;
    }

    public void setDiagnostico(String diagnostico)
    {
        this.diagnostico = diagnostico;
    }

    public void setReceta(String receta)
    {
        this.receta = receta;
    }

    public String getFecha() {return Fecha;}

    public String getSintomas()
    {
        return sintomas;
    }

    public String getDiagnostico()
    {
        return diagnostico;
    }

    public String getReceta()
    {
        return receta;
    }
}
