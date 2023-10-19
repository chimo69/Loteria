package proyecto.loteria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import static proyecto.loteria.Global.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class showDecimo extends AppCompatActivity {

    Navidad numeroMostrar;
    TextView txt_visual_numero, txt_visual_serie, txt_visual_fraccion, txt_visual_euros, txt_visual_origen,txt_visual_origen2;
    ListView listView;
    Type listType;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_decimo);
        txt_visual_numero = findViewById(R.id.txt_visual_numero);
        txt_visual_serie = findViewById(R.id.txt_visual_serie);
        txt_visual_fraccion = findViewById(R.id.txt_visual_fraccion);
        txt_visual_euros = findViewById(R.id.txt_visual_euros);
        txt_visual_origen=findViewById(R.id.txt_visual_origen);
        txt_visual_origen2=findViewById(R.id.txt_visual_origen2);
        listView = findViewById(R.id.lv_participantes);
        gson= new Gson();

        Intent intent = getIntent();
        numeroMostrar = (Navidad) intent.getSerializableExtra("numero");

        ArrayList<String> listado = new ArrayList<>();

        listaParticipantes.clear();
        listType = new TypeToken<LinkedList<Participante>>(){}.getType();
        listaParticipantes = gson.fromJson(numeroMostrar.participantes, listType);

        for (Participante p: listaParticipantes){
            listado.add(p.nombre+" - "+p.participacion + "€");
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listado);
        listView.setAdapter(adapter);

        txt_visual_numero.setText(convierteDigitos(Integer.parseInt(numeroMostrar.numero)));
        txt_visual_serie.setText(numeroMostrar.serie+"ª");
        txt_visual_fraccion.setText(numeroMostrar.fraccion+"ª");
        txt_visual_euros.setText(numeroMostrar.euros);
        txt_visual_origen2.setText(numeroMostrar.origen);
    }

    String convierteDigitos (int numero){
        return String.format("%05d",numero);
    }
}