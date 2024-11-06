package com.example.member.service;

import com.example.member.dto.MemberDto;
import com.example.member.repository.MemberRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberDto joinMember(MemberDto memberDto) throws ChangeSetPersister.NotFoundException {

        MemberDto member = new MemberDto(memberDto.getId(), memberDto.getUsername(), memberDto.getEmail(),memberDto.getCreateDate(),memberDto.getCreateDate());

        return memberRepository.joinMember(member);
    }

    @Override
    public MemberDto findMemberById(Long id) {
        MemberDto result = memberRepository.findById(id);
        return result;
    }
}
