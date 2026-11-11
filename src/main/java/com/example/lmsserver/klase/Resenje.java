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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "termin_student_id", nullable = false)
    @ToString.Exclude
    private StudentTermin studentTermin;

    @Size(max = 500)
    @Column(name = "resenje_url", length = 500)
    private String resenjeUrl;

    private LocalDateTime krenuo;

    private LocalDateTime predao;

    @DecimalMin("0.0")
    @Column(precision = 5, scale = 2)
    private BigDecimal poeni;

    public void validiraj()
    {
        if (poeni != null && poeni.compareTo(zadatak.getMaxPoena()) > 0)
        {
            throw new IllegalStateException("Poeni (" + poeni + ") prelaze maksimum zadatka (" + zadatak.getMaxPoena() + ")");
        }
        if (krenuo != null && predao != null && predao.isBefore(krenuo))
        {
            throw new IllegalStateException("Predaja ne moze biti pre pocetka rada");
        }
    }
}
