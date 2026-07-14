package com.folhear.repository;

import com.folhear.entity.Troca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TrocaRepository extends JpaRepository<Troca, UUID> {
}
