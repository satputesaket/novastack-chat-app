package com.novastack.chat.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.novastack.chat.entities.Room;

public interface RoomRepository extends MongoRepository<Room,String>{

	Room findByRoomId(String roomId);
}
