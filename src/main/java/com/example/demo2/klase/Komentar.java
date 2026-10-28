package com.example.demo2.klase;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Komentar
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private Resenje resenje;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private Predavac predavac;

    @NotBlank
    @Column(nullable = false, length = 2000)
    private String tekst;
}
