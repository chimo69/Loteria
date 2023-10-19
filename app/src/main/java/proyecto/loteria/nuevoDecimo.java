package proyecto.loteria;

import static proyecto.loteria.Global.CREA_TABLA_SI_NO_EXISTE;
import static proyecto.loteria.Global.listaParticipantes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class nuevoDecimo extends AppCompatActivity {


    EditText valorSerie;
    EditText valorFraccion;
    EditText valorImporte;
    EditText valorNumero;
    EditText valorOrigen;
    Button nuevoParticipante;
    Button agregar;
    SQLiteDatabase myDB;
    RecyclerView recycler;
    AdapterParticipantes adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_decimo);

        myDB = openOrCreateDatabase("loteria.db", MODE_PRIVATE, null);

        agregar = findViewById(R.id.btn_Agregar);
        valorNumero = findViewById(R.id.valorNumero);
        valorSerie = findViewById(R.id.valorSerie);
        valorFraccion = findViewById(R.id.valorFraccion);
        valorImporte = findViewById(R.id.valorImporte);
        valorOrigen = findViewById(R.id.valorOrigen);
        nuevoParticipante = findViewById(R.id.btn_agregarParticipantes);

        recycler = findViewById(R.id.rv_participantes);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AdapterParticipantes(listaParticipantes);
        recycler.setAdapter(adapter);
        listaParticipantes.clear();


        nuevoParticipante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaParticipantes.add(new Participante("","",listaParticipantes.size()+1));
                adapter.notifyDataSetChanged();
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (compruebaDatos(view)) {
                    myDB.execSQL(CREA_TABLA_SI_NO_EXISTE);
                    Gson gson= new Gson();
                    Type type = new TypeToken<LinkedList<Participante>>() {}.getType();
                    String gsonConverted=gson.toJson(listaParticipantes, type);

                    Log.d("PART", "Participantes JSON: "+gsonConverted);

                    for (Participante p: listaParticipantes) {
                        Log.d("PART","Participante: " + p.nombre);
                        Log.d("PART","Importe: " + p.participacion);
                    }

                    ContentValues nuevoDecimo = new ContentValues();
                    nuevoDecimo.put("numero", valorNumero.getText().toString());
                    nuevoDecimo.put("serie", valorSerie.getText().toString());
                    nuevoDecimo.put("fraccion", valorFraccion.getText().toString());
                    nuevoDecimo.put("euros", valorImporte.getText().toString());
                    nuevoDecimo.put("fecha", "22/12/2022");
                    nuevoDecimo.put("origen", valorOrigen.getText().toString());
                    nuevoDecimo.put("terminacion", valorNumero.getText().toString().substring(valorNumero.getText().toString().length() - 1));
                    nuevoDecimo.put("participantes",gsonConverted );

                    myDB.insert("navidad", null, nuevoDecimo);

                    Intent intent = new Intent(nuevoDecimo.this, MainActivity.class);
                    startActivity(intent);

                }

            }

        });


    }

    public boolean compruebaDatos(View view) {

        if (valorNumero.getText().toString().equalsIgnoreCase("")) {
            valorNumero.requestFocus();
            Snackbar.make(view, R.string.introduce_loteria, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (valorSerie.getText().toString().equalsIgnoreCase("")) {
            valorSerie.requestFocus();
            Snackbar.make(view, R.string.introduce_serie, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (valorFraccion.getText().toString().equalsIgnoreCase("")) {
            valorFraccion.requestFocus();
            Snackbar.make(view, R.string.introduce_fraccion, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (valorImporte.getText().toString().equalsIgnoreCase("")) {
            valorImporte.requestFocus();
            Snackbar.make(view, R.string.introduce_importe, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (valorOrigen.getText().toString().equalsIgnoreCase("")) {
            valorOrigen.requestFocus();
            Snackbar.make(view, R.string.origen_decimo, Snackbar.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}