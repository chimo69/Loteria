package proyecto.loteria;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

/**
 * @Author Antonio Rodríguez Sirgado
 */
public class AdapterParticipantes extends RecyclerView.Adapter<AdapterParticipantes.ViewHolder> implements View.OnClickListener {

    public void setListaParticipantes(LinkedList<Participante> listaParticipantes) {
        this.listaParticipantes = listaParticipantes;
    }

    public LinkedList<Participante> getListaParticipantes() {
        return listaParticipantes;
    }

    LinkedList<Participante> listaParticipantes;
    private View.OnClickListener listener;

    public AdapterParticipantes(LinkedList<Participante> listaParticipantes) {
        this.listaParticipantes = listaParticipantes;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_borraParticipante:
                Log.d("PART", "pulsado el boton");
                break;
        }
        if (listener != null) {
            listener.onClick(view);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_participante, null, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.llenarDatos(listaParticipantes.get(position));
        holder.borraParticipante.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return listaParticipantes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        EditText nombre, importe;
        TextView numeroParticipante;
        ImageButton borraParticipante;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.et_itemPart_nombre);
            importe = itemView.findViewById(R.id.et_itemPart_importe);
            numeroParticipante = itemView.findViewById(R.id.tv_numero_participante);
            borraParticipante = itemView.findViewById(R.id.im_borraParticipante);


            nombre.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence nombre, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence nombre, int i, int i1, int i2) {
                    listaParticipantes.get(getAdapterPosition()).nombre = nombre.toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            importe.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence participacion, int i, int i1, int i2) {
                    listaParticipantes.get(getAdapterPosition()).participacion = participacion.toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }

        public void llenarDatos(Participante participante) {
            numeroParticipante.setText(String.valueOf(participante.numero));
        }
    }
}
