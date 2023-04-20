package ru.relax.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_users")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class AppUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String jobName;
    private String area;
    private Integer salary;
    private String experience;
    private boolean isWorkRemotely;

}
