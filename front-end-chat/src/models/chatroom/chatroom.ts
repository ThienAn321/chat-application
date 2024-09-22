export interface ChatRoomResponse {
  chatRoomId: number;
  userId: number;
  userName: string;
  avatar: string;
  senderId: number;
  messageContent: string;
  isImage: boolean;
  createAt: string;
}

export interface ChatRoomRequest {
  userId: number;
}
