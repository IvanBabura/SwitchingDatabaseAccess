package io.github.ivanbabura.repositories;

import io.github.ivanbabura.entities.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepositoryHibernateImpl implements PersonRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public PersonRepositoryHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Person findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Person person = session.get(Person.class, id);
        session.getTransaction().commit();
        session.close();
        return person;
    }

    @Override
    public void save(String name) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Person person = new Person();
        person.setName(name);
        session.persist(person);
        transaction.commit();
        session.close();
    }

    @Override
    public boolean update_name(Person person) {
        boolean modified;
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Person person_updated = session.get(Person.class, person.getId());
        if (person_updated != null && !person_updated.getName().equals(person.getName())) {
            person_updated.setName(person.getName());
            session.persist(person_updated);
            modified = true;
        } else modified = false;
        transaction.commit();
        session.close();
        return modified;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.remove(session.find(Person.class, id));
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Person> getAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Person> list = session.createQuery("from Person", Person.class).getResultList();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public List<Person> getAllStartsWith(String startOfName) {
        //я не стал заморачиваться и сделал так, по-хорошему надо выборку тоже внутри SQL делать, но я быстро не нашёл решения.
        final int length = startOfName.length();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Person> list = session.createQuery("from Person", Person.class).getResultList();
        session.getTransaction().commit();
        session.close();
        return list.stream().filter(x-> x.getName().substring(0,length).equals(startOfName)).toList();
    }

    @Override
    public List<String> getAllNamesStartsWith(String startOfName) {
        //я не стал заморачиваться и сделал так, по-хорошему надо выборку тоже внутри SQL делать, но я быстро не нашёл решения.
        return getAllStartsWith(startOfName).stream().map(Person::getName).toList();
    }
}
