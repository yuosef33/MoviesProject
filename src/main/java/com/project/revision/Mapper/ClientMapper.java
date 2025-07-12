package com.project.revision.Mapper;

import com.project.revision.Dto.ClientDto;
import com.project.revision.model.Auth;
import com.project.revision.model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientMapper {


        // Entity → DTO
        public static ClientDto toDto(Client client) {
            if (client == null) return null;
            List<Auth> auths=new ArrayList<>();
            ClientDto dto = new ClientDto();
            dto.setId(client.getId());
            dto.setUser_name(client.getUserName());
            dto.setUser_email(client.getUserEmail());
            dto.setUser_password(client.getUserPassword());
            dto.setUser_phoneNumber(client.getUserPhoneNumber());
            dto.setUserPhoto(client.getUserPhoto());
         for(Auth auth: client.getAuths()){
             auth.setUsers(null);
             auths.add(auth);
         }
            dto.setAuths(auths);

            return dto;
        }

        // DTO → Entity
        public static Client toEntity(ClientDto dto) {
            if (dto == null) return null;

            Client client = new Client();
            client.setId(dto.getId());
            client.setUserName(dto.getUser_name());
            client.setUserEmail(dto.getUser_email());
            client.setUserPassword(dto.getUser_password());
            client.setUserPhoneNumber(dto.getUser_phoneNumber());
            client.setAuths(dto.getAuths());
            client.setUserPhoto(dto.getUserPhoto());

            return client;
        }

        // List<User> → List<UserDto>
        public static List<ClientDto> toDtoList(List<Client> clients) {
            if (clients == null) return null;

            return clients.stream()
                    .map(ClientMapper::toDto)
                    .collect(Collectors.toList());
        }

        // List<UserDto> → List<User>
        public static List<Client> toEntityList(List<ClientDto> dtos) {
            if (dtos == null) return null;

            return dtos.stream()
                    .map(ClientMapper::toEntity)
                    .collect(Collectors.toList());
        }
    }

