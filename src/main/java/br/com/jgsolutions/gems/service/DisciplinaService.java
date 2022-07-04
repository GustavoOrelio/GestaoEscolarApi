package br.com.jgsolutions.gems.service;

import br.com.jgsolutions.gems.exception.BadResourceException;
import br.com.jgsolutions.gems.exception.ResourceAlreadyExistsException;
import br.com.jgsolutions.gems.exception.ResourceNotFoundException;
import br.com.jgsolutions.gems.model.Disciplina;
import br.com.jgsolutions.gems.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    private boolean existsById(Long id){
        return disciplinaRepository.existsById(id);
    }

    public Disciplina findById(Long id) throws ResourceNotFoundException {
        Disciplina disciplina = disciplinaRepository.findById(id).orElse(null);
        if(disciplina == null) {
            throw new ResourceNotFoundException("Disciplina não encontrado com o id: " + id);
        }
        else return disciplina;
    }

    public Page<Disciplina> findAll(Pageable pageable) {
        return disciplinaRepository.findAll(pageable);
    }

    public Page<Disciplina> findAllByNome(String nome, Pageable page) {
        Page<Disciplina> disciplinas = disciplinaRepository.findByNome(nome, page);
        return disciplinas;
    }

    public Disciplina save(Disciplina disciplina) throws BadResourceException, ResourceAlreadyExistsException {
        if(!StringUtils.isEmpty(disciplina.getNomeDisciplina())) {
            if (disciplina.getId() != null && existsById(disciplina.getId())) {
                throw new ResourceAlreadyExistsException("Disciplina com id: " + disciplina.getId() + " já existe");
            }
            return disciplinaRepository.save(disciplina);
        }
        else {
            BadResourceException exc = new BadResourceException("Erro ao salvar o disciplina");
            exc.addErrorMessage("Disciplina está vazio ou é nulo");
            throw exc;
        }
    }

    public void update(Disciplina disciplina) throws BadResourceException, ResourceNotFoundException{
        if (!StringUtils.isEmpty(disciplina.getNomeDisciplina())){
            if (!existsById(disciplina.getId())){
                throw  new ResourceNotFoundException("Disciplina não encontrado com o id: " + disciplina.getId());
            }
            disciplinaRepository.save(disciplina);
        }
        else {
            BadResourceException exc = new BadResourceException("Falha ao salvar o disciplina");
            exc.addErrorMessage("Disciplina está nulo ou em branco");
            throw exc;
        }
    }

    public void deleteById(Long id) throws  ResourceNotFoundException {
        if (!existsById(id)) {
            throw  new ResourceNotFoundException("Disciplina não encontrado com o id: " + id);
        }
        else{
            disciplinaRepository.deleteById(id);
        }
    }

    public Long count() {
        return disciplinaRepository.count();
    }

}
