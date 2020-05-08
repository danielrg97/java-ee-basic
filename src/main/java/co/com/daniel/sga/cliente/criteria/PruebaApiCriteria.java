/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.daniel.sga.cliente.criteria;

import co.com.daniel.sga.domain.Persona;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Admin
 */
public class PruebaApiCriteria {
    private static Logger LOGGER = LogManager.getRootLogger();
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();
        
        CriteriaBuilder cb = null;
        CriteriaQuery<Persona> criteriaQuery = null;
        Root<Persona> fromPersona = null;
        TypedQuery<Persona> query = null;
        Persona persona = null;
        List<Persona> personas = null;
        //Query utilizando el api de criteria 
        //1. Consulta de todas las personas
        
        //Paso 1. El objeto EntityManager crea instancia de CriteriaBuilder
        cb = em.getCriteriaBuilder();
        
        //Paso 2. Se crea un objeto CriteriaQuery
        criteriaQuery = cb.createQuery(Persona.class);
        
        //Paso 3. Creamos el objeto raiz de query
        fromPersona = criteriaQuery.from(Persona.class);
        
        //Paso 4. seleccionamos lo necesario del from 
        criteriaQuery.select(fromPersona);
        
        //Paso 5. creamos el query typeSafe
        query = em.createQuery(criteriaQuery);
        
        personas = query.getResultList();
      

        //2-a. consulta de la Persona con id=1 
        cb = em.getCriteriaBuilder();
        criteriaQuery = cb.createQuery(Persona.class);
        fromPersona = criteriaQuery.from(Persona.class);
        criteriaQuery.select(fromPersona).where(cb.equal(fromPersona.get("idPersona"), 1));
        persona = em.createQuery(criteriaQuery).getSingleResult();
        LOGGER.info(persona.toString());
        
        
        //2-b. Consulta de la Persona  con id = 1
        LOGGER.info("Consulta de la Persona  con id = 1");
        cb = em.getCriteriaBuilder();
        criteriaQuery = cb.createQuery(Persona.class);
        fromPersona = criteriaQuery.from(Persona.class);
        criteriaQuery.select(fromPersona);
        
        //La clase Predicate  permite agregar varios criterios dinamicos
        List<Predicate> predicados = new ArrayList<>();
        
        //Verificamos si hay criterios que agregar
        Integer idPersonaParam = 1;
        ParameterExpression<Integer> parameter = cb.parameter(Integer.class, "idPersona");
        predicados.add(cb.equal(fromPersona.get("idPersona"), parameter));
        
        //Se agregan los criterios 
        if(predicados.isEmpty()){
            throw new RuntimeException("Sin criterios");
        }else if(predicados.size() == 1){
            criteriaQuery.where(predicados.get(0));
        }else{
            criteriaQuery.where(cb.and(predicados.toArray(new Predicate[0])));
        }
        query = em.createQuery(criteriaQuery).setParameter("idPersona", 1);
        personas = query.getResultList();
        personas.stream().forEach(p -> LOGGER.info(p.getNombre()));
    }
}
