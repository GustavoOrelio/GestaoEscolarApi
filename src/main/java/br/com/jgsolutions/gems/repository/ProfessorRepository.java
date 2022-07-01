package br.com.jgsolutions.gems.repository;

import br.com.jgsolutions.gems.model.Professor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProfessorRepository extends PagingAndSortingRepository<Professor, Long>, JpaSpecificationExecutor<Professor> {

    @Query(value = "select a from Professor a where a.nomeProfessor like %?1%")
    Page<Professor> findByNome(String nomeProfessor, Pageable page);

    Page<Professor> findAll(Pageable page);

}