package co.com.daniel.sga.cliente.ciclovidajpa;

import co.com.daniel.sga.domain.Persona;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PersistirObjetoJPA {
    static Logger LOGGER = LogManager.getRootLogger();
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        //Inicia la transaccion
        //Paso 1 : Crear un nuevo objeto
        
        Persona persona = new Persona("Sandra", "Gomez", "sandra.gomez@gmail.com", "3742");
        
        //Inicia la transaccion
        tx.begin();
        
        //Ejecuta sql
        em.persist(persona);
        
        LOGGER.info("Objeto persistido - sin commit: "+persona);

        //commit - rollback
        
        tx.commit();
        
        //Objeto en estado detached
        LOGGER.info("Objeto persistido - estado detached: "+persona);
        
        //Cerrar em
        em.close();
    }
}
