package zona_fit.datos;
import zona_fit.conexion.Conexion;
import zona_fit.dominio.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import static  zona_fit.conexion.Conexion.getConexion;

public class ClienteDAO implements  IClienteDAO{

    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection conexion = Conexion.getConexion();
        var sql = "SELECT * FROM cliente ORDER BY id";
        try{
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                var cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                clientes.add(cliente);
            }
        }catch(Exception e){
            System.out.println("Error al listar clientes: "+e.getMessage());
        }
        finally {try{

            conexion.close();
        }catch(Exception e){
            System.out.println("Error al cerrar conexion "+e.getMessage());
        }
        }
        return clientes;
    }

    @Override
    public boolean buscarClientePorId(Cliente cliente) {
        PreparedStatement ps;
        ResultSet rs;
        var con = getConexion();
        var sql = "SELECT * FROM cliente WHERE id= ?";
        try{
            ps= con.prepareStatement(sql);
            ps.setInt(1, cliente.getId());
            rs= ps.executeQuery();
            if(rs.next()){// pregunta si tiene un registro a leer
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                return true;
            }
        }catch(Exception e){
            System.out.println("Error al recuperar cliente por id : "+ e.getMessage());
        }
        finally {
            try{
                con.close();
        }catch(Exception e){
                System.out.println("Error al cerrar conexion "+e.getMessage());}
        }
        return false;
    }

    @Override
    public boolean agregarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "INSERT INTO cliente (nombre, apellido, membresia)"
                +" VALUES(?,?,?)";
        try{
            ps =con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getMembresia());
            ps.execute();
        }catch(Exception e){
            System.out.println("Error al agregar cliente: "+e.getMessage());
        }
        finally{

                    try{
                        con.close();
                    }catch(Exception e){
                        System.out.println("Error al cerrar conexcion "+e.getMessage());
                    }
        }

        return false;
    }

    @Override
    public boolean modificarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = getConexion();
        var sql = "UPDATE cliente set nombre=?,apellido=?,membresia=? "+
                "WHERE id = ?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getMembresia());
            ps.setInt(4, cliente.getId());
            ps.execute();
            return  true;
        }catch(Exception e){
            System.out.println("Error al modificar cliente: "+e.getMessage());
        }finally {
            try{
                con.close();
            }catch(Exception e){
                System.out.println("Error al cerrar conexion "+e.getMessage());
            }
        }

        return false;
    }

    @Override
    public boolean eliminarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = getConexion();
        var sql= "Delete from cliente where id= ?";
        try{ps = con.prepareStatement(sql);
            ps.setInt(1, cliente.getId());// es un parametro posicional
            ps.execute();
            return  true;

        }catch(Exception e){
            System.out.println("Error al eliminar cliente: "+e.getMessage());
        }finally{
            try{
                con.close();
            }catch(Exception e){
                System.out.println("Error al cerrar conexion "+e.getMessage());
            }
        }
        return false;
    }

    public static void main(String[] args) {
        IClienteDAO clienteDAO = new ClienteDAO();
        // Listar Clientes
//        System.out.println("Listar Clientes");
//        IClienteDAO clienteDAO = new ClienteDAO();
//        var clientes = clienteDAO.listarClientes();
//        clientes.forEach(System.out::println);
        // buscar cliente por id
//        var nuevoCliente = new Cliente("Romi","Arteaga",300);
//        var agregado= clienteDAO.agregarCliente(nuevoCliente);
//        if(agregado){
//            System.out.println("Cliente agregado "+nuevoCliente);
//        }else{
//            System.out.println("Error al agregar cliente "+nuevoCliente);
//        }
        // modificar cliente
//        var modificarCliente  = new Cliente(4,"Carmen","AS",300);
//        var modificado = clienteDAO.modificarCliente(modificarCliente);
//        if(modificado){
//            System.out.println("Cliente modificado");
//        }else{
//            System.out.println("Cliente modificado nulo");
//        }
        //* Eliminar Cliente
        var EliminarCliente = new Cliente(4);
        var eliminado = clienteDAO.eliminarCliente(EliminarCliente);
        if(eliminado){
            System.out.println("Cliente eliminado");
        }else{
            System.out.println("Cliente no eliminado");
        }


        // Listar Clientes
        System.out.println("Listar Clientes");
        var clientes = clienteDAO.listarClientes();
        clientes.forEach(System.out::println);
//

    }
}
///pr