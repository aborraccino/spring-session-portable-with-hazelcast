package com.worldpay.spring.session.controller;

import com.worldpay.spring.session.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ContactController {

    // Session-scoped beans
    @Autowired
    private Contact contact;

    @RequestMapping("/")
    public String showContact(Model model){
        model.addAttribute("contact", contact);
        return "index";
    }

    @RequestMapping(value = "/update-contact")
    public String updateContact(Model model){
        return showContact(model);
    }

    @RequestMapping(value = "/update-contact", method = RequestMethod.POST)
    public String updateContact(Model model, @ModelAttribute("contact") Contact contact){
        this.contact.setCountry(contact.getCountry());
        this.contact.setName(contact.getName());
        this.contact.setSurname(contact.getSurname());
        this.contact.setEmail(contact.getEmail());
        return showContact(model);
    }
}
