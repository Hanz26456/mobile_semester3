package koneksi;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class koneksi {
    static String MyDriver = "com.mysql.jdbc.Driver";
    static String url = "jdbc:mysql://localhost:3306/db_focon";
    static String user = "root";
    static String pwd = "";
    static Connection cn;
    static Connection koneksi;
    public static Connection GetConn(){
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        }   catch (ClassNotFoundException ex){
//            System.out.println ("Gagal Koneksi"+ex.getMessage());
//        }
        
//        String url ="jdbc:mysql://localhost:3306/db_focon";
        try {
            koneksi = DriverManager.getConnection(url,"root","");
//            System.out.println("Berhasil Koneksi");
            System.out.println("Berhasil Koneksi Database");
        }   catch (SQLException ex){
            System.out.println("Gagal Koneksi Database "+ex.getMessage()    );
        }
        return koneksi;
    }
}
