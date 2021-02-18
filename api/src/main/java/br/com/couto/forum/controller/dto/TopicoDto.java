package br.com.couto.forum.controller.dto;


import br.com.couto.forum.model.StatusTopico;
import br.com.couto.forum.model.Topico;
import br.com.couto.forum.model.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.data.domain.Page;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TopicoDto {

    private Long id;
    private String titulo;
    private String mensagem;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataCriacao;
    private Usuario autor;
    private int qtdResposta;
    private String status;


    public TopicoDto(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
        this.autor = topico.getAutor();
        this.qtdResposta = topico.getRespostas().size();
        this.status = topico.getStatus().name();
    }

    public static Page<TopicoDto> convert(Page<Topico> topicos){
        return topicos.map(TopicoDto::new);
    }
}
