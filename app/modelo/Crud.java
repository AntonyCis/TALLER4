package app.modelo;

import java.util.Map;

public interface Crud {

    //MOSTRAR TODOS
    public Map<Integer, Producto>
    seleccionarTodo ();

    //Mostrar Uno
    public Producto buscar(int id);

    //Insertar
    public void insertar (Producto producto);

    //Actualizar
    public void actualizar (Producto producto);

    //Eliminar
    public void eliminar (int id);
}
