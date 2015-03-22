/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *The gnome entity represents the gnomes-articles that the APG store sells.
 * @author Nora
 */


@Entity
public class Gnome implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    private float price;
    private int quantity;

    //status can be sold or available or hold
    private String status;
    private Integer shopQuantity;

    /**
     * Get quantity to be bought be a customer
     * @return shopQuantity the quantity of gnome to be bought
     */
    public Integer getShopQuantity() {
        return shopQuantity;
    }

    /**
     *Set the quantity of gnome that will be bought by a customer
     * @param shopQuantity the quantity of gnome to be bought
     */
    public void setShopQuantity(Integer shopQuantity) {
        this.shopQuantity = shopQuantity;
    }

    /**
     *
     */
    public Gnome() {
    }

    /**
     *Get the name of a gnome
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param id The unique id of a gnome
     * @param name The name of a gnome
     * @param price The price of a gnome
     */
    public Gnome(Long id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.shopQuantity=1;
    }

    /**
     *
     * @param name The name of the gnome
     * @param price The price of a gnome
     * @param quantity The amount of gnomes available
     */
    public Gnome(String name, float price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.status="available";
        this.shopQuantity=1;
    }

    /**
     *
     * @param id The unique id of a gnome
     * @param name The name of a gnome
     * @param price The price of a gnome
     * @param quantity The amount of gnomes available
     */
    public Gnome(Long id, String name, float price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.status="available";
        this.shopQuantity=1;
    }

    /**
     *Get the status of a gnome. 
     * @return status Can be available or na
     */
    public String getStatus() {
        return status;
    }

    /**
     *Set the status of a gnome. 
     * @param status Default value is available. Can also be na which means not available
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     *Set the name of a gnome
     * @param name The name of the gnome
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *Get the price of a gnome.
     * @return price The price of the gnome
     */
    public float getPrice() {
        return price;
    }

    /**
     *Set the price of a gnome
     * @param price the price of the gnome
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     *Get the quantity of gnome. Quantity is the number of available copies.
     * @return quantity The amoung in stock
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     *Set the quantity of a gnome that is in stock
     * @param quantity the quantity of stock for the gnome 
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     *Get the unique id of a gnome
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the id of a gnome The id should be auto generated and this method should be avoided
     *@deprecated 
     * @param id the id of the gnome
     * 
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     *Minus the amount of gnomes after a gnome is bought.
     */
    public void modifyQuantity(){
        this.quantity-=shopQuantity;
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
        if (!(object instanceof Gnome)) {
            return false;
        }
        Gnome other = (Gnome) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "store.model.Gnome[ id=" + id + " ]";
    }
    
}
