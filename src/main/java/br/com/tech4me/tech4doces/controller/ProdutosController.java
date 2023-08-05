package br.com.tech4me.tech4doces.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.tech4me.tech4doces.repository.ProdutosRepository;

import br.com.tech4me.tech4doces.model.Produtos;

@RestController
@RequestMapping("/produtos")
public class ProdutosController {
    @Autowired
    private ProdutosRepository repository;
    List<Produtos> produtos = new ArrayList<>();



    //READ

    //Get -> obter valores / neste caso, todos cadastrados na lista
    @GetMapping
    private List<Produtos> obterProdutos(){
        return repository.findAll();
    }

    @GetMapping("/{descricao}")
    private Produtos obterProdutoPorDescricao(@PathVariable String descricao){
        Produtos produto = produtos.stream()
        .filter(p -> p.getDescricao().equalsIgnoreCase(descricao))
        .findFirst().orElse(null);

        return produto;
    }

    //CREATED
    //Post -> Adicionando valor / neste caso, recebe um objeto JSON
    @PostMapping
    private ResponseEntity<String> cadastrarProduto(@RequestBody Produtos produto){
        if (produto != null) {
            produtos.add(produto);
            String mensagem = "Categoria: " + produto.getTipo() 
            + ". Produto: " + produto.getDescricao()
             + ". Foi cadastrado com sucesso";
            return new ResponseEntity<>(mensagem, HttpStatus.CREATED);
        }
        String mensagem = "O produto informado é nulo";
        return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
    }

    //UPDATE
    //Put -> Atualizar um valor existente / neste caso, recebe um identificador do objeto e um objeto JSON
    @PutMapping("/{descricao}")
    private Produtos atualizarProduto(@PathVariable String descricao, 
                                        @RequestBody Produtos produtoNovo){
        Produtos produto = produtos.stream()
        .filter(p -> p.getDescricao().equalsIgnoreCase(descricao))
        .findFirst().orElse(null);
        if (produto != null) {
            produto.setDescricao(produtoNovo.getDescricao());
            produto.setTipo(produtoNovo.getTipo());
            produto.setValor(produtoNovo.getValor());
        }
        
        return produto;
    }

    //DELETE
    //DeleteMapping -> Deletar um valor armazenado no repositório
    @DeleteMapping("/{descricao}")
    private void removerProduto(@PathVariable String descricao){
        produtos.removeIf(p -> p.getDescricao().equalsIgnoreCase(descricao));
    }
}
