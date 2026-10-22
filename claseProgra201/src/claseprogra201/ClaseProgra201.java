/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package claseprogra201;

/**
 *
 * @author marlo
 */
import java.util.Calendar;
import java.util.Scanner;
public class ClaseProgra201 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Banco banco = new Banco();
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            try {
                System.out.println("\n--- FINANZAS DEL VALLE - MENÚ BANCO ---");
                System.out.println("1. Crear cuenta");
                System.out.println("2. Depositar");
                System.out.println("3. Retirar");
                System.out.println("4. Transferir");
                System.out.println("5. Aplicar intereses a todas las cuentas");
                System.out.println("6. Ver reporte y saldo total");
                System.out.println("7. Ver historial de movimientos");
                System.out.println("8. Girar cheque");
                System.out.println("9. Salir");
                System.out.print("Seleccione una opción: ");

                int opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    case 1:
                        System.out.println("\nTipo de cuenta (1. Ahorro | 2. Cheques | 3. Plazo Fijo): ");
                        int tipoSel = Integer.parseInt(sc.nextLine());

                        System.out.print("Numero de cuenta: ");
                        int num = Integer.parseInt(sc.nextLine());
                        System.out.print("Nombre del cliente: ");
                        String cliente = sc.nextLine();
                        System.out.print("Moneda: ");
                        String moneda = sc.nextLine();

                        if (tipoSel == 1) {
                            cuentaAhorro ca = new cuentaAhorro(num, cliente, moneda);
                            banco.agregarCuenta(ca);
                            System.out.println("Cuenta de Ahorros creada con exito");
                        } else if (tipoSel == 2) {
                            System.out.print("Límite de sobregiro: ");
                            double sobregiro = Double.parseDouble(sc.nextLine());
                            cuentaCheques cc = new cuentaCheques(num, cliente, moneda, sobregiro);
                            banco.agregarCuenta(cc);
                            System.out.println("Cuenta de Cheques creada con éxito.");
                        } else if (tipoSel == 3) {
                            cuentaPlazoFijo cpf = new cuentaPlazoFijo(num, cliente, moneda);
                            System.out.print("Año de vencimiento (ej. 2026): ");
                            int anio = Integer.parseInt(sc.nextLine());
                            System.out.print("Mes de vencimiento (1-12): ");
                            int mes = Integer.parseInt(sc.nextLine());
                            System.out.print("Día de vencimiento (1-31): ");
                            int dia = Integer.parseInt(sc.nextLine());
                            cpf.setPlazo(anio, mes, dia);
                            banco.agregarCuenta(cpf);
                            System.out.println("Cuenta a Plazo Fijo creada con éxito.");
                        } else {
                            System.out.println("Tipo de cuenta no valido");
                        }
                        break;

                    case 2:
                        System.out.print("Número de cuenta: ");
                        int numDep = Integer.parseInt(sc.nextLine());
                        System.out.print("Monto a depositar: ");
                        double montoDep = Double.parseDouble(sc.nextLine());
                        banco.buscarPorNumero(numDep).depositar(montoDep);
                        System.out.println("Depósito realizado exitosamente.");
                        break;

                    case 3:
                        System.out.print("Número de cuenta: ");
                        int numRet = Integer.parseInt(sc.nextLine());
                        System.out.print("Monto a retirar: ");
                        double montoRet = Double.parseDouble(sc.nextLine());
                        boolean exitoRet = banco.buscarPorNumero(numRet).retirar(montoRet);
                        if (exitoRet) {
                            System.out.println("Retiro realizado exitosamente.");
                        } else {
                            System.out.println("No se pudo realizar el retiro (fondos o intereses insuficientes).");
                        }
                        break;
                    case 4:
                        System.out.print("Número de cuenta origen: ");
                        int numOrig = Integer.parseInt(sc.nextLine());
                        System.out.print("Número de cuenta destino: ");
                        int numDest = Integer.parseInt(sc.nextLine());
                        System.out.print("Monto a transferir: ");
                        double montoTrans = Double.parseDouble(sc.nextLine());
                        banco.transferir(numOrig, numDest, montoTrans);
                        System.out.println("Transferencia completada exitosamente.");
                        break;
                    case 5:
                        banco.aplicarInteresesGlobal();
                        System.out.println("Intereses aplicados globalmente a todas las cuentas.");
                        break;

                    case 6:
                        banco.generarReporte();
                        break;

                    case 7:
                        System.out.print("Número de cuenta: ");
                        int numHist = Integer.parseInt(sc.nextLine());
                        banco.buscarPorNumero(numHist).imprimirHistorial();
                        break;

                    case 8:
                        System.out.print("Número de cuenta de cheques: ");
                        int numChq = Integer.parseInt(sc.nextLine());
                        cuentaBancaria cBusqueda = banco.buscarPorNumero(numChq);
                        if (cBusqueda instanceof cuentaCheques) {
                            System.out.print("Monto del cheque a girar: ");
                            double montoChq = Double.parseDouble(sc.nextLine());
                            ((cuentaCheques) cBusqueda).girarCheque(montoChq);
                            System.out.println("Cheque girado exitosamente.");
                        } else {
                            System.out.println("La cuenta ingresada no es una Cuenta de Cheques.");
                        }
                        break;

                    case 9:
                        salir = true;
                        System.out.println("Gracias por usar el sistema de Finanzas del Valle!");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                        break;
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("\n[ERROR DE OPERACION]: " + e.getMessage());
            } 
            
             catch (Exception e) {
                System.out.println("\n[ERROR INESPERADO]: " + e.getMessage());
            }
    }
}
}

