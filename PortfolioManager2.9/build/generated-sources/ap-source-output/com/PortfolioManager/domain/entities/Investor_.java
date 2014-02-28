package com.PortfolioManager.domain.entities;

import com.PortfolioManager.domain.entities.Account;
import com.PortfolioManager.domain.entities.Consultant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-21T22:52:28")
@StaticMetamodel(Investor.class)
public class Investor_ { 

    public static volatile SingularAttribute<Investor, String> lastName;
    public static volatile SingularAttribute<Investor, String> email;
    public static volatile SingularAttribute<Investor, String> name;
    public static volatile SingularAttribute<Investor, Consultant> consultant;
    public static volatile SingularAttribute<Investor, Account> account;
    public static volatile SingularAttribute<Investor, String> login;
    public static volatile SingularAttribute<Investor, String> password;
    public static volatile SingularAttribute<Investor, String> mobile;

}