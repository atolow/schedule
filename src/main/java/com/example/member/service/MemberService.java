package com.example.member.service;

import com.example.member.dto.MemberDto;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface MemberService {
    MemberDto joinMember(MemberDto memberDto) throws ChangeSetPersister.NotFoundException;
    MemberDto findMemberById(Long id);
    List<MemberDto> findAllMembers();
    List<MemberDto> findMemberByUserId(Long id);
}
