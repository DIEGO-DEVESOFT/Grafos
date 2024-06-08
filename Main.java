import javax.swing.*;
import java.awt.*;
import java.util.UUID;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Grafo grafo = new Grafo();

        // Llamar a las funciones iniciales para agregar usuarios y libros
        agregarUsuarios(grafo);
        agregarLibros(grafo);

        // Ejemplo de interacciones (esto puede ser modificado según se necesite)
        String idUsuarioEjemplo = grafo.obtenerNodos().keySet().stream().filter(id -> id.startsWith("Usuario")).findFirst().orElse(null);
        String idLibroEjemplo = grafo.obtenerNodos().keySet().stream().filter(id -> id.startsWith("Libro")).findFirst().orElse(null);
        if (idUsuarioEjemplo != null && idLibroEjemplo != null) {
            grafo.agregarArista(idUsuarioEjemplo, idLibroEjemplo, 1);
        }

        // Mostrar nodos y grafo
        grafo.mostrarNodos();
        grafo.mostrarGrafo();

        // Búsqueda de libros y usuarios
        while (true) {
            String[] opciones = {"Buscar Usuario", "Buscar Libro", "Ver Libros de un Usuario", "Asociar Libro a Usuario", "Agregar Usuarios", "Agregar Libros", "Salir"};
            JPanel panel = new JPanel(new FlowLayout());
            for (String opcion : opciones) {
                JButton button = new JButton(opcion);
                panel.add(button);
                button.addActionListener(e -> {
                    switch (opcion) {
                        case "Buscar Usuario":
                            buscarUsuario(grafo);
                            break;
                        case "Buscar Libro":
                            buscarLibro(grafo);
                            break;
                        case "Ver Libros de un Usuario":
                            verLibrosDeUsuario(grafo);
                            break;
                        case "Asociar Libro a Usuario":
                            asociarLibroAUsuario(grafo);
                            break;
                        case "Agregar Usuarios":
                            agregarUsuarios(grafo);
                            break;
                        case "Agregar Libros":
                            agregarLibros(grafo);
                            break;
                        case "Salir":
                            System.exit(0);
                            break;
                    }
                });
            }

            int option = JOptionPane.showOptionDialog(null, panel, "Menú de opciones",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
            if (option == JOptionPane.CLOSED_OPTION) {
                break;
            }
        }
    }

    public static void agregarUsuarios(Grafo grafo) {
        String nombreUsuario;
        while (true) {
            nombreUsuario = JOptionPane.showInputDialog("Ingrese el nombre del usuario (o 'fin' para terminar):");
            if (nombreUsuario == null || nombreUsuario.equalsIgnoreCase("fin")) {
                break;
            }
            String idUsuario = "Usuario" + UUID.randomUUID().toString(); // ID único para cada usuario

            int edadUsuario = -1;
            while (edadUsuario == -1) {
                try {
                    edadUsuario = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la edad del usuario:"));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido para la edad.");
                }
            }

            grafo.agregarNodo(idUsuario, nombreUsuario, edadUsuario);
        }
    }

    public static void agregarLibros(Grafo grafo) {
        String nombreLibro;
        while (true) {
            nombreLibro = JOptionPane.showInputDialog("Ingrese el nombre del libro (o 'fin' para terminar):");
            if (nombreLibro == null || nombreLibro.equalsIgnoreCase("fin")) {
                break;
            }
            String idLibro = "Libro" + UUID.randomUUID().toString(); // ID único para cada libro
            grafo.agregarNodo(idLibro, nombreLibro);
        }
    }

    public static void buscarUsuario(Grafo grafo) {
        String nombreBuscarUsuario = JOptionPane.showInputDialog("Ingrese el nombre del usuario a buscar:");
        Nodo usuario = grafo.buscarNodoPorNombre(nombreBuscarUsuario);
        if (usuario != null && usuario.edad != -1) {
            JOptionPane.showMessageDialog(null, "Usuario encontrado: " + usuario.nombre + ", Edad: " + usuario.edad);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
        }
    }

    public static void buscarLibro(Grafo grafo) {
        String nombreBuscarLibro = JOptionPane.showInputDialog("Ingrese el nombre del libro a buscar:");
        Nodo libro = grafo.buscarNodoPorNombre(nombreBuscarLibro);
        if (libro != null && libro.edad == -1) {
            JOptionPane.showMessageDialog(null, "Libro encontrado: " + libro.nombre);
        } else {
            JOptionPane.showMessageDialog(null, "Libro no encontrado.");
        }
    }

    public static void verLibrosDeUsuario(Grafo grafo) {
        String nombreUsuarioLibros = JOptionPane.showInputDialog("Ingrese el nombre del usuario para ver sus libros:");
        Nodo usuarioLibros = grafo.buscarNodoPorNombre(nombreUsuarioLibros);
        if (usuarioLibros != null && usuarioLibros.edad != -1) {
            List<Nodo> librosAsociados = grafo.obtenerLibrosAsociados(usuarioLibros.id);
            if (!librosAsociados.isEmpty()) {
                StringBuilder librosMsg = new StringBuilder("Libros asociados a " + usuarioLibros.nombre + ":");
                for (Nodo libroAsociado : librosAsociados) {
                    librosMsg.append("\n- ").append(libroAsociado.nombre);
                }
                JOptionPane.showMessageDialog(null, librosMsg.toString());
            } else {
                JOptionPane.showMessageDialog(null, "No hay libros asociados a este usuario.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
        }
    }

    public static void asociarLibroAUsuario(Grafo grafo) {
        String nombreUsuarioAsociar = JOptionPane.showInputDialog("Ingrese el nombre del usuario para asociar un libro:");
        Nodo usuarioAsociar = grafo.buscarNodoPorNombre(nombreUsuarioAsociar);
        if (usuarioAsociar != null && usuarioAsociar.edad != -1) {
            String nombreLibroAsociar = JOptionPane.showInputDialog("Ingrese el nombre del libro a asociar:");
            Nodo libroAsociar = grafo.buscarNodoPorNombre(nombreLibroAsociar);
            if (libroAsociar != null && libroAsociar.edad == -1) {
                grafo.agregarArista(usuarioAsociar.id, libroAsociar.id, 1);
                JOptionPane.showMessageDialog(null, "Libro asociado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Libro no encontrado.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
        }
    }
}
