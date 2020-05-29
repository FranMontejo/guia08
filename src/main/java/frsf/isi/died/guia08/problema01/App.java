package frsf.isi.died.guia08.problema01;

import java.time.LocalDateTime;

import frsf.isi.died.guia08.problema01.modelo.Empleado;
import frsf.isi.died.guia08.problema01.modelo.Tarea;
import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;

public class App {
	public static void main(String[] args) throws Exception {
		Empleado e1 = new Empleado(01, "Osvaldo", Tipo.CONTRATADO, 50.0);
		Empleado e2 = new Empleado(02, "Marcela",Tipo.EFECTIVO,55.0);
		Empleado e3 = new Empleado(03, "Rodrigo", Tipo.EFECTIVO, 30.0);
		Empleado e4 = new Empleado(04, "Federico", Tipo.CONTRATADO, 45.5);
		Tarea t1 = new Tarea(5,"",12);
		Tarea t2 = new Tarea(6,"",14);
		Tarea t3 = new Tarea(7,"",8);
		Tarea t4 = new Tarea(8,"",4);
		
		e1.asignarTarea(t1);
		e1.asignarTarea(t2);
		e1.asignarTarea(t3);
		e1.comenzar(5);
		e1.comenzar(6);
		e1.comenzar(7);
		e1.finalizar(5);
		Double t = e1.salario();
		
		
		
	}
}
