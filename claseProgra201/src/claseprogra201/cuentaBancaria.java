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
      
public class cuentaBancaria {
    protected int numero;
    protected String cliente;
    protected double saldo;
    protected double tasa;
    protected Calendar apertura;
    protected String historial [] = new String[50];
    protected String moneda;
    protected TipoCuenta tipo;
    protected int cantidadMovimientos;
    public cuentaBancaria(int numero, String cliente, String moneda) {
        this(numero, cliente, moneda, TipoCuenta.cheque);
    }
    public cuentaBancaria(int numero, String cliente, String moneda,TipoCuenta tipo){  
        this.numero=numero;
     this.cliente=cliente;
     this.tipo = tipo;
     this.tasa=tasa; this.saldo=0;
     this.apertura=Calendar.getInstance(); 
     this.cantidadMovimientos =0;
    }
    public void registrarMovimiento(String linea) {
        try {
            if(cantidadMovimientos >=historial.length) {
                throw new IllegalStateException("El historial está lleno, no se pudo registrar: "+linea);
            }
            historial[cantidadMovimientos] = linea;
            cantidadMovimientos++;
        } 
        catch(IllegalStateException e) {
            System.out.println("Aviso: "+e.getMessage());
        }
    }
    public void depositar(double monto){
        if (monto <= 0) {
            throw new IllegalArgumentException("el monto a depositar debe ser mayor a 0");
        }
        saldo+=monto;
        registrarMovimiento("DEPOSITO +"+ monto+" saldo: "+saldo);
    }
    
    public boolean retirar (double monto){  
        if(monto<=0) {
            throw new IllegalArgumentException("el monto debe ser mayor a 0");
        }
        if (monto>saldo) {
            return false;
        }
        saldo-=monto;
        registrarMovimiento("RETIRO -"+monto+ " , saldo: " +saldo);
        return true;
    }
    
    public void mostrarHistorial(){ 
         System.out.println(" ===== HISTORIAL DE "+cliente. toUpperCase()+" ====== ");
         for(String registro:historial) {
             System.out.println(registro);
         }
    } 
    public void reintegrar(double monto) {
        if (monto<=0) {
            throw new IllegalArgumentException("El monto a reintegrar tiene que ser mayor a 0");
        }
        saldo+=monto;
        registrarMovimiento("reversa +" +monto+" ,saldo: "+saldo);
    }

    public void registrarIntereses() {
        double interes = saldo * tipo.tasa();
        saldo += interes;
        registrarMovimiento("Intereses +"+interes+", saldo: "+saldo);
    }

    public void quienSoy() {
        System.out.println("cuentaBancaria ");
    }

    public void imprimirHistorial() {
        System.out.println("===== Historial de movimientos: (Cuenta #"+numero+") =====");
        for (int i = 0; i < cantidadMovimientos; i++) {
            System.out.println(historial[i]);
        }
    }

    @Override
    public String toString() {
        return "Cuenta #"+numero+" | clientw: "+cliente+ " | saldo: " + saldo + " " + moneda 
                + " | tipo: "+ tipo+" | apertura: "+apertura.getTime();
    }

    public double getSaldo(){
        return saldo;
    }

    public int getNumero() {
        return numero;
    }
    
    public void transferir(double monto,cuentaBancaria destino){
        if (destino== null) {
            throw new IllegalArgumentException("la cuenta no existe");
        }
        if (this.retirar(monto)) {
            try {
                destino.depositar(monto);
                this.registrarMovimiento("transferencia enviada a #"+destino.numero+": "+monto);
                destino.registrarMovimiento("TRANSFERENCIA RECIBIDA de #" + this.numero + ": +" + monto);
            } catch (Exception e) {
                this.reintegrar(monto);
                System.out.println("Error en cuenta destino, transferencia revertida");
            }
        } else {
            System.out.println("Transferencia fallida: saldo insuficiente");
        }
    }
    
}  
