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
        return false;
    }

    @Override
    public boolean modificarCliente(Cliente cliente) {
        return false;
    }

    @Override
    public boolean eliminarCliente(Cliente cliente) {
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
        var cliente1 = new Cliente(1);
        System.out.println("Cliuente antes de la busqueda "+cliente1);
        var encontrado = clienteDAO.buscarClientePorId(cliente1);
        if(encontrado)
            System.out.println("Cliente encontrado "+cliente1);
        else
            System.out.println("Cliente no encontrado "+cliente1.getId());
    }
}
