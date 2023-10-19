package proyecto.loteria;

import java.io.Serializable;

/**
 * @Author Antonio Rodríguez Sirgado
 */
public class Navidad implements Serializable {
    public Navidad(Integer id, String numero, String serie, String fraccion, String euros, String fecha, String origen, String terminacion, String participantes) {
        this.id=id;
        this.numero = numero;
        this.serie = serie;
        this.fraccion = fraccion;
        this.euros = euros;
        this.fecha = fecha;
        this.origen=origen;
        this.terminacion=terminacion;
        this.participantes=participantes;
    }

    Integer id;
    String numero;
    String serie;
    String fraccion;
    String euros;
    String fecha;
    String origen;
    String terminacion;
    String participantes;
}


