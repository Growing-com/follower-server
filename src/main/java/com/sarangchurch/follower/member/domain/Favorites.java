package com.sarangchurch.follower.member.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class Favorites {
    @OneToMany(mappedBy = "fromMember", cascade = ALL, orphanRemoval = true, fetch = LAZY)
    private List<Favorite> favorites = new ArrayList<>();

    void toggle(Member fromMember, Long toMemberId) {
         if (fromMember.getId().equals(toMemberId)) {
             throw new IllegalArgumentException("자기 자신을 즐겨찾기 할 수 없습니다.");
         }

         Favorite favorite = new Favorite(fromMember, toMemberId);

         favorites.stream()
                 .filter(it -> it.equals(favorite))
                 .findAny()
                 .ifPresentOrElse(
                         it -> favorites.remove(it),
                         () -> favorites.add(favorite)
                 );
    }
}
