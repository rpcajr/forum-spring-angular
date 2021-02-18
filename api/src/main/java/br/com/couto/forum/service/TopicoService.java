package br.com.couto.forum.service;

import br.com.couto.forum.controller.dto.TopicoDto;
import br.com.couto.forum.model.Topico;
import br.com.couto.forum.repository.TopicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TopicoService {

    private final TopicoRepository topicoRepository;

    public Page<TopicoDto> findAll(Pageable pageable, String titulo) {
        Page<Topico> topicos;

        if(titulo != null){
         topicos = topicoRepository.findAllByTitulo(titulo, pageable);
        }else{
            topicos = topicoRepository.findAll(pageable);
        }

        return TopicoDto.convert(topicos);
    }

    public Topico findById(Long id) {
        return topicoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Topico save(Topico topico){
        return topicoRepository.save(topico);
    }

    public void update(Topico topico){
        topicoRepository.findById(topico.getId())
                .map(topico1 -> {
                    topicoRepository.save(topico);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void delete(Long id){
        topicoRepository.findById(id)
                .map(topico -> {
                    topicoRepository.delete(topico);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
