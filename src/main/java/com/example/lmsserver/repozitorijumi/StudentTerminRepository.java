package com.example.lmsserver.repozitorijumi;

import com.example.lmsserver.klase.StudentTermin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentTerminRepository extends JpaRepository<StudentTermin, Long>
{
    // svaki repository sadrži u sebi sql upite vezane za tu tabelu (klasu), za ovo smatram da treba da se dogovorimo šta će ići u koj

    List<StudentTermin> findByTerminId(Long terminId);

    List<StudentTermin> findByStudentId(Long studentId);

    StudentTermin findByTerminIdAndStudentId(Long terminId, Long studentId);

    boolean existsByTerminIdAndStudentId(Long terminId, Long studentId);

    // da li je student vec prijavljen na bilo koji termin zadate provere
    @Query("select count(st) > 0 from StudentTermin st where st.student.id = :studentId and st.termin.provera.id = :proveraId")
    boolean existsPrijavaZaProveru(@Param("studentId") Long studentId, @Param("proveraId") Long proveraId);
}
