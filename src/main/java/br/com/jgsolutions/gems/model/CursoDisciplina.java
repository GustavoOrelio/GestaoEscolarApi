package br.com.jgsolutions.gems.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "curso_disciplina")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
public class CursoDisciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cargaHoraria;

    private int ano;

    @ManyToOne
    @JoinColumn(name = "idCurso")
    private Curso curso;
    @ManyToOne
    @JoinColumn(name = "idDisciplina")
    private Disciplina disciplina;

    @CreationTimestamp
    private Timestamp dataCadastro;
}
