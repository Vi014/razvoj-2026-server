package com.example.lmsserver.klase;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
public class Zadatak
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "termin_id", nullable = false)
    @ToString.Exclude
    private Termin termin;

    @NotNull
    @Positive
    @Column(name = "redni_broj", nullable = false)
    private Short redniBroj;

    @Column(columnDefinition = "TEXT")
    private String opis;

    @NotBlank
    @Size(max = 500)
    @Column(name = "pocetni_kod_url", nullable = false, length = 500)
    private String pocetniKodUrl;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "max_poena", nullable = false, precision = 5, scale = 2)
    private BigDecimal maxPoena;
}
