package com.github.abigail830.timeticket.util;

import org.junit.Before;
import org.junit.Test;

public class DefaultEncryptorTest {

    DefaultEncryptor encryptor;

    @Before
    public void setUp() throws Exception {
        encryptor = new DefaultEncryptor();
    }

    @Test
    public void encrypt() {
        String pwd = encryptor.encrypt("timeticket20190323qaz#");
        System.out.println(pwd);
    }

    @Test
    public void decrypt() {
        String pwd = "jGG4gOdn4mO2PeAV/+4m6/6cJyM9aGyf89MIksE95ag=";
        System.out.println(encryptor.decrypt(pwd));
    }
}