package main;

import entidades.Equipo;
import entidades.Jugador;

public class Test {

	public static void main(String[] args) {
		
		GestionEquipo gestor=new GestionEquipo();
		
		/*	
		//Creamos 3 equipos y voy a añadirlos a la DB
		Equipo e1=new Equipo("Real Madrid","primera");
		Equipo e2=new Equipo("FC Barcelona","primera");
		Equipo e3=new Equipo("Real Valladolid","primera");
		gestor.crearEquipo(e1);
		gestor.crearEquipo(e2);
		gestor.crearEquipo(e3);
		*/
		
	
		//Metemos jugadores
		Jugador j1e1=new Jugador("12121212A","Vinicius",24,"delantero");
		Jugador j2e1=new Jugador("12121212B","Benzema",33,"delantero");
		Jugador j3e1=new Jugador("12121212C","Rodrygo",22,"delantero");
		Jugador j4e1=new Jugador("12121212E","Jesus Vallejo",21,"defensa");
		/*gestor.crearJugador(1, j1e1);
		gestor.crearJugador(1, j2e1);
		gestor.crearJugador(1, j3e1);
		gestor.crearJugador(1, j4e1);*/
		
		
		
	/*	//Traspasamos jugadores
		gestor.traspaso(1, 3, j2e1);
		gestor.traspaso(1, 3, j3e1);
		*/
		
/*		//Buscar por nombre
		List<Jugador> jugadores=gestor.buscarNombre("e");
		for(Jugador j:jugadores) {
			System.out.println(j);
		}
	*/	
		
		
		//Borrar Jugador
		//gestor.borrarJugador("12121212B");
		
		
		//VAMOS A CREAR EL EQUIPO
		Equipo agentesLibres=new Equipo("JugadoresMercadoPrimera","primera");
		gestor.crearEquipo(agentesLibres);
		
		//Borrar equipo
		gestor.borrarEquipo(1);
		
	}
}
