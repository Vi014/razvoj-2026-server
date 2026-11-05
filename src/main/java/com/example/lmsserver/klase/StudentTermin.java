package com.example.lmsserver.klase;

import com.example.lmsserver.klase.enumi.StatusPolaganja;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"termin_id", "student_id"}))
public class StudentTermin
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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    @ToString.Exclude
    private Student student;

    // Pri prijavi termina, automatski se stavlja status u PRIJAVLJEN
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPolaganja status = StatusPolaganja.PRIJAVLJEN;

    // Monitoring podaci, proširiti
    private LocalDateTime ulazak;

    private LocalDateTime predaja;
}
