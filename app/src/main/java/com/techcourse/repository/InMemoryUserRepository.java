package com.techcourse.repository;

import com.techcourse.domain.User;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepository {

    private static final Map<String, User> database = new ConcurrentHashMap<>();

    static {
        final var user = new User(1, "gugu", "password", "hkkang@woowahan.com");
        database.put(user.getAccount(), user);
    }

    public InMemoryUserRepository() {
    }

    @Override
    public void save(final User user) {
        database.put(user.getAccount(), user);
    }

    @Override
    public Optional<User> findByAccount(final String account) {
        return Optional.ofNullable(database.get(account));
    }
}
