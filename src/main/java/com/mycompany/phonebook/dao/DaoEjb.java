/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.phonebook.dao;


import com.mycompany.phonebook.entitys.Users;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Артур
 */
@Stateless
public class DaoEjb {
     @PersistenceContext(unitName = "Phonebook")
     private EntityManager entityManager;
    
    public List<Users> getAllUsers(){
        return entityManager.createQuery("from Users u", Users.class).getResultList();
    }
     
    public boolean saveUser(String nameParam, String phoneParam){
        Users user = new Users();
        user.setName(nameParam);
        user.setPhone(phoneParam);
      //  entityManager.getTransaction().begin(); //транзакция устанавливается tomee по ходу
        try{
            entityManager.persist(user);
           // entityManager.getTransaction().commit();
        }
        catch (Exception ex) {
           // entityManager.getTransaction().rollback();
            return false;
        }
        return true;
    }
    
    public List<Users> findUsers(String nameParam, String phoneParam){
        Query q = null;
        
        if("".equals(nameParam) == false && "".equals(phoneParam) == false){
             q = entityManager.createNamedQuery("Users.findByNameByPhone",Users.class);
             q.setParameter("name", nameParam);
             q.setParameter("phone", phoneParam);
        }
        else{
            if("".equals(nameParam) == false){
               q = entityManager.createNamedQuery("Users.findByName",Users.class); 
               q.setParameter("name", nameParam);
            }
            else{
               q = entityManager.createNamedQuery("Users.findByPhone",Users.class);
               q.setParameter("phone", phoneParam);
            }
        }
        
        return q.getResultList(); 
    }
    
}
