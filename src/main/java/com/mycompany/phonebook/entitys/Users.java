/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.phonebook.entitys;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Артур
 */
@Entity
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "Users.findByName", query = "SELECT u FROM Users u WHERE u.name = :name")
    , @NamedQuery(name = "Users.findByNameByPhone", query = "SELECT u FROM Users u WHERE u.name = :name and u.phone = :phone")
    , @NamedQuery(name = "Users.findByPhone", query = "SELECT u FROM Users u WHERE u.phone = :phone")})

public class Users implements Serializable {

   // private static final long serialVersionUID = 1L;
    //@Size(max = 2147483647)
    
   // @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Basic(optional = false)
    //@Column(name = "id")
    
    @Id
    @SequenceGenerator(name="users_id_seq",
                       sequenceName="users_id_seq",
                       allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator="users_id_seq")
    @Column(name = "id", updatable=false)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "phone")
    private String phone;

    public Users() {
    }

    public Users(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "phonebookweb.dao.entities.Users[ id=" + id + " ]";
    }
    
}
