package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.dto.Charge;
import com.navinSecurity.thirdSecJWT.model.*;
import com.navinSecurity.thirdSecJWT.repo.*;
import com.navinSecurity.thirdSecJWT.response.NoFileFound;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService  implements ICartService{

    @Autowired
    private final CartRepo cartRepo;
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final CartItemRepo cartItemRepo;
    @Autowired
    private final ProductRepo productRepo;
    @Autowired
    private final TaxModelRepo taxModelRepo;
    @Autowired
    private final ServiceRepo serviceRepo;
    @Value("${stripeToken}")
     String stripeToken;

    @Override
    public List<Cart> getUserCarts(Long user_id) {
        Optional<User> option=userRepo.findById(user_id);
        if(option.isPresent()){
            return option.get().getCarts().stream().toList();
        }else{
            throw new RuntimeException("no user found");
        }



    };
    @Override
    public Cart getCart(Long user_id,Long cartId) {
        Optional<User> optionUser=userRepo.findById(user_id);
        if(optionUser.isPresent()){
            User user=optionUser.get();
            Optional<Cart>hasCart=user.getCarts().stream()
                    .filter(cart->cart.getUser().equals(user))
                    .filter(cart->cart.getId().equals(cartId)).findFirst();
            if(hasCart.isPresent() ){
                return hasCart.get();
            }else{
                throw new RuntimeException("no cart found");
            }
        }else{
            throw new UsernameNotFoundException("no user found");
        }

    };

    @Override
    public Cart addProduct(Long cartId,Long prodId) {

        Optional<Cart> getCart=cartRepo.findById(cartId);
        Optional<Product> getProd=productRepo.findById(prodId);
        boolean check= getProd.isPresent() && getCart.isPresent();
        //PASSES
        if(check){
            Cart cart=getCart.get();
            return addProdItem(cart,getProd.get());

        }else{
            NoFileFound file=new NoFileFound(HttpMethod.GET,"./api/*");
            throw new RuntimeException(file);
        }

    };
    public Cart addProdItem(Cart cart,Product product){
        Set<CartItem> cartItems=cart.getCartItems();
        Optional<CartItem> foundItem=cartItems.stream()
                .filter(cartItem->(cartItem !=null && cartItem.getProduct() !=null &&
                        cartItem.getProduct().getProdId().equals(product.getProdId()))).findFirst();
        if(foundItem.isEmpty()){
            CartItem cartItem=new CartItem(product,null);
            cartItem.setTotalPrice();
            cartItem.setCart(cart);
            cartItem=cartItemRepo.save(cartItem);
            cartItems.add(cartItem);

        }else{
            foundItem.get().setProdQuantity(foundItem.get().getProdQuantity() + 1);
            foundItem.get().setTotalPrice();
            cartItemRepo.save(foundItem.get());
        }
        cart.setCartItems(cartItems);
        cart.updateTotalAmount();
        System.out.println("PRODUCT:cart: id:" + cart.getId() + " : total anmount: "+ cart.getTotalAmount() +": items#: " + cart.getCartItems().stream().toList());
        return cartRepo.save(cart);

    }

    @Override
    public Cart addService(Long cartId,Long servId) {

        Optional<Cart> getCart=cartRepo.findById(cartId);
        Optional<ServiceMod> getServ=serviceRepo.findById(servId);
        boolean check= getServ.isPresent() && getCart.isPresent();
        if(check){
            Cart cart=getCart.get();
            return addServItem(cart,getServ.get());

        }else{
            throw new RuntimeException("no user/service/cart");
        }
    };

    public Cart addServItem(Cart cart,ServiceMod serv){
        Set<CartItem> cartItems=cart.getCartItems();
        Optional<CartItem> foundItem=cartItems.stream()
                .filter(cartItem->(cartItem !=null && cartItem.getServiceMod() !=null &&
                        cartItem.getServiceMod().getId().equals(serv.getId()))).findFirst();
        if(foundItem.isEmpty()){
            CartItem newCart=new CartItem(null,serv);
            newCart.setCart(cart);
            newCart.setTotalPrice();
            newCart=cartItemRepo.save(newCart);
            System.out.println("ADD SERVICE PRICE: "+serv.getPrice() );
            cartItems.add(newCart);
            cart.setCartItems(cartItems);

        }else{
            System.out.println("each:Service " + foundItem.get().getServiceMod() + ": " +foundItem.get().getServicePrice() + ": " +foundItem.get().getId());
            foundItem.get().setProdQuantity(foundItem.get().getProdQuantity() + 1);
            foundItem.get().setTotalPrice();
            cartItemRepo.save(foundItem.get());
        }
        cart.setCartItems(cartItems);
        cart.updateTotalAmount();
        System.out.println("SERVICS:cart: id:" + cart.getId() + " : total anmount: "+ cart.getTotalAmount() +": items#: " + cart.getCartItems().stream().toList());
        return cartRepo.save(cart);

    }

    @Override
    public Cart deleteItem(Long cartId,Long cartItemId) {

        Optional<Cart> getCart=cartRepo.findById(cartId);
        if( getCart.isPresent()){
            Cart cart=getCart.get();
           Optional<CartItem> cartItem=cart.getCartItems().stream()
                    .filter(_cartItem->(_cartItem.getId().equals(cartItemId))).findFirst();
            cartItem.ifPresent(item -> cartItemRepo.delete(item));
            cart.updateTotalAmount();
            return cartRepo.save(cart);

        }else{
            throw new RuntimeException("no user");
        }
    };

    @Override
    public Cart deleteProduct(Long cartId, Long prodId) {
        Optional<Cart> getCart=cartRepo.findById(cartId);
        Optional<Product> isProd=productRepo.findById(prodId);
        if(getCart.isPresent() && isProd.isPresent()){
            Cart cart=getCart.get();
            cart =removeProdItem(cart,isProd.get());
            cart.updateTotalAmount();
            return cartRepo.save(cart);
        }else{
            throw new RuntimeException("no cart");
        }
    };

    public Cart removeProdItem(Cart cart,Product prod){
        Optional<CartItem> cartFound=cart.getCartItems().stream()
                .filter(cartItem->(cartItem.getProduct().getProdId().equals(prod.getProdId()))).findFirst();
        cartFound.ifPresent(cartItem -> cart.getCartItems().remove(cartItem));
        cart.updateTotalAmount();
        return cartRepo.save(cart);
    }

    @Override
    public Cart deleteService(Long cartId, Long servId) {
        Optional<Cart> getCart=cartRepo.findById(cartId);
        Optional<ServiceMod> hasServ=serviceRepo.findById(servId);
        if(getCart.isPresent() && hasServ.isPresent()){
            Cart cart=getCart.get();
            cart=removeServItem(cart,hasServ.get());
            cart.updateTotalAmount();
            return cartRepo.save(cart);

        }else{
            throw new RuntimeException("no user");
        }
    }

    @Override
    public Cart addProductQuantity(Long cartId, Long prodId) {
        Optional<Cart> getCart=cartRepo.findById(cartId);
        Optional<Product> getProd=productRepo.findById(prodId);
        if(getCart.isPresent() && getProd.isPresent()){
            Cart cart=getCart.get();


                Optional<CartItem> isCartItem=cart.getCartItems().stream()
                        .filter(cartItem->(cartItem.getProduct() !=null &&
                                cartItem.getProduct().getProdId() !=null &&
                                cartItem.getProduct().getProdId().equals(prodId))).findFirst();
                if(isCartItem.isPresent()){
                    isCartItem.get().setProdQuantity(isCartItem.get().getProdQuantity() + 1);
                    isCartItem.get().setTotalPrice();
                    cartItemRepo.save(isCartItem.get());
                    cart.updateTotalAmount();
                }
                    return cartRepo.save(cart);

        }else{
            throw new UsernameNotFoundException("no user/product Found");
        }
    }

    @Override
    public Cart addServiceQuantity(Long cartId, Long servId) {
     Optional<Cart> getCart=cartRepo.findById(cartId);
        if(getCart.isPresent()){
            Cart cart=getCart.get();
            Optional<CartItem> isCartItem=cart.getCartItems().stream()
                    .filter(cartItem->(cartItem.getServiceMod() !=null &&
                                    cartItem.getServiceMod().getId() !=null &&
                            cartItem.getServiceMod().getId().equals(servId))).findFirst();
            if(isCartItem.isPresent()){
                isCartItem.get().setServQuantity(isCartItem.get().getServQuantity() + 1);
                isCartItem.get().setTotalPrice();
                cartItemRepo.save(isCartItem.get());
                cart.updateTotalAmount();
                cart=cartRepo.save(cart);
            }
            return cart;


        }else{
            throw new UsernameNotFoundException("no user Found");
        }
    }

    @Override
    public Cart subServiceQuantity(Long cartId, Long servId) {
        Optional<Cart> getCart=cartRepo.findById(cartId);
        if( getCart.isPresent()){
            Cart cart=getCart.get();
            Optional<CartItem> isCartItem=cart.getCartItems().stream()
                    .filter(cartItem->(cartItem.getServiceMod() !=null &&
                            cartItem.getServiceMod().getId() !=null &&
                            cartItem.getServiceMod().getId().equals(servId))).findFirst();
            if(isCartItem.isPresent()){
                int servQty=isCartItem.get().getServQuantity();
                if(servQty >1){
                    isCartItem.get().setServQuantity(servQty - 1);
                    isCartItem.get().setTotalPrice();
                    cartItemRepo.save(isCartItem.get());
                    cart.updateTotalAmount();
                    cart=cartRepo.save(cart);
                }
            }
            return cart;

        }else{
            throw new UsernameNotFoundException("no user Found");
        }
    }

    @Override
    public Cart subProductQuantity(Long cartId, Long prodId) {
        Optional<Cart> getCart=cartRepo.findById(cartId);
        if(getCart.isPresent()){
            Cart cart=getCart.get();
            Optional<CartItem> isCartItem=cart.getCartItems().stream()
                    .filter(cartItem->(cartItem.getProduct() !=null &&
                            cartItem.getProduct().getProdId() !=null &&
                            cartItem.getProduct().getProdId().equals(prodId))).findFirst();
            if(isCartItem.isPresent()){
                int prodQty=isCartItem.get().getProdQuantity();
                if(prodQty >1){
                    isCartItem.get().setProdQuantity(prodQty - 1);
                    isCartItem.get().setTotalPrice();
                    cartItemRepo.save(isCartItem.get());
                    cart.updateTotalAmount();
                    cart=cartRepo.save(cart);
                }
            }
            return cart;

        }else{
            throw new UsernameNotFoundException("no user Found");
        }
    }

    public Cart removeServItem(Cart cart,ServiceMod serv){
        Optional<CartItem> cartFound=cart.getCartItems().stream()
                .filter(cartItem->(cartItem.getServiceMod().getId().equals(serv.getId()))).findFirst();
        cartFound.ifPresent(cartItem -> cart.getCartItems().remove(cartItem));
        return cart;
    }

    @Override
    public Cart getSelectCart(Long userId, Long cartId) {
        Optional<User> getUser=userRepo.findById(userId);
        if(getUser.isPresent()){
            User user=getUser.get();
            Optional<Cart> getCart=cartRepo.findById(cartId).filter(cart->cart.getUser().equals(user));
            if(getCart.isPresent()){
                return getCart.get();
            }else{
                NoFileFound file=new NoFileFound(HttpMethod.GET,"./api/*");
                throw new RuntimeException(file);
            }
        }else{
            throw new UsernameNotFoundException("user could not be found");
        }
    }

    @Override
    public Charge purchase(Long user_id,Long cartId) {
        //THIS GOES TO STRIPE!!!
        Optional<Cart> getCart=cartRepo.findById(cartId);
        Optional<User> getUser=userRepo.findById(user_id);
        if(getCart.isPresent() && getUser.isPresent()){
            User user=getUser.get();
            Cart cart=getCart.get();
            Random rand=new Random();
            int rendNum=rand.nextInt()*1000;
            cart=paymentConfirmation(cart,"1" + rendNum + "1");//REPLACE THIS WHEN HAVE StripeRequest
            cartRepo.save(cart);
            bindNewCartToUser( user); // new Cart

        }
        //AFTER HOOK=> DISENGAGE CART & ASSIGN NEW

        return prePurchase(user_id,cartId);
    }

    @Override
    public Charge prePurchase(Long user_id, Long cartId) {
        Optional<User> getUser=userRepo.findById(user_id);
        Optional<Cart> getCart=cartRepo.findById(cartId);

        if(getUser.isPresent() && getCart.isPresent()){
            Cart cart=getCart.get();
            String summary=constructDescription(getUser.get(),cart);
            cart.setSummary(summary);
            this.calcTotalWithTaxAndSave(cart,getUser.get());
            cartRepo.save(cart);
            return StripBuilder(getUser.get(),cart);
        }else{
            throw new UsernameNotFoundException(" user not found");
        }
    }

    public void bindNewCartToUser(User user){
        Cart cart=new Cart();
        cart.setUser(user);
        cartRepo.save(cart);
        user.getCarts().add(cart);
        userRepo.save(user);
    }

    public Charge StripBuilder(User user,Cart cart){
        Map<String,Object> chargeParams= new HashMap<>();
        chargeParams.put("amount",cart.getTotalAmountWithTax());
        if(getCurrency(user) !=null){
            chargeParams.put("currency",getCurrency(user));
        }else{
            chargeParams.put("currency","USD");
        }
        chargeParams.put("stripeToken",stripeToken);
        chargeParams.put("description",constructDescription(user,cart));
        return Charge.create(chargeParams);
    }

    public void calcTotalWithTaxAndSave(Cart cart,User user){
        Address address=user.getAddress();
        TaxModel taxModel=this.findTaxModel(address);
        Double totalWithTax=taxModel.calcTotalAmount(cart.getTotalAmount());
        cart.setTotalAmountWithTax(totalWithTax);
        cartRepo.save(cart);

    }

    public TaxModel findTaxModel(Address address){
        String prov_state=address.getProv_state();
        String country=address.getCountry();
        Optional<TaxModel> getTaxModel=taxModelRepo.findByCountry(country).stream()
                .filter(tax->tax.getProv_state().equals(prov_state)).findFirst();
        if(getTaxModel.isPresent()){
            return getTaxModel.get();
        }else{
            throw new RuntimeException("no tax found");
        }
    }

    public Cart paymentConfirmation(Cart cart,String confirmation){
        cart.setConfirmation(confirmation);
        Date now=new Date();
        Instant instant=now.toInstant();
        ZoneId zoneId= ZoneId.systemDefault();
        LocalDate date=LocalDate.ofInstant(instant,zoneId);
        cart.setPurchased(now);
        return cart;
    }





    public Object getCurrency(User user){
        Map<String,Object> converter=new HashMap<>();
        converter.put("CAN","CAD");
        converter.put("US","USD");
        converter.put("USA","USD");
        converter.put("ENG","EUR");
        converter.put("MEX","PES");
        try {
            String country=user.getAddress().getCountry();
            for(Map.Entry<String,Object> entry:converter.entrySet()){
                String name=entry.getKey();
                Object value=entry.getValue();
                if(name.equals(country)){
                    return value;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    };

    public String constructDescription(User user,Cart cart){
        Date now=new Date();
        cart.setPurchased(now);
        cartRepo.save(cart);
        Instant instant=now.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate date= LocalDate.ofInstant(instant,zoneId);
        return "purchase by " + user.getEmail() + " for the amount of $ " + cart.getTotalAmount() + " on " + date + "lists of: " + generateItemList(cart);
    }

    public String generateItemList(Cart cart){
        List<String> itemNames=new ArrayList<>();
        Optional<Set<CartItem>> products = Optional.of(cart.getCartItems().stream()
                .filter(item->item.getProduct() !=null).collect(Collectors.toSet()));
        Optional<Set<CartItem>> services = Optional.of(cart.getCartItems().stream()
                .filter(item->item.getServiceMod() !=null).collect(Collectors.toSet()));
        products.ifPresent(cartItems -> {
                    cartItems.forEach(prod -> {
                        itemNames.add(", " + prod.getProduct().getName());
                    });
                });
        services.ifPresent(cartItems -> cartItems.forEach(serv -> {
            itemNames.add(", " + serv.getServiceMod().getName());
        }));
        return List.of(itemNames.stream().map(item->item)).toString();

    }


}


























