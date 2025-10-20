package br.com.petfy.ms_pet.service;

import br.com.petfy.ms_pet.dto.TutorRequest;
import br.com.petfy.ms_pet.model.Tutor;
import br.com.petfy.ms_pet.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    public void criarPerfil(TutorRequest tutorRequest){

        Tutor vet = new Tutor();
        vet.setNome(tutorRequest.nome());
        vet.setUsuarioId(tutorRequest.usuarioId());

        tutorRepository.save(vet);

    }

}
