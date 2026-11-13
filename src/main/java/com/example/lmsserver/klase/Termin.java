package com.example.lmsserver.klase;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Termin
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provera_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private ProveraZnanja provera;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "predavac_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Predavac predavac;

    @NotNull
    @Column(nullable = false)
    private LocalDate datum;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime pocetak;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime kraj;
}
