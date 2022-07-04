package br.com.jgsolutions.gems.repository;

import br.com.jgsolutions.gems.model.Disciplina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DisciplinaRepository extends PagingAndSortingRepository<Disciplina, Long>, JpaSpecificationExecutor<Disciplina> {

    @Query(value = "select a from Disciplina a where a.nomeDisciplina like %?1%")
    Page<Disciplina> findByNome(String nomeDisciplina, Pageable page);

    Page<Disciplina> findAll(Pageable page);

}