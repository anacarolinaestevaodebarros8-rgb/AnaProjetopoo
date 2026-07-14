package com.folhear.repository;

import com.folhear.entity.ListaDesejo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ListaDesejoRepository extends JpaRepository<ListaDesejo, UUID> {
}
