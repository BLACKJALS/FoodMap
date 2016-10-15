package com.example.alejandro.foodmap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import Downloader.DownloaderMapa;

public class Inicio extends AppCompatActivity {
    Button botonAcercaDe;
    Button mapa;
    public static ArrayList<String> coordenadas;
    String url="http://192.168.0.14:81/FoodMap/restaurantes.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        botonAcercaDe = (Button)findViewById(R.id.button2);

        botonAcercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, AcercaDe.class);
                startActivity(intent);
            }
        });

        Button boton = (Button)findViewById(R.id.button);
        DownloaderMapa d =new DownloaderMapa(this,url,boton);
        d.execute();

    }
}
