package com.paulo.crudbaas.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.paulo.crudbaas.PersonNotFoundException;
import com.paulo.crudbaas.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class PersonService {

    @Autowired private Firestore firestore;

    public static final String COL_NAME = "persons";

    private CollectionReference getPersonsCollection() {
        return firestore.collection(COL_NAME);
    }

    public void save(Person person) throws InterruptedException, ExecutionException {
        getPersonsCollection().document(person.getName()).set(person);
    }

    public Person getOne(String name) throws InterruptedException, ExecutionException, PersonNotFoundException {
        DocumentReference documentReference = getPersonsCollection().document(name);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        if (!document.exists())
            throw new PersonNotFoundException("Person with name " + name + " not found");
        return document.toObject(Person.class);
    }

    public Person update(String name, Person person) throws InterruptedException, ExecutionException, PersonNotFoundException {
        this.getOne(name);
        getPersonsCollection().document(name).set(person);
        return person;
    }

    public void delete(String name) throws InterruptedException, ExecutionException, PersonNotFoundException {
        this.getOne(name);
        getPersonsCollection().document(name).delete();
    }

}