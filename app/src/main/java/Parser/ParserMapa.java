package Parser;

/**
 * Created by Alejandro on 15/10/2016.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alejandro.foodmap.Inicio;
import com.example.alejandro.foodmap.Mapa;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by JHON ALEJANDRO on 05/06/2016.
 */
public class ParserMapa extends AsyncTask<Void,Integer,Integer> {
    Context c;
    ListView lv;
    Button boton;
    String data;
    ArrayList<String> nombres=new ArrayList<>();
    ProgressDialog pd;
    String ruta;



    public ParserMapa(Context c, String data, Button boton) {
        this.c = c;
        this.data = data;
        this.lv = lv;
        this.boton = boton;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd=new ProgressDialog(c);
        pd.setTitle("Analizador");
        pd.setMessage("Analizando, espere");
        pd.show();
    }




    @Override
    protected Integer doInBackground(Void... params) {
        return this.parse();
    }




    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(integer == 1)
        {
            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent siguiente = new Intent(c.getApplicationContext(), Mapa.class);
                    c.startActivity(siguiente);
                }
            });

        }else
        {
            Toast.makeText(c,"No se puede analizar",Toast.LENGTH_SHORT).show();
        }
        pd.dismiss();
    }




    //PARSE RECEIVED DATA
    private int parse()
    {
        try
        {
            //ADD THAT DATA TO JSON ARRAY FIRST
            JSONArray ja=new JSONArray(data);
            //CREATE JO OBJ TO HOLD A SINGLE ITEM
            JSONObject jo = null;
            nombres.clear();
            //LOOP THRU ARRAY
            for(int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);

                //RETRIOEVE NAME
                String name=jo.getString("nombre");
                String longitud = jo.getString("longitud");
                String latitud = jo.getString("latitud");

                //ADD IT TO OUR ARRAYLIST
                nombres.add(name+","+longitud+","+latitud);
            }

            Inicio.coordenadas = new ArrayList<>();
            Inicio.coordenadas.addAll(nombres);
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
