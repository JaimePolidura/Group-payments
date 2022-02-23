package es.grouppayments.backend.rooms._shared.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RoomService {
    private final RoomRepository rooms;

    public void save(String title, double money, UUID adminUserId){
        rooms.save(new Room(UUID.randomUUID(), title, LocalDateTime.now(), money, RoomStatus.CREATED, adminUserId));
    }

    public Optional<Room> findById(UUID roomId){
        return this.rooms.findById(roomId);
    }

    public Optional<Room> findByUsernameHost(UUID userId){
        return this.rooms.findByUsernameHost(userId);
    }

    public void deleteById(UUID roomId){
        this.rooms.deleteById(roomId);
    }
}
