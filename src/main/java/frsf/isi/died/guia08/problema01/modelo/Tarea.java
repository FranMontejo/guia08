package frsf.isi.died.guia08.problema01.modelo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Tarea {

	private Integer id;
	private String descripcion;
	private Integer duracionEstimada;
	private Empleado empleadoAsignado;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
	private Boolean facturada;
	
	
	// constructores
	public Tarea(Integer id, String descripcion, Integer duracionEstimada, Empleado empleadoAsignado) {
		this.id = id;
		this.descripcion = descripcion;
		this.duracionEstimada = duracionEstimada;
		this.empleadoAsignado = empleadoAsignado;
		this.facturada = false;
	}
	
	public Tarea(int i, String string, int j) {
		this.id = i;
		this.descripcion = string;
		this.duracionEstimada = j;
		this.facturada = false;
	}
	
	public Tarea(Integer idTarea) {
		this.id = idTarea;
	}

	//Metodos
	public void asignarEmpleado(Empleado e) throws Exception {
		if(this.fechaFin != null || this.empleadoAsignado != null) {
			throw new Exception("Error: la tarea ha finalizado o está asiganada a otro empleado");
		}
		else {
			this.empleadoAsignado = e;
		}
	}

	public long getHorasDiferencia() {
		if(this.fechaFin != null && this.fechaInicio != null) {
		long dias =  ChronoUnit.DAYS.between(this.getFechaInicio(), this.getFechaFin());
		return dias;
		}
		return 0;
	}
	public Double getBeneficio(Double costoH) {
		Double beneficio = 0.0;
		if(this.fechaFin != null) {
			switch (this.getEmpleadoAsignado().getTipo()) {
				case EFECTIVO:{
					if(this.getHorasDiferencia()*4 < this.duracionEstimada) {
						beneficio = 0.2 * costoH;
					}
					
				} break;
				case CONTRATADO:{
					if(this.getHorasDiferencia()*4 < this.duracionEstimada) {
						beneficio = 0.3 * costoH;
					}
					else {
						if(this.getHorasDiferencia()*4 > (this.duracionEstimada)+8) {
							beneficio = -0.25 * costoH;
						}
					}
				}break;
			}
		}
		
		return beneficio;
	}
	

	
	// Getters and setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getDuracionEstimada() {
		return duracionEstimada;
	}

	public void setDuracionEstimada(Integer duracionEstimada) {
		this.duracionEstimada = duracionEstimada;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Boolean getFacturada() {
		return this.facturada;
	}

	public void setFacturada(Boolean facturada) {
		this.facturada = facturada;
	}

	public Empleado getEmpleadoAsignado() {
		return empleadoAsignado;
	}



	public String asCsv() {
		return this.id+";\""+this.descripcion+"\";"+this.duracionEstimada;
	}


	
}
