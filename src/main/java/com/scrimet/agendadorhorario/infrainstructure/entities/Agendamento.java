package com.scrimet.agendadorhorario.infrainstructure.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "agendamentos_tb")
@Builder
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String servico;
    @Column(nullable = false)
    private LocalDateTime dataHoraAgendamento;
    private LocalDateTime dataInsercao = LocalDateTime.now();
    private LocalDateTime dataFinalizacao;
    @PrePersist
    public void onCreate() {
        this.dataHoraAgendamento = LocalDateTime.now();
    }
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "profissional-id")
    private Profissional profissional;

}
