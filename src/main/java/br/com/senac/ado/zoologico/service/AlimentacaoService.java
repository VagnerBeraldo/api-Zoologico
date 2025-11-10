package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.dto.AlimentacaoDTO;
import br.com.senac.ado.zoologico.entity.Alimentacao;
import br.com.senac.ado.zoologico.entity.AlimentacaoId;
import br.com.senac.ado.zoologico.entity.Animal;
import br.com.senac.ado.zoologico.entity.Tratador;
import br.com.senac.ado.zoologico.repository.AlimentacaoRepository;
import br.com.senac.ado.zoologico.repository.AnimalRepository;
import br.com.senac.ado.zoologico.repository.TratadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlimentacaoService {

    private final AlimentacaoRepository repository;
    private final AnimalRepository animalRepo;
    private final TratadorRepository tratadorRepo;

    public List<Alimentacao> listarTodas() {
        return repository.findAll();
    }

    public List<Alimentacao> listarPorAnimal(UUID animalId) {
        return repository.findAll().stream()
                .filter(a -> a.getAnimal().getId().equals(animalId))
                .toList();
    }

    public Alimentacao registrar(AlimentacaoDTO dto) {
        Animal animal = animalRepo.findById(dto.getAnimalId())
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));

        AlimentacaoId alimentacaoId = new AlimentacaoId(
                dto.getAnimalId(),
                dto.getTratadorId(),
                dto.getData()
        );

        Alimentacao alimentacao = new Alimentacao();
        alimentacao.setId(alimentacaoId);
        alimentacao.setAnimal(animal);
        alimentacao.setTipoRacao(dto.getTipoRacao());
        alimentacao.setQuantidadeKg(dto.getQuantidadeKg());
        alimentacao.setHorario(dto.getHorario());

        if (dto.getTratadorId() != null) {
            Tratador tratador = tratadorRepo.findById(dto.getTratadorId())
                    .orElseThrow(() -> new RuntimeException("Tratador não encontrado"));
            alimentacao.setTratador(tratador);
        }

        return repository.save(alimentacao);
    }

    public Alimentacao atualizar(UUID animalId, UUID tratadorId, String data, AlimentacaoDTO dto) {
        LocalDate dataRef = LocalDate.parse(data);
        AlimentacaoId id = new AlimentacaoId(animalId, tratadorId, dataRef);

        Alimentacao existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de alimentação não encontrado"));

        existente.setTipoRacao(dto.getTipoRacao());
        existente.setQuantidadeKg(dto.getQuantidadeKg());
        existente.setHorario(dto.getHorario());

        return repository.save(existente);
    }

    public void deletar(UUID animalId, UUID tratadorId, String data) {
        LocalDate dataRef = LocalDate.parse(data);
        AlimentacaoId id = new AlimentacaoId(animalId, tratadorId, dataRef);
        repository.deleteById(id);
    }

    public Double calcularMediaQuantidade() {
        return repository.findAll().stream()
                .collect(Collectors.averagingDouble(Alimentacao::getQuantidadeKg));
    }


}
