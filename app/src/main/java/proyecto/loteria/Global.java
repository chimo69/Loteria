package proyecto.loteria;

import java.util.LinkedList;

/**
 * @Author Antonio Rodríguez Sirgado
 */
public class Global {

    public static LinkedList<Navidad> listaNumeros= new LinkedList<>();
    public static LinkedList<Participante> listaParticipantes= new LinkedList<>();

    public static final String CREA_TABLA_SI_NO_EXISTE = "CREATE TABLE IF NOT EXISTS navidad (id INTEGER PRIMARY KEY AUTOINCREMENT ,numero INTEGER,  serie VARCHAR(3), fraccion VARCHAR(3), euros INTEGER, fecha DATE, origen VARCHAR (30), terminacion VARCHAR(1), participantes VARCHAR(200))";
    public static final String MUESTRA_NUMEROS="select id, numero,serie,fraccion,euros,fecha, origen, terminacion, participantes from navidad ORDER BY terminacion ASC";
    public static final String SUMA_IMPORTES="Select SUM(euros) from navidad";
}
