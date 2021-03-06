package com.x.common.core.container.factory;

import java.lang.reflect.Field;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.x.base.core.entity.JpaObject;
import com.x.base.core.entity.annotation.CheckPersist;
import com.x.base.core.entity.annotation.CheckRemove;
import com.x.common.core.container.DataMappings;
import com.x.common.core.container.EntityManagerContainer;

public class EntityManagerContainerFactory extends SliceEntityManagerContainerFactory {

	private volatile static EntityManagerContainerFactory instance;

	public static void init(String webApplicationDirectory, DataMappings dataMappings) throws Exception {
		synchronized (EntityManagerContainerFactory.class) {
			if (instance != null) {
				EntityManagerContainerFactory.close();
			}
			instance = new EntityManagerContainerFactory(webApplicationDirectory, dataMappings);
		}
	}

	public static EntityManagerContainerFactory instance() throws Exception {
		if (instance == null) {
			throw new Exception("get EntityManagerContainerFactory instance error, not initial.");
		}
		return instance;
	}

	private EntityManagerContainerFactory(String webApplicationDirectory, DataMappings dataMappings) throws Exception {
		super(webApplicationDirectory, dataMappings);
	}

	public static void close() throws Exception {
		try {
			if (instance != null) {
				for (EntityManagerFactory emf : instance.entityManagerFactoryMap.values()) {
					if (emf.isOpen()) {
						emf.close();
					}
				}
				instance.entityManagerFactoryMap.clear();
				instance.checkPersistFieldMap.clear();
				instance.checkRemoveFieldMap.clear();
			}
			/* 注销驱动程序 */
			// Set<Driver> drivers = new HashSet<>();
			// for (String url : instance.jdbcUrls) {
			// drivers.add(DriverManager.getDriver(url));
			// }
			Enumeration<Driver> drivers = DriverManager.getDrivers();
			while (drivers.hasMoreElements()) {
				Driver driver = drivers.nextElement();
				// System.out.println("deregisterDriver:" + driver);
				DriverManager.deregisterDriver(driver);
			}
			/* 由于可能重新载入 */
			instance = null;
		} catch (Exception e) {
			throw new Exception("close error.", e);
		}
	}

	public EntityManagerContainer create() {
		EntityManagerContainer container = new EntityManagerContainer(this);
		return container;
	}

	public <T extends JpaObject> EntityManager createEntityManager(Class<T> cls) throws Exception {
		try {
			for (Class<?> clazz : entityManagerFactoryMap.keySet()) {
				if (clazz.isAssignableFrom(cls)) {
					return entityManagerFactoryMap.get(clazz).createEntityManager();
				}
			}
			throw new Exception("can not createEntityManager for class " + cls.getCanonicalName()
					+ ", not registed in EntityManagerContainerFactory.");
		} catch (Exception e) {
			throw new Exception("get entityManager for " + cls + " error.", e);
		}
	}

	public Map<Field, CheckPersist> getCheckPersistFields(Class<?> clazz) throws Exception {
		return checkPersistFieldMap.get(assignableFrom(clazz));
	}

	public Map<Field, CheckRemove> getCheckRemoveFields(Class<?> clazz) throws Exception {
		return checkRemoveFieldMap.get(assignableFrom(clazz));
	}
}
