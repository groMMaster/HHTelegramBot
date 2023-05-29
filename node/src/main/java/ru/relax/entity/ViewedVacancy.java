package ru.relax.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "viewed_vacancies")
public class ViewedVacancy {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long VacancyId;


//    @ManyToOne
//    @JoinColumn(name="user_id", nullable=false)
//    private User user;
}
