package br.com.jgsolutions.gems.repository;

import br.com.jgsolutions.gems.model.Turma;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TurmaRepository extends PagingAndSortingRepository<Turma, Long>, JpaSpecificationExecutor<Turma> {

    @Query(value = "select a from Turma a where a.nomeTurma like %?1%")
    Page<Turma> findByNome(String nomeTurma, Pageable page);

    Page<Turma> findAll(Pageable page);

}