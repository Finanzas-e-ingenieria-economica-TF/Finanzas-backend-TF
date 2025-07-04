package com.example.finanzas_back.repository;

import com.example.finanzas_back.model.Bono;
import com.example.finanzas_back.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BonoRepository extends JpaRepository<Bono, Long> {

    List<Bono> findByUsuarioOrderByFechaCreacionDesc(Usuario usuario);

    Optional<Bono> findByIdAndUsuario(Long id, Usuario usuario);

    @Query("SELECT COUNT(b) FROM Bono b WHERE b.usuario = :usuario")
    long countByUsuario(@Param("usuario") Usuario usuario);

    @Query("SELECT SUM(b.valorNominal) FROM Bono b WHERE b.usuario = :usuario")
    Double sumValorNominalByUsuario(@Param("usuario") Usuario usuario);

    @Query("SELECT AVG(b.tasaInteres) FROM Bono b WHERE b.usuario = :usuario")
    Double avgTasaInteresByUsuario(@Param("usuario") Usuario usuario);
}