package br.com.jgsolutions.gems.service;

import br.com.jgsolutions.gems.exception.BadResourceException;
import br.com.jgsolutions.gems.exception.ResourceAlreadyExistsException;
import br.com.jgsolutions.gems.exception.ResourceNotFoundException;
import br.com.jgsolutions.gems.model.Aluno;
import br.com.jgsolutions.gems.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    private boolean existsById(Long id){
        return alunoRepository.existsById(id);
    }

    public Aluno findById(Long id) throws ResourceNotFoundException {
        Aluno aluno = alunoRepository.findById(id).orElse(null);
        if(aluno == null) {
            throw new ResourceNotFoundException("Aluno não encontrado com o id: " + id);
        }
        else return aluno;
    }

    public Page<Aluno> findAll(Pageable pageable) {
        return alunoRepository.findAll(pageable);
    }

    public Page<Aluno> findAllByNome(String nome, Pageable page) {
        Page<Aluno> alunos = alunoRepository.findByNome(nome, page);
        return alunos;
    }

    public Aluno save(Aluno aluno) throws BadResourceException, ResourceAlreadyExistsException {
        if(!StringUtils.isEmpty(aluno.getNomeAluno())) {
            if (aluno.getId() != null && existsById(aluno.getId())) {
                throw new ResourceAlreadyExistsException("Aluno com id: " + aluno.getId() + " já existe");
            }
            return alunoRepository.save(aluno);
        }
        else {
            BadResourceException exc = new BadResourceException("Erro ao salvar o aluno");
            exc.addErrorMessage("Aluno está vazio ou é nulo");
            throw exc;
        }
    }

    public void update(Aluno aluno) throws BadResourceException, ResourceNotFoundException{
        if (!StringUtils.isEmpty(aluno.getNomeAluno())){
            if (!existsById(aluno.getId())){
                throw  new ResourceNotFoundException("Aluno não encontrado com o id: " + aluno.getId());
            }
            alunoRepository.save(aluno);
        }
        else {
            BadResourceException exc = new BadResourceException("Falha ao salvar o aluno");
            exc.addErrorMessage("Aluno está nulo ou em branco");
            throw exc;
        }
    }

    public void deleteById(Long id) throws  ResourceNotFoundException {
        if (!existsById(id)) {
            throw  new ResourceNotFoundException("Aluno não encontrado com o id: " + id);
        }
        else{
            alunoRepository.deleteById(id);
        }
    }

    public Long count() {
        return alunoRepository.count();
    }

}
