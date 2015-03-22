/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *This is an entity class that represents a user-customer of APG gnome store.
 * @author Nora
 */
@NamedQueries({
    @NamedQuery(
            name = "findUserWithName",
            query = "SELECT u FROM ShopUser u WHERE u.username = :userName"
    ),
    @NamedQuery(
            name = "findUserByEmail",
            query = "Select u FROM ShopUser u WHERE u.email= :userMail")
})

@Entity
public class ShopUser implements Serializable, ShopUserDTO {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String shopRole;
    private List<Long> boughtIds;

    /**
     *Get the ids of gnomes a customer has bought.
     * @return boughtIds
     */
    public List<Long> getBoughtIds() {
        return boughtIds;
    }

    /**
     *Set the list of ids of gnomes a customer has bought
     * @param boughtIds The gnome ids a user has bought.
     */
    public void setBoughtIds(List<Long> boughtIds) {
        this.boughtIds = boughtIds;
    }
    
    @OneToMany
    private List<Gnome> cart;

    /**
     *Get the shopping of a customer
     * @return cart
     */
    public List<Gnome> getCart() {
        return cart;
    }

    /**
     *Get the role of a user in the store.
     * Role can be admin, customer or banned.
     * @return shopRole
     */
    public String getShopRole() {
        return shopRole;
    }

    /**
     *Set the role of a customer in the store.
     * Role can be admin, customer or banned.
     * @param shopRole The role that will be assigned to customer
     */
    public void setShopRole(String shopRole) {
        this.shopRole = shopRole;
    }

    /**
     *Set cart to a customer
     * @param cart The cart that will be set to customer
     */
    public void setCart(List<Gnome> cart) {

        this.cart = cart;
    }

    /**
     *Empty constructor
     */
    public ShopUser() {
    }

    /**
     *Get the username of a customer
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username The username of a customer
     * @param password The password of a customer
     * @param email The email of a customer
     */
    public ShopUser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.shopRole="customer";
        
    }

    /**
     *
     * @param id The unique id of a customer
     * @param username The username of a customer
     * @param password The password of a customer
     * @param email The email of a customer
     * @param shopRole The role of a customer
     */
    public ShopUser(Long id, String username, String password, String email, String shopRole) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.shopRole = shopRole;
    }
    
    /**
     *Set the username of a customer
     * @param username The username of the customer
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *Get the password of the customer
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     *Set the password to a customer
     * @param password The password of a customer
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *Get the email of a customer
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     *Set the email for a customer
     * @param email The email to be set to a customer
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *Get the unique id of a customer
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the unique id to a customer/
     *@deprecated The id should be auto generated and this method should be avoided.
     * @param id the unique id of a customer
     */
    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof ShopUser)) {
            return false;
        }
        ShopUser other = (ShopUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param id The id of a customer
     * @param username The username of a customer
     * @param password The password of a customer
     * @param email The email of a customer
     * @param cart The cart of a customer
     */
    public ShopUser(Long id, String username, String password, String email, List<Gnome> cart) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "store.model.User[ id=" + id + " ]";
    }

    /**
     *Remove a gnome from a user's cart
     * @param gnome The gnome that will be removed from a user's cart
     */
    public void removeItemFromCart(Gnome gnome) {
        if (cart.contains(gnome)) {
            System.out.println("cart entity contains gnome....");
            cart.remove(gnome);
        }
    }

    /**
     *Update a gnome that exists in the customer's cart
     * @param gnome The gnome that exist in the cart and will be updated.
     */
    public void updateItemFromCart(Gnome gnome) {
        for (Gnome g : cart) {
            if (g.getId().compareTo(gnome.getId())==0) {
                g.setShopQuantity(gnome.getShopQuantity());
                System.out.println("found item and will update quantity...."+gnome.getShopQuantity());
                System.out.println("new quantity.... "+g.getShopQuantity());
                //g=gnome;
            }
        }
    }

}
