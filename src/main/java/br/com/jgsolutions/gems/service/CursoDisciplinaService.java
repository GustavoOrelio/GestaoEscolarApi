package br.com.jgsolutions.gems.service;

import br.com.jgsolutions.gems.exception.BadResourceException;
import br.com.jgsolutions.gems.exception.ResourceAlreadyExistsException;
import br.com.jgsolutions.gems.exception.ResourceNotFoundException;
import br.com.jgsolutions.gems.model.CursoDisciplina;
import br.com.jgsolutions.gems.repository.CursoDisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CursoDisciplinaService {

    @Autowired
    private CursoDisciplinaRepository cursoDisciplinaRepository;

    private boolean existsbyId(Long id) {
        return cursoDisciplinaRepository.existsById(id);
    }

    public CursoDisciplina findById(Long id) throws ResourceNotFoundException {
        CursoDisciplina cursoDisciplina = cursoDisciplinaRepository.findById(id).orElse(null);
        if(cursoDisciplina == null) {
            throw new ResourceNotFoundException("Curso disciplina não foi encontrado com o id: "+id);
        }else {
            return cursoDisciplina;
        }
    }

    public Page<CursoDisciplina> findAll(Pageable pageable){
        return cursoDisciplinaRepository.findAll(pageable);
    }

    public CursoDisciplina save(CursoDisciplina cursoDisciplina) throws BadResourceException, ResourceAlreadyExistsException {
        if(cursoDisciplina.getId() != null) {
            if(existsbyId(cursoDisciplina.getId())) {
                throw new ResourceAlreadyExistsException("Curso disciplina com o id: "+cursoDisciplina.getId()+"\n já existe");
            }
            return cursoDisciplinaRepository.save(cursoDisciplina);
        }else {
            BadResourceException exc = new BadResourceException("Erro ao salvar Curso disciplina");
            exc.addErrorMessage("Curso disciplina está vazio ou é nulo");
            throw exc;
        }
    }

    public void update(CursoDisciplina cursoDisciplina) throws BadResourceException, ResourceNotFoundException{
        if(cursoDisciplina.getId() != null) {
            if(!existsbyId(cursoDisciplina.getId())) {
                throw new ResourceNotFoundException("Curso disciplina não encontrado com o id: "+cursoDisciplina.getId());
            }
            cursoDisciplinaRepository.save(cursoDisciplina);
        }
    }

    public void deleteById(Long id) throws ResourceNotFoundException{
        if(!existsbyId(id)) {
            throw new ResourceNotFoundException("Curso disciplina não encontrado com o id: "+id);
        }else {
            cursoDisciplinaRepository.deleteById(id);
        }
    }

    public Long count() {
        return cursoDisciplinaRepository.count();
    }
}