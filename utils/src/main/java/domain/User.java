package domain;

import lombok.Data;

@Data
public class User {
    private String account;
    private String password;
    private String remember;
    private String name;
    private String checkcode;
    private String status;
    private String email;
    private String address="none";
    private byte[] avatar;
}
