package com.example.demo.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.awt.*;

@Repository
@Transactional
public class CustomerDAO {
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void add(Customer customer) {
        getSession().save(customer);
    }

    public void delete(Customer customer) {
        getSession().delete(customer);
    }

    /*
    @SuppressWarnings("unchecked")
    public List getAll() {
        return getSession().createQuery("from customer_table").list();
    }

     */

    public Customer getCustomer(int id) {
        return (Customer) getSession().load(Customer.class, id);
    }

    public void update(Customer customer) {
        getSession().update(customer);
    }
}
