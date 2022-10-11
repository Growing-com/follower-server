package com.sarangchurch.follower.common.types;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class OffsetBasedPageRequest implements Pageable, Serializable {

    private static final long serialVersionUID = -25822477129613575L;
    private static final int LIMIT = 10;

    private final int offset;

    public static boolean popIfHasNext(List<?> content, Pageable pageable) {
        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }
        return hasNext;
    }

    public OffsetBasedPageRequest(int offset) {
        this.offset = offset;
    }

    @Override
    public boolean isUnpaged() {
        return Pageable.super.isUnpaged();
    }

    @Override
    public int getPageNumber() {
        return offset / LIMIT;
    }

    @Override
    public int getPageSize() {
        return LIMIT;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return Sort.unsorted();
    }

    @Override
    public Sort getSortOr(Sort sort) {
        return Pageable.super.getSortOr(sort);
    }

    @Override
    public Pageable next() {
        return new OffsetBasedPageRequest((int) (getOffset() + getPageSize()));
    }

    public OffsetBasedPageRequest previous() {
        return hasPrevious()
                ? new OffsetBasedPageRequest((int) (getOffset() - getPageSize()))
                : this;
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new OffsetBasedPageRequest(0);
    }

    @Override
    public Pageable withPage(int pageNumber) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasPrevious() {
        return offset > LIMIT;
    }

    @Override
    public Optional<Pageable> toOptional() {
        return Pageable.super.toOptional();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OffsetBasedPageRequest that = (OffsetBasedPageRequest) o;
        return getOffset() == that.getOffset();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOffset());
    }

    @Override
    public String toString() {
        return "OffsetBasedPageRequest{" +
                "offset=" + offset +
                '}';
    }
}
