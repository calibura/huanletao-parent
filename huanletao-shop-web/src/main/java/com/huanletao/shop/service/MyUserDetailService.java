package com.huanletao.shop.service;

import com.huanletao.pojo.TbSeller;
import com.huanletao.sellergoods.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class MyUserDetailService implements UserDetailsService {

    private SellerService service;

    public void setService(SellerService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_USER"));
        TbSeller seller = service.findOne(username);
        if(seller != null){
            if(seller.getStatus().equals("1")){
                User user = new User(username,seller.getPassword(),list);
                return user;
            }
        }
        return null;
    }
}
