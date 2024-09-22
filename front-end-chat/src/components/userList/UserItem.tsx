import React from 'react';
import { ChatRoomResponse } from '../../models/chatroom/chatroom';
import { useChatRoom } from '../../context/ChatRoomContext';
import { formatDay } from '../../utils/formatDay';

interface IUserItemProps {
  chatRoom: ChatRoomResponse;
  selectedChatRoom: ChatRoomResponse | null;
  selectChatRoom: (chatRoomId: number) => void;
}

const UserItem: React.FC<IUserItemProps> = ({
  chatRoom,
  selectedChatRoom,
  selectChatRoom,
}) => {
  const isActive = selectedChatRoom?.chatRoomId === chatRoom.chatRoomId;
  const { currentUserId } = useChatRoom();

  const formatMessageContent = (
    messageContent: string,
    isImage: boolean
  ): string => {
    if (isImage) {
      return 'Just sent an image';
    }
    if (messageContent?.length > 20) {
      return `${messageContent.substring(0, 10)}...`;
    }
    return messageContent;
  };

  const displayContent =
    chatRoom.messageContent && chatRoom.senderId === currentUserId
      ? `You: ${formatMessageContent(
          chatRoom.messageContent,
          chatRoom.isImage
        )}`
      : formatMessageContent(chatRoom.messageContent, chatRoom.isImage);

  return (
    <div
      className={`user-item ${isActive && 'active'}`}
      onClick={() => selectChatRoom(chatRoom.chatRoomId)}
    >
      <img src={chatRoom.avatar || './avatar.png'} alt="" />
      <div className="content">
        <span>{chatRoom.userName}</span>
        <div className="message-list">
          <p>{displayContent}</p>
          {chatRoom.createAt && <p>&bull; {formatDay(chatRoom.createAt)}</p>}
        </div>
      </div>
    </div>
  );
};

export default UserItem;
