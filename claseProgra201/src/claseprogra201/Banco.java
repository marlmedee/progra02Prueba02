/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package claseprogra201;

/**
 *
 * @author marlo
 */
public class Banco {
    private cuentaBancaria[] cuentas = new cuentaBancaria[20];
    private int cantidadCuentas = 0;
    public void agregarCuenta(cuentaBancaria c) {
        if (c == null) {
            throw new IllegalArgumentException("La cuenta no puede ser nula");
        }
        if (cantidadCuentas >= cuentas.length) {
            throw new IllegalStateException("El banco no acepta más cuentas (límite de 20 alcanzado)");
        }
        if (existeCuenta(c.getNumero(), 0)) {
            throw new IllegalArgumentException("El número de cuenta " + c.getNumero() + " ya existe.");
        }
        cuentas[cantidadCuentas] = c;
        cantidadCuentas++;
    }
    private boolean existeCuenta(int numero, int index) {
        if (index >= cantidadCuentas) {
            return false;
        }
        if (cuentas[index].getNumero() == numero) {
            return true;
        }
        return existeCuenta(numero, index + 1);
    }

    public cuentaBancaria buscarPorNumero(int numero) {
        return buscarPorNumeroAux(numero, 0);
    }
    private cuentaBancaria buscarPorNumeroAux(int numero, int index) {
        if (index >= cantidadCuentas) {
            throw new IllegalArgumentException("La cuenta #" + numero + " no existe en el banco.");
        }
        if (cuentas[index].getNumero() == numero) {
            return cuentas[index];
        }
        return buscarPorNumeroAux(numero, index + 1);
    }
    public void ordenarPorSaldo() {
        if (cantidadCuentas > 1) {
            ordenarSeleccionRecursivo(0);
        }
    }
    private void ordenarSeleccionRecursivo(int i) {
        if (i>=cantidadCuentas-1) {
            return;
        }
        int minIndex=buscarMinimoRecursivo(i, i + 1);
        if (minIndex!=i) {
            cuentaBancaria temp = cuentas[i];
            cuentas[i] = cuentas[minIndex];
            cuentas[minIndex] = temp;
        }
        ordenarSeleccionRecursivo(i+1);
    }

    private int buscarMinimoRecursivo(int minIndex, int j) {
        if (j>=cantidadCuentas) {
            return minIndex;
        }
        if (cuentas[j].getSaldo() < cuentas[minIndex].getSaldo()) {
            minIndex =j;
        }
        return buscarMinimoRecursivo(minIndex, j + 1);
    }
    public double saldoTotalDelBanco() {
        return saldoTotalAux(0);
    }
    private double saldoTotalAux(int index) {
        if (index >= cantidadCuentas) {
            return 0.0;
        }
        return cuentas[index].getSaldo() + saldoTotalAux(index + 1);
    }
    public void transferir(int numeroOrigen, int numeroDestino, double monto) {
        if (numeroOrigen == numeroDestino) {
            throw new IllegalArgumentException("La cuenta de origen y destino no pueden ser la misma");
        }

        cuentaBancaria origen = buscarPorNumero(numeroOrigen);
        cuentaBancaria destino = buscarPorNumero(numeroDestino);

        boolean seRetiro = origen.retirar(monto);
        if (!seRetiro) {
            throw new IllegalStateException("Saldo insuficiente o retiro no permitido en la cuenta de origen");
        }

        try {
            destino.depositar(monto);
        } catch (Exception e) {
            origen.reintegrar(monto);
            throw new IllegalStateException("Fallo el depósito en la cuenta destino. Operación revertida: " + e.getMessage());
        }
    }
    public void aplicarInteresesGlobal() {
        for (int i = 0; i < cantidadCuentas; i++) {
            try {
                cuentas[i].registrarIntereses();
            } 
            catch (Exception e) {
                System.out.println("error al aplicar intereses en cuenta #"+cuentas[i].getNumero() + ": " + e.getMessage());
            }
        }
    }
    public void generarReporte() {
        ordenarPorSaldo();
        System.out.println("\n========== REPORTE DE CUENTAS DEL BANCO ==========");
        for (int i = 0; i < cantidadCuentas; i++) {
            cuentas[i].quienSoy();
            System.out.println(cuentas[i].toString());
            System.out.println("--------------------------------------------------");
        }
        System.out.println("Ssaldo total banco: "+saldoTotalDelBanco()+ " lps.");
        System.out.println("==================================================\n\n");
    }

}
