package com.azad.hateoascrud.account;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/api/accounts")
public class AccountApi {

    private AccountService accountService;
    private AccountModelAssembler accountModelAssembler;

    public AccountApi(AccountService accountService, AccountModelAssembler accountModelAssembler) {
        this.accountService = accountService;
        this.accountModelAssembler = accountModelAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Account>> listAll() {
        List<EntityModel<Account>> listEntityModel = accountService.listAll().stream()
                .map(accountModelAssembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Account>> collectionModel = CollectionModel.of(listEntityModel);

        collectionModel.add(linkTo(methodOn(AccountApi.class).listAll()).withSelfRel());

        return collectionModel;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<EntityModel<Account>> getOne(@PathVariable("id") Integer id) {
        try {
            Account account = accountService.get(id);

            EntityModel<Account> model = accountModelAssembler.toModel(account);

            return new ResponseEntity<>(model, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public HttpEntity<EntityModel<Account>> add(@RequestBody Account account) {
        Account savedAccount = accountService.save(account);

        EntityModel<Account> model = accountModelAssembler.toModel(savedAccount);

        return ResponseEntity.created(linkTo(methodOn(AccountApi.class).getOne(savedAccount.getId()))
                .toUri()).body(model);
    }

    @PutMapping
    public HttpEntity<EntityModel<Account>> replace(@RequestBody Account account) {
        Account updatedAccount = accountService.save(account);

        return new ResponseEntity<>(accountModelAssembler.toModel(updatedAccount), HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}/deposits")
    public HttpEntity<EntityModel<Account>> deposit(@PathVariable("id") Integer id, @RequestBody Amount amount) {
        Account updatedAccount = accountService.deposit(amount.getAmount(), id);

        return new ResponseEntity<>(accountModelAssembler.toModel(updatedAccount), HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}/withdrawal")
    public HttpEntity<EntityModel<Account>> withdraw(@PathVariable("id") Integer id, @RequestBody Amount amount) {
        Account updatedAccount = accountService.withdraw(amount.getAmount(), id);

        return new ResponseEntity<>(accountModelAssembler.toModel(updatedAccount), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
