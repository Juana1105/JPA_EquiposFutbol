package main;

import java.util.Iterator;
import java.util.List;

import entidades.Equipo;
import entidades.Jugador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class GestionEquipo {
	//ni atributos ni métodos estáticos
	
	private EntityManager em=null;
	
	public GestionEquipo() { //constructor
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("gestionEquipos");
		em = factory.createEntityManager(); //esto setea
	}
	
	public boolean crearEquipo(Equipo equipo) {
		boolean exito=true;
		try {
			EntityTransaction tran = em.getTransaction();
			tran.begin();
			em.persist(equipo);
			tran.commit();
		}catch(Exception ex){
			exito=false;//si salta una excepcion es que no se ha insertado y es false
		}
		return exito;
	}
	
	//version cuando era unidireccional
	public boolean crearJugadorV1(int idEquipo, Jugador jugador) {
		boolean exito=true;
		EntityTransaction tran=null;
		//extraemos de la DB el objeto Equipo q coincida cn el ID especificado
		Equipo equipo = em.find(Equipo.class, idEquipo);
		try {
			if(equipo!=null) {//SI EL EQUIPO NO ES NULO, ha sido encontrado¡¡
				tran=em.getTransaction();
				tran.begin();
				//Si el equipo ha sido encontrado
				List<Jugador> jugadores = equipo.getJugadores();
				jugadores.add(jugador);
				tran.commit();
			}else {//si el equipo es null
				exito=false;
			}
		}catch(Exception ex) {
			exito=false;
		}
		return exito;
	}
	//VERSION BIDIRECCIONAL
	public boolean crearJugador(int idEquipo, Jugador jugador) {
		boolean exito=true;
		EntityTransaction tran=null;
		//extraemos de la DB el objeto Equipo q coincida cn el ID especificado
		Equipo equipo = em.find(Equipo.class, idEquipo);
		jugador.setEquipo(equipo);
		try {
				tran=em.getTransaction();
				tran.begin();
				em.persist(jugador);//TENEMOS QUE PERSISTIR AL JUGADOR__ALMACENAR***
				tran.commit();
	
		}catch(Exception ex) {
			exito=false;
			System.out.println(ex.toString());
		}
		return exito;
	}
	
	
	public boolean traspasoV1 (int idOrigen, int idDestino, Jugador jugador) {
		boolean exito=true;
		EntityTransaction tran=null;
		try {
			//extraemos de la DB el objeto Equipo origen y Equipo Destino
			Equipo equipoOrigen = em.find(Equipo.class, idOrigen);
			Equipo equipoDestino = em.find(Equipo.class, idDestino);
			
			if(equipoOrigen!=null && equipoDestino!=null) {//verificamos q AMBOS equipos exidte
				tran=em.getTransaction();
				tran.begin();
				List<Jugador> jugadoresOrigen = equipoOrigen.getJugadores();
				List<Jugador> jugadoresDestino = equipoDestino.getJugadores();
				exito=jugadoresOrigen.remove(jugador);//devuelve un true
				if(!jugadoresDestino.contains(jugador)) {//Si en jugadoresDestino NOO esta jugador, le añades
					jugadoresDestino.add(jugador);
				}
				tran.commit();
			}else {
				exito=false;
			}
		}catch(Exception ex) {
			exito=false;
		}
		return exito;
	}
	public boolean traspaso (int idOrigen, int idDestino, Jugador jugador) {
		boolean exito=true;
		EntityTransaction tran=null;
		try {
			//extraemos de la DB el objeto Equipo origen y Equipo Destino
			Equipo equipoOrigen = em.find(Equipo.class, idOrigen);
			Equipo equipoDestino = em.find(Equipo.class, idDestino);
			Jugador j=em.find(Jugador.class, jugador.getDni()); //HAY QUE COGER EL OBJETO JUGADOR DE LA DB para modificarlo
			
			if(equipoOrigen!=null && equipoDestino!=null) {//verificamos q AMBOS equipos exidte
				tran=em.getTransaction();
				tran.begin();
				j.setEquipo(equipoDestino);
				tran.commit();
			}else {
				exito=false;
			}
		}catch(Exception ex) {
			exito=false;
			System.out.println(ex.toString());
		}
		return exito;
	}
	
	public List<Jugador> buscarNombre (String nombre) {
		String jpql="SELECT j FROM Jugador j WHERE j.nombre LIKE :nombreJugador";
		Query query=em.createQuery(jpql);
		query.setParameter("nombreJugador", "%"+nombre+"%");
		List<Jugador> jugadores = query.getResultList();
		return jugadores;
	}
	

	public List<Jugador> filtroJugadores(int edadMin, int edadMax, String posicion){
		String jpql="SELECT j FROM Jugador j WHERE j.edad>=?1 AND j.edad<=?2 AND j.posicion=?3";
		Query query=em.createQuery(jpql);
		query.setParameter(1, edadMin);
		query.setParameter(2, edadMax);
		query.setParameter(3, posicion);
		List<Jugador> jugadores = query.getResultList();
		return jugadores;
	}
	
	
	public boolean borrarJugador (String dni) {
		int valores=0;
		String jpql="DELETE FROM Jugador j WHERE j.dni='" +dni+ "'";
		Query query=em.createQuery(jpql);
		EntityTransaction tran = em.getTransaction();
		tran.begin();
		valores=query.executeUpdate();
		tran.commit();
		return valores==1?true:false;
	}
	
	
	public int numJugadoresEquipo(int idEquipo) {
		String jpql="SELECT e FROM Equipo e WHERE id="+idEquipo;//podemos usar el FIND de antes 
		Query query=em.createQuery(jpql);
		Equipo e=(Equipo)query.getSingleResult();//me devuelve 1 unico equipo
		int numJugadores=e.getJugadores().size();//cogemos la lista jugadores del objeto cogio de la DB y miramos su tamaño
		return numJugadores;
	}

	
	public double mediaEdadEquipo (int idEquipo) {
		double media=0;
		String jpql="SELECT avg(j.edad) FROM Jugador j WHERE j.equipo.id='"+idEquipo+"'";
		Query query = em.createQuery(jpql);
		media=(double)query.getSingleResult(); //GetSingleResult devuelve un unico resultado-CASTEAMOS
		return media;
	}
	
	
	public List<Equipo> equiposDivision(String division) {
		String jpql="SELECT e FROM Equipo e WHERE e.division=?1 AND e.nombre!=?2";
		Query query=em.createQuery(jpql);
		query.setParameter(1, division);
		//JugadoresMercadoPrimera
		division=division.toLowerCase();
		//CONCATENAR JUGADORESMercado+Primera/Segunda , entonces pasamos todo a minuscula y la primera en mayuscula
		query.setParameter(2, "JugadoresMercado"+Character.toUpperCase(division.charAt(0))+division.substring(1,division.length()-1));//SUBSTRING(caracterInicio, caracterFin)
		List<Equipo> equipos=query.getResultList();
		return equipos;
	}
	
	
	
	public void imprimirEquipos(List<Equipo> equipos) {
		
		for(Equipo eq:equipos) { //recorremos el List de equipos
			System.out.println(eq);
			//Sacamos la lista de jugadores del equipo
			List<Jugador> jugadores=eq.getJugadores();
			Iterator<Jugador> iteradorJug=jugadores.iterator();
			while(iteradorJug.hasNext()) {
				System.out.println(iteradorJug.next());
			}
		}
	}
	

	public boolean borrarEquipo(int idEquipo) {
		boolean exito=true;
		try {
			Equipo equipoAgentesLibres=null;
			Equipo equipo=em.find(Equipo.class, idEquipo); //Podemos extrar de la DB el equipo con este metodo dado q buscamos x id _ si buscaramos por otro campo habria que usar JPQL
			String jpql="SELECT e FROM Equipo e WHERE e.nombre=?1";
			Query query=em.createQuery(jpql);
			String division=equipo.getDivision();
			query.setParameter(1, "JugadoresMercado"+Character.toUpperCase(division.charAt(0))+division.substring(1,division.length()-1));
			List<Equipo> listadoEquipos = query.getResultList();
			
			if(listadoEquipos.size()==0) { //SI ES 0 lo creamos
				System.out.println("Agentes es NULL");
				String nombre="JugadoresMercado"+Character.toUpperCase(division.charAt(0))+division.substring(1,division.length()-1);
				Equipo e=new Equipo(nombre,division);
				crearEquipo(e);
			} else {
				equipoAgentesLibres=listadoEquipos.get(0);
			}
			
			List<Jugador> jugadores=equipo.getJugadores(); 
			for(Jugador jug:jugadores) {
				traspaso(equipo.getId(), equipoAgentesLibres.getId(), jug);
			}
			
			EntityTransaction tran=em.getTransaction();
			tran.begin();
			em.remove(equipo);
			tran.commit();
		}catch(Exception ex) {
			exito=false;
		}
		return exito;
	}
	
	
}

