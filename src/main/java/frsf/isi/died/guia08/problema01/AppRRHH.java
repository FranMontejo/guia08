package frsf.isi.died.guia08.problema01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import frsf.isi.died.guia08.problema01.modelo.Empleado;
import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;
import frsf.isi.died.guia08.problema01.modelo.Tarea;

public class AppRRHH {

	private List<Empleado> empleados;
	
	public AppRRHH() {
		this.empleados = new ArrayList<>();
	}
	
	public List<Empleado> getEmpleados() {
		return this.empleados;
	}
	
	
	public void agregarEmpleadoContratado(Integer cuil,String nombre,Double costoHora) {
		Empleado e = new Empleado(cuil,nombre,Tipo.CONTRATADO,costoHora);
		this.empleados.add(e);
		
	}
	
	public void agregarEmpleadoEfectivo(Integer cuil,String nombre,Double costoHora) {
		Empleado e = new Empleado(cuil,nombre,Tipo.EFECTIVO,costoHora);
		this.empleados.add(e);
		
	}
	
	public void asignarTarea(Integer cuil,Integer idTarea,String descripcion,Integer duracionEstimada) throws Exception {
		Optional<Empleado> e = this.buscarEmpleado(s -> s.getCuil().equals(cuil));	
		if(e.isPresent()) {
			Tarea t = new Tarea(idTarea,"",duracionEstimada);
			e.get().asignarTarea(t);
		}
	}
	
	public void empezarTarea(Integer cuil,Integer idTarea) throws Exception {
		Optional<Empleado> e = this.buscarEmpleado(s -> s.getCuil().equals(cuil));
		if(e.isEmpty()) {
			throw new Exception("No se encontró el empleado con el cuil dado");
		}
		else {
			e.get().comenzar(idTarea);
		}
	}
	
	public void terminarTarea(Integer cuil,Integer idTarea) throws Exception {
		Optional<Empleado> e = this.buscarEmpleado(s -> s.getCuil().equals(cuil));		
		if(e.isEmpty()) {
			throw new Exception("No se encontró el empleado con el cuil dado");
		}
		else {
			e.get().finalizar(idTarea);
		}
	}
	

	public void cargarEmpleadosContratadosCSV(String nombreArchivo) throws FileNotFoundException, IOException {
		BufferedReader lector = null;
		try {
			lector = new BufferedReader(new FileReader(nombreArchivo));
			String linea;
			while((linea = lector.readLine()) != null) {
				String[] datos = linea.split(";");
				this.agregarEmpleadoContratado(Integer.parseInt(datos[0]), datos[1], Double.parseDouble(datos[2]));
			}
			
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			if(lector != null) {
				try {
					lector.close();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void cargarEmpleadosEfectivosCSV(String nombreArchivo) throws FileNotFoundException, IOException {
		BufferedReader lector = null;
		try {
			lector = new BufferedReader(new FileReader(nombreArchivo));
			String linea;
			while((linea = lector.readLine()) != null) {
				String[] datos = linea.split(";");
				this.agregarEmpleadoEfectivo(Integer.parseInt(datos[0]), datos[1], Double.parseDouble(datos[2]));
			}
			
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			if(lector != null) {
				try {
					lector.close();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void cargarTareasCSV(String nombreArchivo) throws Exception, FileNotFoundException {
		BufferedReader lector = null;
		try {
			lector = new BufferedReader(new FileReader(nombreArchivo));
			String linea;
			while((linea = lector.readLine()) != null) {
				String[] datos = linea.split(";");
				Optional<Empleado> e = this.buscarEmpleado(s -> s.getCuil().equals(Integer.parseInt(datos[3])));
				e.get().asignarTarea(new Tarea(Integer.parseInt(datos[0]),datos[1],Integer.parseInt(datos[2]), e.get()));
			}
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			if(lector != null) {
				try {
					lector.close();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void guardarTareasTerminadasCSV() throws IOException {
		List<Tarea> lista = empleados.stream().map(e -> e.getTareasAsignadas()).flatMap(List::stream).collect(Collectors.toList());
		try(Writer fileWriter = new FileWriter("tareasGuardadas.csv")){
			try(BufferedWriter out = new BufferedWriter(fileWriter)){
				for(Tarea t: lista.stream().filter(s -> !s.getFacturada() && s.getFechaFin()!= null).collect(Collectors.toList())) {
						out.write(t.asCsv()+t.getEmpleadoAsignado().asCsv()+System.getProperty("Line.separator"));
				}
			}
		}
	}
	
	
	private Optional<Empleado> buscarEmpleado(Predicate<Empleado> p){
		return this.empleados.stream().filter(p).findFirst();
	}

	public Double facturar() throws IOException {
		this.guardarTareasTerminadasCSV();
		return this.empleados.stream()				
				.mapToDouble(e -> e.salario())
				.sum();
	}
}
