package com.folhear.repository;

import com.folhear.entity.Capitulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CapituloRepository extends JpaRepository<Capitulo, UUID> {
}
