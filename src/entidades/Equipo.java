package entidades;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="equiposLiga")
public class Equipo implements Serializable {
	
	@Id
	@GeneratedValue
	private int id;
	private String nombre;
	private String division;
	//el que es 1 pa muchos, se le añade el mappedBY
	@OneToMany(mappedBy="equipo", cascade=CascadeType.PERSIST) 
	private List<Jugador> jugadores;
	
	
	public Equipo(int id, String nombre, String division, List<Jugador> jugadores) {
		this.id = id;
		this.nombre = nombre;
		this.division = division;
		this.jugadores = jugadores;
	}
	
	
	public Equipo() {
	}

	public Equipo(String nombre, String division, List<Jugador> jugadores) {
		this.nombre = nombre;
		this.division = division;
		this.jugadores = jugadores;
	}

	public Equipo(String nombre, String division) {
		this.nombre = nombre;
		this.division = division;
	}


	

	@Override
	public String toString() {
		String cad="Nombre equipo:"+this.nombre+"\nDivision:"+this.division;
		cad+="\n------------------------------";
		return cad;
	}


	public List<Jugador> getJugadores() {
		return jugadores;
	}
	public void setJugadores(List<Jugador> jugadores) {
		this.jugadores = jugadores;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	
	
	
	

}
