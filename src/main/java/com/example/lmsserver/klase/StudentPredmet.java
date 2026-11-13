package com.example.lmsserver.klase;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@ToString
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "drzi_predmet_id"}))
public class StudentPredmet
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Student student;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "predmet_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Predmet predmet;
}
