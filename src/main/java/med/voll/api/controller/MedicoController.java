package med.voll.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import med.voll.api.medico.DadosListagemMedico;

@RestController
@RequestMapping("/medicos")

public class MedicoController 
{
    // automaticamente cria uma instancia do repository e injeta aqui
    @Autowired
    private MedicoRepository repository;
    //post é usado para criar recursos
    @PostMapping
    //transactional garante que se der algum erro na hora de salvar, ele desfaz tudo que foi feito no banco até aquele ponto
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
        repository.save(new Medico(dados));
    }

    @GetMapping
    public List<DadosListagemMedico> listar() {
        // aqui convertemos cada Medico da lista em um DadosListagemMedico
        return repository.findAll().stream().map(DadosListagemMedico::new).toList();
    }
}
