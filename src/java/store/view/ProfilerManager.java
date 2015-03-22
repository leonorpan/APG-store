/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.view;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import store.control.ShopController;
import store.control.UserController;
import store.model.Gnome;

/**
 *This bean is used to manage the profile interface.
 * This is an interface where a user can see hers/his personal details and buying history.
 * @author Nora
 */

@ManagedBean(name = "profilerManager")
@ApplicationScoped
public class ProfilerManager {
    
        @EJB
    private UserController userFacade;
        
        
            @EJB 
    private ShopController shopFacade;
        
        private List<Gnome> boughtItems;
        
            @Inject 
    LoginManager manager;
        
        @PostConstruct
        public void init(){
//            this.boughtItems=userFacade.getBoughtItemsIds(manager.getCurrent());
           
        }

    /**
     *Get gnomes that a customer has bought
     * @return boughtItems
     */
    public List<Gnome> getBoughtItems() {
        this.boughtItems= shopFacade.getBoughtGnomes(userFacade.getBoughtItemsIds(manager.getCurrent()));
        return boughtItems;
    }

    /**
     *Set the items that a customer has bought
     * @param boughtItems The gnomes a customer has bought
     */
    public void setBoughtItems(List<Gnome> boughtItems) {
        this.boughtItems = boughtItems;
    }
        
        
        
    
}
