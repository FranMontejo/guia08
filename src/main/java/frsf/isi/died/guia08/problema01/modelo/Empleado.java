package frsf.isi.died.guia08.problema01.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.FileNotFoundException;


public class Empleado {

	public enum Tipo { CONTRATADO,EFECTIVO}; 
	
	private Integer cuil;
	private String nombre;
	private Tipo tipo;
	private Double costoHora;
	private List<Tarea> tareasAsignadas;
	
	private Function<Tarea, Double> calculoPagoPorTarea;		
	private Predicate<Tarea> puedeAsignarTarea;

	
	// Constructores 
	
	public Empleado(Integer cuil, String nombre, Tipo tipo, Double costoHora) {
		this.cuil = cuil;
		this.nombre = nombre;
		this.tipo = tipo;
		this.costoHora = costoHora;
		this.tareasAsignadas = new ArrayList<Tarea>();
		this.setPredicateFunction();
	}
	
	
	private void setPredicateFunction() {
		switch(this.tipo) {
			case CONTRATADO:{
				this.puedeAsignarTarea = (Tarea t) -> (this.tareasAsignadas.size() <=5);
				
			} break;
			case EFECTIVO:{
				this.puedeAsignarTarea = (Tarea t) -> (this.tareasAsignadas.stream().mapToInt(s -> s.getDuracionEstimada()).sum() < 15);
			}
		}
		this.calculoPagoPorTarea = t -> (this.costoHora*t.getDuracionEstimada()) + t.getBeneficio(this.costoHora*t.getDuracionEstimada());
	}


	// Metodos
	
	public Double salario() {
		List<Tarea> tareasCobrar = this.tareasAsignadas.stream().filter(s -> !s.getFacturada()).collect(Collectors.toList());
		Double salario = 0.0;
		for(Tarea t: tareasCobrar) {
			salario+= this.calculoPagoPorTarea.apply(t);
			t.setFacturada(true);
		}
		return salario;
	}


	
	public Double costoTarea(Tarea t) {
		return 0.0;
	}
		
	public Boolean asignarTarea(Tarea t) throws Exception {
		if(this.puedeAsignarTarea.test(t)) {
			this.tareasAsignadas.add(t);
			t.asignarEmpleado(this);
			return true;
		}
	
		return false;
	}
	
	public void comenzar(Integer idTarea) throws FileNotFoundException {
		this.asignarFecha(idTarea);
	}
	
	

	public void finalizar(Integer idTarea) throws FileNotFoundException {
		this.asignarFecha(idTarea);
	}

	public void comenzar(Integer idTarea,String fecha) throws FileNotFoundException {
		this.asignarFecha(idTarea, fecha);
		
		
	}
	
	public void finalizar(Integer idTarea,String fecha) throws FileNotFoundException {
		this.asignarFecha(idTarea,fecha);
	}

	private void asignarFecha(Integer idTarea) throws FileNotFoundException {
		Optional<Tarea> t = this.tareasAsignadas.stream().filter(s -> s.getId().equals(idTarea)).findFirst();
		if(t.isEmpty()) {
			throw new FileNotFoundException("No se encontró la tarea");
		}
		else {
			if(t.get().getFechaInicio() != null) {
				t.get().setFechaFin(LocalDateTime.now());
			}
			else {
				t.get().setFechaInicio(LocalDateTime.now());
			}
			
		}
	}

	
	private void asignarFecha(Integer idTarea, String fecha) throws FileNotFoundException {
		Optional<Tarea> t = this.tareasAsignadas.stream().filter(s -> s.getId().equals(idTarea)).findFirst();
		if(t.isEmpty()) {
			throw new FileNotFoundException("No se encontró la tarea");
		}
		else {
			if(t.get().getFechaInicio() != null) {
				t.get().setFechaFin(LocalDateTime.parse(fecha, DateTimeFormatter.ofPattern("DD-MM-YYYY HH:MM")));
			}
			else {
				t.get().setFechaInicio(LocalDateTime.parse(fecha, DateTimeFormatter.ofPattern("DD-MM-YYYY HH:MM")));
			}
		}
	}

	
	public String asCsv() {
		return this.cuil+";\""+this.nombre;
	}
	
	
	// Getters and setters

	public Integer getCuil() {
		return cuil;
	}


	public void setCuil(Integer cuil) {
		this.cuil = cuil;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Tipo getTipo() {
		return tipo;
	}


	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}


	public Double getCostoHora() {
		return costoHora;
	}


	public void setCostoHora(Double costoHora) {
		this.costoHora = costoHora;
	}


	public List<Tarea> getTareasAsignadas() {
		return tareasAsignadas;
	}


	public void setTareasAsignadas(List<Tarea> tareasAsignadas) {
		this.tareasAsignadas = tareasAsignadas;
	}





}



