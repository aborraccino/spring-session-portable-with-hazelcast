package com.worldpay.spring.session.hazelcast.factory;

import com.hazelcast.nio.serialization.Portable;
import com.hazelcast.nio.serialization.PortableFactory;
import com.worldpay.spring.session.model.Contact;

public class ContactPortableFactory implements PortableFactory {

    public static final int FACTORY_ID = 1;

    @Override
    public Portable create(int classId) {
        if (classId == Contact.CLASS_ID) {
            return new Contact();
        }
        return null;
    }
}
