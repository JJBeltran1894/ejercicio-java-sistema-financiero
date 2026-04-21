package com.krakedev.financiero.servicios.test;

import com.krakedev.financiero.entidades.Cliente;
import com.krakedev.financiero.entidades.Cuenta;
import com.krakedev.financiero.servicios.Banco;

public class TestBanco {

	public static void main(String[] args) {
		 Cliente cliente1= new Cliente();
		 Cliente cliente2= new Cliente("1721403549", "Juan Jose","Beltran");
		 
		 Banco banco = new Banco();
		 
		 Cuenta cuenta1Creada = banco.crearCuenta(cliente1);
		 
		 Cuenta cuenta2Creada = banco.crearCuenta(cliente1);
		 
		 System.out.println(cuenta1Creada.getId());
		 System.out.println(cuenta2Creada.getId());
		 
		 boolean resultado = banco.depositar(250, cuenta2Creada);
		 System.out.println("\nValidacion Deposito");
		 System.out.println("Resultado: "+resultado);
		 cuenta2Creada.imprimir();
		 
		 resultado = banco.depositar(-100, cuenta2Creada);
		 System.out.println("\nValidacion Deposito");
		 System.out.println("Resultado: "+resultado);
		 cuenta2Creada.imprimir();
		 
		 resultado = banco.depositar(120, cuenta2Creada);
		 System.out.println("\nValidacion Deposito");
		 System.out.println("Resultado: "+resultado);
		 cuenta2Creada.imprimir();
		 
		 resultado = banco.retirar(-120, cuenta2Creada);
		 System.out.println("\nValidacion Retiro");
		 System.out.println("Resultado: "+resultado);
		 cuenta2Creada.imprimir();
		 
		 resultado = banco.retirar(120, cuenta2Creada);
		 System.out.println("\nValidacion Retiro");
		 System.out.println("Resultado: "+resultado);
		 cuenta2Creada.imprimir();
		 
		 resultado = banco.transferir(120, cuenta2Creada,cuenta1Creada);
		 System.out.println("\nValidacion Transferencia");
		 System.out.println("Resultado: "+resultado);
		 cuenta2Creada.imprimir();
		 cuenta1Creada.imprimir();
		 
		 resultado = banco.transferir(150, cuenta2Creada,cuenta1Creada);
		 System.out.println("\nValidacion Transferencia");
		 System.out.println("Resultado: "+resultado);
		 cuenta2Creada.imprimir();
		 cuenta1Creada.imprimir();
		 
		 
	}

}
