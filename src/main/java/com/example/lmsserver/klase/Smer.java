package com.example.lmsserver.klase;

import com.example.lmsserver.klase.enumi.VrsteStudija;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Smer
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String oznaka;
    @NotBlank
    @Column(nullable = false)
    private String naziv;
    private Integer trajanjeGodina;
    private Integer trajanjeSemestara;
    private VrsteStudija vrstaStudija;
    private Integer ukupnoEspb;

    @JsonIgnore
    @OneToMany(mappedBy = "smer")
    @ToString.Exclude
    private List<Predmet> predmeti = new ArrayList<>();
}
