package com.eternalcode.plots.member;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MemberRepositoryInMemory implements MemberRepository {

    private final Map<UUID, Set<Member>> membersByPlotUUID = new HashMap<>();

    @Override
    public CompletableFuture<Void> saveMember(Member member) {
        Set<Member> indexedMembers = this.membersByPlotUUID.computeIfAbsent(member.plotId(), key -> new HashSet<>());
        indexedMembers.add(member);

        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> removeMember(Member member) {
        Set<Member> indexedMembers = this.membersByPlotUUID.computeIfAbsent(member.plotId(), key -> new HashSet<>());
        indexedMembers.remove(member);

        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Boolean> isMember(UUID plotId, UUID userId) {
        Set<Member> indexedMembers = this.membersByPlotUUID.computeIfAbsent(plotId, key -> new HashSet<>());
        
        return CompletableFuture.completedFuture(indexedMembers.stream().anyMatch(member -> member.userId().equals(userId)));
    }
}
