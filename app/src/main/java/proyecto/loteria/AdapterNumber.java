package proyecto.loteria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

/**
 * @Author Antonio Rodríguez Sirgado
 */
public class AdapterNumber extends RecyclerView.Adapter<AdapterNumber.ViewHolder> implements View.OnClickListener {

    public AdapterNumber(LinkedList<Navidad> listaNumeros, Context context) {
        this.listaNumeros = listaNumeros;
        this.context = context;
    }

    LinkedList<Navidad> listaNumeros;
    Context context;
    private View.OnClickListener listener;

    @NonNull
    @Override
    public AdapterNumber.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_decimo, null, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNumber.ViewHolder holder, int position) {
        holder.llenarDatos(listaNumeros.get(position));
    }

    @Override
    public int getItemCount() {
        return listaNumeros.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView numero, serie, fraccion, precio, itemSerie, itemFraccion, itemPrecio;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            numero = itemView.findViewById(R.id.numLoteria);
            serie = itemView.findViewById(R.id.numSerie);
            fraccion = itemView.findViewById(R.id.numFraccion);
            precio = itemView.findViewById(R.id.numPrecio);
            cardView = itemView.findViewById(R.id.cv_item_decimo);
            itemSerie = itemView.findViewById(R.id.item_serie);
            itemFraccion = itemView.findViewById(R.id.item_fraccion);
            itemPrecio = itemView.findViewById(R.id.item_precio);
        }

        public void llenarDatos(Navidad navidad) {
            numero.setText(convierteDigitos(Integer.parseInt(navidad.numero)));

            serie.setText(navidad.serie);
            fraccion.setText(navidad.fraccion);
            precio.setText(navidad.euros + "€");

            String terminacion = navidad.numero.substring(navidad.numero.length() - 1);
            int color = 0;
            int colorletra = android.R.color.black;

            switch (terminacion) {
                case "0":
                    color = android.R.color.holo_blue_dark;
                    break;
                case "1":
                    color = android.R.color.holo_green_dark;
                    break;
                case "2":
                    color = android.R.color.holo_orange_dark;
                    break;
                case "3":
                    color = android.R.color.holo_orange_light;
                    break;
                case "4":
                    color = android.R.color.holo_purple;
                    break;
                case "5":
                    color = android.R.color.holo_red_light;
                    break;
                case "6":
                    color = android.R.color.black;
                    colorletra = android.R.color.white;
                    break;
                case "7":
                    color = android.R.color.darker_gray;
                    break;
                case "8":
                    color = android.R.color.holo_red_dark;
                    break;
                case "9":
                    color = android.R.color.system_accent3_300;
                    break;
            }
            cardView.setCardBackgroundColor(context.getResources().getColor(color));
            itemSerie.setTextColor(context.getResources().getColor(colorletra));
            itemFraccion.setTextColor(context.getResources().getColor(colorletra));
            itemPrecio.setTextColor(context.getResources().getColor(colorletra));
            serie.setTextColor(context.getResources().getColor(colorletra));
            fraccion.setTextColor(context.getResources().getColor(colorletra));
            precio.setTextColor(context.getResources().getColor(colorletra));
        }
    }

    String convierteDigitos(int numero) {
        return String.format("%05d", numero);
    }
}
