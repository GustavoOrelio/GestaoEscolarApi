package br.com.jgsolutions.gems.service;

import br.com.jgsolutions.gems.exception.BadResourceException;
import br.com.jgsolutions.gems.exception.ResourceAlreadyExistsException;
import br.com.jgsolutions.gems.exception.ResourceNotFoundException;
import br.com.jgsolutions.gems.model.Turma;
import br.com.jgsolutions.gems.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    private boolean existsById(Long id){
        return turmaRepository.existsById(id);
    }

    public Turma findById(Long id) throws ResourceNotFoundException {
        Turma turma = turmaRepository.findById(id).orElse(null);
        if(turma == null) {
            throw new ResourceNotFoundException("Turma não encontrado com o id: " + id);
        }
        else return turma;
    }

    public Page<Turma> findAll(Pageable pageable) {
        return turmaRepository.findAll(pageable);
    }

    public Page<Turma> findAllByNome(String nome, Pageable page) {
        Page<Turma> turmas = turmaRepository.findByNome(nome, page);
        return turmas;
    }

    public Turma save(Turma turma) throws BadResourceException, ResourceAlreadyExistsException {
        if(!StringUtils.isEmpty(turma.getNomeTurma())) {
            if (turma.getId() != null && existsById(turma.getId())) {
                throw new ResourceAlreadyExistsException("Turma com id: " + turma.getId() + " já existe");
            }
            return turmaRepository.save(turma);
        }
        else {
            BadResourceException exc = new BadResourceException("Erro ao salvar o turma");
            exc.addErrorMessage("Turma está vazio ou é nulo");
            throw exc;
        }
    }

    public void update(Turma turma) throws BadResourceException, ResourceNotFoundException{
        if (!StringUtils.isEmpty(turma.getNomeTurma())){
            if (!existsById(turma.getId())){
                throw  new ResourceNotFoundException("Turma não encontrado com o id: " + turma.getId());
            }
            turmaRepository.save(turma);
        }
        else {
            BadResourceException exc = new BadResourceException("Falha ao salvar o turma");
            exc.addErrorMessage("Turma está nulo ou em branco");
            throw exc;
        }
    }

    public void deleteById(Long id) throws  ResourceNotFoundException {
        if (!existsById(id)) {
            throw  new ResourceNotFoundException("Turma não encontrado com o id: " + id);
        }
        else{
            turmaRepository.deleteById(id);
        }
    }

    public Long count() {
        return turmaRepository.count();
    }

}
