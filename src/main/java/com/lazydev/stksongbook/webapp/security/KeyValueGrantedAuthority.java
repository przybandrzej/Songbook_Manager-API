package com.lazydev.stksongbook.webapp.security;

import org.springframework.security.core.GrantedAuthority;

public class KeyValueGrantedAuthority implements GrantedAuthority {

    private final String key;
    private String value;

    public KeyValueGrantedAuthority(String key, String value){
        this.key = key;
        this.value = value;
    }

    @Override
    public String getAuthority() {
        return value;
    }

    public void setAuthority(String authority) {
        this.value = authority;
    }

    public String getKey(){
        return key;
    }

    @Override
    public String toString() {
        return "KeyValueGrantedAuthority{" +
            "key='" + key + '\'' +
            ", value='" + value + '\'' +
            '}';
    }
}
