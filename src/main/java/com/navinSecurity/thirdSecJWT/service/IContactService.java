package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.model.Contact;

import java.util.List;

public interface IContactService {
    List<Contact> getAllAssociates(Long user_id);
    Long deleteAssociate(Long user_id,Long contact_id);
    Contact postAssociate(Contact contact,Long user_id);
    Contact updateAssociate(Contact contact,Long user_id);
    Contact getAssociate(Long user_id, Long contact_id);
}
