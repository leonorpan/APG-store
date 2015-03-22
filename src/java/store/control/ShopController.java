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

/**
 * The class ShopController is a stateless EJB.It is used as a controller between the beans and the Gnome entity.
 *
 * @author Nora
 * 
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class ShopController {

    @PersistenceContext(unitName = "web-storePU")
    private EntityManager em;
    
    /**
     *This is the database hardcoding, in order to fill the database with gnomes.
     * This method is called when the database is empty.
     * @return String That is a confirmation that gnomes were generated.
     */
    public String generateGnomes() {
        Gnome g1 = new Gnome("Brizu", 520, 15);
        em.persist(g1);
        Gnome g2 = new Gnome("Warrick", 430, 10);
        em.persist(g2);
        Gnome g3 = new Gnome("Orpip", 620, 22);
        em.persist(g3);
        Gnome g4 = new Gnome("Vorzu", 390, 25);
        em.persist(g4);
        Gnome g5 = new Gnome("Erbis", 415, 18);
        em.persist(g5);
        Gnome g6 = new Gnome("Tanser", 450, 12);
        em.persist(g6);
        return "OK";
    }

    /**
     *Get all the gnomes from the database. As we need to scan for all the data, the returned result of quering the database 
     * is a list of object arrays which have to be converted into a list.
     * @return gnomes This is the list of all the gnomes that are in the database.
     */
    public List<Gnome> getAllGnomes() {
        List<Gnome> gnomes = new ArrayList<>();
        List<Object[]> gnomesArray = em.createNativeQuery("SELECT * FROM Gnome g").getResultList();
        System.out.println("Gnomes stored is: " + gnomesArray.size());
        if (gnomesArray.isEmpty()){
            generateGnomes();
        }else {
        for (Object[] g:gnomesArray){
            Gnome gnome;
            gnome = new Gnome((long) g[0],(String) g[1],new Float((double) g[2]),(int) g[3]);
            gnomes.add(gnome);
        }
        System.out.println("Gnomes returned is: "+gnomes.size());
        }
        return gnomes;
    }
    
    /**
     *Updates a gnome in the database.
     * @param g Is the gnome that has to be found by id and replaced.
     */
    public void updateGnome(Gnome g){
        Gnome gno = em.find(Gnome.class, g.getId());
        gno.setName(g.getName());
        gno.setPrice(g.getPrice());
        gno.setQuantity(g.getQuantity());
        gno.setStatus(g.getStatus());
        em.persist(gno);
    }
    
    /**
     *Adds a new gnome in the database
     * @param newGnome The gnome to be added
     */
    public void addGnome(Gnome newGnome){
        Gnome g = new Gnome(newGnome.getName(),newGnome.getPrice(),newGnome.getQuantity());
        List<Gnome> gnomes = getAllGnomes();
        for (Gnome gn: gnomes) {
            if (gn.getName().equals(g.getName())) {
                return;
            }else if (g.getPrice()==0){
                return;
            }
        }
        em.persist(g);
    }
    
    /**
     *Finds all the items that a user has bought.
     * @param  bids the list of the gnome ids that the user has bought
     * @return gnms The list of gnomes the user has bought.
     */
    public List<Gnome> getBoughtGnomes(List<Long> bids){
        List<Gnome> gnms = new ArrayList<>();
        if (bids!=null){
            if (!bids.isEmpty()) {
                for (Long b:bids){
                    Gnome g = em.find(Gnome.class, b);
                    if (g!=null){
                        gnms.add(g);
                    }
                }   }
        }
        return gnms;
    }
    
    /**
     *Is called when a user buys items. It modifies the quantity of the bought items and checks if the items can be bought 
    * @param gnomes The gnomes that are attempted to be bought
     * @return gnomes The gnomes that were able to be bought.
     */
    public List<Gnome> buyGnomes(List<Gnome> gnomes){
        List<Gnome> bought = new ArrayList<>(); 
        for (Gnome g: gnomes){
            if (g.getQuantity()>=g.getShopQuantity()){
             Gnome gnome =  em.find(Gnome.class, g.getId());
            gnome.modifyQuantity();
                System.out.println("Gnome availability is ok");
                bought.add(gnome);
                //mivvjjxcf
                System.out.println("nothing specialllllllllll");
            em.persist(gnome);
            }else {
                System.out.println("there are no so many pieces available for "+g.getName());
            }
        }
        return bought;
    }
    


}
