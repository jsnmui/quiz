package com.example.quiz.service;

import com.example.quiz.dao.ContactDao;
import com.example.quiz.domain.Contact;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactDao contactDao;

    public ContactService(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public void submitMessage(Contact contact) {
        contactDao.save(contact);
    }

    // Admin Contact Management
    public List<Contact> getAllContacts() {
        return contactDao.getAllContacts();
    }

    public Contact getContactById(int id) {
        return contactDao.getContactById(id);
    }
}

