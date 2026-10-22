/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package claseprogra201;

/**
 *
 * @author marlo
 */
public class cuentaCheques extends cuentaBancaria{
    private double limiteSobregiro;

    public cuentaCheques(int numero, String cliente, String moneda, double limiteSobregiro) {
        super(numero, cliente, moneda, TipoCuenta.cheque);
        if (limiteSobregiro<0) {
            throw new IllegalArgumentException("el limite de sobregiro no puede ser negativo");
        }
        this.limiteSobregiro = limiteSobregiro;
    }
    public void girarCheque(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("el monto del cheque tiene que ser mayor a 0");
        }
        if (monto > (saldo + limiteSobregiro)) {
            throw new IllegalStateException("fondos insuficientes para girar el cheque");
        }
        saldo -= monto;
        registrarMovimiento("giro cheque-" + monto + " ,saldo: " + saldo);
    }
    @Override
    public void quienSoy() {
        System.out.println("Soy una cuentaCheques");
    }
    @Override
    public String toString() {
        double disponibleTotal = saldo + limiteSobregiro;
        return super.toString() + " |Sobregiro: "+ limiteSobregiro+" | Total Disponible: "+disponibleTotal;
    }
}
