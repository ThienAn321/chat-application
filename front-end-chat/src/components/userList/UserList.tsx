import UserItem from './UserItem';
import { useChatRoom } from '../../context/ChatRoomContext';

const UserList = () => {
  const { chatRooms, selectedChatRoom, selectChatRoom } = useChatRoom();

  return (
    <>
      {chatRooms.map((chatRoom) => (
        <UserItem
          key={chatRoom.chatRoomId}
          chatRoom={chatRoom}
          selectedChatRoom={selectedChatRoom}
          selectChatRoom={selectChatRoom}
        />
      ))}
    </>
  );
};

export default UserList;
