package com.example.member.repository;

import com.example.member.dto.MemberDto;

public interface MemberRepository {
    MemberDto joinMember (MemberDto memberDto);
    MemberDto findById(Long id);
}
