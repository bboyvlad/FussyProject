package jd.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created by bboyvlad on 9/5/16.
 */

@Controller
@RequestMapping(value="/")
public class indexController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/{[path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }

    @RequestMapping(value = "/{[path:[^\\.]*}/{[id:[^\\.]*}")
    public String redirect2Params(HttpServletRequest request) {
        // Also forward to home page so that route is preserved.
        return "forward:/";
    }

    @RequestMapping(value = "/{[path:[^\\.]*}/{[id:[^\\.]*}/{[id:[^\\.]*}")
    public String redirect3Params(HttpServletRequest request) {
        // Also forward to home page so that route is preserved.
        return "forward:/";
    }


    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public @ResponseBody Principal user(Principal user) {

        return user;
    }

    /*@RequestMapping(value = "/users/login", method = RequestMethod.GET)
    public String displayLogin(Model model) {

        return "partials/users/login";
    }*/
}
