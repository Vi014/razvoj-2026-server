package com.example.lmsserver.klase;

import com.example.lmsserver.klase.enumi.UlogaPredavaca;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "predavac_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Predavac predavac;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "predmet_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Predmet predmet;

    @NotNull
    @Column(nullable = false)
    private UlogaPredavaca uloga;
}
