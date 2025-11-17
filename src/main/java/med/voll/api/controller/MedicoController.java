package med.voll.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;

//import org.hibernate.query.Page;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import med.voll.api.medico.DadosListagemMedico;
import med.voll.api.medico.DadosAtualizacaoMedico;

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

    // get é usado para listar recursos
    @GetMapping
    public Page<DadosListagemMedico> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        // aqui convertemos cada Medico da lista em um DadosListagemMedico
        return repository.findAll(paginacao).map(DadosListagemMedico::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }
}
