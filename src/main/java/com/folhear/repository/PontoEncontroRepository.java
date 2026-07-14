package com.folhear.repository;

import com.folhear.entity.PontoEncontro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PontoEncontroRepository extends JpaRepository<PontoEncontro, UUID> {
}
