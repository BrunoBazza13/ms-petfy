package br.com.petfy.ms_clinica.service;

import br.com.petfy.ms_clinica.dto.agenda.AgendaDetalhadaDto;
import br.com.petfy.ms_clinica.dto.veterinatiodto.VeterinarioCadastroRequest;
import br.com.petfy.ms_clinica.dto.veterinatiodto.VeterinarioResponseDTO;
import br.com.petfy.ms_clinica.model.Agenda;
import br.com.petfy.ms_clinica.model.Veterinario;
import br.com.petfy.ms_clinica.repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public List<VeterinarioResponseDTO> listarVeterinarios() {
        return veterinarioRepository.findAll()
                .stream()
                .map(VeterinarioResponseDTO::new)
                .toList();
    }


    public VeterinarioResponseDTO buscarVeterinarioPorId(UUID id){

        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("veterinairo não encontrada com o ID: " + id));

        // 2. Converte a entidade para o DTO de resposta e o retorna.
        return new VeterinarioResponseDTO(veterinario);

    }

    public List<VeterinarioResponseDTO> listarVeterinarios(List<UUID> ids) {
        List<Veterinario> veterinarios = veterinarioRepository.findAllById(ids);
        return veterinarios.stream()
                .map(VeterinarioResponseDTO::new)
                .toList();
    }

}
