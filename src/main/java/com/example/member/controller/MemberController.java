package com.example.member.controller;

import com.example.member.dto.MemberDto;
import com.example.member.service.MemberService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @PostMapping
    public ResponseEntity<MemberDto> createMember(@RequestBody MemberDto dto) throws ChangeSetPersister.NotFoundException {
        return new ResponseEntity<>(memberService.joinMember(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> findMemberById(
            @PathVariable(value = "id") Long id) {

        MemberDto memberDto = memberService.findMemberById(id);
        if (memberDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(memberService.findMemberById(id), HttpStatus.OK);
    }
    @GetMapping
    public List<MemberDto> findAllMembers() {
        return memberService.findAllMembers();
    }
}
