package com.krakedev.financiero.servicios;

import com.krakedev.financiero.entidades.Cliente;
import com.krakedev.financiero.entidades.Cuenta;

public class Banco {
	private int ultimoCodigo=1000;

	public int getUltimoCodigo() {
		return ultimoCodigo;
	}

	public void setUltimoCodigo(int ultimoCodigo) {
		this.ultimoCodigo = ultimoCodigo;
	}
	
	public Cuenta crearCuenta(Cliente cliente) {
		
		String codigoStr=ultimoCodigo +"";
		ultimoCodigo++;
		Cuenta cuenta = new Cuenta(codigoStr);
		cuenta.setPropietario(cliente);
		
		return cuenta;
	}
	
	public boolean depositar(double monto, Cuenta cuenta) {
		if(monto<=0) {
			return false;
		}else{
			double nuevoSaldo = cuenta.getSaldoActual()+monto;
			cuenta.setSaldoActual(nuevoSaldo);
			return true;
		}
			
	}
	
	public boolean retirar(double monto, Cuenta cuenta ) {
		double saldoActual = cuenta.getSaldoActual();
		if(monto>0 && monto <= saldoActual) {
			double nuevoSaldo= saldoActual - monto;
			cuenta.setSaldoActual(nuevoSaldo);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean transferir(double monto, Cuenta cuentaOrigen, Cuenta cuentaDestino) {
		
		if(monto>0 && cuentaOrigen.getSaldoActual()>=monto && cuentaOrigen.getId()!=cuentaDestino.getId()) {
			// Como ya validamos todo arriba, ejecutamos las operaciones por separado
			retirar(monto, cuentaOrigen);
			depositar(monto, cuentaDestino);
			return true; // Retornamos true directamente
		}
		return false;
	}
	
	
}
