package es.grouppayments.backend.rooms._shared.infrastructure;

import es.grouppayments.backend.rooms._shared.domain.Room;
import es.grouppayments.backend.rooms._shared.domain.RoomRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public final class RoomRepositoryInMemory implements RoomRepository {
    private final Set<Room> rooms;

    public RoomRepositoryInMemory(){
        this.rooms = new HashSet<>();
    }

    @Override
    public void save(Room room) {
        this.save(room);
    }

    @Override
    public Optional<Room> findById(UUID roomId) {
        return this.rooms.stream()
                .filter(room -> room.getRoomId().equals(room))
                .findFirst();
    }

    @Override
    public Optional<Room> findByUsernameHost(UUID userId) {
        return this.rooms.stream()
                .filter(room -> room.getRoomId().equals(room))
                .findFirst();
    }

    @Override
    public void deleteById(UUID roomId) {
        this.rooms.removeIf(room -> room.getRoomId().equals(roomId));
    }
}
