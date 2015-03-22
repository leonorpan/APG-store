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
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.spi.Bean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import store.control.ShopController;
import store.control.UserController;
import store.model.Gnome;
import store.model.ShopUser;

/**
 * This is the bean used for the shopping cart interface.
 *
 * @author Nora
 */
@ManagedBean(name = "cartManager")
@ApplicationScoped
public class CartManager implements Serializable {

    private static final long serialVersionUID = 16247164405L;

    @EJB
    private UserController userFacade;

    @EJB
    private ShopController shopFacade;

    @Inject
    LoginManager manager;

    @ManagedProperty(value = "#{shopUser}")
    private ShopUser user;

    private Gnome selectedItem;
    private Double total;
    private int q;

    /**
     * Get shopping quantity that user selected.
     *
     * @return q
     */
    public int getQ() {
        System.out.println("q is: " + q);
        return q;
    }

    /**
     * Set the quantity to be bought.
     *
     * @param q The quantity to be bought.
     */
    public void setQ(int q) {
        System.out.println("set q is: " + q);
        this.q = q;
    }

    /**
     * Get the item the customer has selected.
     *
     * @return selectedItem
     */
    public Gnome getSelectedItem() {
        return selectedItem;
    }

    /**
     * Get the total cost of items on cart
     *
     * @return total
     */
    public Double getTotal() {
        return total;
    }

    /**
     * Set the total cost of items/gnomes on cart
     *
     * @param total The total cost of items on cart
     */
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * Set the gnome the customer has currently selected.
     *
     * @param selectedItem The selectedItem
     */
    public void setSelectedItem(Gnome selectedItem) {
        this.selectedItem = selectedItem;
    }

//    public List<Gnome> getCartGnomes() {
//  
//        return cartGnomes;
//    }
//    
    /**
     * @deprecated Get the current user
     * @return user
     */
    public ShopUser getUser() {
        this.user = userFacade.getShopUserByUsername(manager.getUsername());
        return user;
    }

    /**
     * Set the current user
     *
     * @param user The user of the store
     */
    public void setUser(ShopUser user) {
        this.user = user;
    }

//    public void setCartGnomes(List<Gnome> cartGnomes) {
//        this.cartGnomes = cartGnomes;
//    }
    /**
     * Calculate the total cost of the items that are currently on the
     * customer's shopping cart/
     *
     * @return message
     */
    public String calculateCost() {
        Double cost = 0.0;
        for (Gnome g : user.getCart()) {
            if (g.getShopQuantity() != null) {
                System.out.println("shop quality is: " + g.getShopQuantity());
                cost += (g.getPrice() * g.getShopQuantity());
            }
        }
        System.out.println("cost is: " + cost);
        this.total = cost;
        return "";

    }

    /**
     * Remove item from a customer's shopping cart.
     *
     * @return msg
     */
    public String removeItem() {
        userFacade.printUsersCart(user);
        System.out.println("Item to be removed...." + selectedItem.getName());
        //user.getCart().remove(selectedItem);
        userFacade.removeFromCart(user, selectedItem);
        System.out.println("Item to be removed...." + selectedItem.getName());
        userFacade.printUsersCart(user);
        return "";
    }

    /**
     * Called when a customer buys an item.
     */
    public void buyItem() {
        List<Gnome> cart = user.getCart();
        String message = "";
        List<Gnome> bought = shopFacade.buyGnomes(cart);
        if (!bought.isEmpty()) {
            message = bought.size() + " items bought";
        } else {
            message = "Could not buy any items";
        }
        userFacade.assignBought(user, bought);
        user.getCart().removeAll(bought);
        cart.removeAll(bought);
        userFacade.setCartToUser(user.getUsername(), cart);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Gnomes", message);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    @PostConstruct
    public void init() {
        //this.user=userFacade.getShopUserByUsername(manager.getUsername());

//              String  usr = manager.getCurrent().getUsername();
//        this.user = userFacade.getShopUserByUsername(usr);
//         = user.getCart();     
        this.total = 0.0;
        //System.out.println("size of cart gnomes..... "+user.getCart());
    }

    /**
     * Called when the customer edits a cell on the datatable.
     *
     * @param event The event that edits the cell.
     */
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        Object o = event.getSource();
        System.out.println("event called: " + newValue + " " + oldValue);
        System.out.println(o.toString());
        DataTable table = (DataTable) event.getComponent();
        Long id = (Long) table.getRowKey();
        System.out.println("#### row key is : " + id + " ####");
        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            for (Gnome gnome : user.getCart()) {
                if ((gnome.getId() != null) && (gnome.getId().compareTo(id) == 0)) {
                    System.out.println("cart size on cell edit " + user.getCart().size());
                    gnome.setShopQuantity((Integer) newValue);
                    userFacade.updateCart(user, gnome);
                }
            }
        }
    }
}
