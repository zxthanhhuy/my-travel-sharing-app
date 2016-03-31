package vn.edu.iuh.fit.travelsharing.util;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.data.neo4j.server.RemoteServer;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.data.neo4j.template.Neo4jTemplate;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories(basePackages = "vn.edu.iuh.fit.travelsharing.repository", queryLookupStrategy = QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND)
@EnableTransactionManagement
public class ConnectionManager extends Neo4jConfiguration {
	static final Logger LOGGER = LoggerFactory
			.getLogger(ConnectionManager.class);

	private static final String NEO4J_HOST = "http://localhost:";
	private static final String NEO4J_PORT = "7474";

	@Autowired
	private Environment env;

	@Override
	@Bean
	public Neo4jServer neo4jServer() {
		Neo4jServer neo4jServer = new RemoteServer(NEO4J_HOST + NEO4J_PORT);
		return neo4jServer;
	}

	@Override
	@Bean
	public SessionFactory getSessionFactory() {
		String username = env.getProperty("neo4j.username");
		String password = env.getProperty("neo4j.password");
		System.setProperty("username", username);
		System.setProperty("password", password);
		SessionFactory sessionFactory = new SessionFactory(
				"vn.edu.iuh.fit.travelsharing.model");
		return sessionFactory;
	}

	@Override
	@Bean
	@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public Session getSession() throws Exception {
		Session session = super.getSession();
		return session;
	}

	@Bean
	public Neo4jOperations getNeo4jTemplate() throws Exception {
		return new Neo4jTemplate(getSession());
	}

}
