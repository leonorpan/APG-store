/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.view;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import store.control.UserController;
import store.model.ShopUser;

/**
 *This is the bean mostly used for the managing of the login interface.
 * This bean however, is also used by other beans too, in order for example to get information about the user
 * that is currently logged in.
 * @author Nora
 */

@Named("loginManager")
@SessionScoped
public class LoginManager implements Serializable{
    
    private String username;
    private String pass;
    private String message;
    private boolean loggedIn;
    private ShopUser current;
    
    @EJB
    private UserController controller;

    /**
     *Get username of tha visitor
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     *Get current user of the web store
     * @return current
     */
    public ShopUser getCurrent() {
        return current;
    }

    /**
     *Set current user of the store
     * @param current The current user of the store
     */
    public void setCurrent(ShopUser current) {
        this.current = current;
    }

    /**
     *Return if user is logged in or not.
     * @return loggedIn
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     *Set if user is logged in
     * @param loggedIn true or false
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     *Set the username of the visitor
     * @param username The username of the visitor
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *Get password of the visitor
     * @return pass
     */
    public String getPass() {
        return pass;
    }

    /**
     *Set the password of a visitor
     * @param pass The visitor's password
     */
    public void setPass(String pass) {
        this.pass = pass;
    }
    
    /**
     *Check if the user can log in or not.
     * @return message This message describes the result of the login process
     */
    public String checkCredentials() {
       System.out.println("Username is: "+username +" and password is: "+pass);
       this.message = controller.login(username,pass);
       if (message.equals("Logged in")){
           System.out.println("User has succesfully logged in.");
           this.current=controller.getShopUserByUsername(username);
           this.loggedIn=true;
       }else {
           System.out.println("System could not log in.");
           this.loggedIn=false; 
           this.current=null;
       }
       FacesContext context = FacesContext.getCurrentInstance();
         
        context.addMessage(null, new FacesMessage(message) );
       return jsf22Bugfix();
   }
   
   
    private String jsf22Bugfix() {
        return "";
    }
    
    /**
     *Log out from the system
     * @return emptymessage An empty string.
     */
    public String logout(){
        System.out.println("Logging out....");
        this.loggedIn=false;
        this.current=null;
        FacesContext context = FacesContext.getCurrentInstance();
         
        context.addMessage(null, new FacesMessage("Logged out") );
        return jsf22Bugfix();
    }
    
    
    
}
