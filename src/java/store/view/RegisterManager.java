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
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import store.control.UserController;

/**
 *
 * @author Nora
 */
@Named("registerManager")
@SessionScoped
public class RegisterManager implements Serializable{
    @EJB
    private UserController controller;
    private String username;
    private String pass;
    private String repeatpass;
    private String email;
    private boolean registered;

    private String message;

    /**
     *Get the message that will be displayed when visitor is trying to register
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     *Set the message that will be displayed when the visitor is trying to register
     * @param message The message to be displayed
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *Return true or false whether the visitor is registered or not
     * @return registered true or false
     */
    public boolean isRegistered() {
        return registered;
    }

    /**
     *Set if the visitor is registered or not
     * @param registered true or false
     */
    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
    
    /**
     *Get the username of the visitor
     * @return username The username of the visitor. It has to be unique
     */
    public String getUsername() {
        return username;
    }

    /**
     *Set the username of the visitor
     * @param username The username that will be set to the visitor
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *Get the password of the visitor
     * @return pass The password of the visitor
     */
    public String getPass() {
        return pass;
    }

    /**
     *Set the password of the visitor
     * @param pass The password of the visitor
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     *Get the repeated password of the visitor
     * @return repeatpass The repeated password.
     */
    public String getRepeatpass() {
        return repeatpass;
    }

    /**
     *Set the repeated password of a visitor
     * @param repeatpass The repeated password. It has to be identical with the password
     */
    public void setRepeatpass(String repeatpass) {
        this.repeatpass = repeatpass;
    }

    /**
     *Get the email of the visitor
     * @return email The email of the visitor. It has to be unique
     */
    public String getEmail() {
        return email;
    }

    /**
     *Set the email of the visitor
     * @param email The email of the visitor
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     *Create a new user based on the credentials entered by the visitor.
     * @return message This message contains information about the result of the registraton attempt
     */
    public String createNewAccount(){
        System.out.println("username is: "+username+" pass1 is: "+pass+" pass2 is: "+repeatpass+" email is: "+
                email);
        message=controller.createUser(username, pass,repeatpass, email);
        if (message.equals("Registered")){
            System.out.println("user is created!!!!");
            this.registered=true;
        }else {
            System.out.println("user is not created!! :(");
            this.registered=false;
        }
        FacesContext context = FacesContext.getCurrentInstance();
         
        context.addMessage(null, new FacesMessage(message) );
        return message;
    }
    
    /**
     *Show a faces message that informs the visitor regarding registration success/unsuccess
     * @param event The event that submits the register form
     */
    public void showMessage(ActionEvent event){
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, message,  null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
         
    
}
