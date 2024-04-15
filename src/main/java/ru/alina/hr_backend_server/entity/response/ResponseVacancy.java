package ru.alina.hr_backend_server.entity.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alina.hr_backend_server.entity.user.User;
import ru.alina.hr_backend_server.entity.vacancy.Vacancy;

import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseVacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Vacancy vacancy;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private Date timeResponse;

    public ResponseVacancy(Vacancy vacancy, User user, Date timeResponse) {
        this.vacancy = vacancy;
        this.user = user;
        this.timeResponse = timeResponse;
    }
}
