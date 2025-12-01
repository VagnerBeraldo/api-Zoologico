package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.dto.Alimentacao.AlimentacaoDTO;
import br.com.senac.ado.zoologico.entity.Alimentacao;
import br.com.senac.ado.zoologico.entity.AlimentacaoId;
import br.com.senac.ado.zoologico.entity.Animal;
import br.com.senac.ado.zoologico.entity.Tratador;
import br.com.senac.ado.zoologico.exception.ConflictException;
import br.com.senac.ado.zoologico.exception.ResourceNotFoundException;
import br.com.senac.ado.zoologico.repository.AlimentacaoRepository;
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
    private final AnimalService animalService;
    private final TratadorService tratadorService;

    public List<Alimentacao> getAll() {
        return repository.findAll();
    }



    public Alimentacao save(AlimentacaoDTO dto) {

        // valida animal
        Animal animal = animalService.findById(dto.animalId());

        // valida tratador (opcional)
        Tratador tratador = null;
        if (dto.tratadorId() != null) {
            tratador = tratadorService.findById(dto.tratadorId());
        }

        // monta ID composto
        AlimentacaoId alimentacaoId = new AlimentacaoId(
                dto.animalId(),
                dto.tratadorId(),
                dto.data()
        );

        // checagem de duplicidade
        if (repository.existsById(alimentacaoId)) {
            throw new ConflictException(
                    "Já existe um registro de alimentação para esse animal, tratador e data."
            );
        }

        Alimentacao alimentacao = new Alimentacao();
        alimentacao.setId(alimentacaoId);
        alimentacao.setAnimal(animal);
        alimentacao.setTratador(tratador);
        alimentacao.setTipoRacao(dto.tipoRacao());
        alimentacao.setQuantidadeKg(dto.quantidadeKg());
        alimentacao.setHorario(dto.horario());

        return repository.save(alimentacao);
    }

    public List<Alimentacao> listByAnimal(UUID animalId) {

        // Opcional, mas recomendado: garante que o animal existe
        animalService.findById(animalId);

        return repository.findByAnimalId(animalId);
    }

    public void atualizar(UUID animalId, UUID tratadorId, String data, AlimentacaoDTO dto) {
        LocalDate dataRef = LocalDate.parse(data);

        AlimentacaoId id = new AlimentacaoId(animalId, tratadorId, dataRef);

        Alimentacao existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de alimentação não encontrado"));

        existente.setTipoRacao(dto.tipoRacao());
        existente.setQuantidadeKg(dto.quantidadeKg());
        existente.setHorario(dto.horario());

        repository.save(existente);
    }

    public void deletar(UUID animalId, UUID tratadorId, String data) {
        LocalDate dataRef = LocalDate.parse(data);

        AlimentacaoId id = new AlimentacaoId(animalId, tratadorId, dataRef);

        // Verifica se existe antes de deletar
        Alimentacao existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de alimentação não encontrado"));

        repository.delete(existente);
    }

    public Double calcularMediaQuantidade() {
        var alimentacoes = repository.findAll();

        if (alimentacoes.isEmpty()) {
            return 0.0; // ou lance uma exception, se preferir
        }

        return alimentacoes.stream()
                .collect(Collectors.averagingDouble(Alimentacao::getQuantidadeKg));
    }


}
