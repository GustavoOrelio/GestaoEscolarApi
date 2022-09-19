package br.com.jgsolutions.gems.repository;

import br.com.jgsolutions.gems.model.Matricula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MatriculaRepository extends PagingAndSortingRepository<Matricula, Long>, JpaSpecificationExecutor<Matricula> {

    @Query(value = "select a from Matricula a where a.ra like %?1%")
    Page<Matricula> findByRa(String ra, Pageable page);

    Page<Matricula> findAll(Pageable page);

}
