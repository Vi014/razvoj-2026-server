package com.example.lmsserver.klase;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Zadatak zadatak;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "termin_student_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private StudentTermin studentTermin;

    @Size(max = 500)
    @Column(name = "resenje_url", length = 500)
    private String resenjeUrl;


    @DecimalMin("0.0")
    @Column(precision = 5, scale = 2)
    private BigDecimal poeni;

    public void validiraj()
    {
        if (poeni != null && poeni.compareTo(zadatak.getMaxPoena()) > 0)
        {
            throw new IllegalStateException("Poeni (" + poeni + ") prelaze maksimum zadatka (" + zadatak.getMaxPoena() + ")");
        }
        if (studentTermin.getUlazak() != null && studentTermin.getPredaja() != null && studentTermin.getPredaja().isBefore(studentTermin.getUlazak()))
        {
            throw new IllegalStateException("Predaja ne moze biti pre pocetka rada");
        }
    }
}
