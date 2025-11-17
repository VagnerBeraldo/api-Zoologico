package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.repository.ConsultaVeterinariaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ConsultaRelatorioService {

    private ConsultaVeterinariaRepository repository;

    public void RelatorioService(ConsultaVeterinariaRepository consultaRepo) {
        this.repository = consultaRepo;
    }

    public ConsultaRelatorioService(ConsultaVeterinariaRepository repository) {
        this.repository = repository;
    }

    public List<Map<String, Object>> consultasPorEspecie() {
        return repository.contarConsultasPorEspecie();
    }
}
