import { HttpResponse } from "../../models/http";
import { MessageResponse } from "../../models/message/message";
import { axiosClient, handleRequest } from "../axiosClient";
import config from '../config/config.json';

const messageApi = {
  fetchMessagesByChatRoomId: (chatRoomId: number): Promise<HttpResponse<MessageResponse[]>> => {
    const url = config.apiBaseUrl.private + `/messages`;
    return handleRequest(axiosClient.get(url, { params: { chatRoomId } }));
  },
}

export default messageApi;