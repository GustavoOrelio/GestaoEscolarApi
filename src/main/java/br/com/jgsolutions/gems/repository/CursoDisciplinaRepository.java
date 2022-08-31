package br.com.jgsolutions.gems.repository;

import br.com.jgsolutions.gems.model.CursoDisciplina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CursoDisciplinaRepository extends JpaRepository<CursoDisciplina, Long> {
    @Query(value = "from CursoDisciplina")
    Page<CursoDisciplina> findAll(Pageable page);
}