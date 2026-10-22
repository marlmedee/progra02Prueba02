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
public class cuentaPlazoFijo extends cuentaBancaria{
    private Calendar findePlazo;
    private double intereses;

    public cuentaPlazoFijo(int numero, String cliente, String moneda) {
        super(numero, cliente, moneda, TipoCuenta.plazo);
        this.intereses = 0.0;
        this.findePlazo = null;
    }

    public void setPlazo(int anio, int mes, int dia) {
        try {
            if (mes < 1 || mes > 12) {
                throw new IllegalArgumentException("El mes debe estar entre 1-12");
            }
            Calendar cal = Calendar.getInstance();
            cal.setLenient(false);
            cal.set(anio, mes - 1, dia); 
            cal.getTime(); 
            this.findePlazo = cal;
        } catch (Exception e) {
            throw new IllegalArgumentException("fecha de plazo no válida: "+e.getMessage());
        }
    }

    public boolean esVencida() {
        if (findePlazo == null) {
            return false;
        }
        Calendar hoy = Calendar.getInstance();
        return !hoy.before(findePlazo);
    }

    @Override
    public boolean retirar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a retirar debe ser mayor a cero.");
        }

        if (!esVencida()) {
           
            if (monto > intereses) {
                return false;
            }
            intereses -= monto;
            registrarMovimiento("retiro de intereses previo: " + monto + "  ,intereses: " + intereses);
            return true;
        } 
        else {
            if (intereses > 0) {
                saldo += intereses;
                registrarMovimiento("vencimiento alcanzado: intereses +" + intereses + " liquidados al saldo principal");
                intereses = 0.0;
            }
            return super.retirar(monto);
        }
    }
    @Override
    public void registrarIntereses() {
        double interesGanado = saldo * tipo.tasa();
        if(!esVencida()) {
            intereses += interesGanado;
            registrarMovimiento("intereses acumulados +" + interesGanado + " ,saldo de intereses: " + intereses);
        } 
        else{
            super.registrarIntereses();
        }
    }
    @Override
    public void reintegrar(double monto) {
        if (monto <= 0){
            throw new IllegalArgumentException("el monto a reintegrar tiene que ser mayor a 0");
        }
        if(!esVencida()) {
            intereses += monto;
            registrarMovimiento("reversa a interes +" + monto + " ,saldo de intereses: " + intereses);
        } 
        else {
            super.reintegrar(monto);
        }
    }

    @Override
    public void quienSoy() {
        System.out.println("Soy una cuentaPlazoFijo");
    }

    @Override
    public String toString() {
        String fechaVenc = (findePlazo != null) ? findePlazo.getTime().toString() : "No establecida";
        return super.toString()+" |intereses Acumulados: " + intereses + " | Fecha vencimiento: " + fechaVenc;
    }
}
