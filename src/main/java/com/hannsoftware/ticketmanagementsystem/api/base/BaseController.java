package com.hannsoftware.ticketmanagementsystem.api.base;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import com.hannsoftware.ticketmanagementsystem.dto.UserDTO;
import com.hannsoftware.ticketmanagementsystem.services.UserService;
import com.hannsoftware.ticketmanagementsystem.utils.Utilities;

@Controller
public class BaseController {
	
	@Autowired
    private UserService userService;
	
    protected  <T, S> List<S> listAsDtos(List<T> entities, Class<S> baseEntityDTOClass) {
        if (!Utilities.listHasElements(entities)) return new ArrayList<>();
        try {
            return Utilities.parseList(entities, baseEntityDTOClass);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    
    protected Long getUserIdFromAuthentication(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalStateException("Usuário não autenticado.");
        }

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserDTO userDTO = userService.getUserByUsername(userDetails.getUsername());
            return userDTO.getId();
        } else if (authentication.getPrincipal() instanceof String) {
            return Long.parseLong(authentication.getPrincipal().toString());
        }

        throw new IllegalStateException("Não foi possível identificar o usuário autenticado.");
    }

}
