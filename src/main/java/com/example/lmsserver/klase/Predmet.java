package com.example.lmsserver.klase;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Predmet
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false, unique = true)
    private String sifra;
    @NotBlank
    @Column(nullable = false)
    private String naziv;
    @PositiveOrZero
    private Integer espb;
    @PositiveOrZero
    private Integer semestar;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Smer smer;
}
