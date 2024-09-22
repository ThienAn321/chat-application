import React, { createContext, useEffect, useState, useContext } from 'react';
import { ChatRoomResponse } from '../models/chatroom/chatroom';
import chatRoomApi from '../api/chatroom/chatRoomApi';
import messageApi from '../api/message/messageApi';
import { MessageResponse } from '../models/message/message';
import { CompatClient } from '@stomp/stompjs';
import { connectWebSocket } from '../utils/webSocketClient';

interface ChatRoomContextProps {
  chatRooms: ChatRoomResponse[];
  messages: MessageResponse[];
  searchQuery: string;
  setSearchQuery: (query: string) => void;
  selectedChatRoom: ChatRoomResponse | null;
  selectChatRoom: (chatRoomId: number) => void;
  fetchChatRoom: () => Promise<void>;
  stompClient: CompatClient | null;
  currentUserId: number | null;
  setCurrentUserId: React.Dispatch<React.SetStateAction<number | null>>;
}

const ChatRoomContext = createContext<ChatRoomContextProps | undefined>(
  undefined
);

const ChatRoomProvider: React.FC<{ children: React.ReactNode }> = ({
  children,
}) => {
  const [chatRooms, setChatRooms] = useState<ChatRoomResponse[]>([]);
  const [messages, setMessages] = useState<MessageResponse[]>([]);
  const [selectedChatRoom, setSelectedChatRoom] = useState<ChatRoomResponse | null>(null);
  const [searchQuery, setSearchQuery] = useState<string>('');
  const [stompClient, setStompClient] = useState<CompatClient | null>(null);
  const [currentUserId, setCurrentUserId] = useState<number | null>(null);

  const searchedChatRooms =
    searchQuery.length > 0
      ? chatRooms.filter((chatRoom) =>
          `${chatRoom.userName}`
            .toLowerCase()
            .includes(searchQuery.toLowerCase())
        )
      : chatRooms;

  const fetchChatRoom = async (): Promise<void> => {
    const { ok, body } = await chatRoomApi.fetchChatRoom();
    if (ok && body) {
      setChatRooms(body);
    }
  };

  const fetchMessagesByChatRoomId = async (
    chatRoomId: number
  ): Promise<void> => {
    const { ok, body } = await messageApi.fetchMessagesByChatRoomId(chatRoomId);
    if (ok && body) {
      setMessages(body);
    }
  };

  const handleMessageReceived = (message: MessageResponse) => {
    setMessages((prevMessages) => [...prevMessages, message]);
    fetchChatRoom();
  };

  const connectToWebSocket = (): void => {
    if (currentUserId) {
      const client = connectWebSocket(
        currentUserId,
        handleMessageReceived,
        fetchChatRoom
      );
      setStompClient(client);
    }
  };

  useEffect(() => {
    connectToWebSocket();
    fetchChatRoom();
  }, [currentUserId]);

  const selectChatRoom = (chatRoomId: number): void => {
    const chatRoom =
      chatRooms.find((room) => room.chatRoomId === chatRoomId) || null;
    setSelectedChatRoom(chatRoom);
    fetchMessagesByChatRoomId(chatRoomId);
  };

  return (
    <ChatRoomContext.Provider
      value={{
        chatRooms: searchedChatRooms,
        messages,
        searchQuery,
        setSearchQuery,
        selectedChatRoom,
        selectChatRoom,
        fetchChatRoom,
        stompClient,
        currentUserId,
        setCurrentUserId,
      }}
    >
      {children}
    </ChatRoomContext.Provider>
  );
};

const useChatRoom = (): ChatRoomContextProps => {
  const context = useContext(ChatRoomContext);
  if (!context) {
    throw new Error('useChat must be used within a ChatProvider');
  }
  return context;
};

export { ChatRoomProvider, useChatRoom };
