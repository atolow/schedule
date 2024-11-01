package hello.jdbc.domain;

import lombok.Data;

@Data
public class Member {
    private int id;
    private String username;
    private String password;
    private String email;

    public Member() {
    }

    public Member(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
