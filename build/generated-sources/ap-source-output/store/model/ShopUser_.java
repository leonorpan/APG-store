package store.model;

import java.util.List;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import store.model.Gnome;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-07T11:20:27")
@StaticMetamodel(ShopUser.class)
public class ShopUser_ { 

    public static volatile SingularAttribute<ShopUser, String> password;
    public static volatile SingularAttribute<ShopUser, String> shopRole;
    public static volatile SingularAttribute<ShopUser, Long> id;
    public static volatile SingularAttribute<ShopUser, List> boughtIds;
    public static volatile SingularAttribute<ShopUser, String> email;
    public static volatile ListAttribute<ShopUser, Gnome> cart;
    public static volatile SingularAttribute<ShopUser, String> username;

}