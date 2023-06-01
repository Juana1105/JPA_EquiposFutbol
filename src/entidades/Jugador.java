package entidades;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Jugador implements Serializable {

	@Id
	private String dni;
	private String nombre;
	private int edad;
	private String posicion;
	@ManyToOne  //añadimos mappedBy a equipo
	private Equipo equipo;
	
	
	public Jugador(String dni, String nombre, int edad, String posicion) {
		this.dni = dni;
		this.nombre = nombre;
		this.edad = edad;
		this.posicion = posicion;
	}

	public Jugador() {
	}

	//METODO EQUALSSSSSSSSSS
	public boolean equals(Jugador otroJugador) {
		return this.dni.equalsIgnoreCase(otroJugador.getDni());
	}
	
	
	
	@Override
	public String toString() {
		String cad="Nombre:"+this.nombre+"\nEdad:"+this.edad+"\nPosición:"+this.posicion;
		cad+="\n---------------------------";
		return cad;
	}

	
	
	
	
	
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getPosicion() {
		return posicion;
	}

	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}

	public Equipo getEquipo() {
		return equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}
	
	
	
	
	
}
