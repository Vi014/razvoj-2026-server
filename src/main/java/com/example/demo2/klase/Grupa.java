package com.example.demo2.klase;

import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Smer smer;

    @ManyToMany
    @ToString.Exclude
    private Set<Predmet> predmeti = new HashSet<>();

}
