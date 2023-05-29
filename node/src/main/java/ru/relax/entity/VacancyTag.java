package ru.relax.entity;

import lombok.*;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vacancy_tags")
public class VacancyTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String Name;

    public VacancyTag(String tagName) {
        Name = tagName;
    }


//    @ManyToOne
//    @JoinColumn(name="user_id", nullable=false)
//    private User user;
}
