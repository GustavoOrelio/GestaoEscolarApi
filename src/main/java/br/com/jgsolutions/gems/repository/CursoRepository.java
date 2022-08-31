package br.com.jgsolutions.gems.repository;

import br.com.jgsolutions.gems.model.Curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CursoRepository extends PagingAndSortingRepository<Curso, Long>, JpaSpecificationExecutor<Curso> {

    @Query(value = "select a from Curso a where a.nomeCurso like %?1%")
    Page<Curso> findByNome(String nomeCurso, Pageable page);

    Page<Curso> findAll(Pageable page);

}
