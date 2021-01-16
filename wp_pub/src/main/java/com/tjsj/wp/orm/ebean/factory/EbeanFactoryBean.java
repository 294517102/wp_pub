package com.tjsj.wp.orm.ebean.factory;

import javax.sql.DataSource;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.springsupport.txn.SpringAwareJdbcTransactionManager;


/**
 * 
 * @author gongdzh
 *
 */

@Component
public class EbeanFactoryBean implements FactoryBean<EbeanServer> {

	@Autowired
	private DataSource dataSource;

	@Override
	public EbeanServer getObject() throws Exception {
		
		ServerConfig config = new ServerConfig();
		config.setName("default");

		// set the spring's datasource and transaction manager.
		config.setDataSource(dataSource);
		config.setExternalTransactionManager(new SpringAwareJdbcTransactionManager());

		config.loadFromProperties();

		//set as default and register so that Model can be
		//used if desired for save() and update() etc
		config.setDefaultServer(true);
		config.addPackage("com.tjsj.wp.orm.entity");
		config.setRegister(true);

		//cache setting
		config.setQueryCacheMaxIdleTime(24*12*3600);
		config.setDisableL2Cache(false);
		config.setQueryCacheMaxTimeToLive(2*24*12*3600);
		config.setQueryCacheMaxSize(20000);


		return EbeanServerFactory.create(config);
	}

	@Override
	public Class<?> getObjectType() {
		return EbeanServer.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
