package org.acme.jpa;

import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

public class EntityManagerFactoryHandler {
    
	public static final String PERSISTENCE_UNIT_NAME = "morohub";
	
	public static final String DATA_SOURCE_NAME = "jdbc:postgresql://172.30.240.43:5432/Moro;user=pocuser;password=pocpassword";

	private static EntityManagerFactory emf = null;

	/*
	 * Getter for an EntityManagerFactory. 
	 * If there is already one existing return this.
	 * If not, then create a new one.
	 */
	public static EntityManagerFactory getEntityManagerFactory()
			throws Exception {
		if (emf == null)
			emf = createNewEntityManagerFactory();
		return emf;
	}

	
	/*
	 * Return a new EntityManagerFactory for the defined data source and persistence unit.
	 */
	private static EntityManagerFactory createNewEntityManagerFactory()
			throws Exception {

		InitialContext ctx = new InitialContext();
		DataSource ds = (DataSource) ctx.lookup(DATA_SOURCE_NAME);

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("NON_JTA_DATASOURCE", ds);

		// As replication will change the underlying database without
		// notifying the hub the JPA cache needs to be disabled.
		// Else the hub might provide outdated data
		properties.put("CACHE_SHARED_DEFAULT", "false");

		return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
	}
}