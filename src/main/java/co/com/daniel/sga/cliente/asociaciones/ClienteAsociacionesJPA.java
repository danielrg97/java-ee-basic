package co.com.daniel.sga.cliente.asociaciones;

import co.com.daniel.sga.domain.Persona;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClienteAsociacionesJPA {

    static Logger LOGGER = LogManager.getRootLogger();

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();

        List<Persona> personas = em.createNamedQuery("Persona.findAll").getResultList();

        em.close();

        personas.stream().forEach(p -> {
            LOGGER.info("Personas: "+p.toString());
            p.getUsuarioList().stream().forEach(u -> LOGGER.info("Usuarios: " + u.toString()));
        });
    }
}
