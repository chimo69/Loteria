package proyecto.loteria;

import static proyecto.loteria.Global.CREA_TABLA_SI_NO_EXISTE;
import static proyecto.loteria.Global.MUESTRA_NUMEROS;
import static proyecto.loteria.Global.SUMA_IMPORTES;
import static proyecto.loteria.Global.listaNumeros;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    RecyclerView recycler;
    AdapterNumber adapter;
    SQLiteDatabase myDB;
    TextView txt_sin_decimos, txt_decimos_comprados, txt_importe_gastado, diasSorteo;
    CardView cv_info_decimos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_sin_decimos = findViewById(R.id.txt_sin_decimos);
        txt_decimos_comprados = findViewById(R.id.txt_decimos_comprados);
        txt_importe_gastado = findViewById(R.id.txt_importe_gastado);
        diasSorteo=findViewById(R.id.diasSorteo);
        cv_info_decimos = findViewById(R.id.cv_info_decimos);
        myDB = openOrCreateDatabase("loteria.db", MODE_PRIVATE, null);
        myDB.execSQL(CREA_TABLA_SI_NO_EXISTE);

        Cursor myCursor = myDB.rawQuery(MUESTRA_NUMEROS, null);

        listaNumeros.clear();

        while (myCursor.moveToNext()) {
            Integer id = myCursor.getInt(0);
            String numero = myCursor.getString(1);
            String serie = myCursor.getString(2);
            String fraccion = myCursor.getString(3);
            String euros = myCursor.getString(4);
            String fecha = myCursor.getString(5);
            String origen = myCursor.getString(6);
            String terminacion = myCursor.getString(7);
            String participantes = myCursor.getString(8);
            listaNumeros.addLast(new Navidad(id,numero, serie, fraccion, euros, fecha, origen,terminacion, participantes));
        }

        actualizaPantalla();
        myCursor.close();

        recycler = (RecyclerView) findViewById(R.id.recyclerNumeros);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AdapterNumber(listaNumeros, this);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, showDecimo.class);
                Navidad numeroElegido = listaNumeros.get(recycler.getChildAdapterPosition(view));
                intent.putExtra("numero", (Serializable) numeroElegido);

                startActivity(intent);

            }
        });
        recycler.setAdapter(adapter);
        myDB.close();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.masDecimos);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, nuevoDecimo.class);
                startActivity(intent);
            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback2 = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP, ItemTouchHelper.DOWN) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback( ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int origen = viewHolder.getAdapterPosition();
                int destino = target.getAbsoluteAdapterPosition();

                Collections.swap(listaNumeros, origen, destino);
                recyclerView.getAdapter().notifyItemMoved(origen,destino);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("!Atención!")
                        .setMessage("¿Estas seguro de borrar el numero "+listaNumeros.get(viewHolder.getAdapterPosition()).numero +" ?")
                        .setCancelable(true)
                        .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                eliminaNumeros(listaNumeros.get(viewHolder.getAdapterPosition()));
                                listaNumeros.remove(viewHolder.getAdapterPosition());
                                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                                txt_decimos_comprados.setText(String.valueOf(listaNumeros.size()));
                                actualizaPantalla();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                adapter.notifyDataSetChanged();
                                actualizaPantalla();
                            }
                        })

                        .show();
            }


        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recycler);

    }

    void eliminaNumeros(Navidad numero) {

        myDB = openOrCreateDatabase("loteria.db", MODE_PRIVATE, null);
        myDB.delete("navidad", "id=?", new String[]{String.valueOf(numero.id)});
        myDB.close();

    }

    int devuelveImporte() {
        myDB = openOrCreateDatabase("loteria.db", MODE_PRIVATE, null);
        Cursor myCursor = myDB.rawQuery(SUMA_IMPORTES, null);
        myCursor.moveToNext();
        Integer i = myCursor.getInt(0);
        myCursor.close();
        myDB.close();
        return i;
    }

    void actualizaPantalla() {

        if (listaNumeros.size() == 0) {
            txt_sin_decimos.setText(R.string.sin_decimos);
            cv_info_decimos.setVisibility(View.GONE);

        } else {

            txt_sin_decimos.setText(R.string.Decimos_loteria);
            txt_decimos_comprados.setText(String.valueOf(listaNumeros.size()));
            txt_importe_gastado.setText(devuelveImporte() + "€");
            cv_info_decimos.setVisibility(View.VISIBLE);

            Date actual = new Date();
            String pattern = "dd-MM-yyyy";
            String fechasorteo = "22-12-"+(actual.getYear()+1900);
            Log.d("AÑO","Año actual: "+fechasorteo);

            DateFormat dateFormat = new SimpleDateFormat(pattern);
            String fechaactual = dateFormat.format(actual);

            try {
                Date date1 = dateFormat.parse(fechasorteo);
                Date date2 = dateFormat.parse(fechaactual);

                // obtener la diferencia entre dos fechas en minutos
                long elapsedms = date1.getTime() - date2.getTime();
                long diff = TimeUnit.DAYS.convert(elapsedms, TimeUnit.MILLISECONDS);
                diasSorteo.setText(String.valueOf(diff));
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}