package com.krakedev.financiero.servicios.test;

import com.krakedev.financiero.entidades.Cliente;
import com.krakedev.financiero.entidades.Cuenta;
import com.krakedev.financiero.servicios.Banco;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestBancoJUnitIA {
	
	// Tolerancia para las comparaciones de valores double
    private static final double DELTA = 0.001;

    // ==========================================
    // PRUEBAS NUMERAL 6: crearCuenta
    // ==========================================
    @Test
    void testCrearCuentaCodigosConsecutivos() {
        // Validación: Verifica que al crear cuentas, el código inicial sea 1000 y se incremente de forma consecutiva[cite: 205, 206, 207].
        Banco banco = new Banco();
        // Según el PDF, Cliente tiene un constructor que recibe cedula, nombre y apellido [cite: 158]
        Cliente cliente1 = new Cliente("1717171717", "Juan", "Perez");
        Cliente cliente2 = new Cliente("1818181818", "Maria", "Gomez");

        // Acción
        Cuenta cuenta1 = banco.crearCuenta(cliente1);
        Cuenta cuenta2 = banco.crearCuenta(cliente2);

        // Verificaciones [cite: 215]
        assertEquals("1000", cuenta1.getId(), "La primera cuenta creada debe tener el ID 1000.");
        assertEquals("1001", cuenta2.getId(), "La segunda cuenta creada debe tener el ID consecutivo 1001.");
        assertEquals(1002, banco.getUltimoCodigo(), "El contador ultimoCodigo del banco debe haberse actualizado a 1002.");
        assertEquals(cliente1, cuenta1.getPropietario(), "El propietario asignado a la cuenta 1 debe ser cliente1.");
    }

    // ==========================================
    // PRUEBAS NUMERAL 7: depositar
    // ==========================================
    @Test
    void testDepositarMontoValido() {
        // Validación: Verifica que un depósito válido aumente el saldo correctamente[cite: 218].
        Banco banco = new Banco();
        Cuenta cuenta = new Cuenta("1000"); // Según el PDF, la cuenta nace con saldoActual = 0 [cite: 142]

        boolean resultado = banco.depositar(150.50, cuenta);

        // Verificaciones
        assertTrue(resultado, "El método depositar debe retornar true al recibir un monto mayor a 0.");
        assertEquals(150.50, cuenta.getSaldoActual(), DELTA, "El saldo de la cuenta debe incrementarse correctamente.");
    }

    @Test
    void testDepositarMontoInvalido() {
        // Validación: Verifica que no se pueda depositar 0 o un monto negativo[cite: 220].
        Banco banco = new Banco();
        Cuenta cuenta = new Cuenta("1000");

        boolean resultadoCero = banco.depositar(0.0, cuenta);
        boolean resultadoNegativo = banco.depositar(-50.0, cuenta);

        // Verificaciones [cite: 226]
        assertFalse(resultadoCero, "El método debe retornar false al intentar depositar 0.");
        assertFalse(resultadoNegativo, "El método debe retornar false al intentar depositar un valor negativo.");
        assertEquals(0.0, cuenta.getSaldoActual(), DELTA, "El saldo no debe modificarse tras depósitos inválidos.");
    }

    // ==========================================
    // PRUEBAS NUMERAL 8: retirar
    // ==========================================
    @Test
    void testRetirarMontoValido() {
        // Validación: Verifica un retiro exitoso cuando hay saldo suficiente y el monto es válido[cite: 231, 233, 234].
        Banco banco = new Banco();
        Cuenta cuenta = new Cuenta("1000");
        cuenta.setSaldoActual(200.0); // Preparamos la cuenta con fondos

        boolean resultado = banco.retirar(50.0, cuenta);

        // Verificaciones [cite: 242]
        assertTrue(resultado, "El método retirar debe retornar true al realizarse con éxito.");
        assertEquals(150.0, cuenta.getSaldoActual(), DELTA, "El saldo debe reducirse correctamente tras el retiro.");
    }

    @Test
    void testRetirarFondosInsuficientes() {
        // Validación: Verifica que no se pueda retirar si el monto supera el saldo disponible[cite: 234].
        Banco banco = new Banco();
        Cuenta cuenta = new Cuenta("1000");
        cuenta.setSaldoActual(50.0);

        boolean resultado = banco.retirar(100.0, cuenta);

        // Verificaciones [cite: 243]
        assertFalse(resultado, "El método debe retornar false por fondos insuficientes.");
        assertEquals(50.0, cuenta.getSaldoActual(), DELTA, "El saldo debe mantenerse intacto si falla el retiro.");
    }

    // ==========================================
    // PRUEBAS NUMERAL 9: transferir
    // ==========================================
    @Test
    void testTransferirExitoso() {
        // Validación: Verifica que la transferencia reste de origen y sume en destino[cite: 252, 253].
        Banco banco = new Banco();
        Cuenta cuentaOrigen = new Cuenta("1000");
        cuentaOrigen.setSaldoActual(300.0);
        
        Cuenta cuentaDestino = new Cuenta("1001");
        cuentaDestino.setSaldoActual(50.0);

        boolean resultado = banco.transferir(100.0, cuentaOrigen, cuentaDestino);

        // Verificaciones [cite: 258]
        assertTrue(resultado, "La transferencia debe retornar true si es exitosa.");
        assertEquals(200.0, cuentaOrigen.getSaldoActual(), DELTA, "Se debió descontar 100 de la cuenta origen.");
        assertEquals(150.0, cuentaDestino.getSaldoActual(), DELTA, "Se debió sumar 100 a la cuenta destino.");
    }

    @Test
    void testTransferirFondosInsuficientes() {
        // Validación: Verifica que falle la transferencia si el origen no tiene saldo[cite: 260, 261].
        Banco banco = new Banco();
        Cuenta cuentaOrigen = new Cuenta("1000");
        cuentaOrigen.setSaldoActual(50.0); // Saldo menor al monto a transferir
        
        Cuenta cuentaDestino = new Cuenta("1001");
        cuentaDestino.setSaldoActual(0.0);

        boolean resultado = banco.transferir(100.0, cuentaOrigen, cuentaDestino);

        // Verificaciones
        assertFalse(resultado, "La transferencia debe retornar false si la cuenta origen no tiene fondos suficientes.");
        assertEquals(50.0, cuentaOrigen.getSaldoActual(), DELTA, "El saldo de origen no debe alterarse.");
        assertEquals(0.0, cuentaDestino.getSaldoActual(), DELTA, "El saldo de destino no debe alterarse.");
    }

    @Test
    void testTransferirAMismaCuenta() {
        // Validación: Según tu lógica en Banco.java, la transferencia falla si cuentaOrigen y cuentaDestino tienen el mismo ID.
        Banco banco = new Banco();
        Cuenta cuenta = new Cuenta("1000");
        cuenta.setSaldoActual(100.0);

        boolean resultado = banco.transferir(50.0, cuenta, cuenta);

        // Verificaciones
        assertFalse(resultado, "La transferencia debe retornar false si se intenta transferir a la misma cuenta.");
        assertEquals(100.0, cuenta.getSaldoActual(), DELTA, "El saldo no debe verse afectado.");
    }
    
    @Test
    void testSetUltimoCodigo() {
        // Validación: Verifica que el setter de ultimoCodigo funcione correctamente (Cubre la línea roja).
        Banco banco = new Banco();
        banco.setUltimoCodigo(5000);
        assertEquals(5000, banco.getUltimoCodigo(), "El setter debe actualizar el valor de ultimoCodigo.");
    }

    @Test
    void testRetirarMontoCeroONegativo() {
        // Validación: Verifica que no se pueda retirar 0 o un valor negativo (Cubre el rombo amarillo).
        Banco banco = new Banco();
        Cuenta cuenta = new Cuenta("1000");
        cuenta.setSaldoActual(100.0);

        boolean resultadoCero = banco.retirar(0.0, cuenta);
        boolean resultadoNegativo = banco.retirar(-20.0, cuenta);

        assertFalse(resultadoCero, "Debe retornar false al intentar retirar 0.");
        assertFalse(resultadoNegativo, "Debe retornar false al intentar retirar un monto negativo.");
        assertEquals(100.0, cuenta.getSaldoActual(), DELTA, "El saldo no debe modificarse.");
    }
    
    @Test
    void testTransferirMontoCeroONegativo() {
        // Validación: Verifica que no se pueda transferir un monto inválido.
        Banco banco = new Banco();
        Cuenta cuentaOrigen = new Cuenta("1000");
        cuentaOrigen.setSaldoActual(100.0);
        Cuenta cuentaDestino = new Cuenta("1001");

        boolean resultadoCero = banco.transferir(0.0, cuentaOrigen, cuentaDestino);
        boolean resultadoNegativo = banco.transferir(-10.0, cuentaOrigen, cuentaDestino);

        assertFalse(resultadoCero, "Debe retornar false al transferir 0.");
        assertFalse(resultadoNegativo, "Debe retornar false al transferir monto negativo.");
    }
    
    
    

}
