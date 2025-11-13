package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.entity.ConsultaVeterinaria;
import br.com.senac.ado.zoologico.entity.Animal;
import br.com.senac.ado.zoologico.repository.ConsultaVeterinariaRepository;
import br.com.senac.ado.zoologico.repository.AnimalRepository;
import br.com.senac.ado.zoologico.repository.specifications.ConsultaSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConsultaVeterinariaService {

    private final ConsultaVeterinariaRepository repository;
    private final AnimalRepository animalRepo;


    public List<ConsultaVeterinaria> buscarPorFiltros(
            String especialidade, Boolean urgente, LocalDate dataMin){

        // Exemplo de chamada: Buscar consultas de "Cirurgia" E (urgentes OU a partir de hoje)
        Specification<ConsultaVeterinaria> spec =
                ConsultaSpecifications.buscarConsultasCompostas(especialidade, urgente, dataMin);

        return repository.findAll(spec);
    }






    public List<ConsultaVeterinaria> listarTodos() {
        return repository.findAll();
    }

    public ConsultaVeterinaria buscar(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public ConsultaVeterinaria salvar(ConsultaVeterinaria consulta) {
        Animal animal = animalRepo.findById(consulta.getAnimal().getId())
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));

        ConsultaVeterinaria saved = repository.save(consulta);

        if (Boolean.TRUE.equals(consulta.getUrgente())) {
            animal.setStatus("em_tratamento");
            animalRepo.save(animal);
        }
        return saved;
    }

    public void excluir(UUID id) {
        repository.deleteById(id);
    }
}
