package com.example.calorietracker.helper.api;

import com.example.calorietracker.helper.api.Entities.Credential;
import com.example.calorietracker.helper.api.Entities.User;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class CredentialControllerTest {

    @Test
    public void validateByUsernameAndHash() {
        assertEquals(true, CredentialController.validateByUsernameAndHash("user1", "5a105e8b9d40e1329780d62ea2265d8a"));
        assertEquals(false, CredentialController.validateByUsernameAndHash("user1", "jgfuhdjaskdg2413"));
    }

    @Test
    public void isExistsWIth(){
        assertEquals(true, CredentialController.isExistsWith("user1", "az@gmail.com"));
        assertEquals(false, CredentialController.isExistsWith("user1", "azzz@gmail.com"));
    }

    @Test
    public void add(){
        User user = new User(22, "ivan", "ivanov", "ii@gmail.com",
                new Date(1991, 01, 01), 130.0, 40.0, "male",
                "231 caulfield 223 rd", 2222, 2, 300);
        Credential cr = new Credential(22, user, "user22", "sadfdafdaffa", new Date());
        CredentialController.add(cr);
        assertEquals(true, CredentialController.isExistsWith("user22", "ii@gmail.com"));
    }
}
