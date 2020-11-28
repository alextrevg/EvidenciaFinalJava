package db;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.sql.*;
import java.util.List;
import java.util.Scanner;


public class ConsultorioAdmin {

    static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) throws ClassNotFoundException, SQLException {



        int seleccion;
        String user = "";
        String password = "";
        BaseDatos persist = new BaseDatos("consultorio.db");
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("\nIngrese su usuario y contraseña para iniciar");
            System.out.println("Usuario:");
            user = scanner.nextLine();
            System.out.println("Contraseña:");
            password = scanner.nextLine();
            List<Usuario> usuario = persist.getUsuarioByName(user, password);
            if (!usuario.isEmpty()) {
                while (true) {
                    System.out.println("\n(1) Dar de alta doctores.");
                    System.out.println("(2) Dar de alta pacientes.");
                    System.out.println("(3) Listar doctor.");
                    System.out.println("(4) Listar pacientes.");
                    System.out.println("(5) Crear una cita con fecha y hora.");
                    System.out.println("(6) Listar citas.");
                    System.out.println("(7) Relacionar una cita con un doctor y un paciente");
                    System.out.println("(8) Mostrar citas");
                    System.out.println("(0) Salir");
                    System.out.println("\nPor favor ingrese una opción: ");
                    // Fin de Menu
                    // Try Anidado
                    try {
                        // Asigna token Integer parseado
                        seleccion = scanner.nextInt();
                        switch (seleccion) {
                            case 0:
                                System.out.println("Saliendo..");
                                logger.info("Saliendo...");
                                return;
                            case 1:
                                System.out.println("Ingresar Nombre");
                                Scanner nombreDoc = new Scanner(System.in);
                                String nomDc = nombreDoc.next();
                                System.out.println("Ingresar Apellido");
                                Scanner apellidoDoc = new Scanner(System.in);
                                String apeDc = apellidoDoc.next();
                                System.out.println("Ingresar Especialidad");
                                Scanner especialdiad = new Scanner(System.in);
                                String espe = especialdiad.next();

                                persist.agregarDoctor(nomDc, apeDc, espe);

                                break;
                            case 2:
                                System.out.println("Ingresar Nombre");
                                Scanner nombrePc = new Scanner(System.in);
                                String nomCliente = nombrePc.next();
                                System.out.println("Ingresar Apellido");
                                Scanner apellidoPc = new Scanner(System.in);
                                String apellPc = apellidoPc.next();
                                System.out.println("Ingresar telefono");
                                Scanner telPc = new Scanner(System.in);
                                Integer tellPc = telPc.nextInt();
                                System.out.println("Ingresa Numero de seguro");
                                Scanner NumScPc = new Scanner(System.in);
                                Integer numscpc = NumScPc.nextInt();

                            persist.agregarCliente(nomCliente, apellPc, tellPc, numscpc);

                                break;
                            case 3:
                                System.out.println("\nDoctores\n");
                                persist.listarDoctores();

                                break;
                            case 4:
                                System.out.println("\nPacientes\n");
                                persist.listarCliente();
                                break;

                            case 5:

                                System.out.println("Ingresar Fecha: En formato DD/MM/AAAA");
                                Scanner fecha = new Scanner(System.in);
                                String date = fecha.next();
                                System.out.println("Ingresar hora: En formato 00:00");
                                Scanner hora = new Scanner(System.in);
                                String hour = hora.next();

                                persist.agregarCitas(date, hour);
                                break;
                            case 6:
                                    persist.listarCitas();
                                break;
                            case 7:
                                System.out.println("Ingresar ID de la cita");
                                Scanner idcita = new Scanner(System.in);
                                Integer cita = idcita.nextInt();
                                System.out.println("Ingresar ID del paciente");
                                Scanner idpaciente = new Scanner(System.in);
                                Integer paciente = idpaciente.nextInt();
                                System.out.println("Ingresar ID del doctor");
                                Scanner iddoctor = new Scanner(System.in);
                                Integer doctor = iddoctor.nextInt();

                                    persist.AgendarCita(cita, paciente, doctor);
                                break;
                            case 8:
                                System.out.println("Citas Agendadas");
                                persist.listarCitasAgendadas();
                                break;


                            default:
                                System.err.println("Opción inválida.");
                                logger.error("Opción inválida: {}", seleccion);
                                break;
                        }

                    } catch (Exception ex) {
                        logger.error("{}: {}", ex.getClass(), ex.getMessage());
                        System.err.format("Ocurrió un error. Para más información consulta el log de la aplicación.");
                        scanner.next();
                    }
                }
            } else {
                System.out.println("No tiene autorización");
            }
        } catch (Exception ex) {
            logger.error("{}: {}", ex.getClass(), ex.getMessage());
            System.err.format("Ocurrió un error. Para más información consulta el log de la aplicación.");
        } finally {
            persist.getConnection().close();
        }
    }
}
