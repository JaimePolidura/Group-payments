package es.grouppayments.backend.rooms._shared.domain;

import java.util.Optional;
import java.util.UUID;

public interface RoomRepository {
    void save(Room room);

    Optional<Room> findById(UUID roomId);

    Optional<Room> findByUsernameHost(UUID userId);

    void deleteById(UUID roomId);
}
