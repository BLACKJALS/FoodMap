package Downloader;

/**
 * Created by Alejandro on 15/10/2016.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



import Parser.ParserMapa;

/**
 * Created by JHON ALEJANDRO on 05/06/2016.
 */
public class DownloaderMapa extends AsyncTask<Void,Integer,String> {
    Context c;
    String address;
    ListView lv;
    ProgressDialog pd;
    Button boton;
    Button boton1;

    public DownloaderMapa(Context c, String address, Button boton) {
        this.c = c;
        this.address = address;
        this.lv = lv;
        this.boton = boton;
    }



    //B4 JOB STARTS


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd=new ProgressDialog(c);
        pd.setTitle("Obtener datos");
        pd.setMessage("Obteniendo datos, espere.");
        pd.show();
    }



    @Override
    protected String doInBackground(Void... params) {
        String data=downloadData();
        return data;
    }




    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();
        if(s != null)
        {
            ParserMapa r = new ParserMapa(c,s,boton);
            r.execute();
        }else
        {
            Toast.makeText(c,"No se puede descargar los datos",Toast.LENGTH_SHORT).show();
        }
    }




    private String downloadData()
    {
        //connect and get a stream
        InputStream is=null;
        String line =null;
        try {
            URL url=new URL(address);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            is=new BufferedInputStream(con.getInputStream());
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            StringBuffer sb=new StringBuffer();
            if(br != null) {
                while ((line=br.readLine()) != null) {
                    sb.append(line+"\n");
                }
            }else {
                return null;
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is != null)
            {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
