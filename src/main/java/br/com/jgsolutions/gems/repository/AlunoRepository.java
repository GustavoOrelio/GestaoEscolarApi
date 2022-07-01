package br.com.jgsolutions.gems.repository;

import br.com.jgsolutions.gems.model.Aluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AlunoRepository extends PagingAndSortingRepository<Aluno, Long>, JpaSpecificationExecutor<Aluno> {

    @Query(value = "select a from Aluno a where a.nomeAluno like %?1%")
    Page<Aluno> findByNome(String nomeAluno, Pageable page);

    Page<Aluno> findAll(Pageable page);

}