package frsf.isi.died.guia08.problema01;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import frsf.isi.died.guia08.problema01.modelo.Empleado;
import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;
import frsf.isi.died.guia08.problema01.modelo.Tarea;

public class AppRRHHTest {

		AppRRHH app = new AppRRHH();
		Empleado e1 = new Empleado(01, "Osvaldo", Tipo.CONTRATADO, 50.0);
		Empleado e2 = new Empleado(02, "Marcela",Tipo.EFECTIVO,55.0);
		Empleado e3 = new Empleado(03, "Rodrigo", Tipo.EFECTIVO, 30.0);
		Empleado e4 = new Empleado(04, "Federico", Tipo.CONTRATADO, 45.5);
		Tarea t1 = new Tarea(5,"",12);
		Tarea t2 = new Tarea(6,"",14);
		Tarea t3 = new Tarea(7,"",8);
		Tarea t4 = new Tarea(8,"",4);
		
	@Test
	public void testAsignarTarea() throws Exception {
		e2.asignarTarea(t2);
		e2.asignarTarea(t3);
		assertTrue(e1.asignarTarea(t1));
		assertFalse(e2.asignarTarea(t4));
	}
	
	@Test
	public void testComenzarFinalizar() throws Exception {
		e1.asignarTarea(t1);
		e1.comenzar(5);
		assertEquals(t1.getFechaInicio(),LocalDateTime.now());
	}
		
	
	@Test
	public void testSalario() throws Exception {
		e1.asignarTarea(t1);
		e1.comenzar(5);
		e2.asignarTarea(t2);
		e2.comenzar(6);
		e2.finalizar(6);
		Double g = 924.0;
		Double f = 600.00;
		assertEquals(e1.salario(),f);
		assertEquals(e2.salario(),g);
	}

	@Test
	public void testAsignarEmpleado() throws Exception {
		t1.asignarEmpleado(e1);
		assertTrue(e1.equals(t1.getEmpleadoAsignado()));
	}

	@Test
	public void testAsignarTareaAppRRHH() throws Exception {
		Empleado emp = new Empleado(250,"Jose",Tipo.EFECTIVO,35.0);
		app.getEmpleados().add(emp);
		app.asignarTarea(emp.getCuil(),250,"",16);
		assertTrue(emp.getTareasAsignadas().size() > 0);
	}




}
