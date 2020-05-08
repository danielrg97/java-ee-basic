/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.daniel.sga.cliente.jpql;

import co.com.daniel.sga.domain.Persona;
import co.com.daniel.sga.domain.Usuario;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Admin
 */
public class PruebaJPQL {
    private static Logger LOGGER = LogManager.getRootLogger();
    public static void main(String[] args) {
        String jpql = null;
        Query query = null;
        List<Persona> personas = null;
        Persona persona = null;
        Iterator iter = null;
        Object[] tupla = null;
        List<String> nombres = null;
        List<Usuario> usuarios = null;
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();
        
        //Mostrar todos
        LOGGER.info("Consulta de todas las personas");
        jpql = "select p from Persona p";
        personas = em.createQuery(jpql).getResultList();
        mostrarPersonas(personas); 
        
        //Mostrar por id
        LOGGER.info("Consulta por id");
        jpql = "select p from Persona p where p.idPersona=1";
        persona = (Persona) em.createQuery(jpql).getSingleResult();
        LOGGER.info("Consulta individual por id: "+persona.toString());
        
        //Mostrar  por nombre
        LOGGER.info("Consulta por nombre");
        jpql = "select p from Persona p where p.nombre like '%aniel%'";
        persona = (Persona) em.createQuery(jpql).getSingleResult();
        LOGGER.info("Consulta individual por nombre: "+persona.toString());
        
        //Consulta de datos individuales. Se crea un arreglo (tupla) de tipo object de 3 columnas
        LOGGER.debug("Consulta de datos individuales. Se crea un arreglo (tupla) de tipo object de 3 columnas");
        jpql = "select p.nombre, p.apellido, p.email from Persona p";
        iter = em.createQuery(jpql).getResultList().iterator();
        while(iter.hasNext()){
            tupla = (Object[]) iter.next();
            String nombre = (String) tupla[0];
            String apellido = (String) tupla[1];
            String email = (String) tupla[2];
            LOGGER.info("nombre: "+nombre+" apellido: "+apellido+" email: "+email);
        }
        
        //Se obtiene el objeto Persona y el id, se crea un arreglo  de tipo Object  con dos columnas
        LOGGER.info("Se obtiene el objeto Persona y el id, se crea un arreglo  de tipo Object  con dos columnas");
        jpql = "select p, p.idPersona from Persona p";
        iter= em.createQuery(jpql).getResultList().iterator();
        while(iter.hasNext()){
            tupla = (Object[]) iter.next();
            persona = (Persona) tupla[0];
            int id = (int) tupla[1];
            LOGGER.info("Persona: "+persona.toString());
            LOGGER.info("id persona: "+id);
        }
        
        
        //Consulta todas las personas
        LOGGER.info("Consulta de todas las personas");
        jpql = "select new co.com.daniel.sga.domain.Persona( p.idPersona ) from Persona p";
        personas = em.createQuery(jpql).getResultList();
        mostrarPersonas(personas);
        
        //Regresa el valor minimo y maximo del idPersona (scaler result)
        LOGGER.info("Regresa el valor minimo y maximo del idPersona (scaler result)");
        jpql = "select min(p.idPersona) as minID, max(p.idPersona) as maxID, count(p.idPersona) as contador from Persona p";
        iter = em.createQuery(jpql).getResultList().iterator();
        while(iter.hasNext()){
            tupla = (Object[]) iter.next();
            Integer min = (Integer) tupla[0];
            Integer max = (Integer) tupla[1];
            Long contador = (Long) tupla[2];
            LOGGER.info("minimo: "+min+", maximo: "+max+", contador: "+contador);
        }
         //Consulta de las personas con nombre distinto
        LOGGER.info("Consulta de las personas con nombre distinto");
        jpql = "select count(distinct p.nombre) from Persona p";
        Long contador = (Long) em.createQuery(jpql).getSingleResult();
        LOGGER.info("cantidad de nombres distintos: "+contador);
        
        //concatena y convierte mayusculas el nombre y apellido
        LOGGER.info("concatena y convierte mayusculas el nombre y apellido");
        jpql = "select CONCAT(p.nombre, ' ',p.apellido) as nombre from Persona p";
        nombres = em.createQuery(jpql).getResultList();
        nombres.stream().forEach(p -> LOGGER.info(p));
        
        //Obtiene el objeto persona con el id igual al parametro proporcionado
        LOGGER.info("Obtiene el objeto persona con el id igual al parametro proporcionado");
       jpql = "select p from Persona p where p.idPersona  = :parametro";
       persona = (Persona) em.createQuery(jpql).setParameter("parametro", 1).getSingleResult();
       LOGGER.info("persona con id = 1: "+persona.toString());
       
       //Obtiene las personas que tienen la letra a en el nombre
       LOGGER.info("Obtiene las personas que tienen la letra a en el nombre");
       jpql = "select p from Persona p where upper(p.nombre) like upper('%a%')";
       personas = em.createQuery(jpql).getResultList();
        mostrarPersonas(personas);
       
    }

    private static void mostrarPersonas(List<Persona> personas) {
        personas.stream().forEach(p -> {LOGGER.info(p.toString());});
    }
}
