package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.entity.ConsultaVeterinaria;
import br.com.senac.ado.zoologico.entity.Animal;
import br.com.senac.ado.zoologico.repository.ConsultaVeterinariaRepository;
import br.com.senac.ado.zoologico.repository.AnimalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ConsultaVeterinariaService {

    private final ConsultaVeterinariaRepository consultaRepo;
    private final AnimalRepository animalRepo;

    public ConsultaVeterinariaService(ConsultaVeterinariaRepository c, AnimalRepository a) {
        this.consultaRepo = c;
        this.animalRepo = a;
    }

    public List<ConsultaVeterinaria> listarTodos() {
        return consultaRepo.findAll();
    }

    public ConsultaVeterinaria buscar(UUID id) {
        return consultaRepo.findById(id).orElse(null);
    }

    @Transactional
    public ConsultaVeterinaria salvar(ConsultaVeterinaria consulta) {
        Animal animal = animalRepo.findById(consulta.getAnimal().getId())
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));

        ConsultaVeterinaria saved = consultaRepo.save(consulta);

        if (Boolean.TRUE.equals(consulta.getUrgente())) {
            animal.setStatus("em_tratamento");
            animalRepo.save(animal);
        }
        return saved;
    }

    public void excluir(UUID id) {
        consultaRepo.deleteById(id);
    }
}
