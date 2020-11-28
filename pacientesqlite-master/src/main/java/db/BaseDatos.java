package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BaseDatos {

    private String database;
    private Connection connection;
    private Statement statement;

    public BaseDatos(String db) throws ClassNotFoundException, SQLException {
        this.database = db;
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + database);
        this.statement = connection.createStatement();
    }

    public Connection getConnection() {
        return connection;
    }

    public List<Usuario> getUsuarioByName(String nombre, String password) throws SQLException {
        ResultSet rs = this.statement.executeQuery("select * from usuario where upper(nombre)='" + nombre.toUpperCase() + "' and password='" + password.toUpperCase() + "'");
        List<Usuario> usuario = new ArrayList();
        while (rs.next()) {
            Usuario temp = new Usuario();
            temp.setIdUsuario(rs.getInt("id_usuario"));
            temp.setIdUsuario(rs.getInt("nombre"));
            temp.setIdUsuario(rs.getInt("password"));
            temp.setIdUsuario(rs.getInt("rol"));
            usuario.add(temp);
        }
        return usuario;
    }

    public boolean addCustomer(Usuario usuario) throws SQLException {
        String sql = "insert into Customer(FirstName, LastName, Company, Address, "
                + "City, State, Country, PostalCode, Phone, Fax, Email, SupportRepId) "
                + "values (?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        /*prepStmt.setString(1, customer.getFirstName());
        prepStmt.setString(2, customer.getLastName());
        prepStmt.setString(3, customer.getCompany());
        prepStmt.setString(4, customer.getAddress());
        prepStmt.setString(5, customer.getCity());
        prepStmt.setString(6, customer.getState());
        prepStmt.setString(7, customer.getCountry());
        prepStmt.setString(8, customer.getPostalCode());
        prepStmt.setString(9, customer.getPhone());
        prepStmt.setString(10, customer.getFax());
        prepStmt.setString(11, customer.getEmail());
        prepStmt.setInt(12, customer.getSupportRepId());*/
        return prepStmt.execute();
    }

    public boolean agregarDoctor(String Nombre, String Apellido, String Especialidad) throws SQLException{

        String sql = "insert into Doctores(Nombre, Apellido, Especialidad)"
                +"values (?,?,?)";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
            prepStmt.setString(1, Nombre);
            prepStmt.setString(2, Apellido);
            prepStmt.setString(3,Especialidad);
        return prepStmt.execute();
    }


    public void listarDoctores() throws SQLException {


        String sql = "select Id_Doctor, Nombre, Apellido, Especialidad from Doctores";

        try(Statement state =this.connection.createStatement(); ResultSet Result = state.executeQuery(sql)){

            while(Result.next()){
                System.out.println(Result.getInt("Id_Doctor") + "   ||  " + Result.getString("Nombre") +" "+
                        Result.getString("Apellido") + "\t" +"|| " + Result.getString("Especialidad"));

            }

        }catch (SQLException e){
            System.out.println("Error al listar");
        }

    }


    public boolean agregarCliente(String Nombre, String Apellido, Integer Telefono, Integer NumeroSocial) throws SQLException {
        String sql = "insert into Cliente(Nombre, Apellido, Telefono, NumeroSocial ) "
                + "values (?,?,?,?)";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        prepStmt.setString(1, Nombre);
        prepStmt.setString(2, Apellido);
        prepStmt.setInt(3, Telefono);
        prepStmt.setInt(4, NumeroSocial);
        return prepStmt.execute();
    }

    public void listarCliente()throws SQLException{

        String sql = "select Id_Cliente, Nombre, Apellido, Telefono, NumeroSocial from Cliente";

        try(Statement state1 =this.connection.createStatement(); ResultSet Result = state1.executeQuery(sql)){

            while(Result.next()){
                System.out.println(Result.getInt("Id_Cliente") + " || " + Result.getString("Nombre") +" "+
                        Result.getString("Apellido") + "\t" +" || " + Result.getInt("Telefono") +" || "+Result.getInt("NumeroSocial"));
            }

        }catch (SQLException e){
            System.out.println("Error al listar");
        }
    }

    public boolean agregarCitas(String Fecha, String Hora) throws SQLException{

        String sql = "insert into Citas(Fecha, Hora)" + "values(?,?)";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        prepStmt.setString(1, Fecha);
        prepStmt.setString(2, Hora);
        return prepStmt.execute();

    }

    public void listarCitas()throws SQLException{
        String sql = "select Id_Cita, Fecha, Hora from Citas";

        try(Statement state2 =this.connection.createStatement(); ResultSet Result = state2.executeQuery(sql)){

            while(Result.next()){
                System.out.println(Result.getInt("Id_Cita") + " || " + Result.getString("Fecha") +" "+
                        Result.getString("Hora"));
            }

        }catch (SQLException e){
            System.out.println("Error al listar");
        }
    }

    public boolean AgendarCita(Integer Id_Cita, Integer Id_Cliente, Integer Id_Doctor) throws SQLException{

        String sql = "insert into CitasAgendadas(Id_Cita, Id_Cliente, Id_Doctor)" + "values(?,?,?)";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        prepStmt.setInt(1, Id_Cita);
        prepStmt.setInt(2, Id_Cliente);
        prepStmt.setInt(3, Id_Doctor);
        return prepStmt.execute();
    }

    public void listarCitasAgendadas()throws SQLException{
        String sql = "select c.Id_CitaAgendada, p.Nombre , d.Apellido, f.Fecha, f.Hora  " +
                "from CitasAgendadas c"+
                "join Cliente p on c.Id_Cliente = p.Id_Cliente"+
                "join Doctores d on c.Id_Doctor = d.Id_Doctor"+
                "join Citas f on c.Id_Cita = f.Id_Cita";

        try(Statement state3 =this.connection.createStatement(); ResultSet Result = state3.executeQuery(sql)){

            while(Result.next()){
                System.out.println(Result.getInt("Id_CitaAgendada") + " || " + Result.getString("Nombre") +" || "+"Doc. "+
                        Result.getString("Apellido") +" || "+ Result.getString("Fecha") +" "+
                        Result.getString("Hora"));
            }

        }catch (SQLException e){
            System.out.println("Error al listar");
        }

    }




    public int deleteCustomer(Usuario usuario) throws SQLException {
        String sql = "delete from Cliente where Id_Cliente = ? ";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        /*prepStmt.setInt(1, customer.getCustomerId());*/
        prepStmt.execute();
        return prepStmt.getUpdateCount();
    }








}
