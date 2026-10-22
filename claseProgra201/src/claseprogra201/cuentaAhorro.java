/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package claseprogra201;

/**
 *
 * @author marlo
 */
import java.util.Calendar;
public class cuentaAhorro extends cuentaBancaria{
private Calendar ultimaModif;
private int retirosRealizados =0;
private static final int max_retiros_mes = 3;
    public cuentaAhorro(int numero, String cliente, String moneda) {
        super(numero, cliente, moneda,TipoCuenta.ahorro);
        this.ultimaModif = Calendar.getInstance();
    }
    public boolean estaActiva() {
        Calendar limite = Calendar.getInstance();
        limite.add(Calendar.MONTH, -6);
        return ultimaModif.after(limite);
    }
    @Override
    public void depositar(double monto) {
        if (!estaActiva()) {
            throw new IllegalStateException("la cuenta de ahorro está inactiva por superar 6 meses sin movimientos");
        }
        super.depositar(monto);
        this.ultimaModif = Calendar.getInstance();
    }
    @Override
    public boolean retirar(double monto) {
        if (!estaActiva()) {
            throw new IllegalStateException("la cuenta de ahorro está inactiva por superar 6 meses sin movimientos");
        }
        boolean exito = super.retirar(monto);
        if (exito) {
            this.ultimaModif = Calendar.getInstance();
        }
        return exito;
    }
    @Override
    public void quienSoy() {
        System.out.println("Soy una CuentaAhorro");
    }

    @Override
    public String toString() {
        String estado = estaActiva() ? "activa" : "inactiva";
        return super.toString() + " | Estado: " + estado+" | Último uso: " + ultimaModif.getTime();
    }
   
}
