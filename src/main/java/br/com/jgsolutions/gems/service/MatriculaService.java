package br.com.jgsolutions.gems.service;

import br.com.jgsolutions.gems.exception.BadResourceException;
import br.com.jgsolutions.gems.exception.ResourceAlreadyExistsException;
import br.com.jgsolutions.gems.exception.ResourceNotFoundException;
import br.com.jgsolutions.gems.model.Matricula;
import br.com.jgsolutions.gems.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    private boolean existsById(Long id) {
        return matriculaRepository.existsById(id);
    }

    public Matricula findById(Long id) throws ResourceNotFoundException {
        Matricula matricula = matriculaRepository.findById(id).orElse(null);
        if (matricula == null) {
            throw new ResourceNotFoundException("Matricula não encontrado com o id: " + id);
        } else return matricula;
    }

    public Page<Matricula> findAll(Pageable pageable) {
        return matriculaRepository.findAll(pageable);
    }

    public Page<Matricula> findAllByRa(String ra, Pageable page) {
        Page<Matricula> matriculas = matriculaRepository.findByRa(ra, page);
        return matriculas;
    }

    public Matricula save(Matricula matricula) throws BadResourceException, ResourceAlreadyExistsException {
        if (!StringUtils.isEmpty(matricula.getRa())) {
            if (matricula.getId() != null && existsById(matricula.getId())) {
                throw new ResourceAlreadyExistsException("Matricula com id: " + matricula.getId() + " já existe");
            }
            return matriculaRepository.save(matricula);
        } else {
            BadResourceException exc = new BadResourceException("Erro ao salvar o matricula");
            exc.addErrorMessage("Matricula está vazio ou é nulo");
            throw exc;
        }
    }

    public void update(Matricula matricula) throws BadResourceException, ResourceNotFoundException {
        if (!StringUtils.isEmpty(matricula.getRa())) {
            if (!existsById(matricula.getId())) {
                throw new ResourceNotFoundException("Matricula não encontrado com o id: " + matricula.getId());
            }
            matriculaRepository.save(matricula);
        } else {
            BadResourceException exc = new BadResourceException("Falha ao salvar o matricula");
            exc.addErrorMessage("Matricula está nulo ou em branco");
            throw exc;
        }
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        if (!existsById(id)) {
            throw new ResourceNotFoundException("Matricula não encontrado com o id: " + id);
        } else {
            matriculaRepository.deleteById(id);
        }
    }

    public Long count() {
        return matriculaRepository.count();
    }

}
