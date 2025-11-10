package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.entity.Animal;
import br.com.senac.ado.zoologico.repository.AnimalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AnimalService {

    private final AnimalRepository repo;

    public AnimalService(AnimalRepository repo) {
        this.repo = repo;
    }

    public List<Animal> listarTodos() {
        return repo.findAll();
    }

    public Animal salvar(Animal a) {
        return repo.save(a);
    }

    public Animal buscar(UUID id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));
    }

    public Animal atualizar(UUID id, Animal novo) {
        Animal existente = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));

        existente.setNome(novo.getNome());
        existente.setSexo(novo.getSexo());
        existente.setDataNascimento(novo.getDataNascimento());
        existente.setStatus(novo.getStatus());
        existente.setEspecie(novo.getEspecie());
        existente.setHabitat(novo.getHabitat());
        existente.setTratadores(novo.getTratadores());

        return repo.save(existente);
    }

    public void excluir(UUID id) {
        repo.deleteById(id);
    }

    public List<Animal> buscarPorFiltros(String especie, String habitat, String status) {
        return repo.findByEspecieNomeComumAndHabitatNomeAndStatus(especie, habitat, status);
    }

    public Map<String, Long> contarPorEspecie() {
        return repo.findAll().stream()
                .collect(Collectors.groupingBy(
                        a -> a.getEspecie() != null ? a.getEspecie().getNomeComum() : "N/A",
                        Collectors.counting()
                ));
    }
}
