package be.kdg.dinosaurs.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
//@Entity(name = "application_user") or @Table(name ="app_user") - if you don't do either of these, will make spring security not work
@Entity(name = "application_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name="user_role", nullable=false)
    private UserRole role;



    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
