package com.eternalcode.plots.user;

import panda.std.reactive.Completable;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface UserRepository {

    Completable<List<User>> getUsers();

    CompletableFuture<User> getUser(UUID uuid);

    void saveUser(User user);

}
