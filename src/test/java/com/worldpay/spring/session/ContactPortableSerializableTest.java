//package com.worldpay.spring.session.hazelcast.demo;
//
//import com.hazelcast.config.Config;
//import com.hazelcast.config.SerializerConfig;
//import com.hazelcast.core.Hazelcast;
//import com.hazelcast.core.HazelcastInstance;
//import com.worldpay.spring.session.hazelcast.demo.model.Contact;
//import com.worldpay.spring.session.hazelcast.demo.serializer.ContactSerializer;
//
//public class ContactPortableSerializableTest {
//
//    public static void main(String[] args) {
//        Config config = new Config();
//
//        // portable serialisation
////        config.getSerializationConfig()
////                .addPortableFactory(ContactPortableFactory.FACTORY_ID, new ContactPortableFactory());
//
//        // custom serialisation
//        SerializerConfig serializer = new SerializerConfig()
//                .setImplementation(new ContactSerializer())
//                .setTypeClass(Contact.class);
//        config.getSerializationConfig()
//                .addSerializerConfig(serializer);
//
//        // Start the Embedded Hazelcast Cluster Member.
//        HazelcastInstance hz = Hazelcast.newHazelcastInstance(config);
//
//        //Customer can be used here
//        Contact contact = new Contact();
//        contact.setName("myName");
//        IMap<String, Contact> contactMap = hz.getMap("contactMap");
//        contactMap.put("test", contact);
//
//        System.out.println("the entry is " + hz.getMap("contactMap").get("test"));
//
//        hz.shutdown();
//    }
//
//
//}
