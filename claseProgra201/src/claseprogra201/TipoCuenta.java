/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package claseprogra201;

/**
 *
 * @author marlo
 */
public class TipoCuenta {
    private final String nombre;
    private final double tasa;
    public static final TipoCuenta ahorro = new TipoCuenta("Ahorro ",0.03);
    public static final TipoCuenta cheque = new TipoCuenta("Cheques ",0);
    public static final TipoCuenta plazo = new TipoCuenta("plazo ",0.05);
    private TipoCuenta(String nombre, double tasa){
        this.nombre = nombre;
        this.tasa = tasa;
    }
    public double tasa(){
        return this.tasa;
    }
    @Override
    public String toString(){
        return this.nombre;
    }
}
