import { ChatRoomRequest, ChatRoomResponse } from '../../models/chatroom/chatroom';
import { HttpResponse } from '../../models/http';
import { axiosClient, handleRequest } from '../axiosClient';
import config from '../config/config.json';

const chatRoomApi = {
  fetchChatRoom: (): Promise<HttpResponse<ChatRoomResponse[]>> => {
    const url = config.apiBaseUrl.private + `/chatrooms`;
    return handleRequest(axiosClient.get(url));
  },
  createChatRoom: (body: ChatRoomRequest): Promise<HttpResponse<void>> => {
    const url = config.apiBaseUrl.private + `/chatrooms`;
    return handleRequest(axiosClient.post(url, body));
  },
};

export default chatRoomApi;
