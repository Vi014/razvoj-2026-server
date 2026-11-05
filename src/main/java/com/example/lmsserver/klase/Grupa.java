package com.example.lmsserver.klase;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
public class Grupa
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String oznaka;

    @NotBlank
    @Column(nullable = false)
    private int godina;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Smer smer;

    @ManyToMany
    @ToString.Exclude
    private Set<Predmet> predmeti = new HashSet<>();

}
