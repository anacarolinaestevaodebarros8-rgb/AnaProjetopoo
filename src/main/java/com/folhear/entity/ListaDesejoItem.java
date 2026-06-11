package com.folhear.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "lista_desejo_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaDesejoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lista_desejo_id")
    private ListaDesejo listaDesejo;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;
}
