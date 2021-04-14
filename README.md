# Portable http session with Spring Session and Hazelcast POC

### Contact model

Contact class represents the object stored in the session. It shows
how contained information migrates between different active versions, without
broke the application.

The class implements two interfaces:
 - *VersionPortable*: enable the support to serialize multiple versions of the same object
 - *Serializable*: used for the default serialization (if it not implemented, some unexpected behaviour can occur)

#### VersionPortable
The class that uses this interface needs to implement the following methods:
- ``public int getClassVersion()`` is the current class' version
- ``public int getClassId()`` is the class id
- ``public int getFactoryId()`` is the factory's id for the class ContactPortableFactory
- ``public void writePortable(PortableWriter portableWriter) throws IOException`` contains the custom serialisation logic
- ``public void readPortable(PortableReader portableReader) throws IOException`` contains the custom de-serialisation logic

#### Enable Contact custom serialisation
To enable the portable serialisation:
```
// Contact's Portable serialisation
config.getSerializationConfig()
.setAllowOverrideDefaultSerializers(true)
.addPortableFactory(ContactPortableFactory.FACTORY_ID, new ContactPortableFactory());
```

## Usage examples
There are three branches which have different version of Contact class.
The web home page allows saving and shows the following fields:
- **master**: name, surname, country
- **v2**: name, surname, country, email
- **v3**: name, surname

### How run the three version simultaneously

- Go to the main project directory

- For each branch compile and package the application with this command:
`` mvn package ``. The */target* folder should contain three executable wars

- go to the target folder, open three tabs in the terminal and run:
    - ``java -jar demo-spring-session-with-hazelcast-1.0.0-SNAPSHOT.war``
    - ``java -jar demo-spring-session-with-hazelcast-2.0.0-SNAPSHOT.war --server.port=8081``
    - ``java -jar demo-spring-session-with-hazelcast-3.0.0-SNAPSHOT.war --server.port=8082``
    
- access the first instance by opening the browser and entering the url *http://localhost:8080*. Enter the username **user** and
**password** to login

- update the contact information by entering name, surname and country. 
  The data will be stored into the session (the table at the top will show them)
  
  
- open a new tab and access to second instance (http://localhost:8081). The home page will show 
  the new empty field **email**. The others fields should have the information inserted in the step before

  
- open a new tab and access to the third instance (http://localhost:8082). The home page will show
the two field **name** and **surname** which will not be lost

  
### Compatibility limitation
**Portable is backward compatible only for read**:

-   If we update the data from an application who
    has a previous version, then we'll lose the new field updates done previously by an app has higher version of the Portable object.
-   If we modified the class by removing a field, the old members get null for the objects that are put by the new member.

To improve the backward/forward compatibility we could plug in a custom serializer library, as for example *Google Protocol Buffer* or *Kryo*.
See these link for more info:
- https://docs.hazelcast.com/imdg/4.2/serialization/custom-serialization.html
- https://github.com/EsotericSoftware/kryo
- https://github.com/gokhanoner/data-versioning-protobuf
- https://developers.google.com/protocol-buffers/

## Useful external link
- [Hazelcast portable serialisation](https://docs.hazelcast.com/imdg/4.2/serialization/implementing-portable-serialization.html)
- [Spring session hazelcast](https://guides.hazelcast.org/spring-session-hazelcast/#_whats_more)
- [Spring session](https://docs.spring.io/spring-session/docs/2.0.1.RELEASE/reference/html5/#samples)

## Alternative session replication solution
- [Tomcat HTTP Session](https://guides.hazelcast.org/springboot-tomcat-session-replication/)
- [WebFilter](https://guides.hazelcast.org/springboot-webfilter-session-replication/)
