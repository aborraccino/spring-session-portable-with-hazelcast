package com.worldpay.spring.session.model;

import com.hazelcast.nio.serialization.PortableReader;
import com.hazelcast.nio.serialization.PortableWriter;
import com.hazelcast.nio.serialization.VersionedPortable;
import com.worldpay.spring.session.hazelcast.factory.ContactPortableFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;
import java.io.Serializable;

/**
 * Contact V1 (CLASS_VERSION_ID=1): it has these field:
 *  username
 *  surname
 *  country
 *  email
 */
@SessionScope
@Component("contact")
public class Contact implements VersionedPortable, Serializable {

    public static final int CLASS_ID = 1;

    public static final int CLASS_VERSION_ID = 1;

    private static final long serialVersionUID = 1234567L;

    private String username = "user";
    private String name;
    private String surname;
    private String country;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public int getFactoryId() {
        return ContactPortableFactory.FACTORY_ID;
    }

    @Override
    public int getClassId() {
        return CLASS_ID;
    }

    @Override
    public void writePortable(PortableWriter portableWriter) throws IOException {
        portableWriter.writeString("username", username);
        portableWriter.writeString("name", name);
        portableWriter.writeString("surname", surname);
        portableWriter.writeString("country", country);
    }

    @Override
    public void readPortable(PortableReader portableReader) throws IOException {
        username = portableReader.readString("username");
        name = portableReader.readString("name");
        surname = portableReader.readString("surname");
        country = portableReader.readString("country");
    }

    @Override
    public int getClassVersion() {
        return CLASS_VERSION_ID;
    }
}
