package com.example.lmsserver.klase;

import com.example.lmsserver.klase.enumi.TipPredavaca;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
public class Predavac
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String ime;
    @NotBlank
    @Column(nullable = false)
    private String prezime;
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true, length = 13)
    @Pattern(regexp = "\\d{13}", message = "JMBG mora imati tacno 13 cifara")
    private String jmbg;
    private LocalDate datumRodjenja;
    @Column(nullable = false)
    private boolean aktivan = true;
    private TipPredavaca titula;
    @Column(nullable = false)
    private String lozinka;
}
