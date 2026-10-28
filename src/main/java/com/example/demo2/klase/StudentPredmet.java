package com.example.demo2.klase;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    @ToString.Exclude
    private Student student;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "drzi_predmet_id")
    @ToString.Exclude
    private DrziPredmet drziPredmet;
}
