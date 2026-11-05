package com.example.lmsserver.klase;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Resenje
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private Zadatak zadatak;

    @Size(max = 500)
    @Column(name = "resenje_url", length = 500)
    private String resenjeUrl;

    private LocalDateTime krenuo;

    private LocalDateTime predao;

    @DecimalMin("0.0")
    @Column(precision = 5, scale = 2)
    private BigDecimal poeni;
}
