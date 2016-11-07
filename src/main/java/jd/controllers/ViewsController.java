package jd.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by bboyvlad on 9/7/16.
 */
@Controller
public class ViewsController {

    @RequestMapping("/log")
    public String loginView(Model model){
        System.out.println("ENTRE A LOG %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        return "/users/log-in";
    }

    /*@RequestMapping("/users/sing-up")
    public  String createView(Model model){

        return "singup/singup";
    }*/

    @RequestMapping("/sms_email")
    public  String smsemail(Model model){

        return "singup/sms_email";
    }
}

    /*when('/users/sing-up', {
        templateUrl: 'partials/users/singup.html'
    }).
        when('/users/sms-email', {
        templateUrl: 'partials/users/smsemail.html'
        }).
        when('/users/log-in', {
        templateUrl: 'partials/users/login.html'
        }).
        when('/dashboard', {
        templateUrl: 'partials/dash/main.html'
        }).
        when('/dashboard/card-form', {
        templateUrl: 'partials/dash/paymethodform.html'
        }).
        when('/dashboard/card-buy-form', {
        templateUrl: 'partials/dash/cardbuyform.html'
        }).
        when('/dashboard/refill/jdcard', {
        templateUrl: 'partials/dash/cardrefillform.html'
        })*/