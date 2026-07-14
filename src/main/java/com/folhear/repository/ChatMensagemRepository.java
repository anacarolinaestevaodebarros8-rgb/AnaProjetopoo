package com.folhear.repository;

import com.folhear.entity.ChatMensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMensagemRepository extends JpaRepository<ChatMensagem, Long> {
}
