package com.azad.baeldung_thymeleaf;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    @Autowired
    private MemberRepository repository;

    @GetMapping("/signup")
    public String showSignupForm(Member member) {
        return "add-member";
    }

    @GetMapping("/index")
    public String showMemberList(Model model) {
        model.addAttribute("members", repository.findAll());
        return "index";
    }

    @PostMapping("/addmember")
    public String addMember(@Valid Member member, BindingResult bindingResult,
                            Model model) {
        /*
        If the entity doesn't pass the validation, the signup form will be
        redisplayed.
         */
        if (bindingResult.hasErrors()) {
            return "add-member";
        }

        /*
        Otherwise, once the entity has been saved, the list of persisted
        entities will be updated in the corresponding view.
         */
        repository.save(member);
        return "redirect:/index";
    }

    /*
    Following method is responsible for fetching the Member entity that matches
    the supplied id from the database.
    If the entity exists, it will be passed on as a model attribute to the
    update form view, so that the form can be populated with the values of the
    name and email fields.
     */
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Member member = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member id: " + id));

        model.addAttribute("member", member);
        return "update-member";
    }

    /*
    Following method will persist the updated entity in the database.
     */
    @PostMapping("/update/{id}")
    public String updateMember(@PathVariable("id") Long id, @Valid Member member,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            member.setId(id);
            return "update-member";
        }

        repository.save(member);
        return "redirect:/index";
    }

    /*
    Following method will remove the member by the given id.
     */
    @GetMapping("/delete/{id}")
    public String deleteMember(@PathVariable("id") Long id, Model model) {
        Member member = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member id: " + id));

        repository.delete(member);
        return "redirect:/index";
    }
}
