package com.example.member.service;

import com.example.member.dto.MemberDto;
import com.example.member.repository.MemberRepository;
import com.example.schedule.dto.ScheduleDto;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberDto joinMember(MemberDto memberDto) throws ChangeSetPersister.NotFoundException {

        MemberDto member = new MemberDto(memberDto.getId(), memberDto.getUserName(), memberDto.getEmail(),memberDto.getCreateDate(),memberDto.getCreateDate());

        return memberRepository.joinMember(member);
    }

    @Override
    public MemberDto findMemberById(Long id) {
        MemberDto result = memberRepository.findMemberByIdOrElseThrow(id);
        return result;
    }
    @Override
    public List<MemberDto> findAllMembers() {
        return memberRepository.findAllMembers();
    }
    @Override
    public List<MemberDto> findMemberByUserId(Long id){
        List<MemberDto> result= memberRepository.findMemberByUserId(id);
        return result;
    }
}
