/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PortfolioManager.domain.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author Ahmed
 */
@Entity
public class Investor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String login;
    private String lastName;
    private String name;
    private String email;
    private String password;
    private String mobile;
    
    @OneToOne(cascade= {CascadeType.ALL})
    private Account account;
    
    @OneToOne
    private Consultant consultant;
    
    public Investor() {
    }

    public Investor(String login, String nom, String prenom, String email, String password, String mobile, Account account) {
        this.login = login;
        this.lastName = nom;
        this.name = prenom;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    
    
    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getLogin() != null ? getLogin().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Investor)) {
            return false;
        }
        Investor other = (Investor) object;
        if ((this.getLogin() == null && other.getLogin() != null) || (this.getLogin() != null && !this.login.equals(other.login))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.domain.entities.Investor[ Login=" + getLogin() + " ]";
    }

    /**
     * @return the consultant
     */
    public Consultant getConsultant() {
        return consultant;
    }

    /**
     * @param consultant the consultant to set
     */
    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }
}
