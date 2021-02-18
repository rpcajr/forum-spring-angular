package br.com.couto.forum.controller;

import br.com.couto.forum.controller.dto.TopicoDto;
import br.com.couto.forum.model.Topico;
import br.com.couto.forum.service.TopicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/topicos")
@RequiredArgsConstructor
public class TopicosController {

    private final TopicoService topicoService;

    @GetMapping
    //@Cacheable(value = "listaDeTopicos")
    public Page<TopicoDto> listAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pageable,
                                   @RequestParam(required = false) String titulo) {

        return topicoService.findAll(pageable, titulo);
    }

    @GetMapping("/{id}")
    public Topico findById(@PathVariable("id") Long id) {
        return topicoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //@CacheEvict(value = "listaDeTopicos", allEntries = true)
    public Topico save(@RequestBody @Valid Topico topico) {
        return topicoService.save(topico);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@CacheEvict(value = "listaDeTopicos", allEntries = true)
    public void update(@RequestBody @Valid Topico topico) {
        topicoService.update(topico);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@CacheEvict(value = "listaDeTopicos", allEntries = true)
    public void delete(@PathVariable("id") Long id) {
        topicoService.delete(id);
    }


}
