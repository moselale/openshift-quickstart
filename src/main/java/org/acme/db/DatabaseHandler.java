package org.acme.db;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.acme.jpa.EntityManagerFactoryHandler;
import org.acme.model.Gift;

@ApplicationScoped
public class DatabaseHandler {
    
    @Inject
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHandler.class);
    
    @Transactional 
    public boolean createGift(Gift gift) {
        try{
            gift.setName("giftDescription");
            em.persist(gift);
            return true;
        }catch(Exception e){
            trace("Errore nel creare il Gift. Errore: " + e, e);
            return false;
        }
    }

    @Transactional
    public boolean persistGift(Gift gift) {
		try {

			// EntityManager em = getEm();
			em.getTransaction().begin();
			try {
				em.persist(gift);
				em.flush();
				em.getTransaction().commit();
			} catch (Exception e) {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				trace("Non è stato possibile salvare il Gift. Errore: " + e.getMessage(), e);
				return false;

			} finally {
				em.close();
			}

		} catch (Exception e) {
			trace("Errore nel prendere l'EntityManager. Errore: " + e.getMessage(), e);
			return false;
		}

		return true;
	}

	private static EntityManager getEm() {
		EntityManagerFactory emf = null;
		try {
			emf = EntityManagerFactoryHandler.getEntityManagerFactory();
		} catch (Exception e) {
			trace("could not get EntitiyManagerFactory: " + e.getMessage());
		}
		return emf.createEntityManager();
    }
    

	private static void trace(String toPrint) {
		LOGGER.error(toPrint);
	}

	private static void trace(String toPrint, Exception e) {
		LOGGER.error(toPrint, e);
	}
}