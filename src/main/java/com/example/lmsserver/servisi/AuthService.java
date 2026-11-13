package com.example.lmsserver.servisi;

import com.example.lmsserver.config.SecurityProperties;
import com.example.lmsserver.exceptions.UnauthorizedException;
import com.example.lmsserver.klase.Predavac;
import com.example.lmsserver.klase.Student;
import com.example.lmsserver.klase.enumi.LoginUloga;
import com.example.lmsserver.repozitorijumi.PredavacRepository;
import com.example.lmsserver.repozitorijumi.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@AllArgsConstructor
@Service
public class AuthService {
    final StudentRepository studentRepository;
    final PredavacRepository predavacRepository;
    final SecurityProperties securityProperties;
    final PasswordEncoder passwordEncoder;
    final JwtEncoder jwtEncoder;

    public record Prijava(String token, Instant istice, LoginUloga uloga) {
    }

    public Prijava login(String email, String lozinka, String uloga) {
        // return null;
        Long idKorisnika;
        LoginUloga loginUloga;

        switch(uloga) {
            case "STUDENT":
                Student podaciS = studentRepository.findByEmailIgnoreCase(email);
                if(podaciS == null || !(passwordEncoder.matches(lozinka, podaciS.getLozinka())))
                    throw new UnauthorizedException("Pogrešni podaci za logovanje");
                idKorisnika = podaciS.getId();
                loginUloga = LoginUloga.STUDENT;
                break;
            case "PREDAVAC":
                Predavac podaciP = predavacRepository.findByEmailIgnoreCase(email);
                if(podaciP == null || !(passwordEncoder.matches(lozinka, podaciP.getLozinka())))
                    throw new UnauthorizedException("Pogrešni podaci za logovanje");
                idKorisnika = podaciP.getId();
                loginUloga = LoginUloga.PREDAVAC;
                break;
            case "ADMIN":
                // ovu ulogu msm da za sad nije potrebno zapravo implementirati jer nije predviđena specifikacijom, ali kostur koda stoji tu za svaki slučaj
                throw new UnauthorizedException("Pokušavate da se ulogujete kao nepostojeća uloga");
                // loginUloga = LoginUloga.ADMIN;
                // break;
            default:
                throw new UnauthorizedException("Pokušavate da se ulogujete kao nepostojeća uloga");
        }

        Instant sada = Instant.now();
        Instant istice = sada.plus(securityProperties.jwtTrajanje());
        JwtClaimsSet.Builder claims = JwtClaimsSet.builder()
                .issuer("raflms")
                .subject(email)
                .issuedAt(sada)
                .expiresAt(istice)
                .claim("uloga", loginUloga.name())
                .claim("id", idKorisnika);
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        String token = jwtEncoder.encode(JwtEncoderParameters.from(header, claims.build())).getTokenValue();
        return new Prijava(token, istice, loginUloga);
    }
}
