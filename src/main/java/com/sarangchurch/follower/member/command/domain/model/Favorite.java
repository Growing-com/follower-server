package com.sarangchurch.follower.member.command.domain.model;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.util.Objects;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long id;

    @ManyToOne(optional = false, fetch = LAZY)
    @JoinColumn(name = "from_member_id")
    private Member fromMember;

    private Long toMemberId;

    Favorite(Member fromMember, Long toMemberId) {
        this.fromMember = fromMember;
        this.toMemberId = toMemberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorite favorite = (Favorite) o;
        return Objects.equals(fromMember, favorite.fromMember) && Objects.equals(toMemberId, favorite.toMemberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromMember, toMemberId);
    }
}
