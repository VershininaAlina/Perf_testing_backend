package ru.alina.hr_backend_server.entity.profile;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alina.hr_backend_server.entity.image.Image;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 25)
    private String name;

    @Column(length = 25)
    private String lastname;

    @Column(length = 25)
    private String secondname;

    @ManyToOne(cascade = CascadeType.ALL)
    private Image photoProfile;

    private Date birthDay;


    @ManyToMany(cascade = CascadeType.ALL)
    private List<Experience> expiriences = new ArrayList<>();

    public Profile(String name, String lastname, String secondname, Date birthDay, Image photoProfile) {
        this.name = name;
        this.lastname = lastname;
        this.secondname = secondname;
        this.photoProfile = photoProfile;
        this.birthDay = birthDay;
    }
}
