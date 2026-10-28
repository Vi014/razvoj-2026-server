package com.example.demo2.klase;

import com.example.demo2.klase.enumi.TipPredavaca;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"predavac_id", "predmet_id"}))
public class DrziPredmet
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @MapsId("predavacId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "predavac_id")
    @ToString.Exclude
    private Predavac predavac;

    @NotNull
    @MapsId("predmetId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "predmet_id")
    @ToString.Exclude
    private Predmet predmet;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipPredavaca uloga;
}
