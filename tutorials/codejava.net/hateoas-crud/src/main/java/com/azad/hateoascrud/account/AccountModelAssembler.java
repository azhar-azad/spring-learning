package com.azad.hateoascrud.account;

import com.sun.swing.internal.plaf.metal.resources.metal_zh_HK;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AccountModelAssembler implements RepresentationModelAssembler<Account, EntityModel<Account>> {

    @Override
    public EntityModel<Account> toModel(Account entity) {
        EntityModel<Account> accountEntityModel = EntityModel.of(entity);

        accountEntityModel.add(linkTo(methodOn(AccountApi.class).getOne(entity.getId())).withSelfRel());
        accountEntityModel.add(linkTo(methodOn(AccountApi.class).listAll()).withRel(IanaLinkRelations.COLLECTION));

        // deposits & withdrawal
        accountEntityModel.add(linkTo(methodOn(AccountApi.class).deposit(entity.getId(), null)).withRel("deposits"));
        accountEntityModel.add(linkTo(methodOn(AccountApi.class).withdraw(entity.getId(), null)).withRel("withdrawal"));

        return accountEntityModel;
    }
}
