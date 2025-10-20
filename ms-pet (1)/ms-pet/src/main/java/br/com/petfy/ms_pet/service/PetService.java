package br.com.petfy.ms_pet.service;

import br.com.petfy.ms_pet.dto.*;
import br.com.petfy.ms_pet.model.Especie;
import br.com.petfy.ms_pet.model.Pet;
import br.com.petfy.ms_pet.model.Raca;
import br.com.petfy.ms_pet.model.Tutor;
import br.com.petfy.ms_pet.repository.EspecieRepository;
import br.com.petfy.ms_pet.repository.PetRepository;
import br.com.petfy.ms_pet.repository.RacaRepository;
import br.com.petfy.ms_pet.repository.TutorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final EspecieRepository especieRepository;
    private final RacaRepository racaRepository;
    private final TutorRepository tutorRepository;


    public PetService(PetRepository petRepository, EspecieRepository especieRepository, RacaRepository racaRepository, TutorRepository tutorRepository) {
        this.petRepository = petRepository;
        this.especieRepository = especieRepository;
        this.racaRepository = racaRepository;
        this.tutorRepository = tutorRepository;
    }

    @Transactional
    public PetResponseDto cadastrarPet(PetResquestDto petDto, UUID tutorId) {
        // 1. Validar e buscar a Espécie
        System.out.println(tutorId);
        Tutor tutor = tutorRepository.findByUsuarioId(tutorId)
                .orElseThrow(() -> new RuntimeException("Perfil de Tutor não encontrado para o usuário logado."));

        Especie especie = especieRepository.findById(petDto.especieId())
                .orElseThrow(() -> new RuntimeException("Espécie não encontrada com ID: " + petDto.especieId()));

        // 2. Validar e buscar a Raça
        Raca raca = racaRepository.findById(petDto.racaId())
                .orElseThrow(() -> new RuntimeException("Raça não encontrada com ID: " + petDto.racaId()));

        // 3. **Validação CRÍTICA:** Verificar se a Raça pertence à Espécie fornecida
        if (!Objects.equals(raca.getEspecie().getId(), especie.getId())) {
            throw new RuntimeException("A raça selecionada não pertence à espécie informada.");
        }

        // 4. Criar a entidade Pet
        Pet pet = new Pet();

        // 5. Copiar as propriedades diretas
        BeanUtils.copyProperties(petDto, pet);

        pet.setTutorId(tutor);
        // Os IDs especieId e racaId do DTO não serão copiados diretamente para objetos Especie/Raca
        // na entidade Pet por BeanUtils. É por isso que fizemos os passos acima.

        // 6. Associar a entidade Raca completa ao Pet (que já contém a Espécie)
        pet.setRaca(raca);

        // 7. Salvar o Pet no banco de dados
        Pet petSalvo = petRepository.save(pet);

        // 8. Construir o PetResponseDto

        // Criar o DTO da Espécie a partir da Raça do Pet salvo
        EspecieDto especieDto = new EspecieDto(petSalvo.getRaca().getEspecie().getId(), petSalvo.getRaca().getEspecie().getNome());

        // Criar o DTO da Raça, incluindo o DTO da Espécie
        RacaDto racaDto = new RacaDto(petSalvo.getRaca().getId(), petSalvo.getRaca().getNome());  // colocar no parametro deopis caso de ruim especieDto

        // Criar e retornar o PetResponseDto
        return new PetResponseDto(
                petSalvo.getId(),
                petSalvo.getNome(),
                petSalvo.getPorte(),
                petSalvo.getIdade(),
                petSalvo.getSexo(),
                petSalvo.getPeso(),
                racaDto
               // petSalvo.getTutorId()
                 // Passa o DTO da Raça completo
             //   petSalvo.getTutorID()
        );
    }

    public List<PetResponseDto> listarTodosPets() {
        return petRepository.findAll().stream()
                .map(pet -> new PetResponseDto(
                        pet.getId(),
                        pet.getNome(),
                        pet.getPorte(),
                        pet.getIdade(),
                        pet.getSexo(),
                        pet.getPeso(),
                        // Assumindo que raca e especie NUNCA são nulas aqui
                        new RacaDto(
                                pet.getRaca().getId(),
                                pet.getRaca().getNome() //colocar a virgular caso de ruim
//                                new EspecieDto(
//                                        pet.getRaca().getEspecie().getId(),
//                                        pet.getRaca().getEspecie().getNome()
//                                )
                        )
                       // pet.getTutorId()
                ))
                .collect(Collectors.toList());
    }


    public List<PetResponseDto> findPetsByIds(List<UUID> ids) {

        return petRepository.findAllById(ids).stream()
                .map(pet -> new PetResponseDto(pet.getId(), pet.getNome())) // Converte cada Pet para o DTO
                .collect(Collectors.toList());
    }

    public PetResponseDto bucarPetPorId(UUID id){

        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrada com o ID: " + id));

        // 2. Converte a entidade para o DTO de resposta e o retorna.
        return new PetResponseDto(pet.getId(), pet.getNome(), pet.getPorte(), pet.getIdade(), pet.getSexo(), pet.getPeso(), new RacaDto(pet.getRaca()));
    }


}
