package com.example.lmsserver.klase;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @NotNull
    @Column(nullable = false)
    private int godina;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Smer smer;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Set<Predmet> predmeti = new HashSet<>();

}
