import java.util.*;

public class Grafo {
    private Map<String, Nodo> nodos;
    private Map<String, Map<String, Integer>> aristas;

    public Grafo() {
        nodos = new HashMap<>();
        aristas = new HashMap<>();
    }

    public void agregarNodo(String id, String nombre, int edad) {
        nodos.put(id, new Nodo(id, nombre, edad));
        aristas.put(id, new HashMap<>());
    }

    public void agregarNodo(String id, String nombre) {
        nodos.put(id, new Nodo(id, nombre));
        aristas.put(id, new HashMap<>());
    }

    public void agregarArista(String origen, String destino, int peso) {
        if (nodos.containsKey(origen) && nodos.containsKey(destino)) {
            aristas.get(origen).put(destino, peso);
        }
    }

    public void eliminarNodo(String id) {
        if (nodos.containsKey(id)) {
            nodos.remove(id);
            aristas.remove(id);
            for (String key : aristas.keySet()) {
                aristas.get(key).remove(id);
            }
        }
    }

    public void eliminarArista(String origen, String destino) {
        if (nodos.containsKey(origen) && nodos.containsKey(destino)) {
            aristas.get(origen).remove(destino);
        }
    }

    public boolean existeNodo(String id) {
        return nodos.containsKey(id);
    }

    public boolean existeArista(String origen, String destino) {
        return aristas.containsKey(origen) && aristas.get(origen).containsKey(destino);
    }

    public Nodo buscarNodoPorNombre(String nombre) {
        for (Nodo nodo : nodos.values()) {
            if (nodo.nombre.equalsIgnoreCase(nombre)) {
                return nodo;
            }
        }
        return null;
    }

    public List<Nodo> obtenerLibrosAsociados(String idUsuario) {
        List<Nodo> libros = new ArrayList<>();
        if (aristas.containsKey(idUsuario)) {
            for (String idLibro : aristas.get(idUsuario).keySet()) {
                libros.add(nodos.get(idLibro));
            }
        }
        return libros;
    }

    public void mostrarGrafo() {
        for (String origen : aristas.keySet()) {
            for (String destino : aristas.get(origen).keySet()) {
                System.out.println("Arista de " + origen + " a " + destino + " con peso " + aristas.get(origen).get(destino));
            }
        }
    }

    public void mostrarNodos() {
        for (Nodo nodo : nodos.values()) {
            System.out.println("Nodo ID: " + nodo.id + ", Nombre: " + nodo.nombre + (nodo.edad != -1 ? ", Edad: " + nodo.edad : ""));
        }
    }

    // Método público para obtener los nodos
    public Map<String, Nodo> obtenerNodos() {
        return nodos;
    }
}
