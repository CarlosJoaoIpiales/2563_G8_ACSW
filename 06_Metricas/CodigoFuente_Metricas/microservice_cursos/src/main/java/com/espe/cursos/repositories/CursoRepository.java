package com.espe.cursos.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.espe.cursos.models.entities.Curso;

public interface CursoRepository extends CrudRepository<Curso, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM CursoEstudiantes ce WHERE ce.id = :id AND ce.estudianteId = :userId")
    void deleteCursoUsuario(@Param("id") Long id, @Param("userId") Long userId);
}
