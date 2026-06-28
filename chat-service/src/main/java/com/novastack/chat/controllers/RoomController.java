package com.novastack.chat.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.novastack.chat.entities.Room;
import com.novastack.chat.repositories.RoomRepository;
import com.novastack.chat.entities.Message;


@RestController
@RequestMapping("/api/v1/room")
public class RoomController {
	
	private RoomRepository roomRepository;
	
	
	public RoomController(RoomRepository repository) {
		this.roomRepository=repository;
	}
	
	//create room
	@PostMapping
	public ResponseEntity<?> createRoom(@RequestBody String roomId) {
		
		if(roomRepository.findByRoomId(roomId)!=null) {
			return ResponseEntity.badRequest().body("Room already Exists.");
		}
		
		Room room = new Room();
		room.setRoomId(roomId);
		Room savedRoom = roomRepository.save(room);
		return ResponseEntity.status(HttpStatus.CREATED).body(room);
	}
	
	//get room :join
	
	@GetMapping("/{roomId}")
	public ResponseEntity<?> joinRoom(@PathVariable String roomId) {
		
		Room room = roomRepository.findByRoomId(roomId);
		
		if(room == null)
			return ResponseEntity.badRequest().body("Room doesn't exists.");
		
		
		return ResponseEntity.ok(room);
	}
	
	//get messages of room
	
	
	
	@GetMapping("/{roomId}/messages")
	public ResponseEntity<List<Message>> getMessages(@PathVariable String roomId,
			@RequestParam(value="page",defaultValue="0",required=false)int page,
			@RequestParam(value="size",defaultValue="0",required=false)int size){
	
		Room room = roomRepository.findByRoomId(roomId);
		if(room == null) {
			return ResponseEntity.badRequest().build();
		}
		
		List<Message> messages = room.getMessages();
		int start = Math.max(0, messages.size()-(page +1)*size);
		int end  = Math.min(messages.size(), start + size);
		
		
		List<Message> paginatedMessages = messages.subList(start, end);
		
		return ResponseEntity.ok(paginatedMessages);
	}
	


}
