package io.github.ivanbabura.repositories;

import io.github.ivanbabura.entities.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepositoryJpaImpl implements PersonRepository {
    private final EntityManagerFactory emf;

    @Autowired
    public PersonRepositoryJpaImpl(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    @Override
    public Person findById(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Person p = em.find(Person.class, id);
        em.getTransaction().commit();
        em.close();
        return p;
    }

    @Override
    public void save(String name) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Person p = new Person();
        p.setName(name);
        em.persist(p);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public boolean update_name(Person person) {
        boolean modified;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Person person_updated = em.find(Person.class, person.getId());
        if (person_updated != null && !person_updated.getName().equals(person.getName())) {
            person_updated.setName(person.getName());
            modified = true;
        } else modified = false;
        em.getTransaction().commit();
        em.close();
        return modified;
    }

    @Override
    public void delete(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(Person.class, id));
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<Person> getAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Person> list = em.createQuery("from Person", Person.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return list;
    }

    @Override
    public List<Person> getAllStartsWith(String startOfName) {
        //я не стал заморачиваться и сделал так, по-хорошему надо выборку тоже внутри SQL делать, но я быстро не нашёл решения.
        final int length = startOfName.length();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Person> list = em.createQuery("from Person", Person.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return list.stream().filter(x-> x.getName().substring(0,length).equals(startOfName)).toList();
    }

    @Override
    public List<String> getAllNamesStartsWith(String startOfName) {
        //я не стал заморачиваться и сделал так, по-хорошему надо выборку тоже внутри SQL делать, но я быстро не нашёл решения.
        return getAllStartsWith(startOfName).stream().map(Person::getName).toList();
    }

    public void replaceById(int id, String name){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Person p = new Person();
        p.setId(id);
        p.setName(name);
        em.merge(p);
        em.getTransaction().commit();
        em.close();
    }
    public void updateNative(int id, String name){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("UPDATE people SET name=? WHERE id =?")
                .setParameter(1,name)
                .setParameter(2,id)
                .executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

}