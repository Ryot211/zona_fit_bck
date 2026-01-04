package zona_fit.presentacion;

import zona_fit.datos.ClienteDAO;
import zona_fit.datos.IClienteDAO;
import zona_fit.dominio.Cliente;

import java.util.Scanner;

public class ZonaFitAPP {

    public static void main(String[] args) {
        zonaFitAPP();

    }
    private static void zonaFitAPP(){
        var salir = false;
        var consola = new Scanner(System.in);
        // Creamos un objeto de la clase clienteDAO
        IClienteDAO clienteDAO = new ClienteDAO();
        while(!salir){
            try{
               var opcion = mostrarMenu(consola);
                salir = ejecutarOpciones(consola,opcion,clienteDAO);
            }catch(Exception e){
                System.out.println("Error al ejecutar opciones : "+e.getMessage());
            }
            System.out.println("---------------------------------------------");
        }

    }

    private static int  mostrarMenu(Scanner consola){
        System.out.println("""
                **** ZonaFit GYM ******
                1. Listar Cliente
                2. Buscar Cliente   
                3. Agregar Cliente
                4. Modificar Cliente
                5. Eliminar Cliente
                6. Salir
                
                Elige una opcion:\s""");
        return Integer.parseInt(consola.nextLine());

    }

    private static boolean ejecutarOpciones(Scanner consola, int opcion, IClienteDAO clienteDAO){
        var salir = false;
        switch (opcion){
            case 1 ->{// 1. Listar Clientes
                System.out.println("--- Listado de Cliente ----");
                var clientes = clienteDAO.listarClientes();
                clientes.forEach(System.out::println);
            }
            case 2 ->{// 2. Buscar Cliente
                System.out.println("--- Buscar Cliente ----"   );
                System.out.println("Ingresa el id del cliente a buscar :");
                var idCliente  = Integer.parseInt(consola.nextLine());
                var cliente = new Cliente(idCliente);
                var encontrado = clienteDAO.buscarClientePorId(cliente);
                if(encontrado){
                    System.out.println("Cliente encontrado"+cliente);
                }else{
                    System.out.println("Cliente no encontrado"+cliente);
                }
            }
            case 3 ->{
                System.out.println("--- Agregar Cliente ----");
                System.out.println("Ingresa el nombre del cliente a agregar :");
                var nombreCliente = consola.nextLine();
                System.out.println("Ingresa el apellido del cliente a agregar :");
                var apellidoCliente = consola.nextLine();
                System.out.println("Ingresa la membresia del cliente a agregar :");
                var membresiaCliente = Integer.parseInt(consola.nextLine());
                var cliente  = new Cliente(nombreCliente, apellidoCliente, membresiaCliente);
                var agregado = clienteDAO.agregarCliente(cliente);
                if(agregado){
                    System.out.println("Cliente agregado con exito "+cliente);
                }else{
                    System.out.println("Error al agregar cliente  "+cliente);
                }
            }
            case 4 ->{
                System.out.println("--- Modificar Cliente ----");
                System.out.println("Ingresa el id del cliente a Modificar :");
                var id = Integer.parseInt(consola.nextLine());
                System.out.println("Ingresa el nombre del cliente a Modificar :");
                var nombreCliente = consola.nextLine();
                System.out.println("Ingresa el Apellido del cliente a Modificar :");
                var apellidoCliente = consola.nextLine();
                System.out.println("Ingresa la membresia del cliente a modificar :");
                var membresiaCliente = Integer.parseInt(consola.nextLine());
                var cliente = new Cliente(id,nombreCliente,apellidoCliente,membresiaCliente);
                var modificado = clienteDAO.modificarCliente(cliente);
                if(modificado){
                    System.out.println("El cliente se modifico con exito !!!!"+ cliente);
                }else{
                    System.out.println("Error al modificar el cliente : "+cliente);
                }
            }
            case 5 ->{
                System.out.println("--- Eliminar Cliente ----"  );
                System.out.println("Ingresa el id del cliente a modificar ");
                var idCliente = Integer.parseInt(consola.nextLine());
                var cliente = new Cliente(idCliente);
                var eliminado = clienteDAO.eliminarCliente(cliente);
                if(eliminado){
                    System.out.println("Cliente eliminado con exito "+ cliente);
                }else{
                    System.out.println(" Error al eliminar un cliente "+cliente );
                }
            }
            case 6 ->{// 6. Salir
                System.out.println("Hasta Pronto ");
                salir = true;
            }
            default -> System.out.println("Opcion Incorrecta !!!!!!!!!!!!!");
        }
        return salir;

    }
}

