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
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import store.control.ShopController;
import store.control.UserController;
import store.model.Gnome;
import store.model.ShopUser;

/**
 *This is the bean for managing the store interface.
 * @author Nora
 */
@ManagedBean(name="gnomeManager")
@ViewScoped
public class GnomeManager implements Serializable {

    private static final long serialVersionUID = 16247164405L;

    private List<Gnome> gnomes;
    private List<Gnome> selectedGnomes;
    private ShopUser user;
    
    @Inject 
    LoginManager manager;

    @EJB
    private UserController userFacade;
    
    @EJB
    private ShopController shopFacade;
//    @Inject
//    private Conversation conversation;
//
//    private void startConversation() {
//        if (conversation.isTransient()) {
//            conversation.begin();
//        }
//    }
//
//    private void stopConversation() {
//        if (!conversation.isTransient()) {
//            conversation.end();
//        }
//    }

    
    @PostConstruct
    public void init(){
        this.user=manager.getCurrent();
                this.gnomes = shopFacade.getAllGnomes();

    }
    
    /**
     *Get gnomes that were selected by the customer.
     * @return selectedGnomes
     */
    public List<Gnome> getSelectedGnomes() {
        return selectedGnomes;
        
    }

    /**
     *Set the selected gnomes for a customer.
     * @param selectedGnomes The gnomes that are selected.
     */
    public void setSelectedGnomes(List<Gnome> selectedGnomes) {
        this.selectedGnomes =(List<Gnome>) selectedGnomes;
    }

    /**
     * This method was used for debugging purposees
     * @deprecated 
     * @return nothing
     */
    public String displayAvailableGnomes() {
        System.out.println("trying to display items");
        System.out.println("gnome size: " + gnomes.size());
        return "";
    }

    /**
     *Get all gnomes that are on the store.
     * @return gnomes
     */
    public List<Gnome> getGnomes() {
        return gnomes;
    }

    /**
     *Set the gnomes that will be displayed in the web store.
     * @param gnomes Gnomes to be displayed
     */
    public void setGnomes(List<Gnome> gnomes) {
        this.gnomes = gnomes;
    }

    /**
     *Get current user.
     * @return user
     */
    public ShopUser getUser() {
        return user;
    }

    /**
     *Set current user
     * @param user The current user
     */
    public void setUser(ShopUser user) {
        this.user = user;
    }

    /**
     *Add the selectged items to the cart
     * @return message This message represent the success of the adding he items to the cart.
     */
    public String addToCart() {
        this.user=manager.getCurrent();
        System.out.println("Adding to cart....");
        selectedGnomes.size();
       // user.setCart(selectedGnomes);
       userFacade.setCartToUser(user.getUsername(),selectedGnomes);
        System.out.println("size selected:" +selectedGnomes.size());
        return "";
    }
    


}
