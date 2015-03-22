/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.view;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import store.control.ShopController;
import store.control.UserController;
import store.model.Gnome;
import store.model.ShopUser;

/**
 *This is the bean responsible for the administrator interface.
 * @author Nora
 */
@ManagedBean(name = "adminManager")
@ApplicationScoped
public class AdminManager implements Serializable{
    
    private List<ShopUser> users;
    private Gnome newGnome=new Gnome();
    
    @EJB
    UserController userFacade;
    
    @EJB 
    ShopController shopFacade;
    
    private ShopUser selectedUser;

    /**
     *Get the user that the administrator has selected/
     * @return selectedUser
     */
    public ShopUser getSelectedUser() {
        return selectedUser;
    }

    /**
     *Set the administrator's selected user.
     * @param selectedUser The user that is selected.
     */
    public void setSelectedUser(ShopUser selectedUser) {
        this.selectedUser = selectedUser;
    }

    /**
     *Get added/new gnome
     * @return newGnome
     */
    public Gnome getNewGnome() {
        return newGnome;
    }

    /**
     *Set a newly added gnome.
     * @param newGnome The new/recently added gnome.
     */
    public void setNewGnome(Gnome newGnome) {
        this.newGnome = newGnome;
    }
    
    /**
     *Update a gnome after the administrator has finished editing of the gnome/row.
     * @param event This is the event when user selects the tick sign and finishes rowedit
     */
    public void onRowEdit(RowEditEvent event){
        Gnome edited =(Gnome) event.getObject();
        FacesMessage msg = new FacesMessage("Gnome edited", edited.getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        shopFacade.updateGnome(edited);
    }
    
    /**
     *This method just shows a message when the admit has cancelled editing.
     * When clicked cancel,there are no changes.
     * @param event This event is triggered when the x sign is clicked.
     */
    public void onRowCancel(RowEditEvent event){
                    Gnome edited =(Gnome) event.getObject();
        FacesMessage msg = new FacesMessage("Gnome editing cancelled", edited.getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     *Get all users/customers of the APG gnome store.
     * @return users
     */
    public List<ShopUser> getUsers() {
        this.users=userFacade.getAllUsers();
        return users;
    }

    /**
     *Set the users of the APG gnome store
     * @param users The users that will be set for the APG gnome store.
     */
    public void setUsers(List<ShopUser> users) {
        this.users = users;
    }
    
    /**
     *Add gnome to the APG gnome store.
     */
    public void addGnome(){
        RequestContext.getCurrentInstance().openDialog("addGnomeDialog");
    }
    
    /**
     *Adds a new gnome to the web store.
     * This is triggered when admin selects add button.
     */
    public void addNewGnome(){
        if ((this.newGnome!=null)){
           shopFacade.addGnome(newGnome);
            System.out.println("new gnome added!!");
        }
    }
    
    /**
     *Ban a user. 
     * This is triggered when admin selects the ban button
     */
    public void banUser(){
        if (this.selectedUser!=null){
            if (!selectedUser.getShopRole().equals("admin")){
            userFacade.ban(selectedUser);
            }
        }
    }
    
    /**
     *Unban a user.
     * This is triggered when admin selects the unban button.
     */
    public void unbanUser(){
        if (this.selectedUser!=null){
            userFacade.unban(selectedUser);
        }
    }
    
    @PostConstruct
    public void init(){
        this.users=userFacade.getAllUsers();
    }
}
