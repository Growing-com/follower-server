package com.sarangchurch.follower.department.domain.model;

import com.sarangchurch.follower.common.types.marker.ValueObject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.Objects;

@ValueObject
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DepartmentLinks {
    private URI youtubeLink;
    private URI instagramLink;
    private URI churchLink;

    public DepartmentLinks(URI youtubeLink, URI instagramLink, URI churchLink) {
        this.youtubeLink = youtubeLink;
        this.instagramLink = instagramLink;
        this.churchLink = churchLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentLinks that = (DepartmentLinks) o;
        return Objects.equals(youtubeLink, that.youtubeLink) && Objects.equals(instagramLink, that.instagramLink) && Objects.equals(churchLink, that.churchLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(youtubeLink, instagramLink, churchLink);
    }
}
