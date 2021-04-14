package com.worldpay.spring.session.config;

import com.hazelcast.config.AttributeConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.IndexConfig;
import com.hazelcast.config.IndexType;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.worldpay.spring.session.hazelcast.factory.ContactPortableFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.FlushMode;
import org.springframework.session.MapSession;
import org.springframework.session.SaveMode;
import org.springframework.session.config.SessionRepositoryCustomizer;
import org.springframework.session.hazelcast.Hazelcast4IndexedSessionRepository;
import org.springframework.session.hazelcast.Hazelcast4PrincipalNameExtractor;
import org.springframework.session.hazelcast.HazelcastSessionSerializer;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

@EnableHazelcastHttpSession(maxInactiveIntervalInSeconds = 300)
@Configuration
public class SessionConfig {

	@Value("${session.port:5701}")
	private int port;

	private final String SESSIONS_MAP_NAME = "spring-session-map-name";

	@Bean
	public SessionRepositoryCustomizer<Hazelcast4IndexedSessionRepository> customize() {
		return (sessionRepository) -> {
			sessionRepository.setFlushMode(FlushMode.IMMEDIATE);
			sessionRepository.setSaveMode(SaveMode.ALWAYS);
			sessionRepository.setSessionMapName(SESSIONS_MAP_NAME);
			sessionRepository.setDefaultMaxInactiveInterval(900);
		};
	}

	@Bean(destroyMethod = "shutdown", name = "myHazelcastInstance")
	public HazelcastInstance hazelcastInstance() {
		Config config = new Config();
		config.setClusterName("spring-session-cluster");

		NetworkConfig networkConfig = new NetworkConfig();
		networkConfig.setPortAutoIncrement(true);

		networkConfig.getJoin()
				.getMulticastConfig()
				.setEnabled(false);

		networkConfig.getJoin()
				.getTcpIpConfig()
				.addMember("localhost")
				.setEnabled(true);

		config.setNetworkConfig(networkConfig);

		System.out.println("Hazelcast port #: " + port);

		// Add this attribute to be able to query sessions by their PRINCIPAL_NAME_ATTRIBUTE's
		AttributeConfig attributeConfig = new AttributeConfig()
				.setName(Hazelcast4IndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
				.setExtractorClassName(Hazelcast4PrincipalNameExtractor.class.getName());

		// Configure the sessions map
		config.getMapConfig(SESSIONS_MAP_NAME)
				.addAttributeConfig(attributeConfig).addIndexConfig(
				new IndexConfig(IndexType.HASH, Hazelcast4IndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE));

		// Use custom serializer to de/serialize sessions faster. This is optional.
		// Note that, all members in a cluster and connected clients need to use the
		// same serializer for sessions. For instance, clients cannot use this serializer
		// where members are not configured to do so.
		SerializerConfig serializerConfig = new SerializerConfig();
		serializerConfig.setImplementation(new HazelcastSessionSerializer()).setTypeClass(MapSession.class);
		config.getSerializationConfig().addSerializerConfig(serializerConfig);

		// Contact's Portable serialisation
		config.getSerializationConfig()
				.setAllowOverrideDefaultSerializers(true)
				.addPortableFactory(ContactPortableFactory.FACTORY_ID, new ContactPortableFactory())
				.setPortableVersion(0);

		return Hazelcast.newHazelcastInstance(config);
	}
}
