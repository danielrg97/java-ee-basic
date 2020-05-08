package co.com.daniel.sga.cliente.ciclovidajpa;

import co.com.daniel.sga.domain.Persona;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ActualizarObjetoJPA {
    static Logger LOGGER = LogManager.getRootLogger();
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        //Inicia la transaccion
        tx.begin();
        //Paso 1 : obtener
        Persona persona =  em.find(Persona.class, 4);
        tx.commit();        
        LOGGER.info("Objeto obtenido: "+persona);
        //Ejecuta sql
        persona.setApellido("Ochoa");

        EntityTransaction tx2 = em.getTransaction();
        tx2.begin();
        em.merge(persona);
        tx2.commit();
        LOGGER.info("Objeto recuperado: "+persona);

        //Cerrar em
        em.close();
    }
}
