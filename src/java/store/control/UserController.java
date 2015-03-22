/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.control;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import store.model.Gnome;
import store.model.ShopUser;

/**
 *This is a stateless EJB, that is used as a controller between the beans and the ShopUser entity.
 * @author Nora
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class UserController {

    @PersistenceContext(unitName = "web-storePU")
    private EntityManager em;
    
    /**
     *Create a new user after checking the required fields and if user does not already exists.
     * @param name The username of the prospective customer.
     * @param pass The password of the prospective customer
     * @param repPass The repeated password of the prospective customer.
     * @param email The email of the prospective customer
     * @return message A message to be displayed regarding success of the registration
     */
    public String createUser(String name, String pass, String repPass, String email) {
        if (name.isEmpty() || pass.isEmpty() || repPass.isEmpty() || email.isEmpty()) {
            return "All fields are mandatory";
        } else if ((pass.length() < 8) || (name.length() < 4)) {
            return "Name/password should be more than a and 8 chars respectively";
        } else if (!pass.equals(repPass)) {
            return "Passwords do not match";
        } else if (!email.contains("@")) {
            return "Not valid email address";
        } else if (getUserByName(name).size() > 0) {
            return "User already exists";
        }else if (checkEmailAdress(email).size()>0){
            return "Email address already exists";
        }   
        else {
            ShopUser newUser = new ShopUser(name, pass, email);
            em.persist(newUser);
            return "Registered";
        }

    }

    /**
     *Set a shopping card to a ShopUser
     * @param username The username of the user that the cart will be set
     * @param list The list of Gnomes to be added to the cart
     * @return boolean True if the cart is set, false if not.
     */
    public boolean setCartToUser(String username, List<Gnome> list) {
        ShopUser usr = getShopUserByUsername(username);
        usr.setCart(list);
        em.persist(usr);
        return true;
    }
    
    /**
     *Get akk users that have registered to the APG Gnome web store
     * @return users The list of the store's users.
     */
    public List<ShopUser> getAllUsers(){
         List<ShopUser> users = new ArrayList<>();
        List<Object[]> usersArray = em.createNativeQuery("SELECT * FROM ShopUser g").getResultList();
        System.out.println("Users stored is: " + usersArray.size());
        if (!usersArray.isEmpty()){
        for (Object[] g:usersArray){
            ShopUser user;
           System.out.println("username: "+g[5].toString()+" password: "+g[4].toString()+" email: "+g[3].toString()+" role: "+g[4].toString());
            user = new ShopUser((long) g[0],(String) g[5],(String) g[3],(String) g[2],(String) g[4]);
            users.add(user);
        }
        System.out.println("Users returned is: "+users.size());
        }
        return users;
    }

    /**
     *Check requirements for login to the store
     * @param name The customer's username
     * @param pass The customer's password
     * @return message A message to be displayed regarding login success.
     */
    public String login(String name, String pass) {
        List<ShopUser> users = (List<ShopUser>) getUserByName(name);
        if (users.size() != 1) {
            return "Invalid username";
        } else {
            ShopUser u = users.get(0);
            if (u.getShopRole().equals("banned")){
                return "You are banned!";
            }
            else if ((u.getPassword().equals(pass))&&(u.getUsername().equals(name))) {
                return "Logged in";
            }
            
        }
        return "Invalid user/password combination";
    }

    /**
     *Get ShopUser given the username
     * @param username The username of a customer
     * @return user The user found.
     */
    public ShopUser getShopUserByUsername(String username) {
        List<ShopUser> userList = getUserByName(username);
        if (userList.size() == 1) {
            return userList.get(0);
        }
        return null;
    }

    private List<ShopUser> getUserByName(String name) {
        List<ShopUser> userList = em.createNamedQuery("findUserWithName").setParameter("userName", name).getResultList();
        return userList;
    }
    
    private List<ShopUser> checkEmailAdress(String email){
        List<ShopUser> userList= em.createNamedQuery("findUserByEmail").setParameter("userMail", email).getResultList();
        return userList;
    }

    /**
     *Remove gnomes from a user's cart
     * @param u The user from which a gnome will be removed from hers/his cart. 
     * @param g The gnome that will be removed
     * @return myUsr the updated user
     */
    public ShopUser removeFromCart( ShopUser u,Gnome g){
        ShopUser myUsr = em.find(ShopUser.class, u.getId());
        myUsr.removeItemFromCart(g);
        em.persist(myUsr);
        return myUsr;
    }
    
    /**
     *Update a customer's shopping cart.
     * @param u The user that hers/his cart will be updated
     * @param gnome The gnome that will be updated 
     * @return myUsr The updated instance of ShopUser
     */
    public ShopUser updateCart(ShopUser u,Gnome gnome){
        ShopUser myUsr = em.find(ShopUser.class, u.getId());
        System.out.println("calling the entity update method.....");
        myUsr.updateItemFromCart(gnome);
        em.persist(myUsr);
        return myUsr;
        //em.persist(myUsr);
    }
    
    /**
     *Ban user from the webstore
     * @param u The updated ShopUser
     */
    public void ban(ShopUser u){
        ShopUser myUsr = em.find(ShopUser.class, u.getId());
        myUsr.setShopRole("banned");
        em.persist(myUsr);
    }
    
    /**
     *Remove ban from a user.
     * @param u The updated ShopUser
     */
    public void unban(ShopUser u){
        ShopUser myUsr = em.find(ShopUser.class, u.getId());
        myUsr.setShopRole("customer");
        em.persist(myUsr);
    }
    
    /**
     *Print a user's cart in server log.
     * This was used during development for debugging purposes
     * @param usr The ShopUser that hers/his cart will be printed.
     */
    public void printUsersCart(ShopUser usr){
        ShopUser u = em.find(ShopUser.class, usr.getId());
        List<Gnome> gnomeList = u.getCart();
        for (Gnome g :gnomeList){
            System.out.println(g.getId()+" " +g.getName()+" "+g.getPrice());
        }
    }
    
    /**
     *Assign ids o Gnomes that a customer has bought to a ShopUser.
     * @param u The user that bought the gnomes
     * @param bought The gnomes that were bought
     */
    public void assignBought(ShopUser u, List<Gnome> bought){
        ShopUser myUsr = em.find(ShopUser.class, u.getId());
        List<Long> buyed=myUsr.getBoughtIds();
        System.out.println("Bought items size is : "+bought.size());
        if (buyed==null){
            buyed = new ArrayList<>();
        }
        for (Gnome gn:bought ){
            if (!buyed.contains(gn.getId())){
            buyed.add(gn.getId());
            }
        }
        myUsr.setBoughtIds(buyed);
        em.persist(myUsr);
    }
    
    /**
     *Get gnome ids of gnomes that a user has bought
     * @param u The current ShopUser 
     * @return ids The list of gnome ids a user has bought.
     */
    public List<Long> getBoughtItemsIds(ShopUser u){
        ShopUser myUsr = em.find(ShopUser.class, u.getId());
        return myUsr.getBoughtIds();
        
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
