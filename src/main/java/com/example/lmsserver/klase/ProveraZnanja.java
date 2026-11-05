package com.example.lmsserver.klase;

import com.example.lmsserver.klase.enumi.TipProvere;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
public class ProveraZnanja
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Predmet predmet;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipProvere tip;

    @DecimalMin("0.0")
    @Column(name = "prag_poena", precision = 5, scale = 2)
    private BigDecimal pragPoena; //minimum br. poena za prolazak


}
