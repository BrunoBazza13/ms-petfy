package br.com.petfy.ms_clinica.service;

import br.com.petfy.ms_clinica.dto.veterinatiodto.VeterinarioCadastroRequest;
import br.com.petfy.ms_clinica.model.Veterinario;
import br.com.petfy.ms_clinica.repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VeterinarioService {

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    public void criarPerfil(VeterinarioCadastroRequest dto){

        Veterinario vet = new Veterinario();
        vet.setNome(dto.nome());
        vet.setCrmv(dto.crmv());
        vet.setUf(dto.uf());
        vet.setUsuarioId(dto.usuarioId());
        veterinarioRepository.save(vet);

    }

}
