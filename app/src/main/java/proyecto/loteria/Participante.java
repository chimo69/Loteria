package proyecto.loteria;

import java.io.Serializable;

/**
 * @Author Antonio Rodríguez Sirgado
 */
public class Participante implements Serializable {

    String nombre;
    String participacion;
    Integer numero;

    public Participante(String nombre, String participacion, Integer numero) {
        this.nombre = nombre;
        this.participacion = participacion;
        this.numero=numero;
    }

    public Participante(){}

}
