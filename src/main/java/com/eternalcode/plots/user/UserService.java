package com.eternalcode.plots.user;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class UserService {

    private final Map<UUID, User> usersByUUID = new HashMap<>();
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findOrCreate(UUID uuid) {
        return this.usersByUUID.computeIfAbsent(uuid, k -> new User(uuid));
    }

    public Optional<User> find(UUID uuid) {
        return Optional.ofNullable(this.usersByUUID.get(uuid));
    }

    public Collection<User> users() {
        return Collections.unmodifiableCollection(this.usersByUUID.values());
    }

}
