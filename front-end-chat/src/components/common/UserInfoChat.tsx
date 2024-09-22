import { useChatRoom } from '../../context/ChatRoomContext';

const UserInfoChat = () => {
  const { selectedChatRoom } = useChatRoom();
  return (
    <>
      <div className="user">
        {selectedChatRoom && (
          <>
            <img src={selectedChatRoom.avatar || './avatar.png'} alt="" />
            <div className="content">
              <span>{selectedChatRoom.userName}</span>
            </div>
          </>
        )}
      </div>
    </>
  );
};

export default UserInfoChat;
