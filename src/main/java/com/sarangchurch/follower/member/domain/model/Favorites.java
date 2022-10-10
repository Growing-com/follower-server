package com.sarangchurch.follower.member.domain.model;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static lombok.AccessLevel.PROTECTED;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class Favorites {
    @OneToMany(mappedBy = "fromMember", cascade = ALL, orphanRemoval = true, fetch = EAGER)
    private Set<Favorite> favorites = new HashSet<>();

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

    void bulkUpdate(Member fromMember, List<Long> add, List<Long> remove) {
        addFavorites(fromMember, add);
        removeFavorites(fromMember, remove);
    }

    private void addFavorites(Member fromMember, List<Long> add) {
        if (add.contains(fromMember.getId())) {
            throw new IllegalArgumentException("자기 자신을 즐겨찾기 할 수 없습니다.");
        }

        add.stream()
                .map(it -> new Favorite(fromMember, it))
                .forEach(favorites::add);
    }

    private void removeFavorites(Member fromMember, List<Long> remove) {
        List<Favorite> removeCandidates = remove.stream()
                .map(it -> new Favorite(fromMember, it))
                .collect(Collectors.toList());

        favorites.removeIf(removeCandidates::contains);
    }
}
