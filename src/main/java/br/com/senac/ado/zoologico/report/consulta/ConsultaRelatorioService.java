package br.com.senac.ado.zoologico.report.consulta;

import br.com.senac.ado.zoologico.repository.ConsultaVeterinariaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ConsultaRelatorioService {

    private ConsultaVeterinariaRepository consultaRepo;

    public void RelatorioService(ConsultaVeterinariaRepository consultaRepo) {
        this.consultaRepo = consultaRepo;
    }

    public ConsultaRelatorioService(ConsultaVeterinariaRepository consultaRepo) {
        this.consultaRepo = consultaRepo;
    }

    public List<Map<String, Object>> consultasPorEspecie() {
        return consultaRepo.contarConsultasPorEspecie();
    }
}
