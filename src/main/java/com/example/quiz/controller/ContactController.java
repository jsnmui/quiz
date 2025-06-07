package com.example.quiz.controller;

import com.example.quiz.domain.Contact;
import com.example.quiz.domain.User;
import com.example.quiz.service.ContactService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contact-us")
    public String contactPage(Model model) {
        model.addAttribute("contact", new Contact());
        return "contact";
    }

    @PostMapping("/contact-us")
    public String submitForm(@ModelAttribute Contact contact) {
        contactService.submitMessage(contact);
        return "redirect:/contact-us?success";
    }


   // Admin view for contact us messages
    @GetMapping("/admin/contacts")
    public String viewContacts(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.isAdmin()) {
            return "redirect:/unauthorized";
        }

        List<Contact> contacts = contactService.getAllContacts();
        model.addAttribute("contacts", contacts);
        return "admin-contact-messages";
    }

    @GetMapping("/admin/contacts/{id}")
    public String viewContactMessage(@PathVariable int id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.isAdmin()) {
            return "redirect:/unauthorized";
        }

        Contact contact = contactService.getContactById(id);
        model.addAttribute("contact", contact);
        return "admin-contact-view";
    }

}
