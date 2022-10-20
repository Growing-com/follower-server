package com.sarangchurch.follower.member.command.application;

import com.sarangchurch.follower.member.command.application.dto.BulkUpdateFavorite;
import com.sarangchurch.follower.member.command.domain.exception.IllegalMemberException;
import com.sarangchurch.follower.member.command.domain.model.Member;
import com.sarangchurch.follower.member.command.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static com.sarangchurch.follower.member.command.domain.exception.IllegalMemberException.DUPLICATE_NAME;
import static com.sarangchurch.follower.member.command.domain.exception.IllegalMemberException.DUPLICATE_USERNAME;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long save(Member member) {
        validateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateMember(Member member) {
        if (memberRepository.existsByUsername(member.getUsername())) {
            throw new IllegalMemberException(DUPLICATE_USERNAME);
        }

        if (memberRepository.existsByName(member.getName())) {
            throw new IllegalMemberException(DUPLICATE_NAME);
        }
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void toggleFavorites(Long fromMemberId, Long toMemberId) {
        Member fromMember = memberRepository.findById(fromMemberId)
                .orElseThrow(EntityNotFoundException::new);

        Member toMember = memberRepository.findById(toMemberId)
                .orElseThrow(EntityNotFoundException::new);

        fromMember.toggleFavorite(toMember.getId());
    }

    public void bulkUpdateFavorites(Long fromMemberId, BulkUpdateFavorite request) {
        if (memberRepository.countByIdIn(request.getAdd()) != request.getAdd().size()) {
            throw new EntityNotFoundException("존재하지 않는 멤버를 즐겨찾기에 추가할 수 없습니다.");
        }

        memberRepository.findById(fromMemberId)
                .orElseThrow(EntityNotFoundException::new)
                .bulkUpdateFavorites(request.getAdd(), request.getRemove());
    }
}
