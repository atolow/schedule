package com.example.member.repository;

import com.example.member.dto.MemberDto;
import com.example.schedule.dto.ScheduleDto;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    MemberDto joinMember (MemberDto memberDto) throws ChangeSetPersister.NotFoundException;
    Optional<MemberDto> findMemberById(Long id);
    MemberDto findMemberByIdOrElseThrow(Long id);
    List<MemberDto> findAllMembers();
    List<MemberDto> findMemberByUserId(Long id);
}
