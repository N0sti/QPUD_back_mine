package com.takima.backskeleton.DTO;

import com.takima.backskeleton.models.Room;

public class RoomMapper {

    public static RoomDto toDto(Room room) {
        return new RoomDto(room.getId(), room.getName(), room.getCapacity());
    }

    public static Room fromDto(RoomDto roomDto) {
        return new Room(roomDto.getName(), roomDto.getCapacity());
    }
}
