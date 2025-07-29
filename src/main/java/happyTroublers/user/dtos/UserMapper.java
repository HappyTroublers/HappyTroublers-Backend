//package happyTroublers.user.dtos;
//
//import happyTroublers.destination.dtos.DestinationMapper;
//import happyTroublers.destination.dtos.DestinationResponse;
//import happyTroublers.user.CustomUser;
//
//import java.util.List;
//
//public class UserMapper {
//    public static CustomUser dtoToEntity(UserRequest dto) {
//        return new CustomUser(
//                dto.username(),
//                dto.email(),
//                dto.password()
//        );
//    }
//
//    public static UserResponse entityToDto (CustomUser user) {
//        List<DestinationResponse> destinationsList = user.getDestinations().stream().map(destination -> DestinationMapper.entityToDto(destination)).toList();
//        return new UserResponse(
//                user.getUsername(),
//                destinationsList
//        );
//    }
//}
//
