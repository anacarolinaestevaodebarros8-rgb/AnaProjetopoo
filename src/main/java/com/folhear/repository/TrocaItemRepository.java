package com.folhear.repository;

import com.folhear.entity.TrocaItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TrocaItemRepository extends JpaRepository<TrocaItem, UUID> {
}
