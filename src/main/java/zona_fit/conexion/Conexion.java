package zona_fit.conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    public static Connection getConexion(){
        Connection conexion = null;
        var baseDatos = "zona_fit_db";
        var url = "jdbc:mysql://localhost:3307/"+baseDatos;
        var usuario = "zonafit_user";
        var password = "Sumatra2025@";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url,usuario,password);
        } catch (Exception e) {
            System.out.println("Error al conectarnos a  la base de datos" +e.getMessage());
        }
        return conexion;
    }

    public static void main(String[] args) {
        var conexion = getConexion();
        if(conexion != null){
            System.out.println("Conexion exitosa"+conexion);
        }else{
            System.out.println("Error al conectarnos a la base de datos ");
        }
    }
}

