package com.folhear.controller;

import com.folhear.entity.Troca;
import com.folhear.service.TrocaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trocas")
public class TrocaController {

    @Autowired
    private TrocaService trocaService;

    @GetMapping
    public List<Troca> listarTodas() {
        return trocaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Troca> buscarPorId(@PathVariable UUID id) {
        Troca troca = trocaService.buscarPorId(id);
        if (troca == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(troca);
    }

    @PostMapping
    public Troca criar(@RequestBody Troca troca) {
        return trocaService.criarTroca(troca);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Troca> atualizar(@PathVariable UUID id, @RequestBody Troca troca) {
        Troca atualizada = trocaService.atualizar(id, troca);
        if (atualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        boolean removido = trocaService.deletar(id);
        if (!removido) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}